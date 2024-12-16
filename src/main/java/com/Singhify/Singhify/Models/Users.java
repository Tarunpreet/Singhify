package com.Singhify.Singhify.Models;

import com.Singhify.Singhify.Enum.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    int userId;

    @NotBlank
    @NotNull
    String userName;


    @NotBlank
    @NotNull
    @Email

    String UserEmail;

    @NotNull
    @Size(max = 10,min = 10)
    String  userPhone;

    @NotNull
    @NotBlank
    @Size(max = 15,min = 5)
    String  userPassword;

    @ElementCollection(targetClass = Roles.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
    private Set<Roles> userRoles=new HashSet<>();

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST},
            orphanRemoval = true)
    private Set<Product> products;

}
