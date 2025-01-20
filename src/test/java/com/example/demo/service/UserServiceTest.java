package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.demo.domain.user.CreateUser;
import com.example.demo.domain.user.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService sut;

    @Test
    @DisplayName("user 가 존재하면 runtime exception 을 발생시킨다.")
    void registerFailureCase() {
        // given
        CreateUser user = new CreateUser(
                "yeonuel",
                "12345"
        );
        given(userRepository.userExists(user.getUsername()))
                .willReturn(true);

        // when & then
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,
                () -> sut.register(user)
        );
        Assertions.assertEquals(
                runtimeException.getMessage(),
                "User [yeonuel] already exists"
        );
    }

    @Test
    @DisplayName("user 가 존재하지 않으면 회원가입을 수행한다.")
    void registerSuccessCase() {
        // given
        CreateUser user = new CreateUser(
                "yeonuel",
                "12345"
        );
        given(userRepository.userExists(user.getUsername()))
                .willReturn(false);
        given(userRepository.create(user))
                .willReturn(User.builder()
                        .username(user.getUsername())
                        .build());

        // when
        String register = sut.register(user);

        // then
        Assertions.assertEquals(register, user.getUsername());
        verify(userRepository, times(1))
                .create(user);
    }
}