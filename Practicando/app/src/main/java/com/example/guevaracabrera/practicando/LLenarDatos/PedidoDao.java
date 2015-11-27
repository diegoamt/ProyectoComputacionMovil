package com.example.guevaracabrera.practicando.LLenarDatos;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guevaracabrera.practicando.Modelo.Empleado;
import com.example.guevaracabrera.practicando.Modelo.Mesa;
import com.example.guevaracabrera.practicando.Modelo.Pedido;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 03/11/2015.
 */
public class PedidoDao {


    public Pedido AgregarPedido(Pedido ped, Context applicationContext) {
        Pedido pedido1 = null;
        try {
            String url = Conexion.URL_APP + "Insert/InsertarPedido.php?idme=" + ped.getMesa().getIdMesa()
                    + "&idEmpl=" + ped.getEmpleado().getIdEmpleado() + "&total=" + ped.getTotal();

            String jsonResult = Conexion.getConexion(url);
            JSONObject object = new JSONObject(jsonResult);
            String estado = object.getString("estado");
            if (estado.equals("0")) {
                throw new Exception("Dato incorrecto.");
            } else {
                pedido1 = new Pedido();
                pedido1.setIdPedido(object.getInt("idpedido"));
            }

        } catch (Exception ex) {
            Toast.makeText(applicationContext, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return pedido1;
    }

    public synchronized Pedido BuscarPedido(int Mesa, Context applicationContext) {
        Pedido ped = null;
        if (ListaPedido(applicationContext) == null) {
            ped = null;
        } else {
            List<Pedido> list = ListaPedido(applicationContext);
            for (Pedido pedd : list) {
                if (pedd.getMesa().getIdMesa() == Mesa) {
                    ped = pedd;
                }
            }
        }
        return ped;

    }

    public synchronized List<Pedido> ListaPedido(Context applicationContext) {
        List<Pedido> lista = null;
        try {
            String url = Conexion.URL_APP + "Select/ListPedido.php?";
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                lista = new ArrayList<Pedido>();
            } else {
                JSONObject json = new JSONObject(jsonResult);
                JSONArray jArray = json.getJSONArray("pedido");
                lista = new ArrayList<>();
                Pedido map = null;
                for (int i = 0; i < jArray.length(); i++) {
                    map = new Pedido();
                    JSONObject e = jArray.getJSONObject(i);
                    map.setIdPedido(Integer.parseInt(e.getString("idpedido").toString()));
                    map.setEmpleado(new Empleado(Integer.parseInt(e.getString("idempleado").toString()),
                            e.getString("nombre").toString(), e.getString("apellidos").toString(),
                            e.getString("usuario").toString(), e.getString("contra").toString()));
                    map.setMesa(new Mesa(Integer.parseInt(e.getString("idmesa").toString()),
                            e.getString("nmesa").toString(), e.getString("descripcion").toString()));
                    map.setTotal(Double.parseDouble(e.getString("total").toString()));
                    lista.add(map);
                }
            }
        } catch (Exception e) {
            Toast.makeText(applicationContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return lista;
    }

    public void AgregarPago(Context applicationContext, Pedido pedido, double pagOc) {
        try {
            String url = Conexion.URL_APP + "Insert/InsertarPago.php?IdPedido="+pedido.getIdPedido()+"&MontoP="+pagOc;
            Conexion.getConexion(url);
        } catch (Exception ex) {
            Toast.makeText(applicationContext, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

