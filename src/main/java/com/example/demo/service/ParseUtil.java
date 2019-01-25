package com.example.demo.service;

import com.example.demo.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ParseUtil {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final TypeReference ref = new TypeReference<List<User>>() {
  };

  public static List<User> parseString(String obj) {

    List<User> users = new ArrayList<>();
    try {
      ObjectNode on = objectMapper.readValue(obj, ObjectNode.class);
      JsonNode response = null;
      if (on.has("response")) {
        response = on.get("response");
      }
      if (response != null && response.has("items")) {
        users = objectMapper.readValue(response.get("items").toString(), ref);
      }
    } catch (IOException e) {
      log.error("parse exception {}", e.getMessage());
    }
    return users;
  }
}
