package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametersDto {
    private Integer sex;
    private Integer relation;
    @JsonProperty("age_from")
    private Integer ageFrom;
    @JsonProperty("age_to")
    private Integer ageTo;
    private Integer city;
    @JsonProperty("has_photo")
    private Integer hasPhoto;
    private String group;
}
