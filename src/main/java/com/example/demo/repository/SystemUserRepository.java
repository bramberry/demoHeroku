package com.example.demo.repository;

import com.example.demo.domain.SystemUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends MongoRepository<SystemUser, Integer> {
  Optional<SystemUser> findByAccessToken(String token);
}
