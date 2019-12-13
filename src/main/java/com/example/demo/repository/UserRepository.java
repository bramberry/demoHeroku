package com.example.demo.repository;

import com.example.demo.domain.VkUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<VkUser, Integer> {
    List<VkUser> findByGroupName(String groupName);
}
