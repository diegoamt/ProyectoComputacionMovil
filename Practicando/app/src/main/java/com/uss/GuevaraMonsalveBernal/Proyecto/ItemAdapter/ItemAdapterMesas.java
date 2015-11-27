package com.uss.GuevaraMonsalveBernal.Proyecto.ItemAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uss.GuevaraMonsalveBernal.Proyecto.Item.ItemMesas;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Mesa;

import java.util.List;

/**
 * Created by JUAN MANUEL on 02/10/2015.
 */
public class ItemAdapterMesas extends BaseAdapter {

    private List<Mesa> datos;

    public ItemAdapterMesas(List<Mesa> datos) {
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
        ItemMesas i_view;
        if (view == null) {
            i_view = new ItemMesas(viewGroup.getContext());
        } else {
            i_view = (ItemMesas) view;
        }
        i_view.setItem(datos.get(i));
        return i_view;
    }
}
