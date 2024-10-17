package com.gh486dx;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

public class ExampleUnhappyTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://swapi.dev";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("404 error code validation.")
    public void NotFound() {
        when().
                get("/films/99").
                then().
                statusCode(404);
    }

    @Test
    @DisplayName("405 error code validation.")
    public void InvalidMethodPOST() {
        when().
                post("/people/1").
                then().
                statusCode(405);
    }

}
