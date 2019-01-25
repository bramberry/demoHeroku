package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.ParseUtil;
import com.example.demo.service.UserService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api")
@Slf4j
public class LoginController {
  private final Integer clientId = 6831567;
  private final String SECRET_KEY = "vSEdL5xUddYosScEgTye";
  private final String APP_TOKEN = "d0c8b01ad0c8b01ad0c8b01acbd0a08dd5dd0c8d0c8b01a8c81de7189a4be3b688569ce";
  private final RestTemplate restTemplate;
  private final UserService userService;
  private final VkApiClient vk = new VkApiClient(new HttpTransportClient());


  @Autowired
  public LoginController(RestTemplate restTemplate, UserService userService) {
    this.restTemplate = restTemplate;
    this.userService = userService;
  }

  @GetMapping("/info")
  public ResponseEntity<String> getUser(@RequestParam String token, @RequestParam String userIds) throws URISyntaxException {
    URIBuilder builder = new URIBuilder();
    builder.setScheme("https");
    builder.setHost("api.vk.com");
    builder.setPath("method/users.get");
    builder.addParameter("client_secret", SECRET_KEY);
    builder.addParameter("v", "5.92");
    builder.addParameter("user_ids", userIds);
    builder.addParameter("access_token", token);
    builder.addParameter("fields", "bdate,photo_50,city,verified,domain");
    log.info("url {}", builder.build().toString());
    ResponseEntity<String> response = restTemplate.exchange(builder.build().toString(),
        HttpMethod.GET, null, String.class);
    return ResponseEntity.ok(response.getBody());
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() throws InterruptedException {
    /*GetMembersResponse response = vk.groups().getMembers(new ServiceActor(clientId, APP_TOKEN))
        .groupId("pikabu").offset(0).count(500).execute();*/

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setAcceptLanguage(Locale.LanguageRange.parse("en"));
    headers.setCacheControl("max-age=0");
    for (int i = 0; i < 149000; i += 1000) {
      ResponseEntity<String> response = restTemplate.exchange("https://api.vk.com/method/groups.getMembers?access_token="
              + APP_TOKEN + "&offset=" + i +
              "&count=900&group_id=belteanews&v=5.92&fields=relation,city,sex,bdate", HttpMethod.GET,
          new HttpEntity<>("parameters", headers), String.class);
      List<User> inputUsers = ParseUtil.parseString(response.getBody());
      userService.filter(inputUsers);
      log.info("{} iteration", inputUsers.size());
      Thread.sleep(1000);
    }
    return ResponseEntity.ok(userService.getAll());
  }

  @GetMapping
  public ResponseEntity<List<String>> get() {
    List<String> links = new ArrayList<>();
    userService.getAll().forEach(user -> {
      links.add("https://vk.com/id" + user.getId());
    });
    return ResponseEntity.ok(links);
  }

  @GetMapping("delete")
  public ResponseEntity<String> delete() {
    userService.deleteAll();
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
