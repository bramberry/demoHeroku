package com.example.demo.service;

import com.example.demo.domain.ParametersDto;
import com.example.demo.domain.VkUser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;

import java.util.List;

public interface GroupService {
  List<VkUser> loadMembers(ParametersDto dto, String token) throws InterruptedException, ClientException, ApiException;

    GroupFull getById(String group) throws ClientException, ApiException;
}
