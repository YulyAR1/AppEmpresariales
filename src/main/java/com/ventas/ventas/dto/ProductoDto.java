package com.ventas.ventas.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ventas.ventas.entity.DetalleVenta;
import com.ventas.ventas.entity.Proveedor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class ProductoDto implements Serializable {
    private int id;
    private String ean;

    private int cantidad;

    private String nombre;

    private String fecha;

    private Float precio;

    private Proveedor proveedor;

    private List<DetalleVenta> detalleVentas;

    public ProductoDto() {
    }

    public ProductoDto(int id, String ean, int cantidad, String nombre, String fecha, float precio, Proveedor proveedor, List<DetalleVenta> detalleVentas) {
        this.id = id;
        this.ean = ean;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.fecha = fecha;
        this.precio = precio;
        this.proveedor = proveedor;
        this.detalleVentas = detalleVentas;
    }
}