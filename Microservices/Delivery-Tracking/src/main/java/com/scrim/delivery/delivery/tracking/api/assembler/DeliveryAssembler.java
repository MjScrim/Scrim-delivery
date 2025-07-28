package com.scrim.delivery.delivery.tracking.api.assembler;

import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryAssembler {

  private final ModelMapper modelMapper;

  public Delivery toEntity(DeliveryModel delivery) {
    return modelMapper.map(delivery, Delivery.class);
  }

  public DeliveryModel toModel(Delivery delivery) {
    return modelMapper.map(delivery, DeliveryModel.class);
  }

  public Page<DeliveryModel> toPageModel(Page<Delivery> deliveries) {
    return deliveries.map(this::toModel);
  }

}
