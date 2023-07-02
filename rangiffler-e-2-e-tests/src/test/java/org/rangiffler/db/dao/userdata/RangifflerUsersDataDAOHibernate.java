package org.rangiffler.db.dao.userdata;


import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

public class RangifflerUsersDataDAOHibernate extends JpaTransactionManager implements RangifflerUsersDataDAO {

    public RangifflerUsersDataDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_USERDATA).createEntityManager());
    }

    @Override
    public int createUser(UserDataEntity user) {
        persist(user);
        return 0;
    }

    @Override
    public String getUserId(String userName) {
        return em.createQuery("select u from UserDataEntity u where username=:username", UserDataEntity.class)
                .setParameter("username", userName)
                .getSingleResult()
                .getId()
                .toString();
    }

    @Override
    public int removeUser(UserDataEntity user) {
        remove(user);
        return 0;
    }

    @Override
    public UserDataEntity getUserByUsername(String userName) {
        return em.createQuery("select u from UserDataEntity u where username=:username", UserDataEntity.class)
                .setParameter("username", userName)
                .getSingleResult();
    }
}
