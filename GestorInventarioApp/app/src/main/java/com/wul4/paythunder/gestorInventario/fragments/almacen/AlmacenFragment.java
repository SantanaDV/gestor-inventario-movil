package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.databinding.FragmentAlmacenBinding;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;


public class AlmacenFragment extends Fragment  {

    //Clase binding generada a partir del fragment_almacen.xml
    private FragmentAlmacenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Obtenemos la instancia del ViewModel asociado al fragmento
        AlmacenViewModel almacenViewModel =
                new ViewModelProvider(this).get(AlmacenViewModel.class);
        //inflamos el binding con el layout del fragmento
        binding = FragmentAlmacenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Observamos el LiveData del ViewModel y actualizamos la UI de productos (por implementar)
        /*almacenViewModel.getProductos().observe(getViewLifecycleOwner(), productos -> {
            procesarProductos(productos);
        });
*/
        //Llamamos al método para cargar productos desde la API
        cargarProductos();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Método para cargar los productos.
     * Este método puede realizar una llamada a la API (normalmente se hace en el ViewModel)
     * y actualizar el LiveData con la lista de productos.
     * Para este ejemplo, vamos a simular la carga de datos.
     */
    private void cargarProductos() {
        //Hay que hacer la llamada a la api para obtener los productos
        //Para este ejemplo, vamos a simular la carga de datos.

        // Simulación: Creamos una lista de productos de ejemplo.
        List<Producto> productos = new ArrayList<>();

        Producto p1 = new Producto();
        p1.setId_producto(1);
        p1.setNombre("Producto Activo 1");
        p1.setCantidad(10);
        p1.setEstado("activo");
        p1.setUrl_img("1743679342907_Camiseta_lisa.jpg");
        p1.setCategoria("Categoría A");
        productos.add(p1);


        Producto p2 = new Producto();
        p2.setId_producto(2);
        p2.setNombre("Producto Desactivadooooooooooo 1");
        p2.setCantidad(0);
        p2.setEstado("desactivado");
        p2.setUrl_img("1743679342907_Camiseta_lisa.jpg");
        p2.setCategoria("Categoría B");
        productos.add(p2);

        // Una vez obtenida la lista, la procesa para separarla en activos e inactivos
        procesarProductos(productos);
    }

    /**
     * Método para separar la lista de productos en activos e inactivos,
     * y luego llamar a mostrarProductos() para inflar las tarjetas en las columnas correspondientes.
     *
     * @param productos Lista completa de productos.
     */
    private void procesarProductos(List<Producto> productos) {
        List<Producto> activos = new ArrayList<>();
        List<Producto> inactivos = new ArrayList<>();

        for (Producto p : productos) {
            if ("activo".equalsIgnoreCase(p.getEstado())) {
                activos.add(p);
            } else {
                inactivos.add(p);
            }
        }

        // Llamamos al método que se encargará de inflar las tarjetas y colocarlas en la UI.
        mostrarProductos(activos, inactivos);
    }


    /**
     * Metodo auxiliar para mostrar los productos en función del estado
     * @param activos
     * @param inactivos
     */

    private void mostrarProductos(List<Producto> activos, List<Producto> inactivos) {
        binding.columnaActivos.removeAllViews();
        binding.columnaInactivos.removeAllViews();
        //Obtenemos el LayoutInflater para inflar las tarjetas
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Mostrar productos activos
        agregarProductosAColumna(activos, binding.columnaActivos, inflater);

        // Mostrar productos inactivos
        agregarProductosAColumna(inactivos, binding.columnaInactivos, inflater);
    }


    /**
     * Metodo auxiliar para agregar productos a una columna
     * @param productos
     * @param columna
     * @param inflater
     */
    private void agregarProductosAColumna(List<Producto> productos, LinearLayout columna, LayoutInflater inflater) {
        for (Producto p : productos) {
            View itemView = inflater.inflate(R.layout.item_producto, columna, false);
            //Aplicamos el color de fondo correspondiente rojo inactivo y verde activo
            paintBackgroundCard(itemView, p.getEstado());
            //Obtenemos los elementos de la tarjeta
            TextView tvNombre = itemView.findViewById(R.id.tvNombre);
            TextView tvDetalles = itemView.findViewById(R.id.tvDetalles);
            ImageView imgProducto = itemView.findViewById(R.id.imgProducto);

            tvNombre.setText(p.getNombre());
            tvDetalles.setText("Cantidad: " + p.getCantidad() + " / Categoría: " + p.getCategoria());
            //Cargamos la imagen con reintento con el directorio de la imagen en el back
            String urlImagen = ApiClient.getClient().baseUrl() + "imagen/" + p.getUrl_img();
            Utils.cargaDeImagenesConReintento(this, imgProducto, urlImagen, 3);
            //Configuramos el listener para el click para mostrar el detalle del producto
            itemView.setOnClickListener(v -> {
                DetalleProductoDialogFragment dialog = DetalleProductoDialogFragment.newInstance(p);
                dialog.show(getParentFragmentManager(), "DetalleProducto");
            });

            //Configuramos el listener para el long click para poder editar o borrar el producto
            itemView.setOnLongClickListener(v -> {
                showContextMenu(p, v);
                return true;
            });

            columna.addView(itemView);
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
     * permitiendo editar o borrar el producto (sin iconos visibles en la tarjeta).
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