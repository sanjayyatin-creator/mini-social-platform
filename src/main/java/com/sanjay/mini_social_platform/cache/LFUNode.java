package com.sanjay.mini_social_platform.cache;

public class LFUNode {
    int key;
    int value;

    int frequency;

    public LFUNode(int key, int value){
        this.key = key;
        this.value = value;
        this.frequency = 1;
    }

}
