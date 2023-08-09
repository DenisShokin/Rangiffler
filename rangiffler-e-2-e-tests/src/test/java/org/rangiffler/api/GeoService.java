package org.rangiffler.api;

import org.rangiffler.model.CountryJson;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface GeoService {

    @GET("/countries")
    Call<List<CountryJson>> getCountries();

}
