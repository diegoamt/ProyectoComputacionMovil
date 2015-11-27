package com.uss.GuevaraMonsalveBernal.Proyecto.Item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uss.GuevaraMonsalveBernal.Proyecto.R;
import com.uss.GuevaraMonsalveBernal.Proyecto.Modelo.Productos;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alehexis on 15/10/2015.
 */
public class ItemProductos extends LinearLayout {

    private TextView Txt_Nombre;
    private TextView Txt_Precio;
    private ImageView imagen;
    private Bitmap loadedImage;

    public ItemProductos(Context context) {
        super(context);
        inflate(context, R.layout.item_productos, this);
        Txt_Nombre = (TextView) findViewById(R.id.Txt_Nombre);
      //  imagen = (ImageView) findViewById(R.id.Img_Vista);
        Txt_Precio = (TextView) findViewById(R.id.Txt_PrecioP);

    }

    public void setItem(Productos item) {
        Txt_Nombre.setText(item.getNombre().toString());
        Txt_Precio.setText(Double.toString(item.getPrecio()).toString());
       // imagen.setImageBitmap(downloadFile(item.getUrL().toString()));
    }

    private Bitmap downloadFile(String imageHttpAddress) {
        URL imageUrl = null;
        HttpURLConnection conn = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            InputStream input= conn.getInputStream();
            loadedImage = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            //Toast.makeText(null, "Error cargando la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return loadedImage;
    }
}

