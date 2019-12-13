package com.example.demo.controller;

import com.example.demo.domain.AppConstants;
import com.example.demo.domain.SystemUser;
import com.example.demo.service.SystemUserService;
import com.example.demo.service.util.UrlBuilderUtil;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final SystemUserService systemUserService;
    private final VkApiClient vk;

    @PostMapping
    public void login(HttpServletResponse resp) throws IOException, URISyntaxException {
        resp.sendRedirect(UrlBuilderUtil.buildOauthRedirect().build().toString());
    }

    @GetMapping("callback")
    public SystemUser callback(@RequestParam String code) throws ClientException, ApiException {

        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(
                        Integer.parseInt(AppConstants.CLIENT_ID.getValue()),
                        AppConstants.SECRET_KEY.getValue(),
                        AppConstants.REDIRECT_URL.getValue(),
                        code).execute();

        SystemUser user = SystemUser.builder()
                .accessToken(authResponse.getAccessToken())
                .userId(authResponse.getUserId())
                .expiresIn(authResponse.getExpiresIn())
                .build();

        return systemUserService.save(user);
    }
}
