package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.SignInDTO;
import com.Singhify.Singhify.Data.DTO.UserDTO;
import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    public SignInDTO verifyUser(String[] credDetails) {
        Users user=userRepository.findByUserName(credDetails[0]);
          return authenticationService.authenticate(user,credDetails);
        }
    public String getLoggedInUserName()
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser= (User) auth.getPrincipal();
        String name=loggedInUser.getUsername();
        return name;
    }
    public Users getLoggedInUserId()
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser= (User) auth.getPrincipal();
        String name=loggedInUser.getUsername();
        Users loggedInUserDb=userRepository.findByUserName(name);
        return loggedInUserDb;
    }
}