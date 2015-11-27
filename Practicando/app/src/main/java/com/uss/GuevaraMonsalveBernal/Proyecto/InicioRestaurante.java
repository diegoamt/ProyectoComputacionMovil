package com.uss.GuevaraMonsalveBernal.Proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.uss.GuevaraMonsalveBernal.Proyecto.LLenarDatos.LoginDao;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 31/10/2015.
 */
public class InicioRestaurante extends AppCompatActivity {
    private List<Login> login;
    EditText usuario;
    EditText clave;
    Button acceder;
    Button cancelar;
    LoginDao logeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.IdToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.IniciarLogin);
        logeo = new LoginDao();
        usuario = (EditText) findViewById(R.id.Txt_Usuario);
        clave = (EditText) findViewById(R.id.Txt_Clave);
        acceder = (Button) findViewById(R.id.JTB_Acceder);
        cancelar = (Button) findViewById(R.id.JBT_Cancelar);
        login = new ArrayList<>();
        Logear();
    }

    private void Logear() {
        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login iden = logeo.Logearse(getApplicationContext(), usuario.getText().toString(), clave.getText().toString());
                if (iden != null) {
                    if (iden.getCategoriaempleado().getIdCategoria() == 2) {
                        Intent e = new Intent(getApplicationContext(), SelecionMesa.class);
                        e.putExtra("parametro", iden);
                        startActivity(e);
                        //this.finish();
                    }else{
                        if (iden.getCategoriaempleado().getIdCategoria() == 1) {
                            Toast.makeText(getApplicationContext(), "Codigo Chef", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario Incorecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
