package com.uss.GuevaraMonsalveBernal.Proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import com.uss.GuevaraMonsalveBernal.Proyecto.Complementos.*;
import com.uss.GuevaraMonsalveBernal.Proyecto.ItemAdapter.*;
import com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos.DetallePedDao;
import com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos.PedidoDao;
import com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos.ProductosDao;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.*;

import java.util.ArrayList;
import java.util.List;

public class HacerPedido extends AppCompatActivity {

    private ProductosDao productosDao;
    private static List<DetalleProducto> detalles;
    private ListView listPro, ListDeta, CategoriaPalto;
    private Productos item;
    static TextView total;
    private Button cancel, guardar;
    private Login logeos;
    private Mesa mesas;
    private PedidoDao pedido;
    private DetallePedDao detalle;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        agregarToolbar();
        InicializarMetodos();
    }

    private void InicializarMetodos() {
        logeos = (Login) getIntent().getExtras().getSerializable("login");
        mesas = (Mesa) getIntent().getExtras().getSerializable("mesa");
        pedido = new PedidoDao();
        detalle = new DetallePedDao();
        detalles = new ArrayList<>();
        productosDao = new ProductosDao();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
        CategoriaPalto = (ListView) findViewById(R.id.ListCategoriaPalto1);
        listPro = (ListView) findViewById(R.id.ListPro);
        ListDeta = (ListView) findViewById(R.id.ListDeta);
        total = (TextView) findViewById(R.id.Txt_Total);
        guardar = (Button) findViewById(R.id.JTB_Guardar);
        cancel = (Button) findViewById(R.id.JTB_Cancel);
        CategoriaPalto.setAdapter(new ItemAdapterCategoria(productosDao.ListCategoria(getApplication())));
        CategoriaPalto.setOnItemClickListener(new DrawerItemClickListener());
        ActualizarTotal();
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


    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.IdToolbarN);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        NavigationView view = (NavigationView) findViewById(R.id.Menu1);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Tipoplato tipoplato = (Tipoplato) CategoriaPalto.getAdapter().getItem(position);
            listPro.setAdapter(new ItemAdapterPro(productosDao.ListaProductos(getApplicationContext(), tipoplato.getIdTipo())));
            EventosLista();
            Eleminar();
            ActualizarTotal();
            GuardarDatos();
            mDrawerLayout.closeDrawers();
        }
    }

    private void EventosLista() {
        listPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (Productos) listPro.getAdapter().getItem(position);
                int post = -1;
                for (DetalleProducto det : detalles) {
                    if (det.getProducto() == item) {
                        post = 0;
                    }
                }
                if (post == 0) {
                    Toast.makeText(HacerPedido.this, "Productos Separado, Verifique el Detalle Pedido", Toast.LENGTH_LONG).show();
                } else {
                    createSimpleDialog(item);
                }
            }
        });

    }

    public static void ActualizarTotal() {
        double total1 = 0;
        for (DetalleProducto detalle : detalles) {
            total1 += detalle.getSubtotal();
        }
        total.setText(Double.toString(total1));

    }

    private void Eleminar() {
        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(ListDeta, new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
            }

            @Override
            public void onSwipeRight(ListView listView, final int[] reverseSortedPositions) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HacerPedido.this);
                builder.setTitle("Confirmación de Eleminar").setMessage("¿Deseas Eleminar El Producto Pedido ?").
                        setPositiveButton("Eleminar Producto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                detalles.remove(reverseSortedPositions[0]);
                                new ItemAdapterDeta(detalles, 1).notifyDataSetChanged();
                                ListDeta.setAdapter(new ItemAdapterDeta(detalles, 1));
                                ActualizarTotal();
                            }
                        })
                        .setNegativeButton("Cancelar", null);
                builder.show();
            }
        }, true, false);
        ListDeta.setOnTouchListener(touchListener);
        ListDeta.setOnScrollListener(touchListener.makeScrollListener());
    }

    public void createSimpleDialog(Productos pro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater activador = LayoutInflater.from(this);
        final View entrada = activador.inflate(R.layout.mostar_producto, null);

        TextView descripcion = (TextView) entrada.findViewById(R.id.Txt_Descripcion);
        descripcion.setText(pro.getDescripcion());
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
                                Toast.makeText(HacerPedido.this, "Ingrese un numero Positivo", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(HacerPedido.this, "Ingrese Datos", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void AgregarDetalle(int cantidad) {
                        DetalleProducto detalleProducto = new DetalleProducto();
                        detalleProducto.setPrecio(item.getPrecio());
                        detalleProducto.setCantidad(cantidad);
                        detalleProducto.setProducto(item);
                        detalleProducto.setSubtotal();
                        detalles.add(detalleProducto);
                        ListDeta.setAdapter(new ItemAdapterDeta(detalles, 1));
                        ActualizarTotal();
                    }
                }).setNegativeButton("Cancelar", null);
        builder.show();

    }

    public void GuardarDatos() {
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido ped = new Pedido(mesas, logeos.getEmpleado(), Double.parseDouble(total.getText().toString()));
                Pedido pp = pedido.AgregarPedido(ped, getApplicationContext());
                for (DetalleProducto detalles1 : detalles) {
                    detalle.AgregarDetalle(new DetalleProducto(pp, detalles1.getProducto(), detalles1.getPrecio(), detalles1.getCantidad()), getApplicationContext());
                }
                detalles = new ArrayList<DetalleProducto>();
                ListDeta.setAdapter(new ItemAdapterDeta(detalles, 1));
                ActualizarTotal();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalles = new ArrayList<DetalleProducto>();
                ListDeta.setAdapter(new ItemAdapterDeta(detalles, 1));
                Intent e = new Intent(getApplicationContext(), SelecionMesa.class);
                e.putExtra("parametro", logeos);
                startActivity(e);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Toast.makeText(HacerPedido.this, "Regresando al Selecionar Mesa", Toast.LENGTH_LONG).show();
            detalles = new ArrayList<DetalleProducto>();
            ListDeta.setAdapter(new ItemAdapterDeta(detalles, 1));
            ActualizarTotal();
            pedido.ListaPedido(getApplicationContext());
            SelecionMesa.ListDeta.setVisibility(View.INVISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }
}




