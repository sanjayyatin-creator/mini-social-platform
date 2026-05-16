package com.sanjay.mini_social_platform.controller;

import com.sanjay.mini_social_platform.entity.Post;
import com.sanjay.mini_social_platform.service.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService){
        this.feedService = feedService;
    }

    @GetMapping("/feed/{userId}")
    public List<Post> getPosts(@PathVariable Integer userId) throws InterruptedException {
        return feedService.getFeed(userId);
    }

    @PostMapping("/feed/post")
    public Post createPost(@RequestBody Post post){
        return feedService.createPost(post);
    }
}
