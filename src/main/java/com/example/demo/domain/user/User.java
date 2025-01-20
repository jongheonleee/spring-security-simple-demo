package com.example.demo.domain.user;

import com.example.demo.domain.Authority;
import com.example.demo.domain.EncryptionAlgorithm;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String username;
    private String password;
    private EncryptionAlgorithm algorithm;
    private List<Authority> authorities;
}
