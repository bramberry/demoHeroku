package com.example.demo.service;

import com.example.demo.domain.VkUser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.List;

public interface UserService {
  List<VkUser> filter(List<VkUser> vkUsers, String groupName) throws InterruptedException, ClientException, ApiException;

  List<VkUser> saveAll(List<VkUser> vkUsers);

  List<VkUser> getAll();

    void deleteAll();

  List<VkUser> findByGroupName(String groupName);

    void delete(Integer id);
}
