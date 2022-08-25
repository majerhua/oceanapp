package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioService {

    @POST("user/login")
    Call<UsuarioResponse> login(
            @Query("username") String username,
            @Query("password") String password
    );
}
