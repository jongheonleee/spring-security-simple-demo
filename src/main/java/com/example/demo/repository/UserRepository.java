package com.example.demo.repository;

import com.example.demo.domain.user.CreateUser;
import com.example.demo.domain.user.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.entity.AuthorityEntity;
import com.example.demo.repository.entity.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final AuthorityJpaRepository authorityJpaRepository;

    @Transactional(readOnly = true)
    public Boolean userExists(String username) {
        return userJpaRepository.findUserByUsername(username)
                                .isPresent();
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userJpaRepository.findUserByUsername(username)
                                .orElseThrow(UserNotFoundException::new)
                                .toUser();
    }

    @Transactional
    public User create(CreateUser create) {
        UserEntity user = userJpaRepository.save(UserEntity.newUser(create));
        AuthorityEntity authority = authorityJpaRepository.save(new AuthorityEntity("READ", user));
        user.replaceAuthority(List.of(authority));

        return user.toUser();
    }
}