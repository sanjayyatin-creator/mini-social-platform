# Mini Social Platform

Simple Spring Boot project demonstrating custom cache implementations and cache-aside strategy.

## Features

- Custom LRU Cache
- Custom LFU Cache
- Feed API with cache-aside pattern
- Cache invalidation on new post creation
- H2 in-memory database

---

## Tech Stack

- Java
- Spring Boot
- Maven
- H2 Database

---

## APIs

### Create Post

POST `/post`

Sample Request:

```json
{
  "userId": 10,
  "content": "Hello from user 10"
}
```

---

### Get Feed

GET `/feed/{userId}`

Example:

```bash
GET /feed/10
```

---

## Cache Flow

### First Feed Request

- Cache miss
- Data fetched from database
- Feed added to cache

### Second Feed Request

- Cache hit
- Faster response from cache

### New Post Creation

- Saves post in database
- Invalidates affected followers' feed cache

---

## Run Project

```bash
./mvnw spring-boot:run
```

---

## Test APIs

Create Post:

```bash
curl -X POST http://localhost:8080/feed/post \
-H "Content-Type: application/json" \
-d '{"userId":10,"content":"Hello from user 1"}'
```

Get Feed:

```bash
curl http://localhost:8080/feed/10
```

---

## Latency Comparison

| Request Type | Approx Time |
|---|---|
| First Request (Cache Miss) | ~2000 ms |
| Second Request (Cache Hit) | ~1 ms |

---

## Learning Goals

This project was created to understand:

- Cache eviction strategies
- Cache-aside pattern
- Feed cache invalidation
- Spring Boot backend basics
- API performance optimization