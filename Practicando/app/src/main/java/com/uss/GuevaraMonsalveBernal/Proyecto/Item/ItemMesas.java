package com.uss.GuevaraMonsalveBernal.Proyecto.Item;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Mesa;
import com.uss.GuevaraMonsalveBernal.Proyecto.R;

/**
 * Created by Alehexis on 15/10/2015.
 */
public class ItemMesas extends LinearLayout {
    TextView nombreMesa;

    public ItemMesas(Context context) {
        super(context);
        inflate(context, R.layout.item_mesas, this);
        nombreMesa = (TextView) findViewById(R.id.Txt_NombreMesa);
    }


    public void setItem(Mesa item) {
        nombreMesa.setText(item.getNmesa());
    }

}

