package com.example.guevaracabrera.practicando.LLenarDatos;

import android.content.Context;
import android.widget.Toast;

import com.example.guevaracabrera.practicando.Modelo.Productos;
import com.example.guevaracabrera.practicando.Modelo.Tipoplato;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductosDao {
    public synchronized List<Productos> ListaProductos(Context applicationContext, int idCategoria) {
        ArrayList<Productos> movimientos = null;
        try {
            String url = Conexion.URL_APP + "service/ConsultarPlato.php?codigoTipo=" + idCategoria;
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                throw new Exception("Codigo no existe");
            }
            JSONObject json = new JSONObject(jsonResult);
            JSONArray jArray = json.getJSONArray("platos");
            movimientos = new ArrayList<Productos>();
            Productos map;
            for (int i = 0; i < jArray.length(); i++) {
                map = new Productos();
                JSONObject e = jArray.getJSONObject(i);
                map.setIdproducto(Integer.parseInt(e.getString("idplato").toString()));
                map.setNombre(e.getString("nombreproducto").toString());
                map.setDescripcion(e.getString("descripcion").toString());
                map.setPrecio(Double.parseDouble(e.getString("precio").toString()));
                movimientos.add(map);
            }

        } catch (Exception e) {
            Toast.makeText(applicationContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return movimientos;
    }

    public synchronized List<Tipoplato> ListCategoria(Context applicationContext) {
        List<Tipoplato> movimientos = null;
        try {
            String url = Conexion.URL_APP + "Select/ListaCategoria.php?";
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                throw new Exception("Codigo no existe");
            }
            JSONObject json = new JSONObject(jsonResult);
            JSONArray jArray = json.getJSONArray("catego");
            movimientos = new ArrayList<>();
            Tipoplato map;
            for (int i = 0; i < jArray.length(); i++) {
                map = new Tipoplato();
                JSONObject e = jArray.getJSONObject(i);
                map.setIdTipo(e.getInt("idtipo"));
                map.setNombreTipo(e.getString("nombretipo"));
                map.setDescripcion(e.getString("descripcion"));
                movimientos.add(map);
            }

        } catch (Exception e) {
            Toast.makeText(applicationContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return movimientos;
    }

}


