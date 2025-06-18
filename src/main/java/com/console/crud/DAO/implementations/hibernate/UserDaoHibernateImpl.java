package com.console.crud.DAO.implementations.hibernate;

import com.console.crud.DAO.UserDAO;
import com.console.crud.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Primary
//@Transactional
public class UserDaoHibernateImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<User> showAll() {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            List<User> users = session.createQuery("from User", User.class)
                    .getResultList();

            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return List.of();
        } finally {
            session.close();
        }

    }

    @Override
    public Optional<User> showUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Optional<User> users = Optional.of(session.get(User.class, id));

            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }

    }

    @Override
    public Optional<User> showUserByEmail(String email) {

        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Optional<User> users
                    = session.createQuery("from User where email=:email", User.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();
            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public void addUser(User user) {

        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            if (Objects.nonNull(session.find(User.class, id))) {
                updatedUser.setId(id);
                session.merge(updatedUser);
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void deleteUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = session.find(User.class, id);
            if (Objects.nonNull(user)) {
                session.remove(user);
                session.flush();
            }


            session.getTransaction().commit();
            //session.clear();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }
}
