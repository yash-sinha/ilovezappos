package com.zappos.ilovezappos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yash on 02/02/17.
 */


public interface APIEndPointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("Search?key=b743e26728e16b81da139182bb2094357c31d331")
    Call<Products> getProducts(@Query("term") String term);

//    @GET("group/{id}/users")
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

//    @POST("users/new")
//    Call<User> createUser(@Body User user);
}