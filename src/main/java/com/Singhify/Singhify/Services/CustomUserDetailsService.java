package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Models.Users;
import com.Singhify.Singhify.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user=userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
          return  User.builder().
                    username(username)
                    .password(user.getUserPassword())
                    .roles(user.getUserRoles().
                                 stream()
                                .map(role->role.getRoleName().toString())  // Convert each enum to its name (String)
                                .toArray(String[]::new)
                           )
                  .build();

    }
}
