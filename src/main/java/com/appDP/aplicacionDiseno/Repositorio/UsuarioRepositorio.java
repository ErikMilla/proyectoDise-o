
package com.appDP.aplicacionDiseno.Repositorio;
import com.appDP.aplicacionDiseno.Modelo.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepositorio extends JpaRepository<AppUser,Integer>{
        public AppUser findByEmail(String email); 
}
