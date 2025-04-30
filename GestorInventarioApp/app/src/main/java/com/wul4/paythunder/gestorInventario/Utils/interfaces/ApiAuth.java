package com.wul4.paythunder.gestorInventario.utils.interfaces;



import com.wul4.paythunder.gestorInventario.entities.Usuario;
import com.wul4.paythunder.gestorInventario.request.LoginRequest;
import com.wul4.paythunder.gestorInventario.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiAuth {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("registro")
    Call<Usuario> register(@Body Usuario UsuarioRequest);


    @GET("api/usuario/existe-email")
    Call<Boolean> existeEmail(@Query("email") String email);



}