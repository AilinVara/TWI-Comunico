package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
    private List<ProductoComprable> productosComprables;

    private LocalDateTime fechaDeCompra;

    public Compra(Usuario usuario) {
        this.usuario = usuario;
        this.fechaDeCompra = LocalDateTime.now();
        this.productosComprables = new ArrayList<>();
    }

    public Compra() {

    }

    public Double getPrecioTotal() {
        return productosComprables.stream()
                .mapToDouble(ProductoComprable::getPrecioProducto)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaDeCompra() {
        return fechaDeCompra;
    }


    public List<ProductoComprable> getProductosCompra() {
        return productosComprables;
    }


}