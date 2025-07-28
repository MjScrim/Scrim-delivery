package com.scrim.delivery.delivery.tracking.infrastructure.http.service;

import com.scrim.delivery.delivery.tracking.domain.service.interfaces.CourierPayoutCalculatationService;
import com.scrim.delivery.delivery.tracking.infrastructure.http.client.CourierAPIClient;
import com.scrim.delivery.delivery.tracking.infrastructure.http.model.CourierPayoutResultModel;
import com.scrim.delivery.delivery.tracking.infrastructure.http.model.input.CourierPayoutCalculationInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculatationServiceHttpImpl implements CourierPayoutCalculatationService {

  private final CourierAPIClient courierAPIClient;

  @Override
  public BigDecimal calculatePayout(Double distanceInKm) {
    var courierPayoutResultModel = courierAPIClient
      .payoutCalculation(
      new CourierPayoutCalculationInput(distanceInKm)
    );

    return courierPayoutResultModel.getPayoutFee();
  }

}
