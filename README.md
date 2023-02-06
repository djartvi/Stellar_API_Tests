# Stellar_API_Tests

Java 11 tests for web app https://stellarburgers.nomoreparties.site.

Contains test for registration, login, update users, create orders.

All test users and orders are removed after each run.

## API Documentation
https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf.

## Maven Dependencies
JUnit 4, Rest Assured, Allure Rest Assured, Allure JUnit, GSON, Lombok, JavaFaker.

## Plugins
AntRun Plugin, Surefire, Allure, AspectJ weaver.

## Generate Allure report
Run `mvn clean test allure:serve` from project directory.
