package com.scrim.delivery.delivery.tracking.infrastructure.event.interfaces;

public interface IntegrationEventPublisher {

  void publish(Object event, String key, String topic);

}
