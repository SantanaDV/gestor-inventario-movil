package com.wul4.paythunder.gestorInventario.fragments.almacen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlmacenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AlmacenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is almacen fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}