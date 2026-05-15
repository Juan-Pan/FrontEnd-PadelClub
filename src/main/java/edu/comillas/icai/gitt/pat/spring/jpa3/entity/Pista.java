package edu.comillas.icai.gitt.pat.spring.jpa3.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Pista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonAlias({"id_pista"})
    public Long idPista;

    @Column(nullable = false, unique = true)
    public String nombre;

    @Column(nullable = false)
    public String ubicacion;

    @Column(nullable = false)
    public Double precioHora;

    @Column(nullable = false)
    public Boolean activa;

    @Column(nullable = false)
    public Date fechaAlta;


}
