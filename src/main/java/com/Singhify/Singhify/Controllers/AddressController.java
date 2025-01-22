package com.Singhify.Singhify.Controllers;

import com.Singhify.Singhify.Data.DTO.AddressDTO;
import com.Singhify.Singhify.Models.Address;
import com.Singhify.Singhify.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO)
    {
        AddressDTO addedAddressDTO=addressService.addAddress(addressDTO);
        return new ResponseEntity<>(addedAddressDTO, HttpStatus.OK);
    }
    @PostMapping("/update/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable long addressId,@RequestBody AddressDTO addressDTO)
    {
        AddressDTO addedAddressDTO=addressService.updateAddress(addressId,addressDTO);
        return new ResponseEntity<>(addedAddressDTO, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<AddressDTO>> getAddresses()
    {
        List<AddressDTO> addedAddressDTO=addressService.getAddresses();
        return new ResponseEntity<>(addedAddressDTO, HttpStatus.OK);
    }

}
