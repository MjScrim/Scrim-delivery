package com.scrim.delivery.delivery.tracking.domain.repository;

import com.scrim.delivery.delivery.tracking.domain.model.ContactPoint;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

//Configuração manual via resources/application.yml.
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

  @Autowired
  private DeliveryRepository repository;

  //Teste de agregação de item para Delivery.
  @Test
  public void shouldPersist() {
    Delivery delivery = Delivery.draft();

    delivery.editPreparationDetails(createdValidPreparationDetails());

    delivery.addItem("baixo", 1);
    delivery.addItem("pedaleira", 1);

    repository.saveAndFlush(delivery);

    Delivery deliveryPersist = repository.findById(delivery.getId()).orElseThrow();

    assertEquals(2, deliveryPersist.getItems().size());
  }

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