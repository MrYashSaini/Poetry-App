package com.example.poetryapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiclient {
    public static Retrofit RETROFIT =null;
    public static Retrofit getclient(){
        if (RETROFIT==null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Gson gson = new GsonBuilder().create();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.160//projectapi//") //192.168.29.160
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return RETROFIT;
    }
}
