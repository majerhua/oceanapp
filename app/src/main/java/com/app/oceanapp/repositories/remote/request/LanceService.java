package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.Lance;
import com.app.oceanapp.entity.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LanceService {
    @POST("lance")
    Call<RegisterResponse> register(
            @Query("fechaLance") String fechaLance,
            @Query("horaLance") String horaLance,
            @Query("latitud") String latitud,
            @Query("longitud") String longitud,
            @Query("rumbo") String rumbo,
            @Query("zarpe_id") int zarpe_id
    );

    @GET("lance/getByZarpeId")
    Call<List<Lance>> getByZarpeId(
            @Query("zarpe_id") int zarpe_id
    );
}
