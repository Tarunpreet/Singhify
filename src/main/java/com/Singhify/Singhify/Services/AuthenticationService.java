package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.UserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


        private final AuthenticationManager authenticationManager;

        public AuthenticationService(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        public Boolean authenticate(UserDTO userDTO) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getUserPassword())
                );
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
