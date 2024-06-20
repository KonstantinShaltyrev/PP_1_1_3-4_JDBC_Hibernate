package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    final static private String DELETE_TABLE = "DROP TABLE IF EXISTS utilTestDB";
    final static private String CLEAN_TABLE = "DELETE FROM User";
    final static private String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS utilTestDB " +
            "(id BIGINT not null PRIMARY KEY AUTO_INCREMENT, " +
            " name VARCHAR(45) not null, " +
            " lastName VARCHAR(45) not null, " +
            " age TINYINT not null)";

    private final SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(DELETE_TABLE).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException | IllegalArgumentException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("FROM User").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery(CLEAN_TABLE).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
