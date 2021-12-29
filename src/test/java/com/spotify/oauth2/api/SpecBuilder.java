package com.spotify.oauth2.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static com.spotify.oauth2.api.Routes.BASE_PATH;

public class SpecBuilder {


    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(System.getProperty("BASE_URI"))
                //setBaseUri("https://api.spotify.com")
                .addFilter(new AllureRestAssured())
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL).build();
    }

    public static RequestSpecification getAccountRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(System.getProperty("ACCOUNT_BASE_URI"))
                //setBaseUri("https://accounts.spotify.com")
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.URLENC)
                .log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }


}
