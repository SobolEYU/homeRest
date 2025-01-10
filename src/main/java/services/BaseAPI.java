package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseAPI {

    protected static final String BASE_URL = "https://petstore.swagger.io/v2";

    protected RequestSpecification baseSpec;

    public BaseAPI() {
        baseSpec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }
}
