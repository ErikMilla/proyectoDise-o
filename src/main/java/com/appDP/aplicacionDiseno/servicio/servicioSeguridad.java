package com.appDP.aplicacionDiseno.servicio;

import com.appDP.aplicacionDiseno.Modelo.AppUser;
import com.appDP.aplicacionDiseno.Repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class servicioSeguridad implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = repo.findByEmail(email);
        if(appUser != null){
            return User.withUsername(appUser.getEmail())
                    .password(appUser.getContraseña())
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}
