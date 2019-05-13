package com.example.demo.domain;

public enum AppConstants {
    APP_TOKEN("e5327296ecb5eb4d99347a8c214078c96fc0a548cc7d5f2b443482d27bcbc4503d257e9bb22f9fb2256bc"),
    SECRET_KEY("QfDgRX80pcCCPXvLgyc4"),
    CLIENT_ID("6831567");
    private final String item;

    AppConstants(String item) {
        this.item = item;
    }

    public String toString() {
        return item;
    }
}
