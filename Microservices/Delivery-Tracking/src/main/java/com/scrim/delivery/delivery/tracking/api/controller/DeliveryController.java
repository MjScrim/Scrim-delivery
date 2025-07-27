package com.scrim.delivery.delivery.tracking.api.controller;

import com.scrim.delivery.delivery.tracking.api.assembler.DeliveryAssembler;
import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.api.model.input.DeliveryInput;
import com.scrim.delivery.delivery.tracking.domain.model.Delivery;
import com.scrim.delivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.scrim.delivery.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

  private final DeliveryPreparationService deliveryPreparationService;
  private final DeliveryRepository deliveryRepository;
  private final DeliveryAssembler deliveryAssembler;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DeliveryModel draft(@RequestBody @Valid DeliveryInput input) {
    return deliveryAssembler.toModel(deliveryPreparationService.draft(input));
  }

  @PutMapping("/{deliveryId}")
  @ResponseStatus(HttpStatus.FOUND)
  public DeliveryModel edit(@PathVariable UUID deliveryId,
                       @RequestBody @Valid DeliveryInput input) {
    return deliveryAssembler.toModel(deliveryPreparationService.edit(deliveryId, input));
  }

  @GetMapping
  public Page<DeliveryModel> findAll(@PageableDefault Pageable pageable) {
    Page<Delivery> deliveryPage = deliveryRepository.findAll(pageable);
    return deliveryAssembler.toPageModel(deliveryPage);
  }

  @GetMapping("/{deliveryId}")
  public DeliveryModel findById(@PathVariable UUID deliveryId) {
    return deliveryRepository.findById(deliveryId)
      .map(deliveryAssembler::toModel)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

}
