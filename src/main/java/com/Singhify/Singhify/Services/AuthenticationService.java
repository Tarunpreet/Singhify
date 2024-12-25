package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.SignInDTO;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Utilities.JWTUtil;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {


       @Autowired
    JWTUtil jwtUtil;
        private final AuthenticationManager authenticationManager;


        public AuthenticationService(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        public SignInDTO authenticate(Users users, String[] credDetails) {
            String token=null;
            Map<String, Object> claim=new HashMap<>();
            claim.put("user_id",users.getUserId());
            claim.put("user_roles",users.getUserRoles());
            String givenUserName=credDetails[0];
            String givenPassword=credDetails[1];
            SignInDTO signInDTO=new SignInDTO();
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(givenUserName,givenPassword)
                        //Internally calls the loadbyusername of custom user details and pass encoder
                );

                token=jwtUtil.generateToken(users.getUserName(),users.getUserRoles(),claim);
                signInDTO.setUserId(users.getUserId());
                signInDTO.setAuthentication(token);
                signInDTO.setUserName(users.getUserName());
                signInDTO.setUserRoles(users.getUserRoles());
                
            } catch (Exception e) {
                throw new UsernameNotFoundException("Please check creds and try again! ");
            }
            return signInDTO;
        }


    }
