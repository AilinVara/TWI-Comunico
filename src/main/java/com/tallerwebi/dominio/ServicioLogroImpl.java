package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("servicioLogro")
@Transactional
public class ServicioLogroImpl implements ServicioLogro {

    private RepositorioLogro repositorioLogro;
    private RepositorioCondicionLogro repositorioCondicionLogro;

    @Override
    public List<Logro> obtenerTodosLosLogros() {
        return this.repositorioLogro.obtenerTodosLosLogros();
    }

    @Override
    public Logro obtenerLogroPorId(Long id) {
        return this.repositorioLogro.buscarLogro(id);
    }

    @Override
    public void guardarLogro(Logro logro) {
        this.repositorioLogro.guardar(logro);
    }


    @Override
    public void desbloquearLogro(Long logroId, Long codicionId) {
    Logro logro = obtenerLogroPorId(logroId);
    CondicionLogro condicion = this.repositorioCondicionLogro.buscarCondicion(codicionId);
    List<CondicionLogro> condiciones = this.repositorioCondicionLogro.buscarCondiciones();




    }

    @Override
    public List<Logro> buscarLogrosPorNombre(String nombre) {
        List<Logro> logros = this.repositorioLogro.obtenerTodosLosLogros();
        if (nombre != null) {
            List<Logro> logrosADevolver = logros.stream()
                    .filter(logro -> logro.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());

            if (logrosADevolver.isEmpty()) {
                return logros;
            } else {
                return logrosADevolver;
            }
        }
        return logros;
    }

    @Override
    public List<Logro> buscarLogrosSolamenteDesbloqueados() {
        return List.of();
    }

}
