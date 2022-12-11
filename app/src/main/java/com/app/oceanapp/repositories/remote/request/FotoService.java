package com.app.oceanapp.repositories.remote.request;

import com.app.oceanapp.entity.Embarcacion;
import com.app.oceanapp.entity.Foto;
import com.app.oceanapp.entity.Procesamiento;
import com.app.oceanapp.entity.ProcesamientoBody;
import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.entity.ResponseBody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FotoService {
    @GET("galeria-fotos")
    Call<List<Foto>> getGaleriaFotos(@Query("lance_id") int lance_id);

    @GET("galeria-fotos-all")
    Call<List<Foto>> getGaleriaFotosAll(@Query("user_id") int user_id);

    @Multipart
    @POST("load-image")
    Call<RegisterResponse> cargarFoto(
            @Part MultipartBody.Part image,
            @Part("lance_id") RequestBody lance_id,
            @Part("user_id") RequestBody user_id);

    @DELETE("delete-image")
    Call<RegisterResponse> eliminarFoto(@Query("id") int id);


    @POST("process-image")
    Call<ResponseBody> processPhotos(@Query("ids") String ids);
}
