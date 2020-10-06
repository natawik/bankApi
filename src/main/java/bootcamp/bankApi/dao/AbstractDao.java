package bootcamp.bankApi.dao;

import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

abstract class AbstractDao<T> implements Dao<T> {

    /*
     Добавить запись
      */
    @Override
    public void save(T model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(model);
        transaction.commit();
        session.close();
    }

    /*
     Обновить запись
      */
    @Override
    public void update(T model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(model);
        transaction.commit();
        session.close();
    }

    /*
     Удалить запись
      */
    @Override
    public void delete(T model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(model);
        transaction.commit();
        session.close();
    }
}
