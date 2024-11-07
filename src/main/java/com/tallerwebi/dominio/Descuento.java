package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Descuento {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String codigo;

        private Integer cantDescuento;

        public Descuento() {}


        public Descuento(Integer cantDescuento) {
            this.cantDescuento = cantDescuento;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public Integer getCantDescuento() {
            return cantDescuento;
        }

        public void setCantDescuento(Integer cantDescuento) {
            this.cantDescuento = cantDescuento;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Descuento descuento = (Descuento) o;
            return Objects.equals(id, descuento.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
