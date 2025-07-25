package com.scrim.delivery.delivery.tracking.domain.model;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(AccessLevel.PACKAGE)
@Getter
public class Item {

  @EqualsAndHashCode.Include
  private UUID id;

  private String name;
  private Integer quantity;

  //Factory para criar um novo item expondo sua intenção e predefinindo seus campos. Removendo do resto da aplicação esta necessidade.
  static Item brandNew(String name, Integer quantity) {
    Item item = new Item();
    item.setId(UUID.randomUUID());
    item.setName(name);
    item.setQuantity(quantity);

    return item;
  }

}
