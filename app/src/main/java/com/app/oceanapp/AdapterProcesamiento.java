package com.app.oceanapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.app.oceanapp.entity.Foto;
import com.app.oceanapp.entity.Procesamiento;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProcesamiento extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<Procesamiento> list;

    public AdapterProcesamiento(Context context, List<Procesamiento> list){
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View v = inflater.inflate(R.layout.row_procesamiento,null);

        TextView txtNumeroLance = v.findViewById(R.id.txtNumeroLance);
        TextView txtCantidadFotos = v.findViewById(R.id.txtCantidadFotos);
        TextView txtPelicanos = v.findViewById(R.id.txtPelicanos);
        TextView txtLobosMarinos = v.findViewById(R.id.txtLobosMarinos);

        txtNumeroLance.setText("Número de Lance: "+String.valueOf(list.get(i).getNumeroLance()));
        txtCantidadFotos.setText("Cantidad de fotos: "+String.valueOf(list.get(i).getCantidadFotos()));
        txtPelicanos.setText("Pelicanos: "+String.valueOf(list.get(i).getPelicanos()));
        txtLobosMarinos.setText("Lobos marinos: "+String.valueOf(list.get(i).getLobosMarinos()));

        return v;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position){

        return null;
    }
}
