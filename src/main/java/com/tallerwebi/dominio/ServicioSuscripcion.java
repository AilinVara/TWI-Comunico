package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SuscriptoException;
public interface ServicioSuscripcion {
        void suscripcionLibre(Usuario usuario);

        void comprarSuscripcionBasica(Usuario usuario) throws SuscriptoException;
        void comprarSuscripcionEstandar(Usuario usuario) throws SuscriptoException;
        void comprarSuscripcionPremium(Usuario usuario) throws SuscriptoException;

        void beneficioDescuentoSuscripcion(Usuario usuario, Integer porcentaje);

        void aplicarBeneficioSuscripcionBasica(Usuario usuario);
        void aplicarBeneficioSuscripcionEstandar(Usuario usuario);
        void aplicarBeneficioSuscripcionPremium(Usuario usuario);

        String descripcionSuscripciones(Long id);

        void verificarYActualizarSuscripcionesExpiradas();

        Boolean verificarSuscripcion(Usuario usuario);
   /* void actualizarSuscripcionUsuario(Usuario usuario,Long planId);
    void aplicarBeneficio(Usuario usuario);
*/

}
