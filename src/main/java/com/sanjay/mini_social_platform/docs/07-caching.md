# Adding Caching to Instagram Feed

## Introduction

Instagram feed APIs are read-heavy systems where users refresh feeds very frequently. If every request directly hits the database and feed ranking service, response time increases and backend systems become overloaded. To reduce repeated computation and improve latency, caching is added at multiple layers.

---

# What to Cache

The most important cache is the user feed itself.

Example:

```text
feed:user:101
```

This cache can store:
- ranked post IDs
- pagination information

Feed generation is expensive because it involves follower lookup, ranking logic, filtering, and pagination.

Other useful caches include:

- user profile data
- post metadata
- like and comment counts

Example keys:

```text
profile:user:101
post:5001
```

---

# Where to Cache

Caching should exist at multiple levels.

## Client Cache

Mobile apps or browsers can cache recently opened feeds and profile data.

Advantages:
- fewer API calls
- faster reloads
- smoother user experience

Limitation:
Data may become stale quickly.

---

## CDN / Edge Cache

CDNs are mainly used for:
- images
- videos
- static assets

Advantages:
- low latency globally
- reduced backend traffic
- better scalability

---

## Server-Side Cache

Server-side caches store:
- feed responses
- profile data
- ranking results

Technologies commonly used:
- Redis
- Memcached
- in-memory LRU cache

Advantages:
- reduced database load
- faster API responses

---

# Cache Invalidation

Cache invalidation is required whenever underlying data changes.

## New Post Creation

When a user creates a post:

```text
POST /post
```

feed caches of followers should be invalidated.

Example:

```text
user 10 posts
followers: 1,2,3

invalidate:
feed:user:1
feed:user:2
feed:user:3
```

This ensures followers receive updated feed content.

---

## Profile Updates

If username, bio, or profile image changes, invalidate:

```text
profile:user:{id}
```

---

## Like and Comment Updates

Invalidate:

```text
post:{postId}
```

so updated engagement counts appear correctly.

---

# Eviction Policy

LRU (Least Recently Used) is a suitable eviction policy for feed systems.

Reason:
recently viewed feeds are more likely to be requested again.

When cache capacity becomes full, the least recently used entry is removed first.

---

# Stampede Protection

A cache stampede occurs when many concurrent requests access the same expired or missing cache key.

Example:

```text
100 requests → /feed/101
```

Without protection, all requests may hit the database simultaneously.

To avoid this problem, request coalescing can be used.

Approach:
- only one request fetches data from DB
- remaining requests wait
- after cache is populated, waiting requests use same cached response

This reduces duplicate DB work and prevents sudden load spikes.

---

# Conclusion

Caching is important for improving Instagram feed performance and scalability. A combination of client-side, CDN, and server-side caching helps reduce latency and database load. Proper invalidation and stampede protection are also necessary to maintain consistency and system stability.