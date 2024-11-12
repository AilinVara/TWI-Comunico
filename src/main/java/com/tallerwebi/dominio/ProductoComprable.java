package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity

public class ProductoComprable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Compra compra;

        @OneToOne
        private Vida vida;

        private Double precioProducto;

        public ProductoComprable(Compra compra){
            this.compra = compra;
        }

        public ProductoComprable() {}

        public Vida getLibro() {
            return vida;
        }

        public void setLibro(Vida vida) {
            this.vida = vida;
//            this.precioProducto = vida.getPrecio();
        }

        public Double getPrecioProducto() {
            return precioProducto;
        }

        public Compra getCompra() {
            return compra;
        }

        public void setCompra(Compra compra) {
            this.compra = compra;
        }
    }
