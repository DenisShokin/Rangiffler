package org.rangiffler.api;

import org.rangiffler.model.PhotoJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PhotoService {

    @POST("/photos")
    Call<PhotoJson> addPhoto(
            @Body PhotoJson photoJson);

}
