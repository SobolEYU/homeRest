package services;

import static io.restassured.RestAssured.given;

import dto.PetsDTO;
import io.restassured.response.ValidatableResponse;

public class PetsApi extends BaseAPI {

    private static final String PET_PATH = "/pet";

    public ValidatableResponse addPet(PetsDTO petsDTO) {
        return given(baseSpec)
                .basePath(PET_PATH)
                .body(petsDTO)
        .when()
                .post()
        .then()
                .statusCode(200)
                .log().all();
    }

    public ValidatableResponse getPetById(Long id) {
        return given(baseSpec)
        .when()
                .get(PET_PATH + "/" + id.toString())
        .then()
                .log().all();
    }

    public ValidatableResponse updatePet(PetsDTO petsDTO) {
        return given(baseSpec)
                .basePath(PET_PATH)
                .body(petsDTO)
        .when()
                .put()
        .then()
                .statusCode(200)
                .log().all();
    }

    public ValidatableResponse deletePetById(Long id) {
        return given(baseSpec)
        .when()
                .delete(PET_PATH + "/" + id.toString())
        .then()
                .log().all();
    }
}
