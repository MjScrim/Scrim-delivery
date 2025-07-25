package com.scrim.delivery.delivery.tracking.domain.model;

import java.util.Arrays;
import java.util.List;

public enum DeliveryStatus {

  //Garantindo que só passar para o próximo Status, caso tenha o Staus Anterior. Ex: Para WAITING_FOR_COURIER o DeliveryStatus deve ter DRAFT.
  DRAFT,
  WAITING_FOR_COURIER(DRAFT),
  IN_TRANSIT(WAITING_FOR_COURIER),
  DELIVERED(IN_TRANSIT);

  private final List<DeliveryStatus> previousStatuses;

  //Constructor criado para passar um Array de Delivery Status, podendo interar por quantos forem.
  DeliveryStatus(DeliveryStatus... previousStatuses) {
    this.previousStatuses = Arrays.asList(previousStatuses);
  }

  public boolean canNotChangeTo(DeliveryStatus newStatus) {
    //"this" já carrega o previousStatuses no constructor.
    DeliveryStatus current = this;
    return !newStatus.previousStatuses.contains(current);
  }

  public boolean canChangeTo(DeliveryStatus newStatus) {
    return !canNotChangeTo(newStatus);
  }
}
