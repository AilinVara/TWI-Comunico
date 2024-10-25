package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Vida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidadDeVidasActuales;
    private LocalDateTime ultimaVezQueSeRegeneroLaVida;


    public Vida() {
        this.cantidadDeVidasActuales = 5;  // Constructor por default
        this.ultimaVezQueSeRegeneroLaVida = LocalDateTime.now();
    }

    public Vida(Integer cantidadDeVidasActuales) {
        this.cantidadDeVidasActuales = cantidadDeVidasActuales;  // Le da el valor que le pase en el constructor de arriba
        this.ultimaVezQueSeRegeneroLaVida = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getCantidadDeVidasActuales() {
        return cantidadDeVidasActuales;
    }

    public void setCantidadDeVidasActuales(Integer cantidadDeVidasActuales) {
        this.cantidadDeVidasActuales = cantidadDeVidasActuales;
    }

    public LocalDateTime getUltimaVezQueSeRegeneroLaVida() {
        return ultimaVezQueSeRegeneroLaVida;
    }

    public void setUltimaVezQueSeRegeneroLaVida(LocalDateTime ultimaVezQueSeRegeneroLaVida) {
        this.ultimaVezQueSeRegeneroLaVida = ultimaVezQueSeRegeneroLaVida;
    }
}
