package com.Singhify.Singhify.Data.DTO;

import com.Singhify.Singhify.Enum.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UserDTO {

    int userId;

    String userName;

    @Email
    String UserEmail;

    @NotNull
    @Size(max = 10,min = 10)
    String  userPhone;

    @NotNull
    @NotBlank
    @Size(max = 15,min = 5)
    String  userPassword;

    private Set<Roles> userRoles=new HashSet<>();

}
