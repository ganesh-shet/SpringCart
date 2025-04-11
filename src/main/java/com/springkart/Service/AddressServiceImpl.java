package com.springkart.Service;

import com.springkart.Exceptions.ResourceNotFoundException;
import com.springkart.Payload.AddressDTO;
import com.springkart.Payload.Category.CategoryDTO;
import com.springkart.model.Address;
import com.springkart.model.Category;
import com.springkart.model.User;
import com.springkart.repo.AddressRepository;
import com.springkart.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);

    }

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();

        return addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();

    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressByUser(User user) {
        List<Address> addresses = user.getAddresses();

        return addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, Long addressId) {
        Address savedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","id",addressId));

        savedAddress.setCity(addressDTO.getCity());
        savedAddress.setPincode(addressDTO.getPincode());
        savedAddress.setState(addressDTO.getState());
        savedAddress.setCountry(addressDTO.getCountry());
        savedAddress.setStreet(addressDTO.getStreet());
        savedAddress.setBuildingName(addressDTO.getBuildingName());

        Address newAddress = addressRepository.save(savedAddress);

        User user = savedAddress.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(newAddress);

        userRepository.save(user);

        return modelMapper.map(newAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        addressRepository.delete(address);
        return "Address with ID " + addressId + " has been successfully deleted.";
    }

    //BELOW CODE WILL RETURN THE DTA RESPONSE, SO I HAVE RETURNED THE STRING IN ABOVE CODE

//    public AddressDTO deleteAddress(Long addressId) {
//        Address address = addressRepository.findById(addressId)
//                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
//
//        addressRepository.delete(address);
//        return modelMapper.map(address, AddressDTO.class);
//    }
}
