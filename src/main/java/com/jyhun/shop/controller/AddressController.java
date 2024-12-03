package com.jyhun.shop.controller;

import com.jyhun.shop.dto.AddressDTO;
import com.jyhun.shop.dto.Response;
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
    public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDTO));
    }

}
