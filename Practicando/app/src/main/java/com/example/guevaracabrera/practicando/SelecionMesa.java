package com.example.guevaracabrera.practicando;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guevaracabrera.practicando.ItemAdapter.ItemAdapterDeta1;
import com.example.guevaracabrera.practicando.ItemAdapter.ItemAdapterMesas;
import com.example.guevaracabrera.practicando.LLenarDatos.Conexion;
import com.example.guevaracabrera.practicando.LLenarDatos.DetallePedDao;
import com.example.guevaracabrera.practicando.LLenarDatos.MesaDao;
import com.example.guevaracabrera.practicando.LLenarDatos.PedidoDao;
import com.example.guevaracabrera.practicando.Modelo.DetalleProducto;
import com.example.guevaracabrera.practicando.Modelo.Empleado;
import com.example.guevaracabrera.practicando.Modelo.Login;
import com.example.guevaracabrera.practicando.Modelo.Mesa;
import com.example.guevaracabrera.practicando.Modelo.Pedido;
import com.example.guevaracabrera.practicando.Modelo.Productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 01/11/2015.
 */
public class SelecionMesa extends AppCompatActivity {
    private ListView listaMesas, listPedidoDe;
    private Mesa mesaselct;
    private TextView nombre, estado, descrip, Txt_MontoFinal;
    private LinearLayout ListPedidos, MontoPagar;
    public static LinearLayout ListDeta;
    private Button NuevoPedido, AgregarProducto, JTB_Pagar;
    private Login objecto;
    private MesaDao mesa;
    private PedidoDao pedidoDao;
    private DetallePedDao detallePedDao;
    private Pedido pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mesa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.IdToolbarM);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.SelecionarMesa);
        AnadirContructor();
        objecto = (Login) getIntent().getExtras().getSerializable("parametro");
        Limpiar();
        Eventos();
    }

    private void Eventos() {
        listaMesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListDeta.setVisibility(View.VISIBLE);
                mesaselct = (Mesa) listaMesas.getAdapter().getItem(position);
                MostarDatos(mesaselct);
            }
        });
        NuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getApplicationContext(), HacerPedido.class);
                e.putExtra("login", objecto);
                e.putExtra("mesa", mesaselct);
                startActivity(e);
            }
        });
        AgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getApplicationContext(), AgregarPedidos.class);
                e.putExtra("login", objecto);
                e.putExtra("mesa", mesaselct);
                e.putExtra("pedido", pedido);
                startActivity(e);
            }
        });

    }

    private void AnadirContructor() {
        mesa = new MesaDao();
        pedidoDao = new PedidoDao();
        detallePedDao = new DetallePedDao();
        listPedidoDe = (ListView) findViewById(R.id.ListasDetalles);
        listaMesas = (ListView) findViewById(R.id.ListasMesas);
        listaMesas.setAdapter(new ItemAdapterMesas(mesa.getMesaList(getApplicationContext())));
        nombre = (TextView) findViewById(R.id.Txt_NumeroMesa);
        estado = (TextView) findViewById(R.id.Txt_EstadoMesa);
        descrip = (TextView) findViewById(R.id.TxtDescripcM);
        ListPedidos = (LinearLayout) findViewById(R.id.ListPedidos);
        ListDeta = (LinearLayout) findViewById(R.id.ListDetalles);
        MontoPagar = (LinearLayout) findViewById(R.id.MontoPagar);
        Txt_MontoFinal = (TextView) findViewById(R.id.Txt_MontoFinal);
        NuevoPedido = (Button) findViewById(R.id.JTB_NuevoP);
        AgregarProducto = (Button) findViewById(R.id.JTB_AgregarPe);
        JTB_Pagar = (Button) findViewById(R.id.JTB_Pagar);
        ListDeta.setVisibility(View.INVISIBLE);
    }

    private void Limpiar() {
        nombre.setText("");
        descrip.setText("");
        estado.setText("");
    }

    public void MostarDatos(Mesa mesaselct) {
        pedido = pedidoDao.BuscarPedido(mesaselct.getIdMesa(), getApplicationContext());
        nombre.setText(mesaselct.getNmesa());
        descrip.setText(mesaselct.getDescripcion());
        ListPedidos.setVisibility((pedido == null) ? View.INVISIBLE : View.VISIBLE);
        MontoPagar.setVisibility((pedido == null) ? View.INVISIBLE : View.VISIBLE);
        NuevoPedido.setVisibility((pedido != null) ? View.INVISIBLE : View.VISIBLE);
        AgregarProducto.setVisibility((pedido == null) ? View.INVISIBLE : View.VISIBLE);
        Txt_MontoFinal.setText((pedido == null) ? "0.00" :
                Double.toString(pedido.getTotal()));
        LlenarListView(pedido);
    }

    private void LlenarListView(Pedido pedido) {
        if (pedido != null) {
            List<DetalleProducto> detalles = detallePedDao.DatosDetalle(pedido.getIdPedido(), getApplicationContext());
            listPedidoDe.setAdapter(new ItemAdapterDeta1(detalles));
        }
    }

    public void Pagar(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater activador = LayoutInflater.from(this);
        final View entrada = activador.inflate(R.layout.layout_pago, null);
        final TextView TotalP = (TextView) entrada.findViewById(R.id.Txt_Totalidad);
        final EditText PAGOc = (EditText) entrada.findViewById(R.id.Txt_tO);
        final TextView Vuelto = (TextView) entrada.findViewById(R.id.Txt_Vuelto);
        final Button cal =(Button)entrada.findViewById(R.id.JTBCal);
        TotalP.setText(Double.toString(pedido.getTotal()));
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vuelto.setText(Double.toString(Double.parseDouble(PAGOc.getText().toString())-Double.parseDouble(TotalP.getText().toString())));
            }
        });
        builder.setTitle("Detalle final Pago").setView(entrada).
                setPositiveButton("Realizar Pago", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        pedidoDao.AgregarPago(getApplicationContext(),pedido,Double.parseDouble(PAGOc.getText().toString()));
                        pedidoDao.ListaPedido(getApplicationContext());
                        ListDeta.setVisibility(View.INVISIBLE);
                    }

                }).setNegativeButton("Cancelar", null);
        builder.show();
    }
}
