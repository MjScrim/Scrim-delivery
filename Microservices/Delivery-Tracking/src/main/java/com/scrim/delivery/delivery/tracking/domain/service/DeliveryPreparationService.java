package com.scrim.delivery.delivery.tracking.domain.service;

import com.scrim.delivery.delivery.tracking.api.assembler.DeliveryAssembler;
import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.api.model.input.ContactPointInput;
import com.scrim.delivery.delivery.tracking.api.model.input.DeliveryInput;
import com.scrim.delivery.delivery.tracking.api.model.input.ItemInput;
import com.scrim.delivery.delivery.tracking.domain.exception.DomainException;
import com.scrim.delivery.delivery.tracking.domain.model.ContactPoint;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import com.scrim.delivery.delivery.tracking.domain.model.DeliveryEstimate;
import com.scrim.delivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.scrim.delivery.delivery.tracking.domain.service.interfaces.CourierPayoutCalculatationService;
import com.scrim.delivery.delivery.tracking.infrastructure.fake.DeliveryTimeEstimationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryAssembler deliveryAssembler;
  private final DeliveryTimeEstimationServiceImpl deliveryTimeEstimationService;
  private final CourierPayoutCalculatationService courierPayoutCalculatationService;

  @Transactional
  public DeliveryModel draft(DeliveryInput input) {
    Delivery delivery = Delivery.draft();

    handlePreparation(input, delivery);

    return deliveryAssembler.toModel(deliveryRepository.saveAndFlush(delivery));
  }

  @Transactional
  public DeliveryModel edit(UUID deliveryId, DeliveryInput input) {
    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DomainException::new);

    delivery.removeItems();
    handlePreparation(input, delivery);

    return deliveryAssembler.toModel(deliveryRepository.saveAndFlush(delivery));
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

    DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
    BigDecimal calculatePayout = courierPayoutCalculatationService.calculatePayout(estimate.getDistanceInKm());
    BigDecimal distanceFee = calculateFee(estimate.getDistanceInKm());

    var preparationDetails = Delivery.PreparationDetails.builder()
      .sender(sender)
      .recipient(recipient)
      .expectedDeliveryTime(estimate.getEstimatedTime())
      .courierPayout(calculatePayout)
      .distanceFee(distanceFee)
      .build();

    delivery.editPreparationDetails(preparationDetails);

    for (ItemInput item : input.getItems()) {
      //Apenas o agregado pode instanciar.
      delivery.addItem(item.getName(), item.getQuantity());
    }
  }

  private BigDecimal calculateFee(Double distanceInKm) {
    return new BigDecimal("3")
      .multiply(new BigDecimal(distanceInKm))
      .setScale(2, RoundingMode.HALF_DOWN);
  }

}
