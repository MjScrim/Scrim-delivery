package com.scrim.delivery.courier.management.api.controller;

import com.scrim.delivery.courier.management.api.model.CourierModel;
import com.scrim.delivery.courier.management.api.model.CourierPayoutResultModel;
import com.scrim.delivery.courier.management.api.model.input.CourierInput;
import com.scrim.delivery.courier.management.api.model.input.CourierPayoutCalculationInput;
import com.scrim.delivery.courier.management.domain.service.CourierPayoutService;
import com.scrim.delivery.courier.management.domain.service.CourierQueryService;
import com.scrim.delivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierController {

  private final CourierRegistrationService courierRegistrationService;
  private final CourierQueryService courierQueryService;
  private final CourierPayoutService courierPayoutService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CourierModel create(@Valid @RequestBody CourierInput input) {
    return courierRegistrationService.create(input);
  }

  @PutMapping("/{courierId}")
  public CourierModel update(@PathVariable UUID courierId, @Valid @RequestBody CourierInput input) {
    return courierRegistrationService.update(courierId, input);
  }

  @GetMapping
  public Page<CourierModel> findAll(@PageableDefault Pageable pageable) {
    return courierQueryService.findAll(pageable);
  }

  @GetMapping("/{courierId}")
  public CourierModel findById(@PathVariable UUID courierId) {
    return courierQueryService.findById(courierId);
  }

  @PostMapping("/payout-calculation")
  public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input) {
    BigDecimal payoutFee = courierPayoutService.calculate(input.getDistanceInKm());

    return new CourierPayoutResultModel(payoutFee);
  }

}
