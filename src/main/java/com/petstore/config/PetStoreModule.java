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

  private static final String BASE_URL = "https://petstore.swagger.io/v2";

  @Override
  protected void configure() {

  }

  @Provides
  @Singleton
  public RequestSpecification provideRequestSpecification() {
    return new RequestSpecBuilder()
        .setBaseUri(BASE_URL)
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
