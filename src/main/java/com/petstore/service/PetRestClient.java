package com.petstore.service;

import com.google.inject.Inject;
import com.petstore.dto.Pet;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class PetRestClient {

    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    @Inject
    public PetRestClient(RequestSpecification requestSpec,
        ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }

    public ValidatableResponse createPet(Pet pet) {
        return given()
            .spec(requestSpec)
            .body(pet)
            .when()
            .post("/pet")
            .then()
            .spec(responseSpec);
    }

    public ValidatableResponse getPetById(long petId) {
        return given()
            .spec(requestSpec)
            .pathParam("petId", petId)
            .when()
            .get("/pet/{petId}")
            .then()
            .spec(responseSpec);
    }

    public ValidatableResponse deletePet(long petId) {
        return given()
            .spec(requestSpec)
            .pathParam("petId", petId)
            .when()
            .delete("/pet/{petId}")
            .then()
            .spec(responseSpec);
    }
}
