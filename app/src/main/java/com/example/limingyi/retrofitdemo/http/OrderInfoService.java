package com.example.limingyi.retrofitdemo.http;

import com.example.limingyi.retrofitdemo.modul.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by limingyi on 2015/12/29.
 */
public interface OrderInfoService  {

    @GET("/users/{user}")
    Call<List<User>> list(@Path("user") String user);
}
