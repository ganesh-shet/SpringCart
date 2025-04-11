package com.springkart.Controller;

import com.springkart.Payload.AddressDTO;
import com.springkart.Payload.Cart.CartDTO;
import com.springkart.Service.AddressService;
import com.springkart.model.User;
import com.springkart.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AuthUtil authUtil;

    @Autowired
    AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<AddressDTO>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/allAddress")
    public ResponseEntity<List<AddressDTO>> getAllAddress(){
        List<AddressDTO> addressList = addressService.getAllAddress();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTOS = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressByUser() {
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getAddressByUser(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO addressDTO,
                                                    @PathVariable Long addressId){
        AddressDTO addressDTOS = addressService.updateAddress(addressDTO, addressId);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @DeleteMapping("address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String confirmationMessage = String.valueOf(addressService.deleteAddress(addressId));
        return ResponseEntity.ok(confirmationMessage);
    }
//    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long addressId) {
//        AddressDTO addressDTOS = addressService.deleteAddress(addressId);
//        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
//    }
}
