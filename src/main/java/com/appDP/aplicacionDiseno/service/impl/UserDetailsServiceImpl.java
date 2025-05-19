package com.appDP.aplicacionDiseno.service.impl;

import com.appDP.aplicacionDiseno.model.AppUser;
import com.appDP.aplicacionDiseno.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser usuario = repo.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        return User.withUsername(usuario.getEmail())
                .password(usuario.getContraseña())
                .roles("USER")
                .build();
    }
}
