package com.example.demo.service;

import com.example.demo.domain.ParametrsDto;
import com.example.demo.domain.User;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;

import java.util.List;

public interface GroupService {
    List<User> loadMembers(ParametrsDto dto) throws InterruptedException, ClientException, ApiException;

    GroupFull getById(String group) throws ClientException, ApiException;
}
