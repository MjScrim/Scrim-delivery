package com.scrim.delivery.delivery.tracking.infrastructure.fake;

import com.scrim.delivery.delivery.tracking.domain.model.ContactPoint;
import com.scrim.delivery.delivery.tracking.domain.model.DeliveryEstimate;
import com.scrim.delivery.delivery.tracking.domain.service.interfaces.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DeliveryTimeEstimationServiceImpl implements DeliveryTimeEstimationService {

  @Override
  public DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient) {
    return new DeliveryEstimate(
      Duration.ofHours(1),
      3.0
    );
  }
}
