package com.example.demo.config;


import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplateWithIntercepter(
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        RestTemplate restTemplate =
                new RestTemplate(
                        new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        return restTemplate;
    }
}
