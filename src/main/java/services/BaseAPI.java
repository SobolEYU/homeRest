package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseAPI {

    protected static final String BASE_URL = System.getProperty("base.url");

    protected RequestSpecification baseSpec;

    public BaseAPI() {
        baseSpec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }
}
