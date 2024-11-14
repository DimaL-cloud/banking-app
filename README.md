# Banking App
App for managing bank accounts and performing transactions

## Stack
[![My Skills](https://skillicons.dev/icons?i=java,spring,postgresql)](https://skillicons.dev)

## Features
- [x] Can be scaled horizontally
- [x] Swagger documentation
- [x] Test coverage reporting
- [x] Validation

## How to improve
To work well in high load, it is better to divide the application into microservices.
For example:
- Account Microservice
- Transaction Microservice

Communication between microservices can be done using a message broker like Apache Kafka.
Also it is vital to implement database as a service pattern. But it adds complexity that we need to handle 
distributed transactions. We can use the SAGA or 2PC pattern to solve this problem.
In our case we should use 2PC pattern because it is vital to have a consistent state of the data because it is banking app.

Furthermore, we can add caching and pagination to the application to improve performance.

## Setting up :rocket:
1. Clone the repository:
```
git clone https://github.com/BibusUkraine/media-insights-app.git
```
2. Up services:
```
docker-compose up -d
```