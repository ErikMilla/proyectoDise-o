package com.appDP.aplicacionDiseno.Seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class confSeguridad {
   @Bean
   public SecurityFilterChain SercurityFilterChain(HttpSecurity http) throws Exception{
       return http
           .authorizeHttpRequests(auth -> auth
               .requestMatchers("/", "/medicion", "/login", "/registro", "/registro/**", "/registrar").permitAll()
               .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/webjars/**").permitAll()
               .anyRequest().authenticated()
           )
           .formLogin(form -> form
               .loginPage("/login")
               .defaultSuccessUrl("/")
               .permitAll()
           )
           .csrf(csrf -> csrf.disable())
           .logout(logout -> logout
               .logoutSuccessUrl("/")
               .permitAll()
           )
           .build();      
   }
   
   @Bean
   public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }
}
