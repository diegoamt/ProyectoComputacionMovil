package com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos;

import android.widget.Toast;

import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Tipoplato;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 01/11/2015.
 */
public class CategoriaDao {
    public static List<Tipoplato> tipoplatoList = new ArrayList<>();

    public synchronized List<Tipoplato> ListarTipoplatoList() {
        try {
            String url = Conexion.URL_APP + "Select/ListaCategoria.php";
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                throw new Exception("Errror");
            }
            JSONObject json = new JSONObject(jsonResult);
            JSONArray jArray = json.getJSONArray("catego");
            for (int i = 0; i < jArray.length(); i++) {
                Tipoplato map = new Tipoplato();
                JSONObject e = jArray.getJSONObject(i);
                map.setIdTipo(Integer.parseInt(e.getString("idtipo").toString()));
                map.setNombreTipo(e.getString("nombretipo").toString());
                map.setDescripcion(e.getString("descripcion").toString());
                map.setImagen(e.getString("imagen").toString());
                tipoplatoList.add(map);
            }
        } catch (Exception ex) {
            Toast.makeText(null, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return tipoplatoList;
    }


}
