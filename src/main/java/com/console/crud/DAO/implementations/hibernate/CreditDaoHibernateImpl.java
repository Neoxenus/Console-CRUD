package com.console.crud.DAO.implementations.hibernate;

import com.console.crud.DAO.CreditDAO;
import com.console.crud.entities.Credit;
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
public class CreditDaoHibernateImpl implements CreditDAO {

    private final SessionFactory sessionFactory;


    @Autowired
    public CreditDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Credit> showAll() {
        Session session = this.sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            List<Credit> credits = session.createQuery("from Credit ", Credit.class)
                    .getResultList();
            session.getTransaction().commit();
            return credits;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return List.of();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Credit> showCreditById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Optional<Credit> credits = Optional.ofNullable(session.get(Credit.class, id));

            session.getTransaction().commit();
            return credits;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Credit> showCreditsByCreditCardId(int creditCardId) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            List<Credit> credits =
                    session.createQuery("from Credit where creditCard.id=:creditCardId", Credit.class)
                    .setParameter("creditCardId", creditCardId)
                    .getResultList();

            session.getTransaction().commit();
            return credits;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return List.of();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Credit> showCreditsByUserId(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            List<Credit> credits =
                    session.createQuery("from Credit where creditCard.user.id=:userId", Credit.class)
                            .setParameter("userId", userId)
                            .getResultList();

            session.getTransaction().commit();
            return credits;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return List.of();
        } finally {
            session.close();
        }
    }

    @Override
    public void addCredit(Credit credit) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(credit);
            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateCredit(int id, Credit updatedCredit) {
        Session session = this.sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();

            if (Objects.nonNull(session.find(Credit.class, id))) {
                updatedCredit.setId(id);
                session.merge(updatedCredit);
            }

            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteCredit(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Credit credit = session.find(Credit.class, id);
            if (Objects.nonNull(credit)) {
                session.remove(credit);
                session.flush();
            }


            session.getTransaction().commit();
            //session.clear();
        } catch (HibernateException e){
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
