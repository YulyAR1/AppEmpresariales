package com.ventas.ventas.dto;


import com.ventas.ventas.entity.Producto;
import lombok.Data;


import java.util.List;

@Data
public class ProveedorDto{

    private int idProveedor;

    private String numeroDocumento;

    private String nombre;

    private String correo;

    private String tipoProducto;

    private List<Producto> productos;

    public ProveedorDto() {
    }

    public ProveedorDto(int idProveedor, String numeroDocumento, String nombre, String correo, String tipoProducto, List<Producto> productos) {
        this.idProveedor = idProveedor;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.correo = correo;
        this.tipoProducto = tipoProducto;
        this.productos = productos;
    }
}
