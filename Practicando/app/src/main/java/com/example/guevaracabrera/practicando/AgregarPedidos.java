package com.example.guevaracabrera.practicando;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guevaracabrera.practicando.Complementos.SwipeListViewTouchListener;
import com.example.guevaracabrera.practicando.ItemAdapter.ItemAdapterCategoria;
import com.example.guevaracabrera.practicando.ItemAdapter.ItemAdapterDeta;
import com.example.guevaracabrera.practicando.ItemAdapter.ItemAdapterDeta1;
import com.example.guevaracabrera.practicando.ItemAdapter.ItemAdapterPro;
import com.example.guevaracabrera.practicando.LLenarDatos.DetallePedDao;
import com.example.guevaracabrera.practicando.LLenarDatos.PedidoDao;
import com.example.guevaracabrera.practicando.LLenarDatos.ProductosDao;
import com.example.guevaracabrera.practicando.Modelo.DetalleProducto;
import com.example.guevaracabrera.practicando.Modelo.Pedido;
import com.example.guevaracabrera.practicando.Modelo.Productos;
import com.example.guevaracabrera.practicando.Modelo.Tipoplato;

import java.util.ArrayList;
import java.util.List;


public class AgregarPedidos extends AppCompatActivity {
    private ListView ListaProductos, ListaDetalleBD, ListaDetalleTemporal, CategoriaPalto;
    static List<DetalleProducto> DetalleProdTempo;
    private static List<DetalleProducto> DetalleProdBd;
    private ProductosDao productosDao;
    private DetallePedDao detallePedDao;
    private Pedido pedido;
    private Productos productos;
    static TextView TxtTotalP;
    private Button guardarDetalle;
    private DrawerLayout mDrawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_agregarpedido);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        agregarToolbar();
        IniciarLizarMetodos();
    }

    private void IniciarLizarMetodos() {
        DetalleProdTempo = new ArrayList<>();
        productosDao = new ProductosDao();
        detallePedDao = new DetallePedDao();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        CategoriaPalto = (ListView) findViewById(R.id.ListCategoriaPalto);
        ListaProductos = (ListView) findViewById(R.id.ListaProductos);
        ListaDetalleBD = (ListView) findViewById(R.id.ListaDetalleBD);
        ListaDetalleTemporal = (ListView) findViewById(R.id.ListaDetalleTemporal);
        TxtTotalP = (TextView) findViewById(R.id.TxtTotalP);
        guardarDetalle = (Button) findViewById(R.id.JTB_Agregar);
        pedido = (Pedido) getIntent().getExtras().getSerializable("pedido");
        DetalleProdBd = detallePedDao.DatosDetalle(pedido.getIdPedido(), getApplicationContext());
        ListaDetalleBD.setAdapter(new ItemAdapterDeta1(DetalleProdBd));
        CategoriaPalto.setAdapter(new ItemAdapterCategoria(productosDao.ListCategoria(getApplication())));
        CategoriaPalto.setOnItemClickListener(new DrawerItemClickListener());
        ActualizarTotal();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Tipoplato tipoplato = (Tipoplato) CategoriaPalto.getAdapter().getItem(position);
            ListaProductos.setAdapter(new ItemAdapterPro(productosDao.ListaProductos(getApplicationContext(), tipoplato.getIdTipo())));
            EventosListView();
            EleminarFilaDetalle();
            GuardarDatos();
            mDrawerLayout.closeDrawers();
        }
    }


    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.IdToolbarAPe);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        NavigationView view = (NavigationView) findViewById(R.id.Menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void GuardarDatos() {
        guardarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (DetalleProducto detalleProducto1 : DetalleProdTempo) {
                    detallePedDao.AgregarDetalle(new DetalleProducto(pedido, detalleProducto1.getProducto(), detalleProducto1.getPrecio(), detalleProducto1.getCantidad()), getApplicationContext());
                }
                DetalleProdTempo = new ArrayList<>();
                ListaDetalleTemporal.setAdapter(new ItemAdapterDeta(DetalleProdTempo, 2));
                DetalleProdBd = detallePedDao.DatosDetalle(pedido.getIdPedido(), getApplicationContext());
                ListaDetalleBD.setAdapter(new ItemAdapterDeta1(DetalleProdBd));
                ActualizarTotal();
            }
        });

    }


    private void EventosListView() {
        ListaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                productos = (Productos) ListaProductos.getAdapter().getItem(position);
                int post = -1;
                for (DetalleProducto detalleProducto : DetalleProdTempo) {
                    if (detalleProducto.getProducto() == productos) {
                        post = 0;
                    }
                }
                if (post == 0) {
                    Toast.makeText(getApplicationContext(), "Productos Separado, Verifique el Detalle Pedido", Toast.LENGTH_LONG).show();
                } else {
                    MensajeDialog();
                }
            }
        });

    }

    private void MensajeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater activador = LayoutInflater.from(this);
        final View entrada = activador.inflate(R.layout.mostar_producto, null);
        TextView descripcion = (TextView) entrada.findViewById(R.id.Txt_Descripcion);
        descripcion.setText(productos.getDescripcion());
        builder.setTitle("Detalle del Producto").
                setView(entrada).
                setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText cantidad = (EditText) entrada.findViewById(R.id.Txt_Cantidad);
                        AgregarDetalles(cantidad);
                    }

                    private void AgregarDetalles(EditText cantidad) {
                        if (cantidad.getText().toString().trim() != "") {
                            if (Integer.parseInt(cantidad.getText().toString()) > 0) {
                                AgregarDetalle(Integer.parseInt(cantidad.getText().toString()));
                            } else {
                                Toast.makeText(getApplicationContext(), "Ingrese un numero Positivo", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Ingrese Datos", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void AgregarDetalle(int cantidad) {
                        DetalleProducto detalleProducto = new DetalleProducto();
                        detalleProducto.setPrecio(productos.getPrecio());
                        detalleProducto.setCantidad(cantidad);
                        detalleProducto.setProducto(productos);
                        detalleProducto.setSubtotal();
                        DetalleProdTempo.add(detalleProducto);
                        ListaDetalleTemporal.setAdapter(new ItemAdapterDeta(DetalleProdTempo, 2));
                        ActualizarTotal();
                    }
                }).setNegativeButton("Cancelar", null);
        builder.show();
    }

    public static void ActualizarTotal() {
        double total1 = 0, total2 = 0;
        for (DetalleProducto dd : DetalleProdBd) {
            total2 += dd.getSubtotal();
        }
        for (DetalleProducto detalle : DetalleProdTempo) {
            total1 += detalle.getSubtotal();
        }
        TxtTotalP.setText(Double.toString(total1 + total2));

    }

    public void EleminarFilaDetalle() {
        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(ListaDetalleTemporal, new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
            }

            @Override
            public void onSwipeRight(ListView listView, final int[] reverseSortedPositions) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AgregarPedidos.this);
                builder.setTitle("Confirmación de Eleminar").setMessage("¿Deseas Eleminar El Producto Pedido ?").
                        setPositiveButton("Eleminar Producto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DetalleProdTempo.remove(reverseSortedPositions[0]);
                                new ItemAdapterDeta(DetalleProdTempo, 2).notifyDataSetChanged();
                                ListaDetalleTemporal.setAdapter(new ItemAdapterDeta(DetalleProdTempo, 2));
                                ActualizarTotal();
                            }
                        })
                        .setNegativeButton("Cancelar", null);
                builder.show();
            }
        }, true, false);
        ListaDetalleTemporal.setOnTouchListener(touchListener);
        ListaDetalleTemporal.setOnScrollListener(touchListener.makeScrollListener());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            DetalleProdTempo = new ArrayList<DetalleProducto>();
            ListaDetalleTemporal.setAdapter(new ItemAdapterDeta(DetalleProdTempo, 2));
            ActualizarTotal();
            PedidoDao pedidos = new PedidoDao();
            pedidos.ListaPedido(getApplicationContext());
            SelecionMesa.ListDeta.setVisibility(View.INVISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }

}
