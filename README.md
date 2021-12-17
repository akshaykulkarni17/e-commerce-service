# e-commerce-service

This is a project built using spring webflux consisting 3 REST microservices communicating with each other 

1. User service - Stores User data and transactions in R2DBC.
2. Product service - Stores product data in ManogoDB.
3. Order service - Integrates above two services to pplace an order and stores the data using spring data JPA.
