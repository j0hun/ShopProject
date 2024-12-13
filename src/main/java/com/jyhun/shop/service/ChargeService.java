package com.jyhun.shop.service;

import com.jyhun.shop.dto.ChargeRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.Charge;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.repository.ChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChargeService {

    private final ChargeRepository chargeRepository;
    private final UserService userService;

    @Transactional
    public ResponseDTO createCharge(ChargeRequestDTO chargeRequestDTO){
        User user = userService.getLoginUser();
        Charge charge = Charge.builder()
                .amount(chargeRequestDTO.getAmount())
                .user(user)
                .build();
        user.updateBalance(chargeRequestDTO.getAmount() + user.getBalance());
        chargeRepository.save(charge);

        return ResponseDTO.builder()
                .status(200)
                .message("충전 성공")
                .build();

    }

}
