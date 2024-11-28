package com.jyhun.shop.repository;

import com.jyhun.shop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
