package com.scrim.delivery.delivery.tracking.infrastructure.http.config;

import com.scrim.delivery.delivery.tracking.infrastructure.http.client.CourierAPIClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierAPIClientConfig {

  @Bean
  public CourierAPIClient courierAPIClient(RestClient.Builder builder) {
    RestClient restClient = builder.baseUrl("http://host.docker.internal:8081").build();

    RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

    HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

    return proxyFactory.createClient(CourierAPIClient.class);
  }

}
