package com.scrim.delivery.courier.management.api.controller;

import com.scrim.delivery.courier.management.domain.model.Courier;
import com.scrim.delivery.courier.management.domain.repository.CourierRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourierControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private CourierRepository courierRepository;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
    RestAssured.basePath = "/api/v1/couriers";
  }

  @Test
  public void shouldReturn201() {
    String requestBody = """
      {
        "name": "User",
        "phone":  "11000000000"
      }
      """;

    RestAssured
      .given()
        .body(requestBody)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
      .when()
        .post()
      .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("id", Matchers.notNullValue())
        .body("name", Matchers.equalTo("User"));
  }

  @Test
  public void shouldBeReturn200() {
    UUID courierId = courierRepository.saveAndFlush(
      Courier.brandNew(
        "User",
        "11000000000"
      )
    ).getId();

    RestAssured
      .given()
        .pathParam("courierId", courierId)
        .accept(ContentType.JSON)
      .when()
        .get("/{courierId}")
      .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", Matchers.equalTo(courierId.toString()))
        .body("name", Matchers.equalTo("User"))
        .body("phone", Matchers.equalTo("11000000000"));
  }

}