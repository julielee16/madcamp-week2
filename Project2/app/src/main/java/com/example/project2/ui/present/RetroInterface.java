package com.example.project2.ui.present;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface RetroInterface {

    @GET("/present/get_friend")
    Call<String> executeGetFriend();

    @POST("/present/upload_friend")
    Call<String> executeUploadFriend(@Body String friend);

    @GET("/present/get_present")
    Call<String> executeGetPresent();

    @HTTP(method = "DELETE", path = "/present/delete_friend", hasBody = true)
    Call<String> executeDeleteFriend(@Body String friend);

    @POST("/present/update_present")
    Call<String> executeUpdatePresent(@Body String friend);

    @POST("/present/insert_present")
    Call<String> executeInsertPresent(@Body String friend);
}
