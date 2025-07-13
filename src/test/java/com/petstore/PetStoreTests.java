package com.petstore;

import com.google.inject.Inject;
import com.petstore.dto.Category;
import com.petstore.dto.Pet;
import com.petstore.dto.Tag;
import com.petstore.extensions.ApiExtensions;
import com.petstore.service.PetRestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(ApiExtensions.class)
public class PetStoreTests {

  @Inject
  private PetRestClient petRestClient;

  @Test
  @DisplayName("Создание питомца → проверка GET → удаление")
  void testCreatePetEndToEnd() {
    // arrange
    Pet expected = new Pet();
    expected.setId(123456L);
    expected.setName("Barsik");
    expected.setStatus("available");
    expected.setCategory(new Category(1L, "Cats"));
    expected.setTags(Collections.singletonList(new Tag(10L, "domestic")));
    expected.setPhotoUrls(Collections.singletonList("http://example.com/photo.png"));

    // act 1 – POST
    petRestClient.createPet(expected)
        .statusCode(200);

    // act 2 – GET
    Pet actual = petRestClient.getPetById(expected.getId())
        .statusCode(200)
        .extract()
        .as(Pet.class);

    // assert
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    // cleanup
    petRestClient.deletePet(expected.getId())
        .statusCode(200);
  }

  @Test
  @DisplayName("GET питомца по несуществующему ID")
  void testGetPetNotFound() {
    long nonExistentId = 999_999_999L;
    petRestClient.getPetById(nonExistentId)
        .statusCode(404)
        .body("message", containsString("Pet not found"));
  }
}
