package com.jyhun.shop.service;

import com.jyhun.shop.dto.LoginRequest;
import com.jyhun.shop.dto.Response;
import com.jyhun.shop.dto.UserDTO;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.enums.UserRole;
import com.jyhun.shop.exception.InvalidCredentialsException;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.UserRepository;
import com.jyhun.shop.security.JwtUtils;
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
    private final JwtUtils jwtUtils;
    private final EntityDTOMapper entityDTOMapper;

    public Response register(UserDTO userDTO) {
        UserRole role = UserRole.USER;

        if (userDTO.getRole() != null && userDTO.getRole().equalsIgnoreCase("admin")) {
            role = UserRole.ADMIN;
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .phoneNumber(userDTO.getPhoneNumber())
                .userRole(role)
                .build();

        User savedUser = userRepository.save(user);

        UserDTO userDTO1 = entityDTOMapper.mapUserToDTOBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User Add")
                .user(userDTO1)
                .build();
    }

    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new NotFoundException("이메일을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("패스워드가 맞지 않습니다.");
        }
        String token = jwtUtils.generateToken(user);
        return Response.builder()
                .status(200)
                .message("User Login")
                .token(token)
                .role(user.getUserRole().name())
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
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDTO userDTO = entityDTOMapper.mapUserToDTOPlusAddressAndOrderHistory(user);

        return Response.builder()
                .status(200)
                .user(userDTO)
                .build();
    }

}
