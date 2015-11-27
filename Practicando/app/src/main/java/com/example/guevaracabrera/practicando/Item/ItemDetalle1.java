package com.example.guevaracabrera.practicando.Item;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guevaracabrera.practicando.HacerPedido;
import com.example.guevaracabrera.practicando.Modelo.DetalleProducto;
import com.example.guevaracabrera.practicando.R;

/**
 * Created by Alehexis on 15/10/2015.
 */
public class ItemDetalle1 extends LinearLayout {

    private TextView Txt_NombreCategoria;
    public TextView Txt_Can;
    private TextView TxtPrecio;
    private TextView TxtSub;


    public ItemDetalle1(Context context) {
        super(context);
        inflate(context, R.layout.item_detallemesa, this);
        Txt_NombreCategoria = (TextView) findViewById(R.id.TxtNombrePro);
        Txt_Can = (TextView) findViewById(R.id.TxtCantidad1);
        TxtPrecio = (TextView) findViewById(R.id.TxtPrecio1);
        TxtSub = (TextView) findViewById(R.id.Txt_Sub);

    }


    public void setItem(final DetalleProducto item) {
        Txt_NombreCategoria.setText(item.getProducto().getNombre().toString());
        Txt_Can.setText(Integer.toString(item.getCantidad()));
        TxtPrecio.setText(Double.toString(item.getPrecio()));
        TxtSub.setText(Double.toString(item.getSubtotal()));

    }

}

