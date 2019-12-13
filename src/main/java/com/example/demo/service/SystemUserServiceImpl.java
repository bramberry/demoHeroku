package com.example.demo.service;

import com.example.demo.domain.SystemUser;
import com.example.demo.repository.SystemUserRepository;
import com.example.demo.service.exception.SystemUserServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepository systemUserRepository;

    @Override
    public SystemUser save(SystemUser user) {
        log.info("Saving user to db {}", user);
        return systemUserRepository.save(user);
    }

    @Override
    public SystemUser update(SystemUser user) {
        log.info("Updating user in db {}", user);
        Optional<SystemUser> dbUserOptional = systemUserRepository.findById(user.getUserId());
        if (!dbUserOptional.isPresent()) {
            return systemUserRepository.save(user);
        }
        SystemUser dbUser = dbUserOptional.get();
        if (!dbUser.getAccessToken().equals(user.getAccessToken())) {
            dbUser.setAccessToken(user.getAccessToken());
        }
        return systemUserRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        log.info("delete user in db {}", id);
        systemUserRepository.deleteById(id);
    }

    @Override
    public SystemUser findByAccessToken(String token) {
        return systemUserRepository.findByAccessToken(token)
                .orElseThrow(() -> new SystemUserServiceException("User not found"));
    }

    @Override
    public SystemUser findById(Integer id) {
        return systemUserRepository.findById(id)
                .orElseThrow(() -> new SystemUserServiceException("User not found"));
    }
}
