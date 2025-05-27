package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.wul4.paythunder.gestorInventario.databinding.FragmentAlmacenBinding;
import com.wul4.paythunder.gestorInventario.response.AlmacenResponse;

import java.util.List;

public class AlmacenFragment extends Fragment {

    private FragmentAlmacenBinding binding;
    private AlmacenViewModel viewModel;
    private AlmacenAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlmacenBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AlmacenViewModel.class);

        setupRecycler();
        observeAlmacenes();

        return binding.getRoot();
    }

    private void setupRecycler() {
        adapter = new AlmacenAdapter(List.of(), almacen -> {
            // Aquí manejas click en un almacén:
            // por ejemplo, navegas a EstanteriasFragment pasando almacen.getId()
        });
        binding.recyclerAlmacenes.setAdapter(adapter);
    }

    private void observeAlmacenes() {
        binding.progressAlmacenes.setVisibility(View.VISIBLE);
        viewModel.getAlmacenes().observe(getViewLifecycleOwner(), this::onData);
    }

    private void onData(List<AlmacenResponse> lista) {
        binding.progressAlmacenes.setVisibility(View.GONE);
        if (lista != null) {
            adapter.updateData(lista);
            adapter.notifyDataSetChanged();
        }
        // podrías mostrar un "vacío" si lista==null o empty
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
