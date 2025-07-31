package com.scrim.delivery.delivery.tracking.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryPickUpEvent {

  private final OffsetDateTime currentAt;
  private final UUID deliveryId;

}
