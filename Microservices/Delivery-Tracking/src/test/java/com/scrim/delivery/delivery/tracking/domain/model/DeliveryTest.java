package com.scrim.delivery.delivery.tracking.domain.model;

import com.scrim.delivery.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

  @Test
  //Verificando a criação e permissão de mudança de status de Delivery.
  public void shoudChangeToPlaced() {
    Delivery delivery = Delivery.draft();

    delivery.editPreparationDetails(createdValidPreparationDetails());

    delivery.place();

    assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
    assertNotNull(delivery.getPlacedAt());
  }

  @Test
  //Verificando a negação de permissão de mudança de status do Delivery.
  public void shoudNotChangeToPlace() {
    Delivery delivery = Delivery.draft();

    assertThrows(DomainException.class, delivery::place);

    assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
    assertNull(delivery.getPlacedAt());
  }

  //Prepara Delivery Tester.
  private Delivery.PreparationDetails createdValidPreparationDetails() {
    ContactPoint sender = ContactPoint.builder()
      .zipCode("00000-00")
      .street("Rua Java")
      .number("100")
      .complement("Casa Roxa")
      .name("Marcos Andrade")
      .phone("(00) 00000-0000")
      .build();

    ContactPoint recipient = ContactPoint.builder()
      .zipCode("11111-00")
      .street("Rua Java 21")
      .number("200")
      .complement("Casa Vermelha")
      .name("Marcos Com Fome")
      .phone("(00) 00000-0000")
      .build();

    return Delivery.PreparationDetails.builder()
      .sender(sender)
      .recipient(recipient)
      .distanceFee(new BigDecimal("10.00"))
      .courierPayout(new BigDecimal("5.00"))
      .expectedDeliveryTime(Duration.ofHours(1))
      .build();
  }

}