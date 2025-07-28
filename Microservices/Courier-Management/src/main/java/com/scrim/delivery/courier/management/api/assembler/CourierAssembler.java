package com.scrim.delivery.courier.management.api.assembler;

import com.scrim.delivery.courier.management.api.model.CourierModel;
import com.scrim.delivery.courier.management.domain.model.Courier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CourierAssembler {

  private final ModelMapper modelMapper;

  public CourierModel toModel(Courier courier) {
    return modelMapper.map(courier, CourierModel.class);
  }

  public Page<CourierModel> toPageModel(Page<Courier> courierPage) {
    return courierPage.map(this::toModel);
  }

}
