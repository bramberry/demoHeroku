package com.example.demo.domain.enumirations;

public enum UserRelation {
    SINGLE(0);
    private final Integer item;

    UserRelation(Integer item) {
        this.item = item;
    }

    public Integer getValue() {
        return item;
    }
}
