# Technical Assessment - Backend Developer Java

Senior-level Spring Boot backend assessment demonstrating Spring IoC, Java Stream API, advanced native SQL, microservices, Docker, Kafka stream processing, Redis cache-aside strategy, and Elasticsearch search.

## Architecture

```text
Client
  |
  +--> API Service :8080
  |       |
  |       +--> PostgreSQL :5433
  |       |       |
  |       |       +--> JPA/Hibernate
  |       |       +--> Native SQL Analytics
  |       |
  |       +--> Redis :6379
  |       |
  |       +--> Kafka :9092
  |               |
  |               +--> Stream Service :8081
  |                       |
  |                       +--> Elasticsearch :9200
  |
  +--> JSP View
  ```

## Modules

- `api-service`: REST API, JSP, JPA/Hibernate, native SQL, Redis and Kafka producer.
- `stream-service`: Kafka consumer/stream processing and Elasticsearch indexing/search.

## Senior requirements

| Requirement | Implementation |
|---|---|
| Spring IoC | Constructor injection with Spring-managed services, repositories and components |
| Java Stream | Filtering, mapping, grouping, sorting and aggregation in service processing |
| Advanced Native SQL | CTE, joins, aggregation, correlated filtering and window functions |
| Containerization | Dockerfiles and Docker Compose |
| Microservices | Independent API and stream services |
| Kafka | Domain event publication and consumption |
| Stream Based Application | Kafka consumer performs transformation and indexing |
| Redis | Cache-aside for frequently read product data |
| Caching Strategy | Read cache, database fallback, cache write and explicit invalidation |
| Data Grid | Redis-backed shared cache across API instances |
| Elasticsearch | Search-oriented index for product events |
| Non-Relational DB | Elasticsearch for denormalized search data |

## Run

Start PostgreSQL:

```cmd
docker compose -f docker/postgres/docker-compose.yml up -d

```


Build and start application services:

```
mvn clean package
 docker compose up --build
```

## Postman Collection

```text
Technical Assessment.postman_collection.json
```

## Endpoint

API: `http://localhost:8080`

Health: `http://localhost:8080/api/products/health`

Search: `GET /api/search/products?q=coffee`

Analytics: `GET /api/products/analytics`

JSP: `http://localhost:8080/products`

## Database

PostgreSQL initialization is located under `docker/postgres/init.sql`.

The application uses JPA/Hibernate for normal persistence and explicit native SQL for the analytics use case.


## Author

Created and Developed by Ayub Krislastono

Technical Assessment — Backend Developer Java

12 August 2026
