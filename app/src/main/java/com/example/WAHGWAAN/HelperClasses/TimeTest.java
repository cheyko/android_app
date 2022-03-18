package com.example.WAHGWAAN.HelperClasses;

import com.example.WAHGWAAN.Models.Time;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimeTest {

    @GET("/api/time")
    Call<Time> getTime();
}
