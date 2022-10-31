package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.entity.Zarpe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ZarpeService {

    @POST("zarpe")
    Call<RegisterResponse> register(
            @Query("embarcacion_id") int embarcacion_id,
            @Query("puertoZarpe") String puertoZarpe,
            @Query("fechaZarpe") String fechaZarpe,
            @Query("horaZarpe") String horaZarpe,
            @Query("puertoArribo") String puertoArribo,
            @Query("objetivo") String objetivo,
            @Query("comentario") String comentario
    );

    @GET("zarpe")
    Call<List<Zarpe>> get(@Query("zarpe_id") int zarpe_id);

    @POST("zarpe/close")
    Call<RegisterResponse> close(@Query("zarpe_id") int zarpe_id);

}
