package com.scrim.delivery.delivery.tracking.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryStatusTest {

  @Test
  //Test da mudança de um Status para o próximo.
  void draft_canChangeToWaitingForCourier() {
    assertTrue(
      DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.WAITING_FOR_COURIER)
    );
  }

  @Test
  //Test de mudança de um Status DIFERENTE do próximo.
  void draft_canNotChangeToWaitingForCourier() {
    assertFalse(
      DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.IN_TRANSIT)
    );
  }

}