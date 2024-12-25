package com.Singhify.Singhify.Models;

import com.Singhify.Singhify.Enum.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = "userEmail"), @UniqueConstraint(columnNames = "userName")})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotBlank
    @NotNull
    private String userName;

    @NotBlank
    @NotNull
    @Email
    private String userEmail;

    @NotNull
    @Size(max = 10, min = 10)
    private String userPhone;

    @NotNull
    @NotBlank
    @Size(max = 15, min = 5)
    private String userPassword;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
    private Set<Roles> userRoles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private Set<Product> products;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"cart"})
    private Cart cart;

}
