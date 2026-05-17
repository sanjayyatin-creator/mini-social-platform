-- ============================================
-- Migration Script : V1__create_social_schema.sql
-- Description:
-- This script creates the database schema
-- for the social media platform.
--
-- Entities:
-- 1. users
-- 2. posts
-- 3. follows
-- 4. likes
--
-- Database Choice:
-- SQL (H2/PostgreSQL compatible)
-- ============================================



-- ============================================
-- USERS TABLE
-- Stores user profile information
-- ============================================

CREATE TABLE users (

    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(100) UNIQUE NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);



-- ============================================
-- POSTS TABLE
-- Stores posts created by users
-- One user can create multiple posts
-- ============================================

CREATE TABLE posts (

    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    content TEXT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_post_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)

);



-- ============================================
-- FOLLOWS TABLE
-- Stores follower-following relationships
-- Many-to-many self relationship on users
-- ============================================

CREATE TABLE follows (

    follower_id BIGINT NOT NULL,

    following_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (follower_id, following_id),

    FOREIGN KEY (follower_id)
        REFERENCES users(id),

    FOREIGN KEY (following_id)
        REFERENCES users(id)

);



-- ============================================
-- LIKES TABLE
-- Stores likes on posts
-- One user can like many posts
-- One post can be liked by many users
-- Composite primary key prevents duplicate likes
-- ============================================

CREATE TABLE likes (

    user_id BIGINT NOT NULL,

    post_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, post_id),

    FOREIGN KEY (user_id)
        REFERENCES users(id),

    FOREIGN KEY (post_id)
        REFERENCES posts(id)

);