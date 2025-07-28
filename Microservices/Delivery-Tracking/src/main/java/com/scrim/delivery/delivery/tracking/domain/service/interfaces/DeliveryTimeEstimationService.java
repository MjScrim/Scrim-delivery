package com.scrim.delivery.delivery.tracking.domain.service.interfaces;

import com.scrim.delivery.delivery.tracking.domain.model.ContactPoint;
import com.scrim.delivery.delivery.tracking.domain.model.DeliveryEstimate;

public interface DeliveryTimeEstimationService {

  DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient);

}
