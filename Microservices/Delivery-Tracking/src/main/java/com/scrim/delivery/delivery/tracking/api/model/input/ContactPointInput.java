package com.scrim.delivery.delivery.tracking.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPointInput {

  @NotBlank
  private String zipCode;

  @NotBlank
  private String street;

  @NotBlank
  private String number;

  private String complement;

  @NotBlank
  private String name;

  @NotBlank
  private String phone;

}
