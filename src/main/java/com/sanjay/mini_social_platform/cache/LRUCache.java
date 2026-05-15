package com.sanjay.mini_social_platform.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private final int capacity;

    private final Map<Integer, Node> cache = new HashMap<>();

    private final Node head = new Node(0,0);
    private final Node tail = new Node(0,0);

    public LRUCache(int capacity){
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public void addNode(Node node){
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    public void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void moveToHead(Node node){
        removeNode(node);
        addNode(node);
    }

    public Node removeTail(){
        Node lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode;
    }

    public int get(int key){
        Node node = cache.get(key);
        if(null == node){
            return -1;
        }
        moveToHead(node);
        return node.value;
    }
    public void put(int key, int value){
        Node existingNode = cache.get(key);
        if(existingNode !=null){
            existingNode.value = value;
            moveToHead(existingNode);
        }else {
            Node node = new Node(key, value);
            cache.put(key, node);
            addNode(node);
            if(cache.size() > capacity){
                Node lastRecentlyUsed = removeTail();
                cache.remove(lastRecentlyUsed.key);
            }
        }
    }
    public void invalidate(int key){
        Node node = cache.get(key);
        if(node != null){
            removeNode(node);
            cache.remove(key);
        }
    }


}

// (1,200) <-> (2,300) where one is key and 200 is value. and whole is node which knows which is prev and next.