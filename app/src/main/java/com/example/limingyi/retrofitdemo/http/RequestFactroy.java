package com.example.limingyi.retrofitdemo.http;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by limingyi on 2015/12/29.
 */
public class RequestFactroy {


    public static OrderInfoService getInstance(){

         Retrofit retrofit=new Retrofit.Builder().baseUrl("http://www.baidu.com").addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(OrderInfoService.class);
    }

}
