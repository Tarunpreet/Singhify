package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.AddressDTO;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Exception.NotValid;
import com.Singhify.Singhify.Models.Address;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.AddressRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserServices userServices;

    @Autowired
    ModelMapper mapper;

    public AddressDTO addAddress(AddressDTO addressDTO) {
        Users loggedInUser= userServices.getLoggedInUserId();
        Address address=mapper.map(addressDTO,Address.class);
        address.setUser(loggedInUser);
        // Add the address to the user's address list
        List<Address> addressList = loggedInUser.getAddressList();
        if (addressList == null) {
            addressList = new ArrayList<>();
            loggedInUser.setAddressList(addressList);
        }
        if(addressList.size()==5)
        {
          throw  new NotValid("Cant add more than 5 addresses. ");
        }
        addressList.add(address);

        Address savedAddress=addressRepo.save(address);
        return customMapToDTO(savedAddress);

    }




    private AddressDTO customMapToDTO(Address savedAddress) {
        AddressDTO addressDTO=new AddressDTO();
        addressDTO.setAddressType(savedAddress.getAddressType());
        addressDTO.setId(savedAddress.getId());
        addressDTO.setState(savedAddress.getState());
        addressDTO.setCity(savedAddress.getCity());
        addressDTO.setCountry(savedAddress.getCountry());
        addressDTO.setPostalCode(savedAddress.getPostalCode());
        addressDTO.setStreetAdd(savedAddress.getStreetAdd());
       addressDTO.setUserId(savedAddress.getUser().getUserId());
       return addressDTO;
    }
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public AddressDTO updateAddress(long addressId, AddressDTO addressDTO) {
        Address existingAddress=addressRepo.findById(addressId).orElseThrow(
                ()->new EntityNotFoundException("Address","Id",addressId)
        );
        // Update only if the fields are non-null and not empty
        if (isNotEmpty(addressDTO.getAddressType())) {
            existingAddress.setAddressType(addressDTO.getAddressType());
        }
        if (isNotEmpty(addressDTO.getCity())) {
            existingAddress.setCity(addressDTO.getCity());
        }
        if (isNotEmpty(addressDTO.getCountry().toString())) {
            existingAddress.setCountry(addressDTO.getCountry());
        }
        if (isNotEmpty(addressDTO.getState())) {
            existingAddress.setState(addressDTO.getState());
        }
        if (isNotEmpty(addressDTO.getStreetAdd())) {
            existingAddress.setStreetAdd(addressDTO.getStreetAdd());
        }
        if (isNotEmpty(addressDTO.getPostalCode())) {
            existingAddress.setPostalCode(addressDTO.getPostalCode());
        }
        Address updatedAddress=addressRepo.save(existingAddress);
        return customMapToDTO(updatedAddress);
     }

    public List<AddressDTO> getAddresses() {
        Users loggedInUser= userServices.getLoggedInUserId();
        List<Address> addressList = loggedInUser.getAddressList();
        if (addressList==null)
        {
            throw new EntityNotFoundException("Addresses for this user");
        }
        List<AddressDTO> addressDTOList=addressList.stream()
                .map(address -> customMapToDTO(address)).toList();

        return addressDTOList;
    }
}
