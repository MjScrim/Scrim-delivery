package com.scrim.delivery.delivery.tracking.domain.model;

import com.scrim.delivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.scrim.delivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.scrim.delivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.scrim.delivery.delivery.tracking.domain.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//Identificando o AgregateRoot via Id.
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//Apenas para nível privado, sem expor os campos.
@Setter(AccessLevel.PRIVATE)
@Getter
public class Delivery extends AbstractAggregateRoot<Delivery> {

  @Id
  @EqualsAndHashCode.Include
  private UUID id;

  private UUID courierId;

  private DeliveryStatus status;

  private OffsetDateTime placedAt;
  private OffsetDateTime assignedAt;
  private OffsetDateTime expectedDeliveryAt;
  private OffsetDateTime fullfilledAt;

  private BigDecimal distanceFee;
  private BigDecimal courierPayout;
  private BigDecimal totalCost;

  private Integer totalItems;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "zipCode", column = @Column(name = "sender_zip_code")),
    @AttributeOverride(name = "street", column = @Column(name = "sender_street")),
    @AttributeOverride(name = "number", column = @Column(name = "sender_number")),
    @AttributeOverride(name = "complement", column = @Column(name = "sender_complement")),
    @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
    @AttributeOverride(name = "phone", column = @Column(name = "sender_phone"))
  })
  private ContactPoint sender;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "zipCode", column = @Column(name = "recipient_zip_code")),
    @AttributeOverride(name = "street", column = @Column(name = "recipient_street")),
    @AttributeOverride(name = "number", column = @Column(name = "recipient_number")),
    @AttributeOverride(name = "complement", column = @Column(name = "recipient_complement")),
    @AttributeOverride(name = "name", column = @Column(name = "recipient_name")),
    @AttributeOverride(name = "phone", column = @Column(name = "recipient_phone"))
  })
  private ContactPoint recipient;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "delivery")
  private List<Item> items = new ArrayList<>();

  //Contrustor preparado para demonstrar a intenção e uso do Model. Com predefinição dos atributos. Seguindo o DDD.
  public static Delivery draft() {
    Delivery delivery = new Delivery();

    //Removendo a dependência do banco de criar os UUIDS.
    delivery.id = UUID.randomUUID();
    delivery.setStatus(DeliveryStatus.DRAFT);
    delivery.setTotalItems(0);
    delivery.setTotalCost(BigDecimal.ZERO);
    delivery.setCourierPayout(BigDecimal.ZERO);
    delivery.setDistanceFee(BigDecimal.ZERO);

    return delivery;
  }

  //Garantindo que somente AgregateRoot possa acessar e modificar a lista.
  public UUID addItem(String name, int quantity) {
    Item item = Item.brandNew(name, quantity, this);
    items.add(item);

    calculateTotalItems();

    return item.getId();
  }

  public void removeItem(UUID itemId) {
    items.removeIf(item -> item.getId().equals(itemId));
    calculateTotalItems();
  }

  public void removeItems() {
    items.clear();
    calculateTotalItems();
  }

  public void editPreparationDetails(PreparationDetails details) {
    verifyIfCanBeEdit();

    this.setSender(details.sender);
    this.setRecipient(details.recipient);
    this.setDistanceFee(details.distanceFee);
    this.setCourierPayout(details.courierPayout);

    this.setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDeliveryTime()));
    this.setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
  }

  public void place() {
    verifyIfCanBePlaced();

    this.setStatus(DeliveryStatus.WAITING_FOR_COURIER);
    this.setPlacedAt(OffsetDateTime.now());

    super.registerEvent(
      new DeliveryPlacedEvent(
        this.getPlacedAt(),
        this.getId()
      )
    );
  }

  public void pickUp(UUID courierId) {
    this.setCourierId(courierId);
    this.changeStatusTo(DeliveryStatus.IN_TRANSIT);
    this.setAssignedAt(OffsetDateTime.now());

    super.registerEvent(
      new DeliveryPickUpEvent(
        this.getAssignedAt(),
        this.getId()
      )
    );
  }

  public void markAsDelivery() {
    this.changeStatusTo(DeliveryStatus.DELIVERED);
    this.setFullfilledAt(OffsetDateTime.now());

    super.registerEvent(
      new DeliveryFulfilledEvent(
        this.getFullfilledAt(),
        this.getId()
      )
    );
  }

  //Removendo o acesso a item, retornando uma List não modificavel.
  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }

  public void chanceItemQuantity(UUID itemId, int quantity) {
    Item item = getItems().stream()
      .filter(i -> i.getId().equals(itemId))
      .findFirst().orElseThrow();

    item.setQuantity(quantity);
    calculateTotalItems();
  }

  private void calculateTotalItems() {
    int totalItems = getItems().stream()
      .mapToInt(Item::getQuantity)
      .sum();

    this.setTotalItems(totalItems);
  }

  private void verifyIfCanBePlaced() {
    if (!isFilled()) {
      throw new DomainException();
    }

    if (!getStatus().equals(DeliveryStatus.DRAFT)) {
      throw new DomainException();
    }
  }

  private void verifyIfCanBeEdit() {
    if (!getStatus().equals(DeliveryStatus.DRAFT)) {
      throw new DomainException();
    }
  }

  //Encapsulando
  private boolean isFilled() {
    return this.getSender() != null
      && this.getRecipient() != null
      && this.totalCost != null;
  }

  private void changeStatusTo(DeliveryStatus newStatus) {
    if (newStatus != null && this.getStatus().canNotChangeTo(newStatus)) {
      throw new DomainException(
        "Invalid Status transition from: " + this.getStatus() +
          " to " + newStatus
      );
    }

    this.setStatus(newStatus);
  }

  @Getter
  @Builder
  @AllArgsConstructor
  //InnerClass para preparação do pedido, já que não representa nada no Domain, não é preciso ser um DTO.
  public static class PreparationDetails {
    private ContactPoint sender;
    private ContactPoint recipient;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;

    private Duration expectedDeliveryTime;
  }
}