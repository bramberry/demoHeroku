package com.example.demo.service;

import com.example.demo.domain.User;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.List;

public interface UserService {
    List<User> filter(List<User> users, String groupName) throws InterruptedException, ClientException, ApiException;

    List<User> saveAll(List<User> users);

    List<User> getAll();

    void deleteAll();

    List<User> findByGroupName(String groupName);

    void delete(Integer id);
}
