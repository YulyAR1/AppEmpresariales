package com.ventas.ventas.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="venta")
public class Venta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idventa")
    private int id;
    @Column(name="fecha")
    private String fecha;
    @Column(name="valor")
    private Float valor;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
    @OneToMany(mappedBy = "venta", cascade={CascadeType.ALL})
    private List<DetalleVenta> detalleVentas;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "idempleado")
    private Empleado empleado;

}
