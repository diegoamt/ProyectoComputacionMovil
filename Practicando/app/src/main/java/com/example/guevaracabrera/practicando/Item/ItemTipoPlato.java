package com.example.guevaracabrera.practicando.Item;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guevaracabrera.practicando.Modelo.Tipoplato;
import com.example.guevaracabrera.practicando.R;

/**
 * Created by Alehexis on 15/10/2015.
 */
public class ItemTipoPlato extends LinearLayout {

    private TextView Txt_Nombre;

    public ItemTipoPlato(Context context) {
        super(context);
        inflate(context, R.layout.item_tipoplato, this);
        Txt_Nombre = (TextView) findViewById(R.id.Txt_TipNom);
    }

    public void setItem(Tipoplato item) {
        Txt_Nombre.setText(item.getNombreTipo());
    }


}

