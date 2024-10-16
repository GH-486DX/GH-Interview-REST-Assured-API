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
    @DisplayName("Error code validation.")
    public void InvalidMethod() {
        when().
                post("/people/1").
                then().
                statusCode(405);
    }
}
