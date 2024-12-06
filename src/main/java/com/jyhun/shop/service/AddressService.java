package com.jyhun.shop.service;

import com.jyhun.shop.dto.AddressRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.Address;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public ResponseDTO saveAndUpdateAddress(AddressRequestDTO addressRequestDTO) {
        User user = userService.getLoginUser();
        Address currentAddress = user.getAddress();

        if(currentAddress == null) {
            Address newAddress = createNewAddress(addressRequestDTO);
            user.changeAddress(newAddress);
            addressRepository.save(newAddress);
        }else {
            updateAddress(addressRequestDTO, currentAddress);
        }

        String message = (user.getAddress() == null) ? "주소 생성 성공" : "주소 변경 성공";

        return ResponseDTO.builder()
                .status(200)
                .message(message)
                .data(null)
                .build();
    }

    private Address createNewAddress(AddressRequestDTO addressRequestDTO) {
        return Address.builder()
                .city(addressRequestDTO.getCity())
                .street(addressRequestDTO.getStreet())
                .zipCode(addressRequestDTO.getZipCode())
                .build();
    }

    private static void updateAddress(AddressRequestDTO addressRequestDTO, Address currentAddress) {
        currentAddress.updateAddress(
                addressRequestDTO.getCity(),
                addressRequestDTO.getStreet(),
                addressRequestDTO.getZipCode()
        );
    }

}
