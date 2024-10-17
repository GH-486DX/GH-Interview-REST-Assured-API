package com.gh486dx;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
    public void ValidateLukeExists() {
        assertTrue(
                given().
                        get("/people/1").
                        getBody().
                        asString().
                        contains("Luke Skywalker"),
                "Luke not found!");
    }

    @Test
    @DisplayName("Match body response from OPTION REST method.")
    public void ValidatePeopleEndpoint() {
        options("/people").
                then().
                body("name", equalTo("People List"));
    }

    @Test
    @DisplayName("Assert response header includes expected value.")
    public void ValidateResponseHeader() {
        assertTrue(
                given().
                        get("/people/3").
                        getHeaders().
                        toString().
                        contains("application/json"),
                "Application/json not found in response headers!");
    }

    @Test
    @DisplayName("Find (Groovy) matching key/value, then iterate (Hamcrest) match against specified key/values.")
    public void ValidateSomeTemperatePlanets() {
        when().
                get("/planets").
                then().
                body("results.findAll { it.climate == 'temperate' }.name",
                        hasItems("Alderaan", "Bespin", "Endor"));
    }

    @Test
    @DisplayName("Check response conforms to the expected JSON Schema.")
    public void ValidateStarshipSchemaConformation() {
        get("/starships/2").
                then().
                assertThat().
                body(matchesJsonSchemaInClasspath("starships-schema.json"));
    }

    @Test
    @DisplayName("Extract value to use in follow-up request.")
    public void ValidateFirstStarDestroyerFilmAppearance() {
        String filmLinks =
                when().
                        get("starships/3").
                        then().
                        contentType(JSON).
                        body("name", equalTo("Star Destroyer")).
                        extract().
                        path("films").toString();

        String firstFilmLink =
                filmLinks.substring(filmLinks.indexOf("[") + 1, filmLinks.indexOf(","));

        assertTrue(
                get(firstFilmLink).getBody().
                        asString().
                        contains("A New Hope"),
                "A New Hope not found!");
    }

    @Test
    @DisplayName("Loop through endpoint values and validate value format using regex.")
    public void ValidateFilmsReleaseDatesFormat() {
        for (int i = 1; i < 6; i++) {
            String releaseDate =
                    when().
                            get("films/" + i).
                            then().
                            contentType(JSON).
                            extract().
                            path("release_date").toString();

            assertThat(releaseDate, matchesPattern("\\d{4}-\\d{2}-\\d{2}"));
        }
    }

    @Test
    @DisplayName("Assert result length based on query parameter.")
    public void ValidateHowManySkyWalkersWithSearch() {
        expect().
                body("results", hasSize(3)).
                when().
                get("/people/?search=skywalker");
    }

}
