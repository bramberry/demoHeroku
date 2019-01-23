package com.example.demo;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.fave.responses.GetPostsResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
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
import java.util.List;

@RestController
@RequestMapping("api")
public class LoginController {
  private final Integer clientId = 6828896;
  private final String key = "31sxzWtetqBBYcIn4l2U";
  private final RestTemplate restTemplate;
  private final Logger log = LoggerFactory.getLogger(LoginController.class);
  private final VkApiClient vk;


  @Autowired
  public LoginController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.vk = new VkApiClient(new HttpTransportClient());
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
    builder.addParameter("scope", "notes,friends,wall,groups");
    builder.addParameter("response_type", "code");
    builder.addParameter("v", "5.92");
    log.info("url {}", builder.build().toString());
    resp.sendRedirect(builder.build().toString());

  }

  @GetMapping("/callback")
  public void callback(@RequestParam(required = false) String code,
                       @RequestParam(required = false) String error,
                       HttpServletResponse resp) throws IOException, URISyntaxException, ClientException, ApiException {
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
    UserAuthResponse response = vk.oauth().userAuthorizationCodeFlow(clientId, key,
        "https://demoherokudeploy.herokuapp.com/api/callback", code).execute();
    /*ResponseEntity<UserAuthResponse> response = restTemplate.exchange(builder.build().toString(),
        HttpMethod.POST, null, UserAuthResponse.class);*/
    resp.sendRedirect("/api/info?token=" + response.getAccessToken() + "&userIds=" + response.getUserId());
  }

  @GetMapping("/implicit")
  public void token(@RequestParam String access_token, @RequestParam String user_id, HttpServletResponse resp) throws IOException {
    resp.sendRedirect("/api/info?token=" + access_token + "&userIds=" + user_id);
  }

  @GetMapping("/info")
  public ResponseEntity<String> getUser(@RequestParam String token, @RequestParam String userIds) throws URISyntaxException {
    URIBuilder builder = new URIBuilder();
    builder.setScheme("https");
    builder.setHost("api.vk.com");
    builder.setPath("method/users.get");
    builder.addParameter("client_secret", key);
    builder.addParameter("v", "5.92");
    builder.addParameter("user_ids", userIds);
    builder.addParameter("access_token", token);
    builder.addParameter("fields", "bdate,photo_50,city,verified");
    log.info("url {}", builder.build().toString());
    ResponseEntity<String> response = restTemplate.exchange(builder.build().toString(),
        HttpMethod.GET, null, String.class);
    return ResponseEntity.ok(response.getBody());
  }

  @GetMapping("/likes")
  public ResponseEntity<List<WallPostFull>> getLikes(@RequestParam String token, @RequestParam Integer id) throws ClientException, ApiException {

    UserActor userActor = new UserActor(id, token);
    log.info("get likes {}", vk.fave().getPosts(userActor).extended(false));
    GetPostsResponse response = vk.fave().getPosts(userActor).extended(false).execute();

    return ResponseEntity.ok(response.getItems());
  }

  @GetMapping
  public ResponseEntity<String> get() {
    return ResponseEntity.ok("Hello world");
  }
}
