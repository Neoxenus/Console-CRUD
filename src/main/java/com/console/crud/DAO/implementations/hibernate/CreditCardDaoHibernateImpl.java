package com.console.crud.DAO.implementations.hibernate;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.entities.CreditCard;
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
public class CreditCardDaoHibernateImpl implements CreditCardDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public CreditCardDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Optional<CreditCard> showCreditCardByNumber(String number) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Optional<CreditCard> creditCards
                    = Optional.ofNullable(session.createQuery(
                    "from CreditCard where number=:number", CreditCard.class)
                    .setParameter("number", number)
                    .getSingleResultOrNull());
            session.getTransaction().commit();
            return creditCards;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<CreditCard> showCreditCardById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Optional<CreditCard> creditCards
                    = Optional.ofNullable(session.get(CreditCard.class, id));

            session.getTransaction().commit();
            return creditCards;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<CreditCard> showAll() {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            List<CreditCard> creditCards =
                    session.createQuery("from CreditCard", CreditCard.class)
                    .getResultList();

            session.getTransaction().commit();
            return creditCards;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return List.of();
        } finally {
            session.close();
        }
    }

    @Override
    public void addCreditCard(CreditCard creditCard) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(creditCard);
            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateCreditCard(int id, CreditCard creditCard) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            if (Objects.nonNull(session.find(CreditCard.class, id))) {
                creditCard.setId(id);
                session.merge(creditCard);
            }

            session.getTransaction().commit();
        } catch (HibernateException e){
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteCreditCard(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            CreditCard creditCard = session.find(CreditCard.class, id);
            if (Objects.nonNull(creditCard)) {
                session.remove(creditCard);
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

    @Override
    public List<CreditCard> findByUserId(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<CreditCard> creditCards
                    = session.createQuery("from CreditCard where user.id=:userId", CreditCard.class)
                    .setParameter("userId", userId)
                    .getResultList();
            session.getTransaction().commit();
            return creditCards;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return List.of();
        } finally {
            session.close();
        }
    }
}
