package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser {
    @Id
    private Integer id;
    private Integer relation = 0;
    private City city;
    private Integer sex;
    @JsonProperty("is_closed")
    private Boolean isClosed;
    private String bdate;
    private String domain;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String groupName;
}
