package com.example.project2.ui.pictures;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {

    @Multipart
    @POST("/image/upload") //경로 수정필요
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    @GET("/image/download")
    Call<ResponseBody> downloadFiles();



}
