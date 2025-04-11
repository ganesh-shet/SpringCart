package com.springkart.Service;

import com.springkart.Payload.AddressDTO;
import com.springkart.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAllAddress();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAddressByUser(User user);

    AddressDTO updateAddress(@Valid AddressDTO addressDTO, Long addressId);

    String deleteAddress(Long addressId);
}
