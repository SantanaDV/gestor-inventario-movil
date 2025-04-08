package com.wul4.paythunder.gestorInventario.fragments.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> username;
    private final MutableLiveData<String> password;

    public LoginViewModel() {
        username = new MutableLiveData<>("");;
        password = new MutableLiveData<>("");
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String user) {
        username.setValue(user);
    }

    public void setPassword(String pass) {
        password.setValue(pass);
    }


}
