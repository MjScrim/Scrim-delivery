package com.scrim.delivery.delivery.tracking.domain.service;

import com.scrim.delivery.delivery.tracking.api.model.input.ContactPointInput;
import com.scrim.delivery.delivery.tracking.api.model.input.DeliveryInput;
import com.scrim.delivery.delivery.tracking.api.model.input.ItemInput;
import com.scrim.delivery.delivery.tracking.domain.exception.DomainException;
import com.scrim.delivery.delivery.tracking.domain.model.ContactPoint;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import com.scrim.delivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

  private final DeliveryRepository deliveryRepository;

  @Transactional
  public Delivery draft(DeliveryInput input) {
    Delivery delivery = Delivery.draft();

    handlePreparation(input, delivery);

    return deliveryRepository.saveAndFlush(delivery);
  }

  @Transactional
  public Delivery edit(UUID deliveryId, DeliveryInput input) {
    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);

    delivery.removeItems();
    handlePreparation(input, delivery);

    return deliveryRepository.saveAndFlush(delivery);
  }

  private void handlePreparation(DeliveryInput input, Delivery delivery) {
    ContactPointInput senderInput = input.getSender();
    ContactPointInput recipientInput = input.getRecipient();

    ContactPoint sender = ContactPoint.builder()
      .phone(senderInput.getPhone())
      .name(senderInput.getName())
      .complement(senderInput.getComplement())
      .number(senderInput.getNumber())
      .zipCode(senderInput.getZipCode())
      .street(senderInput.getStreet())
      .build();

    ContactPoint recipient = ContactPoint.builder()
      .phone(recipientInput.getPhone())
      .name(recipientInput.getName())
      .complement(recipientInput.getComplement())
      .number(recipientInput.getNumber())
      .zipCode(recipientInput.getZipCode())
      .street(recipientInput.getStreet())
      .build();

    Duration expectedDeliveryTime = Duration.ofHours(1);

    //Valores fixos até então.
    BigDecimal courierPayout = new BigDecimal("10");
    BigDecimal distanceFee = new BigDecimal("10");

    var preparationDetails = Delivery.PreparationDetails.builder()
      .sender(sender)
      .recipient(recipient)
      .expectedDeliveryTime(expectedDeliveryTime)
      .courierPayout(courierPayout)
      .distanceFee(distanceFee)
      .build();

    delivery.editPreparationDetails(preparationDetails);

    for (ItemInput item : input.getItems()) {
      //Apenas o agregado pode instanciar.
      delivery.addItem(item.getName(), item.getQuantity());
    }
  }

}
