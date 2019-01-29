package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<User> filter(List<User> users, String groupName) {
        users = users.stream().filter(user -> (user.getRelation() == 0 || user.getRelation() == 1
                || user.getRelation() == 6) && !user.getIsClosed()).collect(Collectors.toList());
        ParseUtil.setAdditionalInfo(users, groupName);
        if (users.size() > 0) {
            saveAll(users);
        }
        return users;
    }

    @Override
    public List<User> saveAll(List<User> users) {
        log.info("users to save: {}", users.size());
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

    @Override
    public List<User> findByGroupName(String groupName) {
        return userRepository.findByGroupName(groupName);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
