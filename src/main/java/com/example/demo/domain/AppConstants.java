package com.example.demo.domain;

public enum AppConstants {
    APP_TOKEN("08d10f884b375ba4ddd86da4fe3e94df4a31ae4c594ac5da8e33b75e2a577d108e0fe511370c255d65299"),
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
