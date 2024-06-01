package com.ventas.ventas.controller;

import com.ventas.ventas.dto.EmpleadoDto;
import com.ventas.ventas.dto.Mensaje;
import com.ventas.ventas.entity.Empleado;
import com.ventas.ventas.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class EmpleadoControllerTest {

    @Autowired
    EmpleadoController empleadoController;

    @MockBean
    EmpleadoService empleadoService;

    @Test
    public void testList() {
        Empleado empleado1 = new Empleado();
        empleado1.setIdEmpleado(1);
        empleado1.setNombre("Empleado1");

        Empleado empleado2 = new Empleado();
        empleado2.setIdEmpleado(2);
        empleado2.setNombre("Empleado2");

        when(empleadoService.list()).thenReturn(Arrays.asList(empleado1, empleado2));

        ResponseEntity<List<Empleado>> response = empleadoController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateEmpleado() {
        EmpleadoDto empleadoDto = new EmpleadoDto();
        empleadoDto.setIdEmpleado(1);
        empleadoDto.setNombre("Empleado1");
        empleadoDto.setNumeroDocumento("123456789");
        empleadoDto.setCorreo("empleado1@mail.com");

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(empleadoDto.getIdEmpleado());
        empleado.setNombre(empleadoDto.getNombre());
        empleado.setNumeroDocumento(empleadoDto.getNumeroDocumento());
        empleado.setCorreo(empleadoDto.getCorreo());

        when(empleadoService.existsByNombre(empleadoDto.getNombre())).thenReturn(false);
        when(empleadoService.save(any(Empleado.class))).thenReturn(empleado);

        ResponseEntity<?> response = empleadoController.create(empleadoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("empleado creado", ((Mensaje) response.getBody()).getMensaje());
    }
}