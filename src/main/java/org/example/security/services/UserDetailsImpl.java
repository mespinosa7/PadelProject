package org.example.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
/**
 * Implementación de la interfaz UserDetails que representa los detalles del usuario para la autenticación y autorización en Spring Security.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;



    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    /**
     * Constructor para crear una instancia UserDetailsImpl con los detalles del usuario.
     * @param id ID del usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param authorities Autoridades del usuario.
     */
    public UserDetailsImpl(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;

        this.password = password;
        this.authorities = authorities;
    }
    /**
     * Método estático para construir un UserDetailsImpl a partir de un objeto User.
     * @param user Objeto User del cual se extraen los detalles.
     * @return UserDetailsImpl construido a partir del objeto User.
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName().name()));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
    // Métodos de la interfaz UserDetails.. getters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
