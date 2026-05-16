package com.sanjay.mini_social_platform.service;

import com.sanjay.mini_social_platform.cache.LRUCache;
import com.sanjay.mini_social_platform.entity.Post;
import com.sanjay.mini_social_platform.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedService {
    private final PostRepository postRepository;

    private final LRUCache lruCache = new LRUCache(5);
    private final Map<Integer, List<Integer>> followers = new HashMap<>();

    public FeedService(PostRepository postRepository){
        this.postRepository = postRepository;
        followers.put(10, List.of(1, 2, 3));
    }

    public List<Post> getFeed(int userId) throws InterruptedException{
        int cachedValue = lruCache.get(userId);
        if(cachedValue != -1){
            System.out.println("CACHE HIT");
            return postRepository.findByUserId(userId);
        }
        System.out.println("CACHE MISS");
        Thread.sleep(2000);
        List<Post> posts = postRepository.findByUserId(userId);
        lruCache.put(userId, 1);
        return posts;
    }
    public Post createPost(Post post){
        Post savedPost = postRepository.save(post);
        // logic to invalidate follwers cache feed
        List<Integer> affectedFollowers = followers.get(post.getUserId());
        if(affectedFollowers !=null){
            for(Integer followerId : affectedFollowers){
                lruCache.invalidate(followerId);
            }
        }
        return savedPost;
    }
}
