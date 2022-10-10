package com.rea.app.user.entity;

import com.rea.app.common.model.BaseEntity;
import com.rea.app.role.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"user_email","phone_number"}))
@NoArgsConstructor
@Setter
@Getter
public class User extends BaseEntity implements UserDetails {
    @Column(name = "username", nullable = false, length = 255, unique = true)
    private String username;

    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Column(name = "user_email", nullable = true, unique = true)
    private String email;

    @Size(max = 10,min = 10, message = "Size Must be Exactly 10 digit")
    @Column(name = "phone_number", nullable = true, unique = true, length = 10)
    private String phone;

    @Size(min = 6, message = "Size Must be More then 6")
    @Column(name = "user_password", nullable = true, length = 100)
    private String password;

    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    private Long otpGeneratedTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "users", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
