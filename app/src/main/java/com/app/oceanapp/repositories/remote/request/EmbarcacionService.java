package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.Embarcacion;
import com.app.oceanapp.entity.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EmbarcacionService {
    @GET("embarcacion")
    Call<List<Embarcacion>> get();
}
