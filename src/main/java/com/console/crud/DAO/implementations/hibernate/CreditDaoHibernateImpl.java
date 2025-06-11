package com.console.crud.DAO.implementations.hibernate;

import com.console.crud.DAO.CreditDAO;
import com.console.crud.entities.Credit;
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
public class CreditDaoHibernateImpl implements CreditDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public CreditDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Credit> showAll() {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            List<Credit> credits = session.createQuery("from Credit ", Credit.class)
                    .getResultList();

            session.getTransaction().commit();
            return credits;
        }
    }

    @Override
    public Optional<Credit> showCreditById(int id) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Optional<Credit> credits = Optional.ofNullable(session.get(Credit.class, id));

            session.getTransaction().commit();
            return credits;
        }
    }

    @Override
    public List<Credit> showCreditsByCreditCardId(int creditCardId) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            List<Credit> credits =
                    session.createQuery("from Credit where creditCard.id=:creditCardId", Credit.class)
                    .setParameter("creditCardId", creditCardId)
                    .getResultList();

            session.getTransaction().commit();
            return credits;
        }
    }

    @Override
    public List<Credit> showCreditsByUserId(int userId) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            List<Credit> credits =
                    session.createQuery("from Credit where creditCard.user.id=:userId", Credit.class)
                            .setParameter("userId", userId)
                            .getResultList();

            session.getTransaction().commit();
            return credits;
        }
    }

    @Override
    public void addCredit(Credit credit) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.persist(credit);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateCredit(int id, Credit updatedCredit) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            if (Objects.nonNull(session.find(Credit.class, id))) {
                updatedCredit.setId(id);
                session.merge(updatedCredit);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCredit(int id) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Credit credit = session.find(Credit.class, id);
            if (Objects.nonNull(credit)) {
                session.remove(credit);
                session.flush();
            }


            session.getTransaction().commit();
            //session.clear();
        }
    }
}
