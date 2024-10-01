package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.RepositorioLetra;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @Rollback
    public void dadoQueExisteUnRepositorioLetraCuandoGuardoUnaLetraEntoncesLaEncuentroEnLaBaseDeDatos() {
        Letra letra = new Letra();
        letra.setNombre("B");
        letra.setImagenSenias("senias-b.png");

        this.repositorioLetra.guardar(letra);

        String hql = "FROM Letra WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "B");

        Letra letraObtenida = (Letra) query.getSingleResult();

        assertThat(letraObtenida, equalTo(letra));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaLetraEntoncesCuandoLaBuscoEntoncesLaEncuentroEnLaBaseDeDatos() {
        Letra letra = new Letra();
        letra.setNombre("B");
        letra.setImagenSenias("senias-b.png");

        this.repositorioLetra.guardar(letra);

        Letra letraObtenida = this.repositorioLetra.buscarUnaLetra("B");

        assertThat(letraObtenida, equalTo(letra));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenTresLetrasEnLaBaseDeDatosCuandoBuscoTodasLasLetrasEntoncesLasObtengoDeLaBaseDeDatos() {
        Letra letra1 = new Letra("A", "senias-a.png", "braille-a.png");
        Letra letra2 = new Letra("B", "senias-b.png", "braille-b.png");
        Letra letra3 = new Letra("C", "senias-c.png", "braille-c.png");

        this.repositorioLetra.guardar(letra1);
        this.repositorioLetra.guardar(letra2);
        this.repositorioLetra.guardar(letra3);

        List<Letra> letras = this.repositorioLetra.buscarLetras();

        assertNotNull(letras);
        assertThat(letras.size(), equalTo(3) );
        assertThat(letras.contains(letra1), equalTo(true));
    }

}
