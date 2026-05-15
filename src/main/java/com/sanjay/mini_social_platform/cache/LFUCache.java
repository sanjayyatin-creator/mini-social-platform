package com.sanjay.mini_social_platform.cache;

import java.util.*;

public class LFUCache {
    private final int capacity;

    private final Map<Integer, LFUNode> cache = new HashMap<>();

    private final Map<Integer, LinkedHashSet<LFUNode>> frequencyMap = new HashMap<>();

    private int minFrequency = 1;

    public LFUCache(int capacity){
        this.capacity = capacity;
    }

    public int getKey(int key){
        LFUNode node = cache.get(key);
        if(node == null){
            return -1;
        }
        updateFrequency(node);
        return node.value;
    }
    private void updateFrequency(LFUNode node){

        int oldFrequency = node.frequency;
        frequencyMap.get(oldFrequency).remove(node);
        if(oldFrequency == minFrequency && frequencyMap.get(oldFrequency).isEmpty()){
            minFrequency++;
        }
        node.frequency++;
        frequencyMap.computeIfAbsent(node.frequency, k-> new LinkedHashSet<>()).add(node);
    }
    public void put(int key, int value){
        if(capacity==0){
            return;
        }
        if(cache.containsKey(key)){
            LFUNode existingNode = cache.get(key);
            existingNode.value = value;
            updateFrequency(existingNode);
            return;
        }
        if(cache.size() >= capacity){
            eviction();
        }
        LFUNode newNode = new LFUNode(key, value);
        cache.put(key, newNode);
        frequencyMap.computeIfAbsent(1, k->new LinkedHashSet<>()).add(newNode);
        minFrequency =1;
    }
    private void removeNode(LFUNode node){
        frequencyMap.get(node.frequency).remove(node);
        cache.remove(node.key);
    }
    private void eviction(){
        LinkedHashSet<LFUNode> leastFrequentNode = frequencyMap.get(minFrequency);
        LFUNode nodeToRemove = leastFrequentNode.iterator().next();
        removeNode(nodeToRemove);
    }

}
