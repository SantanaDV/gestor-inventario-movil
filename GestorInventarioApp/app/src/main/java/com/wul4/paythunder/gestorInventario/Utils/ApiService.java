package com.wul4.paythunder.gestorInventario.Utils;



import com.wul4.paythunder.gestorInventario.request.LoginRequest;
import com.wul4.paythunder.gestorInventario.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}