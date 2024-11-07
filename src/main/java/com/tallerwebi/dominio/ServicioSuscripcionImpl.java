package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.SuscriptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class ServicioSuscripcionImpl implements ServicioSuscripcion {

        private RepositorioSuscripcion repositorioSuscripcion;
        private RepositorioUsuario repositorioUsuario;
        private RepositorioDescuento repositorioDescuento;
        private RepositorioTipoSuscripcion repositorioTipoSuscripcion;

        private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final SecureRandom RANDOM = new SecureRandom();

        @Autowired
        public ServicioSuscripcionImpl(RepositorioSuscripcion repositorioSuscripcion, RepositorioUsuario repositorioUsuario, RepositorioDescuento repositorioDescuento, RepositorioTipoSuscripcion repositorioTipoSuscripcion) {
            this.repositorioSuscripcion = repositorioSuscripcion;
            this.repositorioUsuario = repositorioUsuario;
            this.repositorioDescuento = repositorioDescuento;
            this.repositorioTipoSuscripcion = repositorioTipoSuscripcion;
        }

        @Override
        public void suscripcionLibre(Usuario usuario) {
            usuario.setSuscripcion(repositorioSuscripcion.buscarSuscripcion(1L));
        }

        @Override
        public void comprarSuscripcionBasica(Usuario usuario) throws SuscriptoException {
            if (verificarSuscripcion(usuario)) {
                usuario.setSuscripcion(repositorioSuscripcion.buscarSuscripcion(2L));
                usuario.getSuscripcion().setFechaCompra(LocalDateTime.now());
                usuario.getSuscripcion().setFechaVencimiento(LocalDateTime.now().plusMonths(1));
                usuario.setDescuentosEmitidos(1);
                repositorioUsuario.modificar(usuario);
            } else {
                throw new SuscriptoException();
            }
        }

        @Override
        public void comprarSuscripcionEstandar(Usuario usuario) throws SuscriptoException {
            if (verificarSuscripcion(usuario)) {
                usuario.setSuscripcion(repositorioSuscripcion.buscarSuscripcion(3L));
                usuario.getSuscripcion().setFechaCompra(LocalDateTime.now());
                usuario.getSuscripcion().setFechaVencimiento(LocalDateTime.now().plusMonths(1));
                usuario.setDescuentosEmitidos(1);
                repositorioUsuario.modificar(usuario);
            } else {
                throw new SuscriptoException();
            }
        }

        @Override
        public void comprarSuscripcionPremium(Usuario usuario) throws SuscriptoException {
            if (verificarSuscripcion(usuario)) {
                usuario.setSuscripcion(repositorioSuscripcion.buscarSuscripcion(4L));
                usuario.getSuscripcion().setFechaCompra(LocalDateTime.now());
                usuario.getSuscripcion().setFechaVencimiento(LocalDateTime.now().plusMonths(1));
                usuario.setDescuentosEmitidos(1);
                repositorioUsuario.modificar(usuario);
            } else {
                throw new SuscriptoException();
            }
        }

        @Override
        public void beneficioDescuentoSuscripcion(Usuario usuario, Integer porcentaje) {
            Descuento a1 = new Descuento(porcentaje);
            a1.setCodigo(generateRandomString());
            repositorioDescuento.guardarDescuento(a1);
            CodigoDescuento codigos = repositorioDescuento.buscarCodigoDescuento(1L);
            codigos.getCuponesDescuento().add(a1);
            repositorioDescuento.modificarCodigoDescuento(codigos);
            usuario.getCuponesDeDescuento().add(a1);
            usuario.setDescuentosEmitidos(usuario.getDescuentosEmitidos() + 1);
            repositorioUsuario.modificar(usuario);
        }

        @Override
        public void aplicarBeneficioSuscripcionBasica(Usuario usuario) {
            if (usuario != null && usuario.getSuscripcion().getTipoSuscripcion().getNombre().equalsIgnoreCase("basico")) {
                beneficioDescuentoSuscripcion(usuario, 20);
                repositorioUsuario.modificar(usuario);
            }
        }


        @Override
        public void aplicarBeneficioSuscripcionEstandar(Usuario usuario) {
            if (usuario != null && usuario.getSuscripcion().getTipoSuscripcion().getNombre().equalsIgnoreCase("estandar")) {
                beneficioDescuentoSuscripcion(usuario, 25);
                repositorioUsuario.modificar(usuario);
            }
        }

        @Override
        public void aplicarBeneficioSuscripcionPremium(Usuario usuario) {
            if (usuario != null && usuario.getSuscripcion().getTipoSuscripcion().getNombre().equalsIgnoreCase("premium")) {
                beneficioDescuentoSuscripcion(usuario, 30);
                repositorioUsuario.modificar(usuario);
            }
        }

        @Override
        public String descripcionSuscripciones(Long id) {
            return repositorioTipoSuscripcion.obtenerDescripcionSuscripciones(id);
        }



        @Override
        public Boolean verificarSuscripcion(Usuario usuario) {
            return usuario.getSuscripcion().getTipoSuscripcion().getNombre().equalsIgnoreCase("sin plan");
        }


        public String generateRandomString() {
            int length = 6 + RANDOM.nextInt(3); // Genera un número entre 6 y 8 inclusive
            StringBuilder sb = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int index = RANDOM.nextInt(LETTERS.length());
                sb.append(LETTERS.charAt(index));
            }

            return sb.toString();
        }

        @Override
        public void verificarYActualizarSuscripcionesExpiradas() {
            List<Usuario> usuarios = repositorioUsuario.buscarTodos();
            LocalDateTime now = LocalDateTime.now();
            for (Usuario usuario : usuarios) {
                if (usuario.getSuscripcion() != null && usuario.getSuscripcion().getFechaVencimiento() != null) {
                    if (now.isAfter(usuario.getSuscripcion().getFechaVencimiento())) {
                        usuario.setSuscripcion(repositorioSuscripcion.buscarSuscripcion(1L));
                        usuario.setCuponesDeDescuento(new HashSet<>());
                        usuario.setDescuentosEmitidos(0);
                        repositorioUsuario.modificar(usuario);
                    }
                }
            }
        }
}
