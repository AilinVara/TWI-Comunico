package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.ExpresionSenias;
import com.tallerwebi.dominio.RepositorioExpresion;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateTestConfig.class)
public class RepositorioExpresionImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioExpresion repositorioExpresion;

    @BeforeEach
    public void init(){
        this.repositorioExpresion = new RepositorioExpresionImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void yaQueExisteUnRepositorioExpresionQueGuardeUnaExpresionYLoEncuentreEnLaBaseDeDatos(){
        ExpresionSenias expresionSenias = new ExpresionSenias();
        expresionSenias.setNombre("Gracias");

        this.repositorioExpresion.guardar(expresionSenias);

        String script = "from ExpresionSenias where nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(script);
        query.setParameter("nombre", "Gracias");

        ExpresionSenias obtenida = (ExpresionSenias) query.getSingleResult();

        assertThat(obtenida, equalTo(expresionSenias));
    }

    @Test
    @Transactional
    @Rollback
    public void ahoraQueTenemosUnRepositorioExpresionQueGuardeUnaExpresionYObtenerloEnLaBaseDeDatos(){
        ExpresionSenias expresion = new ExpresionSenias();
        String nombre = "Mucho";
        expresion.setNombre(nombre);

        this.repositorioExpresion.guardar(expresion);

        ExpresionSenias obtenida = this.repositorioExpresion.obtenerUnaExpresionPorNombre("Mucho");

        assertThat(obtenida, equalTo(expresion));
    }

    @Test
    @Transactional
    @Rollback
    public void ahoraQueTenemosUnRepositorioExpresionQueGuardeUnGrupoDeExpresionesYObtenerlosEnLaBaseDeDatos(){
        ExpresionSenias expresionA = new ExpresionSenias();
        ExpresionSenias expresionB = new ExpresionSenias();
        ExpresionSenias expresionC = new ExpresionSenias();
        Integer tamanioEsperado = 3;

        expresionA.setNombre("Gracias");
        expresionB.setNombre("Mucho");
        expresionC.setNombre("Por favor");

        this.repositorioExpresion.guardar(expresionA);
        this.repositorioExpresion.guardar(expresionB);
        this.repositorioExpresion.guardar(expresionC);

        List<ExpresionSenias> expresionesObtenidas = this.repositorioExpresion.obtenerExpresiones();

        assertNotNull(expresionesObtenidas);
        assertThat(expresionesObtenidas.size(), equalTo(tamanioEsperado));
        assertEquals(expresionA.getNombre(), "Gracias");
    }


}
