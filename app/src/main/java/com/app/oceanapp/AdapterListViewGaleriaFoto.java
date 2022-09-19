package com.app.oceanapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

import com.app.oceanapp.entity.Foto;

import java.util.List;

public class AdapterListViewGaleriaFoto extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<Foto> listFoto;

    public AdapterListViewGaleriaFoto(Context context, List<Foto> listFoto){
        this.context = context;
        this.listFoto = listFoto;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listFoto.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.row_galeria_foto,null);

        ImageView imgPorcino = vista.findViewById(R.id.foto);
        Button btnEliminar = vista.findViewById(R.id.btnEliminar);

        Picasso.with(context).load(listFoto.get(i).getUrl()).into(imgPorcino);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", listFoto.get(i).getId());
                Navigation.findNavController(v).navigate(R.id.action_registrarFotoFragment_to_eliminarFotoFragment,bundle);
            }
        });

        return vista;
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
