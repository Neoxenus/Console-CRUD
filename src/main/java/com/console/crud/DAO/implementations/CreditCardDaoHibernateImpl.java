package com.console.crud.DAO.implementations;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.entities.CreditCard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Primary
public class CreditCardDaoHibernateImpl implements CreditCardDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public CreditCardDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<CreditCard> showCreditCardByNumber(String number) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            List<CreditCard> creditCards
                    = session.createQuery("from CreditCard where number=:number", CreditCard.class)
                    .setParameter("number", number)
                    .getResultList()
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            session.getTransaction().commit();
            return creditCards;
        }
    }

    @Override
    public List<CreditCard> showCreditCardById(int id) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            List<CreditCard> creditCards
                    = Stream.of(session.get(CreditCard.class, id))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            session.getTransaction().commit();
            return creditCards;
        }
    }

    @Override
    public List<CreditCard> showAll() {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            List<CreditCard> creditCards =
                    session.createQuery("from CreditCard", CreditCard.class)
                    .getResultList();

            session.getTransaction().commit();
            return creditCards;
        }
    }

    @Override
    public void addCreditCard(CreditCard creditCard) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.persist(creditCard);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateCreditCard(int id, CreditCard creditCard) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();

            if (Objects.nonNull(session.find(CreditCard.class, id))) {
                creditCard.setId(id);
                session.merge(creditCard);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCreditCard(int id) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            CreditCard creditCard = session.find(CreditCard.class, id);
            if (Objects.nonNull(creditCard)) {
                session.remove(creditCard);
                session.flush();
            }


            session.getTransaction().commit();
            //session.clear();
        }
    }

    @Override
    public List<CreditCard> findByUserId(int userId) {
        try(Session session = this.sessionFactory.getCurrentSession()){
            session.beginTransaction();
            List<CreditCard> creditCards
                    = session.createQuery("from CreditCard where user_id=:userId", CreditCard.class)
                    .setParameter("userId", userId)
                    .getResultList()
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            session.getTransaction().commit();
            return creditCards;
        }
    }
}
