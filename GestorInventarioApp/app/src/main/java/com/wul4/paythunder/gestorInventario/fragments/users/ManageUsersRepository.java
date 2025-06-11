// ManageUsersRepository.java
package com.wul4.paythunder.gestorInventario.fragments.users;

import androidx.lifecycle.*;
import com.wul4.paythunder.gestorInventario.response.UserResponse;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAdmin;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUsersRepository {
    private final ApiAdmin api = ApiClient.getClient().create(ApiAdmin.class);

    public LiveData<List<UserResponse>> fetchAll() {
        MutableLiveData<List<UserResponse>> data = new MutableLiveData<>();
        api.getUsers().enqueue(new Callback<List<UserResponse>>() {
            @Override public void onResponse(Call<List<UserResponse>> c, Response<List<UserResponse>> r) {
                if (r.isSuccessful() && r.body()!=null) data.postValue(r.body());
                else data.postValue(Collections.emptyList());
            }
            @Override public void onFailure(Call<List<UserResponse>> c, Throwable t) {
                data.postValue(Collections.emptyList());
            }
        });
        return data;
    }

    public LiveData<Boolean> create(UserResponse u) {
        MutableLiveData<Boolean> ok = new MutableLiveData<>();
        api.createUser(u).enqueue(new Callback<UserResponse>() {
            @Override public void onResponse(Call<UserResponse> c, Response<UserResponse> r) {
                ok.postValue(r.isSuccessful());
            }
            @Override public void onFailure(Call<UserResponse> c, Throwable t) {
                ok.postValue(false);
            }
        });
        return ok;
    }

    public LiveData<Boolean> update(UserResponse u) {
        MutableLiveData<Boolean> ok = new MutableLiveData<>();
        api.updateUser(u).enqueue(new Callback<UserResponse>() {
            @Override public void onResponse(Call<UserResponse> c, Response<UserResponse> r) {
                ok.postValue(r.isSuccessful());
            }
            @Override public void onFailure(Call<UserResponse> c, Throwable t) {
                ok.postValue(false);
            }
        });
        return ok;
    }

    public LiveData<Boolean> delete(int id) {
        MutableLiveData<Boolean> ok = new MutableLiveData<>();
        api.deleteUser(id).enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> c, Response<Void> r) {
                ok.postValue(r.isSuccessful());
            }
            @Override public void onFailure(Call<Void> c, Throwable t) {
                ok.postValue(false);
            }
        });
        return ok;
    }
}
