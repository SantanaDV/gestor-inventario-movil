package com.wul4.paythunder.gestorInventario.fragments.users;

import androidx.lifecycle.*;
import com.wul4.paythunder.gestorInventario.response.UserResponse;
import java.util.List;

public class ManageUsersViewModel extends ViewModel {
    private final ManageUsersRepository repo = new ManageUsersRepository();
    private LiveData<List<UserResponse>> source;
    private final MediatorLiveData<List<UserResponse>> users = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> result = new MutableLiveData<>();

    public ManageUsersViewModel() {
        source = repo.fetchAll();
        users.addSource(source, users::setValue);
    }

    public LiveData<List<UserResponse>> getUsers()  { return users; }
    public LiveData<Boolean>      getResult() { return result; }

    /** Crea un nuevo usuario */
    public void createUser(UserResponse u) {
        repo.create(u).observeForever(ok -> {
            result.setValue(ok);
            if (ok) reload();
        });
    }

    /** Actualiza cualquier campo del usuario (rol, estado, fechas, datos, etc) */
    public void updateUser(UserResponse u) {
        repo.update(u).observeForever(ok -> {
            result.setValue(ok);
            if (ok) reload();
        });
    }

    /** Alterna admin/empleado */
    public void toggleAdmin(int id, boolean makeAdmin) {
        List<UserResponse> cur = users.getValue();
        if (cur == null) return;
        for (UserResponse u : cur) {
            if (u.getId() == id) {
                u.setRol(makeAdmin ? "admin" : "empleado");
                updateUser(u);
                break;
            }
        }
    }

    /** Habilita/deshabilita (estado + fecha_baja / fecha_alta) */
    public void toggleState(UserResponse u) {
        if (u.isActive()) {
            u.setEstado(0);
            u.setFechaBaja(new java.text.SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ssZ", java.util.Locale.getDefault()
            ).format(new java.util.Date()));
        } else {
            u.setEstado(1);
            u.setFechaBaja(null);
            u.setFechaAlta(new java.text.SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ssZ", java.util.Locale.getDefault()
            ).format(new java.util.Date()));
        }
        updateUser(u);
    }

    /** Elimina un usuario */
    public void deleteUser(int id) {
        repo.delete(id).observeForever(ok -> {
            result.setValue(ok);
            if (ok) reload();
        });
    }

    private void reload() {
        users.removeSource(source);
        source = repo.fetchAll();
        users.addSource(source, users::setValue);
    }
}
