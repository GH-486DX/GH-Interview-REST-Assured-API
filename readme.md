## GH Interview REST Assured API example

https://github.com/GH-486DX/GH-Interview-REST-Assured-API

### Intro

Example of using Java, REST Assured, JUnit and Maven to show some basic tests against a free public API:

* [The Star Wars API](https://swapi.dev/)

The public API is limited:

* Does not allow methods other than GET.
* Requires no authentication.
* Doesn't always use best JSON practices in responses.

### Requirements & setup:

Below should be working on your machine:

* `java -version`
* `mvn -v`

### Running the tests:

1. Launch tests: `mvn test`