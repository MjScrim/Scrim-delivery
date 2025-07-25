package com.scrim.delivery.delivery.tracking.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(AccessLevel.PACKAGE)
@Getter
public class Item {

  @Id
  @EqualsAndHashCode.Include
  private UUID id;

  private String name;
  private Integer quantity;

  @ManyToOne(optional = false)
  //Privando para tirar o acesso da entity fora do Root.
  @Getter(AccessLevel.PRIVATE)
  private Delivery delivery;

  //Factory para criar um novo item expondo sua intenção e predefinindo seus campos. Removendo do resto da aplicação esta necessidade.
  static Item brandNew(String name, Integer quantity, Delivery delivery) {
    Item item = new Item();
    item.setId(UUID.randomUUID());
    item.setName(name);
    item.setQuantity(quantity);
    item.setDelivery(delivery);

    return item;
  }

}
