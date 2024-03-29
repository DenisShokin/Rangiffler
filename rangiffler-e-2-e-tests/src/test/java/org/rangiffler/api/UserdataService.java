package org.rangiffler.api;


import org.rangiffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UserdataService {

    @GET("/currentUser")
    Call<UserJson> currentUser(
            @Query("username") String username
    );

    @POST("/addFriend")
    Call<UserJson> addFriend(
            @Query("username") String username,
            @Body UserJson friendJson);

    @POST("/acceptInvitation")
    Call<UserJson> acceptInvitation(
            @Query("username") String username,
            @Body UserJson friendJson);

    @GET("/allUsers")
    Call<List<UserJson>> getAllUsers(
            @Query("username") String username
    );

}
