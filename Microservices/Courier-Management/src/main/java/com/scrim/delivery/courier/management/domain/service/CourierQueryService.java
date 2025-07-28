package com.scrim.delivery.courier.management.domain.service;

import com.scrim.delivery.courier.management.api.assembler.CourierAssembler;
import com.scrim.delivery.courier.management.api.model.CourierModel;
import com.scrim.delivery.courier.management.domain.exception.DomainException;
import com.scrim.delivery.courier.management.domain.model.Courier;
import com.scrim.delivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class CourierQueryService {

  private final CourierRepository courierRepository;
  private final CourierAssembler courierAssembler;

  public Page<CourierModel> findAll(Pageable pageable) {
    Page<Courier> courierPage = courierRepository.findAll(pageable);

    return courierAssembler.toPageModel(courierPage);
  }

  public CourierModel findById(UUID courierId) {
    Courier courier = courierRepository.findById(courierId)
      .orElseThrow(DomainException::new);

    return courierAssembler.toModel(courier);
  }

}
