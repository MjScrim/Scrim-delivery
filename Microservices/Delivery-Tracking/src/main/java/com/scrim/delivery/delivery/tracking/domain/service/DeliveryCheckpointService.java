package com.scrim.delivery.delivery.tracking.domain.service;

import com.scrim.delivery.delivery.tracking.domain.exception.DomainException;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import com.scrim.delivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

  //private final DeliveryAssembler deliveryAssembler;
  private final DeliveryRepository deliveryRepository;

  public void place(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
      .orElseThrow(DomainException::new);

    delivery.place();

    deliveryRepository.saveAndFlush(delivery);
  }

  public void pickUp(UUID deliveryId, UUID courierId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
      .orElseThrow(DomainException::new);

    delivery.pickUp(courierId);

    deliveryRepository.saveAndFlush(delivery);
  }

  public void complete(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
      .orElseThrow(DomainException::new);

    delivery.markAsDelivery();

    deliveryRepository.saveAndFlush(delivery);
  }

}
