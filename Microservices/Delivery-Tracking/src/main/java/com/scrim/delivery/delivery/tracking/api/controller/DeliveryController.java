package com.scrim.delivery.delivery.tracking.api.controller;

import com.scrim.delivery.delivery.tracking.api.model.DeliveryModel;
import com.scrim.delivery.delivery.tracking.api.model.input.CourierInput;
import com.scrim.delivery.delivery.tracking.api.model.input.DeliveryInput;
import com.scrim.delivery.delivery.tracking.domain.service.DeliveryCheckpointService;
import com.scrim.delivery.delivery.tracking.domain.service.DeliveryPreparationService;
import com.scrim.delivery.delivery.tracking.domain.service.DeliveryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

  private final DeliveryPreparationService deliveryPreparationService;
  private final DeliveryQueryService deliveryQueryService;
  private final DeliveryCheckpointService deliveryCheckpointService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DeliveryModel draft(@RequestBody @Valid DeliveryInput input) {
    return deliveryPreparationService.draft(input);
  }

  @PutMapping("/{deliveryId}")
  @ResponseStatus(HttpStatus.FOUND)
  public DeliveryModel edit(@PathVariable UUID deliveryId,
                       @RequestBody @Valid DeliveryInput input) {
    return deliveryPreparationService.edit(deliveryId, input);
  }

  @GetMapping
  public Page<DeliveryModel> findAll(@PageableDefault Pageable pageable) {
    return deliveryQueryService.findAll(pageable);
  }

  @GetMapping("/{deliveryId}")
  public DeliveryModel findById(@PathVariable UUID deliveryId) {
    return deliveryQueryService.findById(deliveryId);
  }

  @PostMapping("/{deliveryId}/placement")
  public void place(@PathVariable UUID deliveryId) {
    deliveryCheckpointService.place(deliveryId);
  }

  @PostMapping("/{deliveryId}/pickups")
  public void pickUp(@PathVariable UUID deliveryId,
                     @Valid @RequestBody CourierInput input) {
    deliveryCheckpointService.pickUp(deliveryId, input.getCourierId());
  }

  @PostMapping("/{deliveryId}/completion")
  public void complete(@PathVariable UUID deliveryId) {
    deliveryCheckpointService.complete(deliveryId);
  }

}
