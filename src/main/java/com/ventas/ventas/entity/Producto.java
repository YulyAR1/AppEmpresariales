package com.ventas.ventas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name="producto")
public class Producto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idproducto")
    private int id;
    @Column(name="ean")
    private String ean;
    @Column(name="cantidad")
    private int cantidad;
    @Column(name="descripcion_producto")
    private String nombre;
    @Column(name="fecha_ingreso_producto")
    private String fecha;
    @Column(name="valor_venta")
    private float precio;

   // @JsonManagedReference
    //@ManyToOne(cascade={CascadeType.ALL})
    @ManyToOne()
    @JoinColumn(name = "idproveedor")
    private Proveedor proveedor;
    @OneToMany(mappedBy = "producto", cascade={CascadeType.ALL})
    private List<DetalleVenta> detalleVentas;


    public Producto(int id, String ean, int cantidad, String nombre, String fecha, float precio, Proveedor proveedor, List<DetalleVenta> detalleVentas) {
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