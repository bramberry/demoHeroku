package com.example.demo.domain;

public enum AppConstants {
    APP_TOKEN("eaca05728d426c4299f79df50b190286e997bda690d117540868d12a0aa57640391a44cac1e0b605ef2e8"),
    SECRET_KEY("vSEdL5xUddYosScEgTye"),
    CLIENT_ID("6831567");
    private final String item;

    AppConstants(String item) {
        this.item = item;
    }

    public String toString() {
        return item;
    }
}
