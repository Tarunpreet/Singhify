package com.Singhify.Singhify.Repos;

import com.Singhify.Singhify.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUserName(String username);
}
