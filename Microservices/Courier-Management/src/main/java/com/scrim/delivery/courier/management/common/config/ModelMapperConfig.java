package com.scrim.delivery.courier.management.common.config;

import com.scrim.delivery.courier.management.api.model.CourierModel;
import com.scrim.delivery.courier.management.domain.model.Courier;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();

    modelMapper.createTypeMap(Courier.class, CourierModel.class)
      .addMappings(mapper -> mapper.map(Courier::getPendingDeliverys, CourierModel::setPendingDeliverys));

    return modelMapper;
  }

}
