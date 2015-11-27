package com.example.guevaracabrera.practicando.LLenarDatos;

import android.content.Context;
import android.widget.Toast;

import com.example.guevaracabrera.practicando.Modelo.Categoriaempleado;
import com.example.guevaracabrera.practicando.Modelo.Empleado;
import com.example.guevaracabrera.practicando.Modelo.Login;
import com.example.guevaracabrera.practicando.Modelo.Mesa;
import com.example.guevaracabrera.practicando.Modelo.Pedido;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 18/11/2015.
 */
public class LoginDao {

    public synchronized Login Logearse(Context applicationContext, String user, String clave) {
        Login logeo = null;
        try {
            String url = Conexion.URL_APP + "service/ConsultaEmpleado.php?usuario=" + user.trim() + "&pass=" + clave.trim();
            String jsonResult = Conexion.getConexion(url);
            JSONObject e = new JSONObject(jsonResult);
            String estado = e.getString("estado");
            if (estado.equals("0")) {
                throw new Exception("Dato incorrecto.");
            } else {
                logeo= new Login();
                logeo.setIdLogeo(e.getInt("idlogeo"));
                Empleado ll = new Empleado(e.getInt("idempleado"), e.getString("nombre"),
                        e.getString("apellidos"), e.getString("usuario"), e.getString("contra"));
                logeo.setEmpleado(ll);
                Categoriaempleado mm = new Categoriaempleado(e.getInt("idcategoria"), e.getString("nombrecategoria"));
                logeo.setCategoriaempleado(mm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(applicationContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return logeo;
    }
}
