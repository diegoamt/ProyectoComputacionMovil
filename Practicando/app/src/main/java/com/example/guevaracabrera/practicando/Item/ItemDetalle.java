package com.example.guevaracabrera.practicando.Item;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guevaracabrera.practicando.AgregarPedidos;
import com.example.guevaracabrera.practicando.Modelo.DetalleProducto;
import com.example.guevaracabrera.practicando.HacerPedido;
import com.example.guevaracabrera.practicando.R;

/**
 * Created by Alehexis on 15/10/2015.
 */
public class ItemDetalle extends LinearLayout {

    private TextView Txt_NombreCategoria;
    public TextView Txt_Can;
    private TextView TxtPrecio;
    ImageButton add, rest;

    public ItemDetalle(Context context) {
        super(context);
        inflate(context, R.layout.item_detalle, this);
        Txt_NombreCategoria = (TextView) findViewById(R.id.Txt_NombrePro);
        Txt_Can = (TextView) findViewById(R.id.Txt_Cantidad1);
        TxtPrecio = (TextView) findViewById(R.id.TxtPrecio);
        add = (ImageButton) findViewById(R.id.JBT_add);
        rest = (ImageButton) findViewById(R.id.JBT_rest);
    }


    public void setItem(final DetalleProducto item, final int estados) {
        Txt_NombreCategoria.setText(item.getProducto().getNombre().toString());
        Txt_Can.setText(Integer.toString(item.getCantidad()));
        TxtPrecio.setText(Double.toString(item.getPrecio()));
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setCantidad(item.getCantidad() + 1);
                Txt_Can.setText(Integer.toString(item.getCantidad()));
                item.setSubtotal();
                Estad(estados);
                rest.setVisibility((item.getCantidad() >= 1) ? View.VISIBLE : View.INVISIBLE);
            }
        });

        rest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setCantidad(item.getCantidad() - 1);
                item.setSubtotal();
                Txt_Can.setText(Integer.toString(item.getCantidad()));
                Estad(estados);
                rest.setVisibility((item.getCantidad() >= 1) ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    private void Estad(int estados) {
        if (estados == 1) {
            HacerPedido.ActualizarTotal();
        } else {
            if (estados == 2) {
                AgregarPedidos.ActualizarTotal();
            }
        }
    }

}

