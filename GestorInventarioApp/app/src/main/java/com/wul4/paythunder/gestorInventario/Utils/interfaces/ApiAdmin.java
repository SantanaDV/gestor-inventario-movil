package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Usuario;
import com.wul4.paythunder.gestorInventario.response.UserResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Endpoints de administración de usuarios.
 */
public interface ApiAdmin {

    /** Listar todos los usuarios **/
    @GET("api/usuario/admin/listarUsuarios")
    Call<List<UserResponse>> getUsers();

    /** Crear nuevo usuario **/
    @POST("registro")
    Call<UserResponse> createUser(@Body UserResponse user);

    /** Actualizar usuario existente (incluye cambio de rol) **/
    @PUT("api/usuario/admin/actualizarUsuario")
    Call<UserResponse> updateUser(@Body UserResponse user);
    /** Eliminar usuario **/
    @DELETE("api/usuario/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
