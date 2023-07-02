package org.rangiffler.db.dao.user;


import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

public class RangifflerUsersDAOHibernate extends JpaTransactionManager implements RangifflerUsersDAO {

    public RangifflerUsersDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_AUTH).createEntityManager());
    }

    @Override
    public int createUser(UserEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        persist(user);
        return 0;
    }

    @Override
    public String getUserId(String userName) {
        return em.createQuery("select u from UserEntity u where username=:username", UserEntity.class)
                .setParameter("username", userName)
                .getSingleResult()
                .getId()
                .toString();
    }

    @Override
    public int removeUser(UserEntity user) {
        remove(user);
        return 0;
    }

    @Override
    public UserEntity getUserByUsername(String userName) {
        return em.createQuery("select u from UserEntity u where username=:username", UserEntity.class)
                .setParameter("username", userName)
                .getSingleResult();
    }
}
