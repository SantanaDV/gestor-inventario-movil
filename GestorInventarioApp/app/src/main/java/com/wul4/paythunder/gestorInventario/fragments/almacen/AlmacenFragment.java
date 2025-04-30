package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.databinding.FragmentAlmacenBinding;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.Utils;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAlmacen;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Response;


public class AlmacenFragment extends Fragment  {

    private FragmentAlmacenBinding binding;
    private  int cantidad;
    private List<Categoria> categorias = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Obtenemos la instancia del ViewModel asociado al fragmento
        AlmacenViewModel.AlmacenViewModel almacenViewModel =
                new ViewModelProvider(this).get(AlmacenViewModel.AlmacenViewModel.class);
        //inflamos el binding con el layout del fragmento
        binding = FragmentAlmacenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inflarSpinner(binding);
        cantidad = -1;
        //Observamos el LiveData del ViewModel y actualizamos la UI de productos (por implementar)
        almacenViewModel.getProductos().observe(getViewLifecycleOwner(), productos -> {
            // Actualizamos la lista de productos en la UI
            procesarProductos(productos, cantidad, "");
        });
        binding.btnFiltrar.setOnClickListener(v -> {
            if(!(binding.etCantidadMaxima.getText().toString().equals(""))){
                cantidad = Integer.parseInt(binding.etCantidadMaxima.getText().toString());

            }else{
                cantidad = -1;
            }
            almacenViewModel.getProductos().observe(getViewLifecycleOwner(), productos -> {
                // Actualizamos la lista de productos en la UI
                procesarProductos(productos, cantidad, binding.spinnerCategoria.getSelectedItem().toString());
            });
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void inflarSpinner(FragmentAlmacenBinding binding){
        List<String> categorias = new ArrayList<>();
        List<String> nombresCategorias = new ArrayList<>();
        nombresCategorias.add("");
        ApiAlmacen apiAlmacen = ApiClient.getClient().create(ApiAlmacen.class);
        Call<List<Categoria>> call = apiAlmacen.getCategorias();
        call.enqueue(new retrofit2.Callback<List<Categoria>>() {

            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Categoria> categorias = response.body();
                    for (Categoria c : categorias) {
                        nombresCategorias.add(c.getDescripcion());

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });



        binding.spinnerCategoria.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresCategorias));
    }



    /**
     * Método para separar la lista de productos en activos e inactivos,
     * y luego llamar a mostrarProductos() para inflar las tarjetas en las columnas correspondientes.
     *
     * @param productos Lista completa de productos.
     */
    private void procesarProductos(List<Producto> productos, int filtroCantidad, String filtroCategoria) {
        List<Producto> activos;
        List<Producto> inactivos;

        activos = new ArrayList<>();
        inactivos = new ArrayList<>();
        for (Producto p : productos) {
            if ("activo".equalsIgnoreCase(p.getEstado())) {
                activos.add(p);
            } else {
                inactivos.add(p);
            }
        }

        // Llamamos al método que se encargará de inflar las tarjetas y colocarlas en la UI.
        mostrarProductos(activos, inactivos, filtroCantidad, filtroCategoria);
    }


    /**
     * Metodo auxiliar para mostrar los productos en función del estado
     * @param activos
     * @param inactivos
     */

    private void mostrarProductos(List<Producto> activos, List<Producto> inactivos, int filtroCantidad, String filtroCategoria) {
        binding.columnaActivos.removeAllViews();
        binding.columnaInactivos.removeAllViews();
        //Obtenemos el LayoutInflater para inflar las tarjetas
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Mostrar productos activos
        agregarProductosAColumna(activos, binding.columnaActivos, inflater, filtroCantidad, filtroCategoria);

        // Mostrar productos inactivos
        agregarProductosAColumna(inactivos, binding.columnaInactivos, inflater, filtroCantidad, filtroCategoria);
    }


    /**
     * Metodo auxiliar para agregar productos a una columna, que filtra por cantidad y categoría en caso de estar activos
     * @param productos
     * @param columna
     * @param inflater
     */
    private void agregarProductosAColumna(List<Producto> productos,
                                          LinearLayout columna,
                                          LayoutInflater inflater,
                                          int filtroCantidad,
                                          String filtroCategoria) {
        // Definimos si cada filtro está activo
        boolean filtroCantidadActivado  = filtroCantidad != -1;
        boolean filtroCategoriaActivado = filtroCategoria != null && !filtroCategoria.isEmpty();

        for (Producto p : productos) {
            // coincideCantidad será true si:
            //   el filtro cantidad está apagado (-1), o
            //  el producto tiene exactamente esa cantidad
            boolean coincideCantidad = !filtroCantidadActivado
                    || p.getCantidad() == filtroCantidad;

            // coincideCategoria será true si:
            //   el filtro categoría está apagado (""), o
            //  el producto pertenece a esa categoría
            boolean coincideCategoria = !filtroCategoriaActivado
                    || filtroCategoria.equals(p.getCategoria().getDescripcion());

            // Solo cuando pase ambos (los que estén activos), lo mostramos
            if (coincideCantidad && coincideCategoria) {
                View itemView = inflater.inflate(
                        R.layout.item_producto, columna, false
                );
                paintBackgroundCard(itemView, p.getEstado());

                TextView tvNombre   = itemView.findViewById(R.id.tvNombre);
                TextView tvDetalles = itemView.findViewById(R.id.tvDetalles);
                ImageView imgProd   = itemView.findViewById(R.id.imgProducto);

                tvNombre.setText(p.getNombre());
                tvDetalles.setText(
                        "Cantidad: " + p.getCantidad() +
                                " / Categoría: " + p.getCategoria().getDescripcion()
                );

                String urlImagen = ApiClient
                        .getClient().baseUrl() +
                        "imagen/" + p.getUrl_img();
                Utils.cargaDeImagenesConReintento(
                        this, imgProd, urlImagen, 3
                );

                itemView.setOnClickListener(v -> {
                    DetalleProductoDialogFragment dialog =
                            DetalleProductoDialogFragment.newInstance(p);
                    dialog.show(getParentFragmentManager(), "DetalleProducto");
                });
                itemView.setOnLongClickListener(v -> {
                    showContextMenu(p, v);
                    return true;
                });

                columna.addView(itemView);
            }
        }
    }



    /**
     * Método para editar un producto.
     *
     *
     * @param producto El producto a editar.
     */
    private void editarProducto(Producto producto) {
        Toast.makeText(getContext(), "Editar " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        // Por implementar
    }

    /**
     * Método para borrar un producto.
     *
     * @param producto El producto a borrar.
     */
    private void borrarProducto(Producto producto) {
        Toast.makeText(getContext(), "Borrar " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        // Por implementar
    }


    private void paintBackgroundCard(View itemView, String estado) {
        if ("activo".equalsIgnoreCase(estado)) {
            itemView.setBackgroundResource(R.drawable.card_background_activo);
        } else {
            itemView.setBackgroundResource(R.drawable.card_background_inactivo);
        }
    }

    /**
     * Método para mostrar un menú contextual al hacer long click en la tarjeta de producto,
     * permitiendo editar o borrar el producto.
     *
     * @param producto El producto sobre el que se hizo long click.
     * @param anchorView La vista donde se ancla el menú contextual.
     */
    private void showContextMenu(Producto producto, View anchorView) {
        // Creamos un PopupMenu anclado a la vista (la tarjeta de producto)
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        // Inflamos el menú contextual desde el recurso menu/producto_context_menu.xml
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.producto_context_menu, popup.getMenu());
        // Configuramos el listener para manejar las opciones
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_editar) {
                editarProducto(producto);
                return true;
            } else if (id == R.id.action_borrar) {
                borrarProducto(producto);
                return true;
            }
            return false;
        });
        popup.show();
    }

}
