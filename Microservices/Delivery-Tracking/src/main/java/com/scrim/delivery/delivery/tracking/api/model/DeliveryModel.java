package com.scrim.delivery.delivery.tracking.api.model;

import com.scrim.delivery.delivery.tracking.domain.model.ContactPoint;
import com.scrim.delivery.delivery.tracking.domain.model.DeliveryStatus;
import com.scrim.delivery.delivery.tracking.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryModel {

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

  private ContactPoint sender;

  private ContactPoint recipient;

  private List<Item> items = new ArrayList<>();

}
