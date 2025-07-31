package com.scrim.delivery.delivery.tracking.infrastructure.event;

import com.scrim.delivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.scrim.delivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.scrim.delivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.scrim.delivery.delivery.tracking.infrastructure.event.interfaces.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.scrim.delivery.delivery.tracking.infrastructure.kafka.KafkaTopicConfig.deliveryEventsTopicName;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

  private final IntegrationEventPublisher integrationEventPublisher;

  @EventListener
  public void handle(DeliveryPlacedEvent event) {
    integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveryEventsTopicName);
  }

  @EventListener
  public void handle(DeliveryPickUpEvent event) {
    integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveryEventsTopicName);
  }

  @EventListener
  public void handle(DeliveryFulfilledEvent event) {
    integrationEventPublisher.publish(event, event.getDeliveryId().toString(), deliveryEventsTopicName);
  }


}
