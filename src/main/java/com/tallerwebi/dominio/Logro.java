package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private boolean desbloqueado;

    @Lob
    private byte[] imagen;

    @OneToMany(mappedBy = "logro", cascade = CascadeType.ALL)
    private List<CondicionLogro> condiciones = new ArrayList<>();

    public Logro() {}

    public Logro(String nombre, byte[] imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.desbloqueado = false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<CondicionLogro> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(List<CondicionLogro> condiciones) {
        this.condiciones = condiciones;
    }

//    // Método para añadir una condición
//    public void agregarCondicion(CondicionLogro condicion) {
//        condicion.setLogro(this);
//        this.condiciones.add(condicion);
//    }
//
//    // Método para verificar si el logro se desbloquea (si todas las condiciones están completas)
//    public void verificarDesbloqueo() {
//        boolean todasCompletadas = condiciones.stream().allMatch(CondicionLogro::isCompletada);
//        if (todasCompletadas) {
//            this.desbloqueado = true;
//        }
//    }
}
