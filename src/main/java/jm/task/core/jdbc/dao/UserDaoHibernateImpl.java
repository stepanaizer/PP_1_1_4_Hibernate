package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE if not exists `testdb`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `firstName` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));";

        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Message: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE if exists `testdb`.`users`";

        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Message: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            System.out.println("User с именем " + name + " был добавлен в БД");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Message: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction tx = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);

            if(user != null) {
                String hql = "DELETE FROM User WHERE id= :userId";
                session.createQuery(hql)
                        .setParameter("userId", id).executeUpdate();
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Message: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> users = null;

        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String hql = "from User";
            Query<User> query = session.createQuery(hql, User.class);
            users = query.list();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Message: " + e.getMessage());
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;

        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String hql = "DELETE FROM User";
            session.createQuery(hql)
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Message: " + e.getMessage());
        }
    }
}
