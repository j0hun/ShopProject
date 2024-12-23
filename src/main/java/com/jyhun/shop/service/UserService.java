package com.jyhun.shop.service;

import com.jyhun.shop.dto.*;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.enums.Role;
import com.jyhun.shop.exception.InvalidCredentialsException;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.UserRepository;
import com.jyhun.shop.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EntityDTOMapper entityDTOMapper;

    public ResponseDTO register(RegisterDTO registerDTO) {
        User user = User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .phoneNumber(registerDTO.getPhoneNumber())
                .role(Role.USER)
                .balance(0L)
                .build();

        userRepository.save(user);
        return ResponseDTO.builder()
                .status(200)
                .message("회원 가입 성공")
                .build();
    }

    public ResponseDTO loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new NotFoundException("이메일을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("패스워드가 맞지 않습니다.");
        }
        String token = jwtService.generateToken(user);
        AuthDTO authDTO = new AuthDTO(token, user.getRole().name());
        return ResponseDTO.builder()
                .status(200)
                .message("로그인 성공")
                .data(authDTO)
                .build();
    }

    @Transactional(readOnly = true)
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 아닙니다"));
    }

    @Transactional(readOnly = true)
    public ResponseDTO getUserInfo() {
        User user = getLoginUser();
        UserResponseDTO userResponseDTO = entityDTOMapper.mapUserToDTOPlusAddress(user);

        return ResponseDTO.builder()
                .status(200)
                .message("유저 조회 성공")
                .data(userResponseDTO)
                .build();
    }

}
