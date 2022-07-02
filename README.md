# v1: listing-contact-report
Maven is used as a build automation tool. Below commands could be used to clean/install and run the application using the command line:
mvn clean install
mvn spring-boot:run

Spring Batch is used for processing input data (CSV files) to the Database, in this project in-memory Database (H2) is used. While the application is running, the below link could be used to reach DB (no need password):
http://localhost:8080/h2-console/

Implemented Swagger UI, could be reached from the below link:
http://localhost:8080/swagger-ui.html#

Spring Boot Cache is used for caching the requests that fetch data from the DB.
For mapping models to DTOs mapstruct and custom mapper classes are used.
SLF4J Logger is used for logging purposes.
Mock is used for testing purposes.

All configurations could be found in the resources: application.properties