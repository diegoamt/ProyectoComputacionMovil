package com.example.guevaracabrera.practicando.LLenarDatos;

import android.content.Context;
import android.widget.Toast;

import com.example.guevaracabrera.practicando.Modelo.Mesa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 01/11/2015.
 */
public class MesaDao implements Serializable {
    public List<Mesa> mesaList = new ArrayList<>();

    public synchronized List<Mesa> getMesaList(Context applicationContext) {
        List<Mesa> mesaList = null;
        try {
            String url = Conexion.URL_APP + "Select/ListaMesa.php";
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                throw new Exception("Errror no encontro las mesas");
            }
            JSONObject json = new JSONObject(jsonResult);
            JSONArray jArray = json.getJSONArray("mesa");
            mesaList = new ArrayList<>();
            Mesa map = null;
            for (int i = 0; i < jArray.length(); i++) {
                map = new Mesa();
                JSONObject e = jArray.getJSONObject(i);
                map.setIdMesa(Integer.parseInt(e.getString("idmesa").toString()));
                map.setNmesa(e.getString("nmesa").toString());
                map.setDescripcion(e.getString("descripcion").toString());
                mesaList.add(map);
            }
        } catch (Exception e) {
            Toast.makeText(applicationContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return mesaList;
    }
}
