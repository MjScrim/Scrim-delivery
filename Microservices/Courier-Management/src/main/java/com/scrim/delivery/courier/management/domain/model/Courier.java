package com.scrim.delivery.courier.management.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.UUID;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
//Identificador único de Entity como UUID.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Courier {

  @Id
  @EqualsAndHashCode.Include
  private UUID id;

  @Setter(AccessLevel.PUBLIC)
  private String name;

  @Setter(AccessLevel.PUBLIC)
  private String phone;

  private Integer fulfilledDeliveryQuantity;
  private Integer pendingDeliveryQuantity;

  private OffsetDateTime lastFulfilledDeliveryAt;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "courier")
  private List<AssignedDelivery> pendingDeliverys = new ArrayList<>();

  //Mantendo a list acessivel somente pelo AggregoreRoot
  public List<AssignedDelivery> getPendingDeliverys() {
    return Collections.unmodifiableList(pendingDeliverys);
  }

  //Constructor para Courier.
  public static Courier brandNew(String name, String phone) {
    Courier courier = new Courier();

    courier.setId(UUID.randomUUID());
    courier.setName(name);
    courier.setPhone(phone);
    courier.setPendingDeliveryQuantity(0);
    courier.setFulfilledDeliveryQuantity(0);

    return courier;
  }

  //Garantindo a modificação de acesso dos ValueObjects somenete via classe Root.
  public void assign(UUID deliveryId) {
    this.pendingDeliverys.add(
      AssignedDelivery.pending(deliveryId, this)
    );

    this.setPendingDeliveryQuantity(
      this.getPendingDeliveryQuantity() + 1
    );
  }

  public void fulfill(UUID deliveryId) {
    AssignedDelivery delivery = this.pendingDeliverys
      .stream()
      .filter(
        d -> d.getId().equals(deliveryId)
      )
      .findFirst()
      .orElseThrow();

    this.pendingDeliverys.remove(delivery);

    this.setPendingDeliveryQuantity(
      this.getPendingDeliveryQuantity() - 1
    );

    this.setFulfilledDeliveryQuantity(
      this.getFulfilledDeliveryQuantity() + 1
    );

    this.setLastFulfilledDeliveryAt(OffsetDateTime.now());
  }

}
