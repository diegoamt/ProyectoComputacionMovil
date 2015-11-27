package com.example.guevaracabrera.practicando.ItemAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.guevaracabrera.practicando.Item.ItemProductos;
import com.example.guevaracabrera.practicando.Item.ItemTipoPlato;
import com.example.guevaracabrera.practicando.Modelo.Productos;
import com.example.guevaracabrera.practicando.Modelo.Tipoplato;

import java.util.List;

/**
 * Created by JUAN MANUEL on 02/10/2015.
 */
public class ItemAdapterCategoria extends BaseAdapter {

    private List<Tipoplato> datos;

    public ItemAdapterCategoria(List<Tipoplato> datos) {
        this.datos = datos;
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
        ItemTipoPlato i_view;
        if (view == null) {//NO existe, creamos uno
            i_view = new ItemTipoPlato(viewGroup.getContext());
        } else
            i_view = (ItemTipoPlato) view;
            i_view.setItem(datos.get(i));
        return i_view;
    }
}
