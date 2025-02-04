package com.jyhun.shop.controller;

import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/my-info")
    public ResponseEntity<ResponseDTO> getUserInfo(){
        return ResponseEntity.ok(userService.getUserInfo());
    }

}
