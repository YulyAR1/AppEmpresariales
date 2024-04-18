package com.ventas.ventas.security.controller;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.ventas.ventas.dto.Mensaje;
import com.ventas.ventas.security.dto.JwtDto;
import com.ventas.ventas.security.dto.LoginUsuarioDto;
import com.ventas.ventas.security.entity.Rol;
import com.ventas.ventas.security.entity.Usuario;
import com.ventas.ventas.security.enums.RolNombre;
import com.ventas.ventas.security.jwt.JwtProvider;
import com.ventas.ventas.security.service.RolService;
import com.ventas.ventas.security.dto.NuevoUsuarioDto;
import com.ventas.ventas.security.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("Se detectaron campos mal puestos o un email inválido al intentar crear un nuevo usuario: {}", bindingResult.getAllErrors());
            return new ResponseEntity<>(new Mensaje("Campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            log.info("Intento de crear un nuevo usuario con un nombre de usuario que ya existe: {}", nuevoUsuario.getNombreUsuario());
            return new ResponseEntity<>(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            log.info("Intento de crear un nuevo usuario con un email que ya existe: {}", nuevoUsuario.getEmail());
            return new ResponseEntity<>(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if(rolService.obtenerTodos().isEmpty()){
            Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
            Rol rolUser = new Rol(RolNombre.ROLE_USER);
            rolService.save(rolAdmin);
            rolService.save(rolUser);
        }
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        log.info("Se creó el usuario {}", usuario.getNombreUsuario());
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuarioDto loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            log.error("Se recibieron datos incorrectos al intentar iniciar sesión: {}", bindingResult.getAllErrors());
            return new ResponseEntity(new Mensaje("datos incorrectos"), HttpStatus.BAD_REQUEST);
        }
        try {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        JwtDto jwtDto = new JwtDto(jwt);
        log.info("Usuario '{}' ha iniciado sesión correctamente.", loginUsuario.getNombreUsuario());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            log.error("Error al intentar autenticar al usuario '{}': {}", loginUsuario.getNombreUsuario(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        JwtDto jwt = new JwtDto(token);
        return new ResponseEntity(jwt, HttpStatus.OK);
    }
}
