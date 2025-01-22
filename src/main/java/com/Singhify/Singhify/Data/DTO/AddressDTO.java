package com.Singhify.Singhify.Data.DTO;

import com.Singhify.Singhify.Enum.Country;
import com.Singhify.Singhify.Enum.Roles;
import com.Singhify.Singhify.Models.Users;
import jakarta.persistence.*;
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
public class AddressDTO {

    long id;

    private long userId;

    private Country country;

    private String state;

    private String streetAdd; ;

    private String city;

    private String postalCode;

    private String addressType;
}
