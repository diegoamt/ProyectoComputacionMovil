package com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos;

import android.content.Context;
import android.widget.Toast;

import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.DetalleProducto;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 03/11/2015.
 */
public class DetallePedDao {
    public void AgregarDetalle(DetalleProducto detalleProducto, Context applicationContext) {
        try {
            String url = Conexion.URL_APP + "Insert/InsertarDetallePedido.php?idped=" + detalleProducto.getPedido().getIdPedido()
                    + "&idpr=" + detalleProducto.getProducto().getIdproducto() + "&cant=" + detalleProducto.getCantidad();
            Conexion.getConexion(url);
        } catch (Exception ex) {
            Toast.makeText(applicationContext, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public  List<DetalleProducto> DatosDetalle(Integer idPedido, Context applicationContext) {
        List<DetalleProducto> lista = null;
        try {
            String url = Conexion.URL_APP + "service/ConsultarDetallePedido.php?idpedido=" + idPedido;
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                lista = new ArrayList<>();
            } else {
                JSONObject json = new JSONObject(jsonResult);
                JSONArray jArray = json.getJSONArray("detalle");
                lista = new ArrayList<>();
                DetalleProducto map = null;
                for (int i = 0; i < jArray.length(); i++) {
                    map = new DetalleProducto();
                    JSONObject e = jArray.getJSONObject(i);
                    map.setIdDetalle(e.getInt("iddetalle"));
                    map.setProducto(new Productos(e.getInt("idplato"), e.getString("nombreproducto")));
                    map.setCantidad(e.getInt("cantidad"));
                    map.setPrecio(e.getDouble("precio"));
                    map.setSubtotal();
                    lista.add(map);
                }
            }
        } catch (Exception e) {
            Toast.makeText(applicationContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return lista;
    }
}

