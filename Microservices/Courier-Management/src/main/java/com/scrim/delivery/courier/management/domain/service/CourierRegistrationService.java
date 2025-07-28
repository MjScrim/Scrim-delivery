package com.scrim.delivery.courier.management.domain.service;

import com.scrim.delivery.courier.management.api.assembler.CourierAssembler;
import com.scrim.delivery.courier.management.api.model.CourierModel;
import com.scrim.delivery.courier.management.api.model.input.CourierInput;
import com.scrim.delivery.courier.management.domain.exception.DomainException;
import com.scrim.delivery.courier.management.domain.model.Courier;
import com.scrim.delivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class CourierRegistrationService {

  private final CourierRepository courierRepository;
  private final CourierAssembler courierAssembler;

  public CourierModel create(CourierInput input) {
    Courier courier = Courier.brandNew(input.getName(), input.getPhone());

    return courierAssembler.toModel(courierRepository.saveAndFlush(courier));
  }

  public CourierModel update(UUID courierId, CourierInput input) {
    Courier courier = courierRepository.findById(courierId)
      .orElseThrow(DomainException::new);

    courier.setName(input.getName());
    courier.setPhone(input.getPhone());

    return courierAssembler.toModel(courierRepository.saveAndFlush(courier));
  }

}
