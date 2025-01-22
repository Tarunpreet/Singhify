package com.Singhify.Singhify.Models;

import com.Singhify.Singhify.Enum.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @NotNull
    private Country country;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String streetAdd; ;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String postalCode;

    private String addressType;

}
