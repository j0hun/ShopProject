package com.jyhun.shop.service;

import com.jyhun.shop.dto.AddressDTO;
import com.jyhun.shop.dto.Response;
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

    public Response saveAndUpdateAddress(AddressDTO addressDTO) {
        User user = userService.getLoginUser();
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
            address.setUser(user);
        }
        if (addressDTO.getStreet() != null) address.setStreet(address.getStreet());
        if (addressDTO.getCity() != null) address.setCity(addressDTO.getCity());
        if (addressDTO.getZipCode() != null) address.setZipCode(addressDTO.getZipCode());
        addressRepository.save(address);

        String message = (user.getAddress() == null) ? "Address successfully created" : "Address successfully updated";
        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }

}
