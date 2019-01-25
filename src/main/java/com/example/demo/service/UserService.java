package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.List;

public interface UserService {
  void filter(List<User> users);

  List<User> saveAll(List<User> users);

  List<User> getAll();

  void deleteAll();
}
