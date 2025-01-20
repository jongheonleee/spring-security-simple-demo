package com.example.demo.controller;

import com.example.demo.controller.request.UserRegisterRequest;
import com.example.demo.controller.response.ResultResponse;
import com.example.demo.domain.user.CreateUser;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/api/v1/register")
    public ResultResponse<String> register(@RequestBody UserRegisterRequest request) {
        String result = userService.register(new CreateUser(request.getUsername(), bCryptPasswordEncoder.encode(request.getPassword())));
        return ResultResponse.ok(result);
    }
}
