package bootcamp.bankApi.dao;

import bootcamp.bankApi.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

abstract class AbstractDao<T> implements Dao<T> {

    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public void save(T model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(model);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(T model) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(model);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(T model) {

    }

    @Override
    public List<T> findAll() {
        return null;
    }
}
