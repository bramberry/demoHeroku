package com.example.demo.service;

import com.example.demo.domain.VkUser;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<VkUser> filter(List<VkUser> vkUsers, String groupName) {
        vkUsers = vkUsers.stream().filter(checkUserRelation())
                .collect(Collectors.toList());
        ParseUtil.setAdditionalInfo(vkUsers, groupName);
        if (!vkUsers.isEmpty()) {
            saveAll(vkUsers);
        }
        return vkUsers;
    }

    private Predicate<VkUser> checkUserRelation() {
        return user -> (user.getRelation() == 0 || user.getRelation() == 1
                || user.getRelation() == 6) && !user.getIsClosed();
    }

    @Override
    public List<VkUser> saveAll(List<VkUser> vkUsers) {
        log.info("users to save: {}", vkUsers.size());
        return userRepository.saveAll(vkUsers);
    }

    @Override
    public List<VkUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<VkUser> findByGroupName(String groupName) {
        return userRepository.findByGroupName(groupName);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
