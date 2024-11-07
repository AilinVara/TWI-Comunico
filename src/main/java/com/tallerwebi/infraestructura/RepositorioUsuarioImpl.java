package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = this.sessionFactory.getCurrentSession().createCriteria(Usuario.class).list();
        return usuarios;
    }

    @Override
    public List<Usuario> buscarTodosLosUsuarios(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Override
    public Set<Usuario> buscarAmigos(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        List<Usuario> amigosUsuario = session.createQuery(
                        "SELECT a.usuario FROM Amigo a WHERE a.amigo = :usuario", Usuario.class)
                .setParameter("usuario", usuario)
                .getResultList();

        return new HashSet<>(amigosUsuario);
    }

    @Override
    public List<Usuario> buscarUsuarios() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE rol = :userRole")
                .setParameter("userRole", "USER")
                .list();
    }

    @Override
    public List<Usuario> buscarAdmins() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE rol = :userRole")
                .setParameter("userRole", "ADMIN")
                .list();
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Usuario WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", usuario.getId());
        query.executeUpdate();
    }

    @Override
    public void eliminarRelacionesDeAmistad(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Amigo WHERE usuario = :usuario OR amigo = :usuario";
        Query query = session.createQuery(hql);
        query.setParameter("usuario", usuario);
        query.executeUpdate();
    }

    @Override
    public Usuario buscarPorToken(String token) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("tokenDeVerificacion", token))
                .uniqueResult();
    }

//    @Override
//    public Integer cantidadDeCompras(Usuario usuario, LocalDateTime fechaCompra) {
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "SELECT COUNT(*) FROM Compra WHERE usuario = :usuario AND fechaDeCompra > :fechaCompra";
//        Query<Long> query = session.createQuery(hql, Long.class);
//        query.setParameter("usuario", usuario);
//        query.setParameter("fechaCompra", fechaCompra);
//        Long count = query.uniqueResult();
//        return count != null ? count.intValue() : 0;
//    }

//    @Override
//    public List<Compra> historialDeCompras(Usuario usuario) {
//        Session session = sessionFactory.getCurrentSession();
//
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<Compra> criteriaQuery = builder.createQuery(Compra.class);
//
//        Root<Compra> compraRoot = criteriaQuery.from(Compra.class);
//        Join<Compra, Usuario> usuarioJoin = compraRoot.join("usuario");
//
//        criteriaQuery.select(compraRoot);
//        criteriaQuery.where(builder.equal(usuarioJoin.get("id"), usuario.getId()));
//
//        Query<Compra> query = session.createQuery(criteriaQuery);
//        return query.getResultList();
//    }


}
