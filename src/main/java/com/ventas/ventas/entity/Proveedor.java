package com.ventas.ventas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="proveedor")
public class Proveedor extends Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproveedor")
    private int idProveedor;

    @Column(name = "tipo_producto")
    private String tipoProducto;

    @JsonBackReference(value="productos")
    @OneToMany(mappedBy = "proveedor", cascade={CascadeType.ALL})
    private List<Producto> productos;
}
