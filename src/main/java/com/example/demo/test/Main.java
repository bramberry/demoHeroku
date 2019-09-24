package com.example.demo.test;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class Main {
    public static void main(String[] args) {

        List<Long> carrierPaymentId = Arrays.stream("1235,4,8,8,8".split(","))
                .map(Long::parseLong)
                .distinct()
                .collect(Collectors.toList());
        log.info(carrierPaymentId);
    }
}
