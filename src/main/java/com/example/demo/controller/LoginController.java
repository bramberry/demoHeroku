package com.example.demo.controller;

import com.example.demo.domain.AppConstants;
import com.example.demo.domain.ParametrsDto;
import com.example.demo.domain.User;
import com.example.demo.service.GroupService;
import com.example.demo.service.UserService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@Slf4j
public class LoginController {
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public LoginController(RestTemplate restTemplate, UserService userService, GroupService groupService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getUser(@RequestParam String token, @RequestParam String userIds) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("api.vk.com");
        builder.setPath("method/users.get");
        builder.addParameter("client_secret", AppConstants.SECRET_KEY.toString());
        builder.addParameter("v", "5.92");
        builder.addParameter("user_ids", userIds);
        builder.addParameter("access_token", token);
        builder.addParameter("fields", "bdate,photo_50,city,verified,domain");
        log.info("url {}", builder.build().toString());
        ResponseEntity<String> response = restTemplate.exchange(builder.build().toString(),
                HttpMethod.GET, null, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("users")
    public ResponseEntity<List<User>> getUsers(@RequestBody ParametrsDto dto) {
        List<User> users = new ArrayList<>();
        try {
            users = groupService.loadMembers(dto);
        } catch (ClientException | ApiException | InterruptedException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("users")
    public ResponseEntity<List<String>> get(@PathVariable String group) {
        return ResponseEntity.ok(userService.findByGroupName(group)
                .stream().map(User::getDomain).collect(Collectors.toList()));
    }

    @GetMapping("delete")
    public ResponseEntity<String> delete() {
        userService.deleteAll();
        return ResponseEntity.ok("ok");
    }

    @GetMapping("update")
    public ResponseEntity<String> update() {
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/login")
    public void login(HttpServletResponse resp) throws IOException {
    /*URIBuilder builder = new URIBuilder();
    builder.setScheme("https");
    builder.setHost("oauth.vk.com");
    builder.setPath("authorize");
    builder.addParameter("client_id", clientId.toString());
    builder.addParameter("display", "popup");
    builder.addParameter("redirect_uri", "https://demoherokudeploy.herokuapp.com/api/implicit?");
    builder.addParameter("scope", "notes,friends,wall,groups");
    builder.addParameter("response_type", "token");
    builder.addParameter("v", "5.92");
    builder.addParameter("revoke", "1");
    log.info("url {}", builder.build().toString());
    resp.sendRedirect(builder.build().toString());*/
        resp.sendRedirect("/api/");
    }
}
