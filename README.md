# Payment API 
This is a demo project to show how Spring Boot can be employed to implement microservice based payment API.<br>
This project currently included two microservices: <br>
- payment-api: which is the core application to implement payment functionality. More information is provided in <a href="payment-api/README.md">payment-api module</a>.
- config-server: loads and provides configuration data to microservices. config-server uses a git repository as a source of truth. More information is here <a href="config-server/README.md">payment-api module</a>.<br>