# Technical Assessment - Backend Developer Java

# Overview

Backend application developed using Java and Spring Boot to demonstrate enterprise backend development capabilities, covering application architecture, database processing, asynchronous event streaming, caching, containerization, and non-relational data search.

The application is designed with a modular and service-oriented architecture and exposes functionality through REST APIs.

# Technology Stack

- Java
- Spring Boot
- Spring IoC / Dependency Injection
- Spring Data JPA
- Hibernate ORM
- REST API
- JSP
- PostgreSQL
- Native SQL Query
- Java Stream API
- Apache Kafka
- Redis
- Elasticsearch
- Docker
- Maven

## Senior-Level Requirements

This project implements the following technical requirements:

| Requirement | Implementation |
|---|---|
| Spring IoC | Dependency Injection using Spring Beans, Services, Repositories, and Components |
| Java Stream | Collection processing, filtering, mapping, grouping, sorting, and aggregation |
| Advanced Native SQL Query | Complex SQL involving JOIN, aggregation, filtering, subquery, pagination, and analytical data processing |
| Containerization | Application services are containerized using Docker |
| Microservices | Application components are separated into independent backend services |
| Kafka | Event-driven communication and asynchronous processing |
| Stream Based Application | Kafka event streams are consumed and processed by backend services |
| Redis | Caching and temporary data storage |
| Caching Strategy | Cache-aside strategy for frequently accessed data |
| Data Grid | Redis-based distributed data access |
| Elasticsearch | Full-text search and indexing of application data |
| Non-Relational Database | Elasticsearch is used for search-oriented data storage and retrieval |

# Architecture

The application follows a service-oriented backend architecture.

```text
                        Client
                          |
                          v
                    REST API Layer
                          |
                          v
                 +-------------------+
                 |   API Service     |
                 +-------------------+
                    |      |      |
                    |      |      |
                    v      v      v
                 PostgreSQL Redis Kafka
                    |             |
                    |             v
                    |       Event Consumer
                    |             |
                    |             v
                    |       Processing Service
                    |             |
                    |             v
                    |       Elasticsearch
                    |
                    v
              JPA / Hibernate
