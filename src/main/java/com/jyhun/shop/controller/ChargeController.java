package com.jyhun.shop.controller;

import com.jyhun.shop.dto.ChargeRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge")
@RequiredArgsConstructor
public class ChargeController {

    private final ChargeService chargeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCharge(@RequestBody ChargeRequestDTO chargeRequestDTO) {
        return ResponseEntity.ok(chargeService.createCharge(chargeRequestDTO));
    }

}
