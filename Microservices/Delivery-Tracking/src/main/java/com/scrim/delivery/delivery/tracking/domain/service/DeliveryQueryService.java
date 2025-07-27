package com.scrim.delivery.delivery.tracking.domain.service;

import com.scrim.delivery.delivery.tracking.api.assembler.DeliveryAssembler;
import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import com.scrim.delivery.delivery.tracking.domain.repository.DeliveryRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryQueryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryAssembler deliveryAssembler;

  @Transactional
  public Page<DeliveryModel> findAll(Pageable pageable) {
    Page<Delivery> deliveryPage = deliveryRepository.findAll(pageable);
    return deliveryAssembler.toPageModel(deliveryPage);
  }

  @Transactional
  public DeliveryModel findById(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId)
      .map(deliveryAssembler::toModel)
      .orElseThrow(EntityExistsException::new);
  }

}
