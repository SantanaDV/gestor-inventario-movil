package com.wul4.paythunder.gestorInventario.fragments.users;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.FragmentManageUsersBinding;
import com.wul4.paythunder.gestorInventario.response.UserResponse;

public class ManageUsersFragment extends Fragment {
    private FragmentManageUsersBinding binding;
    private ManageUsersViewModel vm;

    @Override public View onCreateView(@NonNull LayoutInflater inf,
                                       ViewGroup parent,
                                       Bundle bb) {
        binding = FragmentManageUsersBinding.inflate(inf, parent, false);
        setHasOptionsMenu(true);
        vm = new ViewModelProvider(this).get(ManageUsersViewModel.class);

        UsersAdapter adapter = new UsersAdapter(new UsersAdapter.OnUserAction() {
            @Override public void onToggleAdmin(UserResponse u) {
                vm.toggleAdmin(u.getId(), !u.isAdmin());
            }
            @Override public void onDeleteUser(UserResponse u) {
                vm.deleteUser(u.getId());
            }
            @Override public void onToggleState(UserResponse u) {
                vm.toggleState(u);
            }
            @Override public void onEditUser(UserResponse u) {
                // Abre dialogo en modo edición
                new UserDialog(requireContext(), u, updated -> vm.updateUser(updated));
            }
        });

        binding.recyclerUsers.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerUsers.setAdapter(adapter);

        vm.getUsers().observe(getViewLifecycleOwner(), list -> {
            adapter.submitList(list);
            binding.tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        });

        vm.getResult().observe(getViewLifecycleOwner(), ok -> {
            if (ok != null) Toast.makeText(
                    requireContext(),
                    ok ? "✅ Operación exitosa" : "❌ Hubo un error",
                    Toast.LENGTH_SHORT
            ).show();
        });

        binding.fabAddUser.setOnClickListener(v ->
                new UserDialog(requireContext(), null, created -> vm.createUser(created))
        );

        return binding.getRoot();
    }





    @Override public void onCreateOptionsMenu(@NonNull Menu menu,
                                              @NonNull MenuInflater inflater) {
        menu.clear();  // Sin overflow aquí
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).navigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
