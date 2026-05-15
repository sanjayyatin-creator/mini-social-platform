package com.sanjay.mini_social_platform.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class LRUCacheTest {

    @Test
    void shouldStoreAndGetValue() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 100);
        int value = cache.get(1);
        Assertions.assertEquals(100, value);
    }

    @Test
    void testEvictionOrder(){
        LRUCache cache = new LRUCache(2);
        cache.put(1,100);
        cache.put(2,200);

        cache.get(2);
        cache.put(3,300);

        Assertions.assertEquals(-1, cache.get(1));
        Assertions.assertEquals(200, cache.get(2));
        Assertions.assertEquals(300, cache.get(3));
    }

}
