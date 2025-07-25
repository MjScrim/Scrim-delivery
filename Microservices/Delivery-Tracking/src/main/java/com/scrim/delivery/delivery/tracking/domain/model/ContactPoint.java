package com.scrim.delivery.delivery.tracking.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

//Para persistência com o Jakarta.
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@EqualsAndHashCode
@Getter
public class ContactPoint {

  private String zipCode;
  private String street;
  private String number;
  private String complement;
  private String name;
  private String phone;

}
