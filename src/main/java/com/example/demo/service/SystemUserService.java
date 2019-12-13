package com.example.demo.service;

import com.example.demo.domain.SystemUser;

public interface SystemUserService {

  SystemUser save(SystemUser systemUser);

  SystemUser update(SystemUser systemUser);

  void delete(Integer id);

  SystemUser findByAccessToken(String token);

  SystemUser findById(Integer id);
}
