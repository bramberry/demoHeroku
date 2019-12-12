package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametersDto {
    private Integer sex;
    private Integer relation;
    private Integer ageFrom;
    private Integer ageTo;
    private Integer city;
    private Integer hasPhoto;
    private String group;
}
