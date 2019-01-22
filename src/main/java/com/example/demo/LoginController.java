package com.example.demo;

import com.vk.api.sdk.objects.UserAuthResponse;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class LoginController {
  private final Integer clientId = 6828896;
  private final String key = "31sxzWtetqBBYcIn4l2U";
  private final RestTemplate restTemplate;
  private final Logger log = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  public LoginController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  @GetMapping("/login")
  public void login(HttpServletResponse resp) throws IOException, URISyntaxException {
    URIBuilder builder = new URIBuilder();
    builder.setScheme("https");
    builder.setHost("oauth.vk.com");
    builder.setPath("authorize");
    builder.addParameter("client_id", clientId.toString());
    builder.addParameter("display", "page");
    builder.addParameter("redirect_uri", "https://demoherokudeploy.herokuapp.com/api/callback");
    builder.addParameter("scope", "notes");
    builder.addParameter("response_type", "code");
    builder.addParameter("v", "5.92");
    log.info("url {}", builder.build().toString());
    resp.sendRedirect(builder.build().toString());

  }

  @GetMapping("/callback")
  public void callback(@RequestParam(required = false) String code,
                       @RequestParam(required = false) String error,
                       HttpServletResponse resp) throws IOException, URISyntaxException {
    System.out.println(code);
    URIBuilder builder = new URIBuilder();
    builder.setScheme("https");
    builder.setHost("oauth.vk.com");
    builder.setPath("access_token");
    builder.addParameter("client_id", clientId.toString());
    builder.addParameter("client_secret", key);
    builder.addParameter("redirect_uri", "https://demoherokudeploy.herokuapp.com/api/callback");
    builder.addParameter("code", code);
    builder.addParameter("v", "5.92");
    log.info("url {}", builder.build().toString());
    ResponseEntity<UserAuthResponse> response = restTemplate.exchange(builder.build().toString(),
        HttpMethod.POST, null, UserAuthResponse.class);
    resp.sendRedirect("/info?token=" + response.getBody().getAccessToken() + "&user=" + response.getBody().getUserId());
  }

  @GetMapping("/info")
  public ResponseEntity<String> getUser(@RequestParam String token, @RequestParam String user) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> vars = new HashMap<>();
    vars.put("user_ids", user);
    vars.put("client_secret", key);
    vars.put("v", "5.92");
    vars.put("access_token", token);
    vars.put("fields", "bdate");
    ResponseEntity<String> response = restTemplate.exchange("https://oauth.vk.com/users.get",
        HttpMethod.GET, null, String.class, vars);
    return ResponseEntity.ok(response.getBody());
  }

  @GetMapping
  public ResponseEntity<String> get() {
    return ResponseEntity.ok("Hello world");
  }
}
