package com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos;

import android.content.Context;

import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Categoriaempleado;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Empleado;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Login;

import org.json.JSONObject;

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
