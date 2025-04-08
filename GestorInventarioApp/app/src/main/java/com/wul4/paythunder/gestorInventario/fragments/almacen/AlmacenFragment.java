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

import java.util.ArrayList;
import java.util.List;


public class AlmacenFragment extends Fragment {

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
        p1.setUrl_img("producto1.jpg");
        p1.setCategoria("Categoría A");
        productos.add(p1);


        Producto p2 = new Producto();
        p2.setId_producto(2);
        p2.setNombre("Producto Desactivado 1");
        p2.setCantidad(0);
        p2.setEstado("desactivado");
        p2.setUrl_img("producto2.jpg");
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
     * Método que, dado dos listas de productos, infla el layout de cada producto y lo añade
     * a la columna correspondiente (activos o inactivos).
     *
     * @param activos Lista de productos activos.
     * @param inactivos Lista de productos inactivos.
     */
    private void mostrarProductos(List<Producto> activos, List<Producto> inactivos) {
        // Obtenemos las referencias a las columnas del layout a través del binding.
        LinearLayout columnaActivos = binding.columnaActivos;
        LinearLayout columnaInactivos = binding.columnaInactivos;

        // Limpiamos cualquier vista previa en las columnas
        columnaActivos.removeAllViews();
        columnaInactivos.removeAllViews();

        // Obtenemos el LayoutInflater para inflar cada tarjeta de producto.
        LayoutInflater inflater = LayoutInflater.from(getContext());



        // Iteramos sobre la lista de productos activos
        for (Producto p : activos) {
            // Inflamos el layout de tarjeta para cada producto sin adjuntarlo aún al padre.
            View itemView = inflater.inflate(R.layout.item_producto, columnaActivos, false);

            //Pintamos el background de la card en función del estado
            paintBackgroundCard(itemView, p.getEstado());

            // Obtenemos las referencias a los elementos de la tarjeta
            TextView tvNombre = itemView.findViewById(R.id.tvNombre);
            TextView tvDetalles = itemView.findViewById(R.id.tvDetalles);
            ImageView imgProducto = itemView.findViewById(R.id.imgProducto);

            // Asignamos datos al TextView
            tvNombre.setText(p.getNombre());
            tvDetalles.setText("Cantidad: " + p.getCantidad() + " / Categoría: " + p.getCategoria());

            // Construimos la URL de la imagen, mediante la URL del producto
            //Esta es de prueba, hay que cambiarla
            String urlImagen = ApiClient.getClient().baseUrl() + "imagen/1743679342907_Camiseta_lisa.jpg";
            // Cargamos la imagen en el ImageView con Glide
            Glide.with(getContext())
                    .load(urlImagen)
                    .placeholder(R.drawable.ic_placeholder) // el placeholder es una imagen temporal de carga o en caso de falla
                    .centerCrop()
                    .into(imgProducto);

            // Activamos el menu contextual al hacer click largo en la tarjeta
            itemView.setOnLongClickListener(v -> {
                showContextMenu(p, v);
                return true;
            });

            // Añadimos la tarjeta a la columna de activos
            columnaActivos.addView(itemView);
        }

        // Repetimos el proceso para los productos inactivos:
        for (Producto p : inactivos) {
            View itemView = inflater.inflate(R.layout.item_producto, columnaInactivos, false);

            //Pintamos el background de la card en función del estado
           paintBackgroundCard(itemView, p.getEstado());

            // Obtenemos las referencias a los elementos de la tarjeta
            TextView tvNombre = itemView.findViewById(R.id.tvNombre);
            TextView tvDetalles = itemView.findViewById(R.id.tvDetalles);
            ImageView imgProducto = itemView.findViewById(R.id.imgProducto);


            tvNombre.setText(p.getNombre());
            tvDetalles.setText("Cantidad: " + p.getCantidad() + " / Categoría: " + p.getCategoria());

            String urlImagen = ApiClient.getClient().baseUrl() + "imagen/" + p.getUrl_img();
            Glide.with(getContext())
                    .load("http://localhost:8080/imagen/1743679342907_Camiseta_lisa.jpg")
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imgProducto);

            //Activamos el menu contextual al hacer click largo en la tarjeta
            itemView.setOnLongClickListener(v -> {
                showContextMenu(p, v);
                return true;
            });

            columnaInactivos.addView(itemView);
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