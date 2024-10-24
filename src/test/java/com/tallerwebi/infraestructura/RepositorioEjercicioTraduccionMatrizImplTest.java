package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioMatriz;
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
public class RepositorioEjercicioTraduccionMatrizImplTest {
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
        EjercicioMatriz ejercicioMatriz = new EjercicioMatriz();
        ejercicioMatriz.setPuntos("101000");

        this.repositorioMatriz.guardar(ejercicioMatriz);

        String hql = "FROM EjercicioMatriz where puntos = :puntosBraille";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("puntosBraille", "101000");
        EjercicioMatriz ejercicioMatrizObtenida = (EjercicioMatriz) query.getSingleResult();

        assertThat(ejercicioMatrizObtenida, equalTo(ejercicioMatriz));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaMatrizEnLaBaseDeDatosLaEncuentroPorSuId(){
        EjercicioMatriz ejercicioMatriz = new EjercicioMatriz();

        this.repositorioMatriz.guardar(ejercicioMatriz);

        EjercicioMatriz ejercicioMatrizObtenida = this.repositorioMatriz.buscarMatriz(ejercicioMatriz.getId());

        assertThat(ejercicioMatrizObtenida, equalTo(ejercicioMatriz));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenTresMatricesEnLaBaseDeDatosEncuentroUnaPorSuId(){
        EjercicioMatriz ejercicioMatriz = new EjercicioMatriz();
        EjercicioMatriz ejercicioMatriz2 = new EjercicioMatriz();
        EjercicioMatriz ejercicioMatriz3 = new EjercicioMatriz();

        this.repositorioMatriz.guardar(ejercicioMatriz);
        this.repositorioMatriz.guardar(ejercicioMatriz2);
        this.repositorioMatriz.guardar(ejercicioMatriz3);

        EjercicioMatriz ejercicioMatrizObtenida = this.repositorioMatriz.buscarMatriz(ejercicioMatriz2.getId());

        assertThat(ejercicioMatrizObtenida, equalTo(ejercicioMatriz2));
    }

}
