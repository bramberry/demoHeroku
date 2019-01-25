package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
  @Id
  private Long id;
  private Integer relation;
  private Lookup city;
  private Lookup country;
  private Integer sex;
  private String bdate;
  private String domain;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
}
