package com.wul4.paythunder.gestorInventario.fragments.users;

import android.view.*;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.*;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.ItemUserBinding;
import com.wul4.paythunder.gestorInventario.response.UserResponse;

public class UsersAdapter
        extends ListAdapter<UserResponse, UsersAdapter.VH> {

    public interface OnUserAction {
        void onToggleAdmin(UserResponse u);
        void onDeleteUser(UserResponse u);
        void onToggleState(UserResponse u);
        void onEditUser(UserResponse u);
    }

    private final OnUserAction action;

    public UsersAdapter(OnUserAction action) {
        super(new DiffUtil.ItemCallback<UserResponse>() {
            @Override public boolean areItemsTheSame(@NonNull UserResponse a, @NonNull UserResponse b) {
                return a.getId()==b.getId();
            }
            @Override
            public boolean areContentsTheSame(@NonNull UserResponse a, @NonNull UserResponse b) {
                return a.getEmail().equals(b.getEmail())
                        && a.getNombre().equals(b.getNombre())       // <— añadimos comparación del nombre
                        && a.getRol().equals(b.getRol())
                        && a.getEstado().equals(b.getEstado())
                        && (a.getFechaBaja() == null
                        ? b.getFechaBaja() == null
                        : a.getFechaBaja().equals(b.getFechaBaja()));
            }
        });
        this.action = action;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int i) {
        ItemUserBinding b = ItemUserBinding.inflate(
                LayoutInflater.from(p.getContext()), p, false
        );
        return new VH(b);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        h.bind(getItem(pos));
    }

    class VH extends RecyclerView.ViewHolder {
        private final ItemUserBinding b;
        private final CardView card;
        VH(ItemUserBinding binding) {
            super(binding.getRoot());
            this.b = binding;
            this.card = binding.cardUser;
        }
        void bind(UserResponse u) {
            // 1) Datos
            b.tvName.setText(u.getNombre());
            b.tvEmail.setText(u.getEmail());

            // 2) Fondo según estado
            int color = u.getEstado() == 1
                    ? R.color.light_green
                    : R.color.light_red;
            card.setCardBackgroundColor(
                    ContextCompat.getColor(card.getContext(), color)
            );

            // 3) Switch rol
            b.switchAdmin.setOnCheckedChangeListener(null);
            b.switchAdmin.setChecked(u.isAdmin());
            b.switchAdmin.setOnCheckedChangeListener((sw, checked) ->
                    action.onToggleAdmin(u)
            );

            // 4) Borrar
            b.btnDelete.setOnClickListener(v ->
                    action.onDeleteUser(u)
            );
            itemView.setOnLongClickListener(v -> {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenu().add(
                        u.isActive() ? "Deshabilitar" : "Habilitar"
                );
                popup.getMenu().add("Editar");
                popup.setOnMenuItemClickListener(item -> {
                    String t = item.getTitle().toString();
                    if (t.equals("Editar")) {
                        action.onEditUser(u);
                    } else {
                        action.onToggleState(u);
                    }
                    return true;
                });
                popup.show();
                return true;
            });
        }
    }
}
