package com.scrim.delivery.courier.management.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierInput {

  @NotBlank
  private String name;

  @NotBlank
  private String phone;

}
