package com.console.crud;

import com.console.crud.DAO.CreditCardDAO;
import com.console.crud.entities.CreditCard;
import com.console.crud.entities.User;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class Config {
    //config for hibernate
//    @Autowired
//    private EntityManagerFactory emf;
//
//    @Bean
//    public SessionFactory sessionFactory(EntityManagerFactory emf) {
//        return emf.unwrap(SessionFactory.class);
//    }


    //config for jdbcTemplate

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages(User.class, CreditCard.class)
                .persistenceUnit("emf")
                .build();
    }


    @Bean
    @Primary
    public SessionFactory sessionFactory(EntityManagerFactory emf) {
        return emf.unwrap(SessionFactory.class);
    }




}
