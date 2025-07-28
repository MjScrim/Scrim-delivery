package com.scrim.delivery.delivery.tracking.domain.service.interfaces;

import java.math.BigDecimal;

public interface CourierPayoutCalculatationService {

  BigDecimal calculatePayout(Double DistanceInKm);

}
