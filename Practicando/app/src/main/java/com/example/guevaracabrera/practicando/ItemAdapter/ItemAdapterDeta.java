package com.example.guevaracabrera.practicando.ItemAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.guevaracabrera.practicando.Modelo.DetalleProducto;
import com.example.guevaracabrera.practicando.Item.ItemDetalle;

import java.util.List;

/**
 * Created by JUAN MANUEL on 02/10/2015.
 */
public class ItemAdapterDeta extends BaseAdapter {

    private List<DetalleProducto> datos;
    int estados;

    public ItemAdapterDeta(List<DetalleProducto> datos, int estado) {
        this.datos = datos;
        this.estados = estado;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int i) {

        return datos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemDetalle i_view;
        if (view == null) {
            i_view = new ItemDetalle(viewGroup.getContext());
        } else {
            i_view = (ItemDetalle) view;
        }
        i_view.setItem(datos.get(i),estados);
        return i_view;
    }
}
