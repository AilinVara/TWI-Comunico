package com.tallerwebi.dominio;

public interface RepositorioDescuento {
        void guardarDescuento(Descuento descuento);
        void modificarCodigoDescuento(CodigoDescuento codigoDescuento);
        void guardarCodigoDescuento(CodigoDescuento codigoDescuento);
        CodigoDescuento buscarCodigoDescuento(Long id);

        Descuento buscarDescuentoPorId(Long id);

        void eliminarDescuento(Descuento descuento);
    }