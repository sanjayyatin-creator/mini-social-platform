package com.sanjay.mini_social_platform.repository;

import com.sanjay.mini_social_platform.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(int userId);
}
