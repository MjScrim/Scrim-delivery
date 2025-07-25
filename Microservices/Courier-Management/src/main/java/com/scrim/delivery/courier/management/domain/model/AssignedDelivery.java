package com.scrim.delivery.courier.management.domain.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
//Identificador único para a Entity como UUID.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssignedDelivery {

  @EqualsAndHashCode.Include
  private UUID id;

  private OffsetDateTime assignedAt;

  //Contructor criado mas só acesso pelo Root.
  static AssignedDelivery pending(UUID deliveryId) {
    AssignedDelivery delivery = new AssignedDelivery();

    delivery.setId(deliveryId);
    delivery.setAssignedAt(OffsetDateTime.now());

    return delivery;
  }

}
