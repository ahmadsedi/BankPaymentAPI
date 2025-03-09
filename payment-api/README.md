# Simple Payment API with Spring Boot
This is a simple payment API implementation for a bank. An imaginary one, of course!
The aim of this project is demonstrating how RESTful APIs can be implemented in Java/Spring Boot stack. 
## Features
These are the APIs implemented:
- Create account
- Find account by ID
- Transfer money from one account to the other
- Find the transaction history of an account
## Technologies
This project includes a couple of APIs, developed by Java/Spring boot stack. It uses an in-memory database in the database layer, 
so you don't need to worry about installing a database to run it.   
The APIs in this project are employing asynchronous reactive Spring WebFlux which is great! 
It also provides OpenAPI swagger UI to facilitate manual testing. 
 
## Deployment Requirements
Java 11 and Maven installed. 

## How to run

~~~
mvn spring-boot:run
~~~

## Business Requirements (Acceptance Criteria)
<br><br>
Given valid account details and positive funds available<br>
When account-id 111 sends £10 to account-id 222<br>
Then account-111’s account should be debited with £10<br>
And account-222’s account should be credited with £10<br>
<br>
<br>
Given invalid receiver account details and positive funds available<br>
When account-id 111 sends £10 to account-id 999<br>
Then system should reject the transfer and report invalid account<br>
<br>
<br>
Given valid account details and no funds available (£0)<br>
When account-id 111 sends £10 to account-id 222<br>
Then system should reject the transfer with error Insufficient<br>
funds available<br>
<br>
<br>
Given valid account details<br>
When I call a service to check my account balance<br>
Then system should be able to report my current balance<br>
<br>
<br>
Given valid account details<br>
When I call mini-statement service<br>
Then system should be able to show me last 20 transactions<br>
<br>
<br>
Given invalid account details<br>
When I call a service to check my account balance<br>
Then system should return error saying invalid account number<br>
<br>
<br>
Given invalid account details<br>
When I call mini statement service<br>
Then system should return error saying invalid account number<br>