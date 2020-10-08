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
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(model);
            transaction.commit();
        }
    }

    /*
     Обновить запись
      */
    @Override
    public void update(T model) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(model);
            transaction.commit();
        }
    }

    /*
     Удалить запись
      */
    @Override
    public void delete(T model) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(model);
            transaction.commit();
        }
    }
}
