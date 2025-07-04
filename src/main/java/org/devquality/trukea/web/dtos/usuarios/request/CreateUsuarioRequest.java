package org.devquality.trukea.web.dtos.usuarios.request;

public class CreateUsuarioRequest {

    private String nombre;
    private String correo;
    private String contrasenia;

    public CreateUsuarioRequest() {
    }

    public CreateUsuarioRequest(String nombre, String correo, String contrasenia) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    // Método de validación
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty() &&
                correo != null && !correo.trim().isEmpty() &&
                contrasenia != null && !contrasenia.trim().isEmpty();
    }
}