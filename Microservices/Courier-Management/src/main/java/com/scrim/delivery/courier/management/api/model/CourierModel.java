package com.scrim.delivery.courier.management.api.model;

import com.scrim.delivery.courier.management.domain.model.AssignedDelivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierModel {

  private UUID id;

  private String name;
  private String phone;

  private Integer fulfilledDeliveryQuantity;
  private Integer pendingDeliveryQuantity;

  private OffsetDateTime lastFulfilledDeliveryAt;

  private List<AssignedDelivery> pendingDeliverys = new ArrayList<>();

}
