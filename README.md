# Diplom_2

Java 11 + Selenium tests for web app https://stellarburgers.nomoreparties.site.

The main goal of the project is checking registration and authorization. Also added some tests for navigation through the app.

Planned to add tests for drag-n-drop in constructor.

## API Documentation
https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf.

## Maven Dependencies
JUnit 4, Rest Assured, Allure Rest Assured, Allure JUnit, GSON, Lombok, JavaFaker.

## Plugins
AntRun Plugin, Surefire, Allure, AspectJ weaver.

## Cross browser testing
Use pom.xml to change browser (just type "chrome" or "yandex" into properties->browser).
Then run `mvn clean test allure:serve` from project directory.