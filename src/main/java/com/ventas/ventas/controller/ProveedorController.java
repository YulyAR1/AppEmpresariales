package com.ventas.ventas.controller;

import com.ventas.ventas.dto.Mensaje;
import com.ventas.ventas.dto.ProveedorDto;
import com.ventas.ventas.entity.Proveedor;
import com.ventas.ventas.service.ProveedorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/proveedor")
@CrossOrigin("*")
public class ProveedorController {

    ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Proveedor>> list() {
        List<Proveedor> list = proveedorService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody ProveedorDto proveedorDto){
        if(StringUtils.isBlank(proveedorDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(proveedorDto.getNumeroDocumento()))
            return new ResponseEntity(new Mensaje("el numero de documento es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(proveedorDto.getCorreo()))
            return new ResponseEntity(new Mensaje("el correo es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(proveedorDto.getTipoProducto()))
            return new ResponseEntity(new Mensaje("el tipo d eproducto es obligatorio"), HttpStatus.BAD_REQUEST);

        if(proveedorService.existsByNombre(proveedorDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(proveedorDto.getIdProveedor());
        proveedor.setNumeroDocumento(proveedorDto.getNumeroDocumento());
        proveedor.setNombre(proveedorDto.getNombre());
        proveedor.setCorreo(proveedorDto.getCorreo());
        proveedor.setTipoProducto(proveedorDto.getTipoProducto());
        proveedor.setProductos(proveedorDto.getProductos());

        proveedorService.save(proveedor);
        return new ResponseEntity(new Mensaje("proveedor creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!proveedorService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        proveedorService.delete(id);
        return new ResponseEntity(new Mensaje("proveedor eliminado"), HttpStatus.OK);
    }


}
