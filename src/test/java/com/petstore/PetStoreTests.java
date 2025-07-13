package com.petstore;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.google.inject.Inject;
import com.petstore.dto.Category;
import com.petstore.dto.Pet;
import com.petstore.dto.Tag;
import com.petstore.extensions.ApiExtensions;
import com.petstore.service.PetRestClient;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ApiExtensions.class)
public class PetStoreTests {

  @Inject
  private PetRestClient petRestClient;

  @Test
  @DisplayName("Создание питомца (валидные данные)")
  void testCreatePetValid() {
    Pet pet = new Pet();
    pet.setId(123456L);
    pet.setName("Barsik");
    pet.setStatus("available");
    pet.setCategory(new Category(1L, "Cats"));
    pet.setTags(Collections.singletonList(new Tag(10L, "domestic")));
    pet.setPhotoUrls(Collections.singletonList("http://example.com/photo.png"));

    petRestClient.createPet(pet)
        .body("id", equalTo(123456))
        .body("name", equalTo("Barsik"))
        .body("status", equalTo("available"));
  }

  @Test
  @DisplayName("GET питомца по несуществующему ID")
  void testGetPetNotFound() {
    long nonExistentId = 99999999L;
    petRestClient.getPetById(nonExistentId)
        .statusCode(404)
        .body("message", containsString("Pet not found"));
  }
}
