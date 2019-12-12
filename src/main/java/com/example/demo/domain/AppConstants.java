package com.example.demo.domain;

public enum AppConstants {
    APP_TOKEN("9748413929dc2d7d61c8b764384c0d9370ef61cfcae35f42622cf65003faf77f4e7e0b68788d29ae55b23"),
    SECRET_KEY("QfDgRX80pcCCPXvLgyc4"),
    CLIENT_ID("6831567");
    private final String item;

    AppConstants(String item) {
        this.item = item;
    }

    public String toString() {
        return item;
    }

    /**
     * https://oauth.vk.com/authorize?client_id=6831567&display=page&redirect_uri=https://demoherokudeploy.herokuapp.com/api/usersk&scope=friends&response_type=token&v=5.103
     */
}
