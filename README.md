# Transactions REST API

The purpose of the project is to create a REST API that handles transactions. A transaction is formed of a double amount and a long timestamp .Transactions that are longer than 60 seconds will return a 204 response while a valid transaction will return a 201 response. The API can also be called to get the statistics of the past 60 seconds. 

## Getting Started

To get the project running:
1. Checkout the project from GitHub : https://github.com/rana-abdelmeguid/Transactions-N26-Rest-API
2. Install JDK 1.8.
3. Run maven install to add the maven dependencies.
4. Run the Application.java class as a java project.

### Prerequisites

JAVA JDK 1.8

## Running the tests

The two main blocks for testing are the TransactionsController.java and TransactionsService.java. The TransactionsController follows the Sping MVC testing methodolgy.

To run the test and show class coverage , go to the desired class , right click then click on Coverage AS -> Junit Test

## Built With

* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Rana Abdel Meguid** - [rana-abdelmeguid](https://github.com/rana-abdelmeguid)


