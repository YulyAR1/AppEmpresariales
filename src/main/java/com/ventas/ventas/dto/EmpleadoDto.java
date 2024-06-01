package com.ventas.ventas.dto;

import com.ventas.ventas.security.entity.Usuario;
import lombok.Data;

@Data
public class EmpleadoDto {

    private int idEmpleado;

    private String correo;

    private String nombre;

    private String numeroDocumento;

    private Usuario usuario;

    public EmpleadoDto() {
    }

    public EmpleadoDto(int idEmpleado, String correo, String nombre, String numero_documento, Usuario usuario) {
        this.idEmpleado = idEmpleado;
        this.correo = correo;
        this.nombre = nombre;
        this.numeroDocumento = numero_documento;
        this.usuario = usuario;
    }
}
