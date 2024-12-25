package com.Singhify.Singhify.Controllers;


import com.Singhify.Singhify.Data.DTO.SignInDTO;
import com.Singhify.Singhify.Data.DTO.UserDTO;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Services.CustomUserDetailsService;
import com.Singhify.Singhify.Services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Base64;

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
    public ResponseEntity<SignInDTO> verifyUser(HttpServletRequest request) {

        {
            String token = null;
           String[] credDetials=getDetailsFromHeader(request.getHeader("Authorization"));
           SignInDTO signInDTO = userServices.verifyUser(credDetials);
           return new ResponseEntity<>(signInDTO, HttpStatus.OK);

        }


    }

    private String[] getDetailsFromHeader(String authorization) {
        String token=authorization.substring(6);
       String decodedToken=new String(Base64.getDecoder().decode(token));
       String[] credDetails=decodedToken.split(":");
       return credDetails;
    }
}
