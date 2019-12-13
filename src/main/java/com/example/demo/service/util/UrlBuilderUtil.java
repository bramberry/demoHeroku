package com.example.demo.service.util;

import com.example.demo.domain.enumirations.AppConstants;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.bind.annotation.RequestParam;


public final class UrlBuilderUtil {

    private UrlBuilderUtil() {
    }

    public static URIBuilder buildOauthRedirect() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("oauth.vk.com");
        builder.setPath("authorize");
        builder.addParameter("client_id", AppConstants.CLIENT_ID.getValue());
        builder.addParameter("display", "page");
        builder.addParameter("redirect_uri", AppConstants.REDIRECT_URL.getValue());
        builder.addParameter("scope", "groups");
        builder.addParameter("response_type", "code");
        return builder;
    }

    public static URIBuilder buildGetUserInfoURI(@RequestParam String token, @RequestParam String userIds) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("api.vk.com");
        builder.setPath("method/users.get");
        builder.addParameter("client_secret", AppConstants.SECRET_KEY.getValue());
        builder.addParameter("v", "5.92");
        builder.addParameter("user_ids", userIds);
        builder.addParameter("access_token", token);
        builder.addParameter("fields", "bdate,photo_50,city,verified,domain");
        return builder;
    }
}
