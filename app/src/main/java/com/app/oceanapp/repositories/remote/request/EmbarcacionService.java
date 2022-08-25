package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmbarcacionService {

    @POST("embarcacion")
    Call<RegisterResponse> register(
            @Query("embarcacion") String embarcacion,
            @Query("matricula") String matricula,
            @Query("puertoZarpe") String puertoZarpe,
            @Query("fechaZarpe") String fechaZarpe,
            @Query("horaZarpe") String horaZarpe,
            @Query("puertoArribo") String puertoArribo,
            @Query("objetivo") String objetivo,
            @Query("comentario") String comentario
    );

}
