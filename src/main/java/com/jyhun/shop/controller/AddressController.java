package com.jyhun.shop.controller;

import com.jyhun.shop.dto.AddressRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveAndUpdateAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressRequestDTO));
    }

}
