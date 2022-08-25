package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LanceService {
    @POST("lance")
    Call<RegisterResponse> register(
            @Query("numeroLance") String numeroLance,
            @Query("fechaLance") String fechaLance,
            @Query("horaLance") String horaLance,
            @Query("latitud") String latitud,
            @Query("longitud") String longitud,
            @Query("rumbo") String rumbo,
            @Query("embarcacion") String nombreEmbarcacion,
            @Query("matricula") String matriculaEmbarcacion
    );
}
