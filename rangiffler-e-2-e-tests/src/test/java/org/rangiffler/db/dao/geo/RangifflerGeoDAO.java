package org.rangiffler.db.dao.geo;

import org.rangiffler.db.entity.geo.CountryEntity;

import java.util.List;

public interface RangifflerGeoDAO {

    List<CountryEntity> getCountries();
}
