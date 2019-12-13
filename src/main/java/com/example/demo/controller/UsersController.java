package com.example.demo.controller;

import com.example.demo.domain.ParametersDto;
import com.example.demo.domain.VkUser;
import com.example.demo.service.GroupService;
import com.example.demo.service.UserService;
import com.example.demo.service.util.UrlBuilderUtil;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@Log4j2
public class UsersController {
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final GroupService groupService;


    @Autowired
    public UsersController(RestTemplate restTemplate, UserService userService,
                           GroupService groupService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("info")
    public String getUser(@RequestParam String token, @RequestParam String userIds) throws URISyntaxException {
        URIBuilder builder = UrlBuilderUtil.buildGetUserInfoURI(token, userIds);
        log.info("url {}", builder.build().toString());
        ResponseEntity<String> response = restTemplate.exchange(builder.build().toString(),
                HttpMethod.GET, null, String.class);
        return response.getBody();
    }


    @SuppressWarnings("MultiCatchCanBeSplit")
    @PostMapping
    public List<VkUser> getUsers(@RequestBody ParametersDto dto) {
        List<VkUser> vkUsers = new ArrayList<>();
        try {
            vkUsers = groupService.loadMembers(dto);
        } catch (ClientException | ApiException | InterruptedException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return vkUsers;
    }

    @GetMapping("{group}")
    public List<String> get(@PathVariable String group) {
        return userService.findByGroupName(group)
                .stream().map(VkUser::getDomain).collect(Collectors.toList());
    }

    @DeleteMapping
    public String delete() {
        userService.deleteAll();
        return "ok";
    }

    @GetMapping("update")
    public String update() {
        return "ok";
    }

}
