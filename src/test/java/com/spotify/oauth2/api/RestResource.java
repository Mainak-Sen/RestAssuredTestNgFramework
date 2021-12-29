package com.spotify.oauth2.api;

import com.spotify.oauth2.pojos.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static com.spotify.oauth2.api.Routes.API;
import static com.spotify.oauth2.api.Routes.TOKEN;
import static io.restassured.RestAssured.given;

/*This class is to create reusable methods to accommodate the various http methods that can be used across
all api's as the application scales*/

public class RestResource {

    public static Response post(String token, String path, Object payload) {

        return given(SpecBuilder.getRequestSpecification())
                .body(payload)
                .auth().oauth2(token)
                .when()
                .post(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract().response();
    }

    public static Response postAccount(Map<String, String> formParams) {

        return given(SpecBuilder.getAccountRequestSpecification())
                .formParams(formParams)
                .when()
                .post(API + TOKEN)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract().response();
    }

    public static Response get(String path, String token) {
        return given(SpecBuilder.getRequestSpecification())
                .auth().oauth2(token)
                .when()
                .get(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract().response();
    }

    public static Response update(Object payload, String path, String token) {
        return given(SpecBuilder.getRequestSpecification())
                .auth().oauth2(token)
                .body(payload)
                .when()
                .put(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract().response();
    }
}
