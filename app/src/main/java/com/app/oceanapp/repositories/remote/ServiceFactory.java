package com.app.oceanapp.repositories.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {
    private static final String BASE_URL_DESARROLLO = "https://wsocean.herokuapp.com/api/";

    public static Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_DESARROLLO)
                    .addConverterFactory(GsonConverterFactory.create()).build();
}
