package com.scrim.delivery.delivery.tracking.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInput {

  @NotNull
  @Valid
  private ContactPointInput sender;

  @NotNull
  @Valid
  private ContactPointInput recipient;

  @NotEmpty
  @Valid
  @Size(min = 1)
  private List<ItemInput> items;

}
