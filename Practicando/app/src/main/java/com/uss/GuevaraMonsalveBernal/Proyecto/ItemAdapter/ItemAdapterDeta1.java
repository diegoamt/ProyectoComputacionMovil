package com.uss.GuevaraMonsalveBernal.Proyecto.ItemAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uss.GuevaraMonsalveBernal.Proyecto.Item.ItemDetalle1;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.DetalleProducto;

import java.util.List;

/**
 * Created by JUAN MANUEL on 02/10/2015.
 */
public class ItemAdapterDeta1 extends BaseAdapter {

    private List<DetalleProducto> datos;

    public ItemAdapterDeta1(List<DetalleProducto> datos) {
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
        ItemDetalle1 i_view;
        if (view == null) {
            i_view = new ItemDetalle1(viewGroup.getContext());
        } else {
            i_view = (ItemDetalle1) view;
        }
        i_view.setItem(datos.get(i));
        return i_view;
    }
}
