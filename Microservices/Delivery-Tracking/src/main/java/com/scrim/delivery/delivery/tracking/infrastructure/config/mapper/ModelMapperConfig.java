package com.scrim.delivery.delivery.tracking.infrastructure.config.mapper;

import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();

    modelMapper.createTypeMap(Delivery.class, DeliveryModel.class)
      .addMappings(mapper -> mapper.map(Delivery::getItems, DeliveryModel::setItems));

    return modelMapper;
  }

}
