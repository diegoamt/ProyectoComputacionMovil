package com.uss.GuevaraMonsalveBernal.Proyecto.ItemAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.uss.GuevaraMonsalveBernal.Proyecto.Item.ItemProductos;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Productos;

import java.util.List;

/**
 * Created by JUAN MANUEL on 02/10/2015.
 */
public class ItemAdapterPro extends BaseAdapter {

    private List<Productos> datos;

    public ItemAdapterPro(List<Productos> datos) {
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
        ItemProductos i_view;
        if (view == null) {//NO existe, creamos uno
            i_view = new ItemProductos(viewGroup.getContext());
        } else
            i_view = (ItemProductos) view;
            i_view.setItem(datos.get(i));
        return i_view;
    }
}
