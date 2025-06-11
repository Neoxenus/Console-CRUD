package com.console.crud.DAO.implementations.hibernate;

import com.console.crud.DAO.UserDAO;
import com.console.crud.entities.User;
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
//    @Transactional(readOnly = true)
    public List<User> showAll() {

        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            List<User> users = session.createQuery("from User", User.class)
                    .getResultList();

            session.getTransaction().commit();
            return users;
        }

    }

    @Override
    public Optional<User> showUser(int id) {

        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Optional<User> users = Optional.of(session.get(User.class, id));

            session.getTransaction().commit();
            return users;
        }

    }

    @Override
    public Optional<User> showUserByEmail(String email) {

        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Optional<User> users
                    = session.createQuery("from User where email=:email", User.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();
            session.getTransaction().commit();
            return users;
        }
    }

    @Override
    public void addUser(User user) {

        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            if (Objects.nonNull(session.find(User.class, id))) {
                updatedUser.setId(id);
                session.merge(updatedUser);
            }

            session.getTransaction().commit();
        }

    }

    @Override
    public void deleteUser(int id) {

        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            User user = session.find(User.class, id);
            if (Objects.nonNull(user)) {
                session.remove(user);
                session.flush();
            }


            session.getTransaction().commit();
            //session.clear();
        }

    }
}
