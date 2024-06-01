package com.ventas.ventas.controller;

import com.ventas.ventas.dto.EmpleadoDto;
import com.ventas.ventas.dto.Mensaje;
import com.ventas.ventas.dto.ProveedorDto;
import com.ventas.ventas.entity.Empleado;
import com.ventas.ventas.entity.Proveedor;
import com.ventas.ventas.service.EmpleadoService;
import com.ventas.ventas.service.ProveedorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
@CrossOrigin("*")
public class EmpleadoController {
    EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Empleado>> list() {
        List<Empleado> list = empleadoService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody EmpleadoDto empleadoDto){
        if(StringUtils.isBlank(empleadoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(empleadoDto.getNumeroDocumento()))
            return new ResponseEntity(new Mensaje("el numero de documento es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(empleadoDto.getCorreo()))
            return new ResponseEntity(new Mensaje("el correo es obligatorio"), HttpStatus.BAD_REQUEST);

        if(empleadoService.existsByNombre(empleadoDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(empleadoDto.getIdEmpleado());
        empleado.setNumeroDocumento(empleadoDto.getNumeroDocumento());
        empleado.setNombre(empleadoDto.getNombre());
        empleado.setCorreo(empleadoDto.getCorreo());
        empleado.setUsuario(empleado.getUsuario());
        empleadoService.save(empleado);

        return new ResponseEntity(new Mensaje("empleado creado"), HttpStatus.OK);
    }
}
