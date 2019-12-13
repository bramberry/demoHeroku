package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUser {
  @Id
  private Integer userId;
  private String accessToken;
  private Integer expiresIn;
}
