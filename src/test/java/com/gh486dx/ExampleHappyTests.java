package com.gh486dx;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class ExampleHappyTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://swapi.dev";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("String includes assertion (JUnit), with defined error message.")
    public void ValidateLuke() {
        assertTrue(
                given().
                        get("/people/1").
                        getBody().
                        asString().
                        contains("Luke Skywalker"),
                "Luke not found!");
    }

    @Test
    @DisplayName("Find (Groovy) matching key/value, then iterate (Hamcrest) match against specified key/values.")
    public void ValidateTemperatePlanets() {
        when().
                get("/planets").
                then().
                body("results.findAll { it.climate == 'temperate' }.name",
                        hasItems("Alderaan", "Bespin", "Naboo", "Coruscant", "Coruscant"));
    }
}
