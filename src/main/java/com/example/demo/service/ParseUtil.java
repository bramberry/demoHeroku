package com.example.demo.service;

import com.example.demo.domain.VkUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ParseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @SuppressWarnings("rawtypes")
    private static final TypeReference ref = new TypeReference<List<VkUser>>() {
    };
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static List<VkUser> parseString(String obj) {

        List<VkUser> vkUsers = new ArrayList<>();
        try {
            ObjectNode on = objectMapper.readValue(obj, ObjectNode.class);
            JsonNode response = null;
            if (on.has("response")) {
                response = on.get("response");
            }
            if (response != null && response.has("items")) {
                vkUsers = objectMapper.readValue(response.get("items").toString(), ref);
            }
        } catch (IOException e) {
            log.error("users parse exception {}", e.getMessage());
        }
        return vkUsers;
    }

    public static Integer parseResultCount(String obj) {
        int count = 0;
        try {
            ObjectNode on = objectMapper.readValue(obj, ObjectNode.class);
            JsonNode response = null;
            if (on.has("response")) {
                response = on.get("response");
            }
            if (response != null && response.has("count")) {
                count = response.get("count").intValue();
            }
        } catch (IOException e) {
            log.error("count parse exception {}", e.getMessage());
        }
        return count;
    }

    public static Long parseDate(String str) {
        try {
            return dateFormat.parse(str).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    public static void setAdditionalInfo(List<VkUser> vkUsers, String groupName) {
        vkUsers.forEach(user -> {
            user.setDomain("https://vk.com/id" + user.getId());
            user.setGroupName(groupName);
        });
    }
}
