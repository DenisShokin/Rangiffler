package org.rangiffler.db.dao.geo;

import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.geo.CountryEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

import java.util.List;

public class RangifflerGeoDAOHibernate extends JpaTransactionManager implements RangifflerGeoDAO {
    public RangifflerGeoDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_GEO).createEntityManager());
    }

    @Override
    public List<CountryEntity> getCountries() {
        return em.createQuery("select c from CountryEntity c", CountryEntity.class)
                .getResultList();
    }
}
