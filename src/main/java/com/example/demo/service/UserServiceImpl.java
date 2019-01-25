package com.example.demo.service;

import com.example.demo.UserRepository;
import com.example.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
  private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void filter(List<User> users) {
    for (User user : users) {

      if (user.getCity() == null || user.getRelation() == null || user.getSex() == null || user.getBdate() == null) {

        continue;
      }
      if (user.getCity().getId() != 282) {
        continue;
      }
      if (user.getSex() != 2) {
        continue;
      }
      if (user.getRelation() != 0 && user.getRelation() != 1 && user.getRelation() != 6) {
        continue;
      }
      Long date = parseDate(user.getBdate());
      if (date < 631152000000L/*1990*/ || date > 788832000000L/*1994*/) {
        continue;
      }
      log.info("user to save: {}", user);
      userRepository.save(user);
    }
  }

  @Override
  public List<User> saveAll(List<User> users) {
    log.info("users to save: {}", users);
    return userRepository.saveAll(users);
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public void deleteAll() {
    userRepository.deleteAll();
  }

  private Long parseDate(String str) {

    Date date;
    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      return 0L;
    }
    return date.getTime();
  }
}
