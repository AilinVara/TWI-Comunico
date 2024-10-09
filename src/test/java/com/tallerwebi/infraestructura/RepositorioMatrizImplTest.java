package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Matriz;
import com.tallerwebi.dominio.RepositorioMatriz;
import com.tallerwebi.infraestructura.config.HibernateRepositorioTestConfig;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateRepositorioTestConfig.class)
public class RepositorioMatrizImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMatriz repositorioMatriz;

    @BeforeEach
    public void init() {
        this.repositorioMatriz = new RepositorioMatrizImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioMatrizCuandoGuardoUnaMatrizEntoncesLaEncuentroEnLaBaseDeDatos(){
        Matriz matriz = new Matriz();
        matriz.setPuntos("101000");

        this.repositorioMatriz.guardar(matriz);

        String hql = "FROM Matriz where puntos = :puntosBraille";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("puntosBraille", "101000");
        Matriz matrizObtenida = (Matriz) query.getSingleResult();

        assertThat(matrizObtenida, equalTo(matriz));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaMatrizEnLaBaseDeDatosLaEncuentroPorSuId(){
        Matriz matriz = new Matriz();

        this.repositorioMatriz.guardar(matriz);

        Matriz matrizObtenida = this.repositorioMatriz.buscarMatriz(matriz.getId());

        assertThat(matrizObtenida, equalTo(matriz));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenTresMatricesEnLaBaseDeDatosEncuentroUnaPorSuId(){
        Matriz matriz = new Matriz();
        Matriz matriz2 = new Matriz();
        Matriz matriz3 = new Matriz();

        this.repositorioMatriz.guardar(matriz);
        this.repositorioMatriz.guardar(matriz2);
        this.repositorioMatriz.guardar(matriz3);

        Matriz matrizObtenida = this.repositorioMatriz.buscarMatriz(matriz2.getId());

        assertThat(matrizObtenida, equalTo(matriz2));
    }

}
