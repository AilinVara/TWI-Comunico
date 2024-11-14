package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String rol;
    private Boolean activo = false;
    private Integer comunicoPoints;
    private Integer ayudas;
    private String titulo = "Principiante";
    private String descripcion;
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String foto;
    private String tokenDeVerificacion;
    private Boolean emailVerificado = false;

    @OneToOne
    private Vida vida;

    @OneToOne
    private Experiencia experiencia;

    @ManyToOne
    @JoinColumn(name = "suscripcion_id")
    private Suscripcion suscripcion;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Descuento> cuponesDeDescuento = new HashSet<>();


    private Integer descuentosEmitidos = 0;

    public Integer getDescuentosEmitidos() {
        return descuentosEmitidos;
    }

    public Set<Descuento> getCuponesDeDescuento() {
        return cuponesDeDescuento;
    }

    public void setCuponesDeDescuento(Set<Descuento> cuponesDeDescuento) {
        this.cuponesDeDescuento = cuponesDeDescuento;
    }

    public void setDescuentosEmitidos(Integer descuentosEmitidos) {
        this.descuentosEmitidos = descuentosEmitidos;
    }


    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public Vida getVida() {
        return vida;
    }

    public void setVida(Vida vida) {
        this.vida = vida;
    }

    public void activar() {
        this.activo = true;
    }

    public Integer getComunicoPoints() {
        return comunicoPoints;
    }

    public void setComunicoPoints(Integer comunicoPoints) {
        this.comunicoPoints = comunicoPoints;
    }

    public Integer getAyudas() {
        return ayudas;
    }

    public void setAyudas(Integer ayudas) {
        this.ayudas = ayudas;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTokenDeVerificacion() {
        return tokenDeVerificacion;
    }

    public void setTokenDeVerificacion(String tokenDeVerificacion) {
        this.tokenDeVerificacion = tokenDeVerificacion;
    }

    public Boolean getEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(Boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
