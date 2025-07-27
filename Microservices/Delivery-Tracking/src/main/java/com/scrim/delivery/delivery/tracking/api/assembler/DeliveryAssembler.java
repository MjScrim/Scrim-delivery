package com.scrim.delivery.delivery.tracking.api.assembler;

import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.api.model.input.DeliveryInput;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DeliveryAssembler {

  private final ModelMapper modelMapper;

  public Delivery toEntity(DeliveryInput input) {
    return modelMapper.map(input, Delivery.class);
  }

  public DeliveryModel toModel(Delivery delivery) {
    return modelMapper.map(delivery, DeliveryModel.class);
  }

  public List<DeliveryModel> toCollectionModel(List<Delivery> deliveries) {
    return deliveries.stream()
      .map(this::toModel)
      .collect(Collectors.toList());
  }

  public Page<DeliveryModel> toPageModel(Page<Delivery> deliveries) {
    return deliveries.map(this::toModel);
  }

}
