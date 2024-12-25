package com.Singhify.Singhify.Data.DTO;

import com.Singhify.Singhify.Enum.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SignInDTO {

    int userId;

    String userName;

    private Set<Roles> userRoles=new HashSet<>();

    String Authentication;
}
