package com.scrim.delivery.courier.management.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CourierPayoutService {

  public BigDecimal calculate(Double distanceInKm) {
    return new BigDecimal("10")
      .multiply(new BigDecimal(distanceInKm))
      .setScale(2, RoundingMode.HALF_DOWN);
  }

}
