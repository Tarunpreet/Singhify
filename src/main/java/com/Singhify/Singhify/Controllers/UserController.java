package com.Singhify.Singhify.Controllers;


import com.Singhify.Singhify.Data.DTO.UserDTO;
import com.Singhify.Singhify.Services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO)
    {
        userServices.registerUser(userDTO);
        return new ResponseEntity<>("User Added", HttpStatus.OK);
    }
    @PostMapping("/auth/signin")
    public ResponseEntity<String> verifyUser(@RequestBody UserDTO userDTO)
    {
       Boolean Succesfull= userServices.verifyUser(userDTO);
        if(Succesfull)
        {
            return new ResponseEntity<>("Successful Login!",HttpStatus.OK);

        }

        return new ResponseEntity<>("Invalid Creds!",HttpStatus.UNAUTHORIZED);
    }



}
