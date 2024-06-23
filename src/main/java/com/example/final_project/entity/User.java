package com.example.final_project.entity;

import com.example.final_project.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "Name", nullable = false)
    private String name;

    @NotNull
    @Email
    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "Role", nullable = false)
    private Role role;

//        @OneToOne(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
//        private Cart cart;
//
//        @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
//        private Set<Favorites> favorites = new HashSet<>();
//
//        @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
//        private Set<Orders> orders = new HashSet<>();
}

