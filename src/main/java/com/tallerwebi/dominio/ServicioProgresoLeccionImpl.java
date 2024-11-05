package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("servicioProgresoLeccion")
@Transactional
public class ServicioProgresoLeccionImpl implements ServicioProgresoLeccion{
    private RepositorioProgresoLeccion repositorioProgresoLeccion;
    private ServicioLogin servicioUsuario;
    private ServicioLeccion servicioLeccion;

    @Autowired
    public ServicioProgresoLeccionImpl(RepositorioProgresoLeccion repositorioProgresoLeccion, ServicioLogin servicioUsuario, ServicioLeccion servicioLeccion) {
        this.repositorioProgresoLeccion = repositorioProgresoLeccion;
        this.servicioUsuario = servicioUsuario;
        this.servicioLeccion = servicioLeccion;
    }
    @Override
    public void guardarProgresoLeccion(ProgresoLeccion progresoLeccion) {
        this.repositorioProgresoLeccion.guardar(progresoLeccion);
    }

    @Override
    public ProgresoLeccion buscarPorIds(Long leccionId, Long usuarioId, Long ejercicioId) {
        return this.repositorioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
    }

    @Override
    public List<ProgresoLeccion> buscarProgresoLeccionDeUsuario(Long usuarioId, Long leccionId) {
        return this.repositorioProgresoLeccion.buscarPorUsuarioIdYLeccionId(usuarioId, leccionId);
    }

    @Override
    public void crearProgresoLeccion(Long leccionId, Long usuarioId) {
        Usuario usuario = this.servicioUsuario.obtenerUsuarioPorId(usuarioId);
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        List<Ejercicio> ejercicios = leccion.getEjercicios();

        for(Ejercicio ejercicio : ejercicios){
            ProgresoLeccion progresoExistente = this.repositorioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicio.getId());
            if(progresoExistente == null){
                ProgresoLeccion progresoLeccion = new ProgresoLeccion(usuario, leccion, ejercicio);
                this.guardarProgresoLeccion(progresoLeccion);
            }
        }
    }

    @Override
    public void actualizarProgreso(ProgresoLeccion progresoLeccion, Boolean resuelto) {
        progresoLeccion.setCompleto(resuelto);
        this.repositorioProgresoLeccion.actualizar(progresoLeccion);
    }

    @Override
    public Boolean verificarCompletado(List<ProgresoLeccion> progresoLeccion) {
        return progresoLeccion.stream().allMatch(ProgresoLeccion::getCompleto);
    }

    @Override
    public Boolean verificarCompletadoPorLeccion(Long leccionId, Long usuarioId) {
        List<ProgresoLeccion> progresosDeLeccion = this.buscarProgresoLeccionDeUsuario(usuarioId, leccionId);
        return this.verificarCompletado(progresosDeLeccion);
    }

    @Override
    public List<ProgresoLeccion> buscarProgresoPorTipoEjercicio(String tipoEjercicio, Long usuarioId) {
       List<ProgresoLeccion> todosProgresosLeccion = this.repositorioProgresoLeccion.buscarProgresosPorUsuario(usuarioId);

        if(todosProgresosLeccion.isEmpty() || todosProgresosLeccion.stream().noneMatch(p -> p.getLeccion().getTipo().equals(tipoEjercicio))){
            for (Leccion leccion : this.servicioLeccion.obtenerLeccionesPorTipo(tipoEjercicio)){
                this.crearProgresoLeccion(leccion.getId(), usuarioId);
            }
            todosProgresosLeccion = this.repositorioProgresoLeccion.buscarProgresosPorUsuario(usuarioId);
        }

        return  todosProgresosLeccion.stream()
                .filter(l -> l.getLeccion().getTipo().equals(tipoEjercicio))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, Boolean> buscarProgresoPorTipoEjercicioConEstado(String tipoEjercicio, Long usuarioId) {
        List<ProgresoLeccion> todosProgresosLeccion = this.repositorioProgresoLeccion.buscarProgresosPorUsuario(usuarioId);

        if (todosProgresosLeccion.isEmpty() || todosProgresosLeccion.stream().noneMatch(p -> p.getLeccion().getTipo().equals(tipoEjercicio))) {
            for (Leccion leccion : this.servicioLeccion.obtenerLeccionesPorTipo(tipoEjercicio)) {
                this.crearProgresoLeccion(leccion.getId(), usuarioId);
            }
            todosProgresosLeccion = this.repositorioProgresoLeccion.buscarProgresosPorUsuario(usuarioId);
        }

        return todosProgresosLeccion.stream()
                .filter(l -> l.getLeccion().getTipo().equals(tipoEjercicio))
                .collect(Collectors.groupingBy(
                        p -> p.getLeccion().getId(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::verificarCompletado)));
    }
}
