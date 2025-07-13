package com.petstore.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PetStoreModule extends AbstractModule {

  /** URL по‑умолчанию (production). */
  private static final String DEFAULT_BASE_URL = "https://petstore.swagger.io/v2";

  @Override
  protected void configure() {
    // явных биндов не требуется – всё @Provides
  }

  @Provides
  @Singleton
  public RequestSpecification provideRequestSpecification() {
    /* 1) -Dbase.url=…   2) $BASE_URL   3) дефолт */
    String baseUrl = System.getProperty(
        "base.url",
        System.getenv().getOrDefault("BASE_URL", DEFAULT_BASE_URL)
    );

    return new RequestSpecBuilder()
        .setBaseUri(baseUrl)
        .setContentType(ContentType.JSON)
        .build();
  }

  @Provides
  @Singleton
  public ResponseSpecification provideResponseSpecification() {
    return new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .build();
  }
}
