package com.example.WAHGWAAN.HelperClasses;

import android.media.Image;

import com.example.WAHGWAAN.Models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/api/login")
    Call<User> signIn(@Body SignInCred body);

    @POST("/api/get-mirror-pic")
    Call<ResponseBody> getPic(@Body User user);
}




