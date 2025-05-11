
package com.appDP.aplicacionDiseno.Modelo;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;


public class RegistroDto {
@NotEmpty
private String nombre;
@NotEmpty
private String apellidos;
@NotEmpty
@Email
private String email;
private String telefono;
@NotEmpty
@Column(unique=true)
private String direccion;
@Size(min=6, message= "La contraseña debe tener un minimo de 6 caracteres")
private String contraseña;
private String confircontraseña;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getConfircontraseña() {
        return confircontraseña;
    }

    public void setConfircontraseña(String confircontraseña) {
        this.confircontraseña = confircontraseña;
    }


}
