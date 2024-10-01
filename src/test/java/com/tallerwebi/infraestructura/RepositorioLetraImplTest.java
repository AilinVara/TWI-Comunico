package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.RepositorioLetra;
import com.tallerwebi.infraestructura.config.HibernateRepositorioTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateRepositorioTestConfig.class)
public class RepositorioLetraImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioLetra repositorioLetra;

    @BeforeEach
    public void init() {
        this.repositorioLetra = new RepositorioLetraImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void dadoQueExisteUnRepositorioLetraCuandoGuardoUnaLetraEntoncesLaEncuentroEnLaBaseDeDatos() {

        Letra letra = new Letra();
        letra.setNombre("B");
        letra.setImagenSenias("senias-b.png");


        this.repositorioLetra.guardar(letra);


        String hql = "FROM Letra where nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "B");
        Letra letraObtenida = (Letra) query.getSingleResult();


        assertThat(letraObtenida, equalTo(letra));
    }

}
