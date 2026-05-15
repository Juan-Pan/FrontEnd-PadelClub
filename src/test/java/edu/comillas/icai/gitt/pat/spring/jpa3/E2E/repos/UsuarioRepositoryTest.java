package edu.comillas.icai.gitt.pat.spring.jpa3.E2E.repos;

import edu.comillas.icai.gitt.pat.spring.jpa3.entity.Rol;
import edu.comillas.icai.gitt.pat.spring.jpa3.entity.Usuario;
import edu.comillas.icai.gitt.pat.spring.jpa3.repos.RolRepository;
import edu.comillas.icai.gitt.pat.spring.jpa3.repos.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Test
    @DisplayName("Debe buscar usuario por email")
    void buscarUsuarioPorEmail() {
        Rol rol = rolRepository.findByNombreRol("USER").orElseGet(() -> {
            Rol r = new Rol();
            r.nombreRol = "USER";
            r.descripcion = "Usuario";
            return rolRepository.save(r);
        });

        Usuario usuario = new Usuario();
        usuario.nombre = "Carlos";
        usuario.apellidos = "Perez";
        usuario.email = "carlos@test.com";
        usuario.password = "pass";
        usuario.telefono = "622222222";
        usuario.rol = rol;
        usuario.fechaRegistro = new Date();
        usuario.activo = true;
        usuarioRepository.save(usuario);

        Optional<Usuario> resultado = usuarioRepository.findByEmail("carlos@test.com");

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().nombre);
    }
}