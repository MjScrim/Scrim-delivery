package com.scrim.delivery.courier.management.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//Identificador único para a Entity como UUID.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssignedDelivery {

  @Id
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne
  @Getter(AccessLevel.PRIVATE)
  private Courier courier;

  private OffsetDateTime assignedAt;

  //Contructor criado mas só acesso pelo Root.
  static AssignedDelivery pending(UUID deliveryId, Courier courier) {
    AssignedDelivery delivery = new AssignedDelivery();

    delivery.setId(deliveryId);
    delivery.setAssignedAt(OffsetDateTime.now());
    delivery.setCourier(courier);

    return delivery;
  }

}
