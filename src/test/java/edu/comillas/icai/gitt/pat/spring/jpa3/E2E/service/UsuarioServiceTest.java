package edu.comillas.icai.gitt.pat.spring.jpa3.E2E.service;


import edu.comillas.icai.gitt.pat.spring.jpa3.entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.jpa3.repos.UsuarioRepository;
import edu.comillas.icai.gitt.pat.spring.jpa3.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import edu.comillas.icai.gitt.pat.spring.jpa3.repos.RolRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    RolRepository rolRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void crearUsuario_Exito() {
        Usuario usuario = new Usuario();
        usuario.email = "nuevo@test.com";
        usuario.nombre = "nombre";
        usuario.apellidos = "apellidos";
        usuario.password = "pass";
        usuario.telefono = "123456";

        when(usuarioRepository.findByEmail(usuario.email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(usuario.password)).thenReturn("encoded");
        when(rolRepository.findByNombreRol("USER")).thenReturn(Optional.of(new edu.comillas.icai.gitt.pat.spring.jpa3.entity.Rol()));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.crearUsuario(usuario);

        assertNotNull(resultado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void crearUsuario_ErrorSiEmailExiste() {
        Usuario usuario = new Usuario();
        usuario.email = "existe@test.com";
        usuario.nombre = "nombre";
        usuario.apellidos = "apellidos";
        usuario.password = "pass";
        usuario.telefono = "123456";

        when(usuarioRepository.findByEmail(usuario.email)).thenReturn(Optional.of(usuario));

        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> {
            usuarioService.crearUsuario(usuario);
        });
    }
}