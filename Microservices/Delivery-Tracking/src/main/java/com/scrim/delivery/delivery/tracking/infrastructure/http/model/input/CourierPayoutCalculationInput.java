package com.scrim.delivery.delivery.tracking.infrastructure.http.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutCalculationInput {

  private Double distanceInKm;

}
