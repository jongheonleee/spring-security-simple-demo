package com.example.demo.repository.entity;

import com.example.demo.domain.EncryptionAlgorithm;
import com.example.demo.domain.user.CreateUser;
import com.example.demo.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm; // 복호화에서 사용하는 알고리즘

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities; // 1명의 유저 엔티티는 n개의 권한 엔티티를 가질 수 있음

    public UserEntity(final String username, final String password, final EncryptionAlgorithm algorithm) {
        this.username = username;
        this.password = password;
        this.algorithm = algorithm;
    }

    public User toUser() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .algorithm(this.algorithm)
                .authorities(this.authorities.stream().map(AuthorityEntity::toAuthority).toList())
                .build();
    }

    public void replaceAuthority(List<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    public static UserEntity newUser(CreateUser create) {
        return new UserEntity(
                create.getUsername(),
                create.getPassword(),
                EncryptionAlgorithm.BCRYPT
        );
    }
}
