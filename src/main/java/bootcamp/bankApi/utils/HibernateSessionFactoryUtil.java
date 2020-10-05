package bootcamp.bankApi.utils;

import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Card;
import bootcamp.bankApi.models.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Card.class);
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Account.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }

        return sessionFactory;
    }
}
