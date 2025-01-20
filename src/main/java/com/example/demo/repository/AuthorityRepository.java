package com.example.demo.repository;

import com.example.demo.domain.Authority;
import com.example.demo.repository.entity.AuthorityEntity;
import com.example.demo.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AuthorityRepository {
    private final AuthorityJpaRepository authorityJpaRepository;

    @Transactional
    public Authority create(String name, UserEntity user) {
        return authorityJpaRepository.save(new AuthorityEntity(name, user))
                .toAuthority();
    }
}
