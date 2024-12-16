package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.UserDTO;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {


    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserServices(AuthenticationService authenticationService, UserRepository userRepository,
                        PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public void registerUser(UserDTO userDTO) {

        userDTO.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        Users saveUser=mapper.map(userDTO, Users.class);
        userRepository.save(saveUser);
    }

    public Boolean verifyUser(UserDTO userDTO) {
          return authenticationService.authenticate(userDTO);
        }
}