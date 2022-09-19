package com.app.oceanapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.app.oceanapp.entity.Foto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListViewGaleriaFotoTwo extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<Foto> listFoto;
    BaseAdapter b;

    public AdapterListViewGaleriaFotoTwo(Context context, List<Foto> listFoto){
        this.context = context;
        this.listFoto = listFoto;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        b = this;
    }

    @Override
    public int getCount() {
        return listFoto.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.row_galeria_foto_two,null);

        ImageView imgPorcino = vista.findViewById(R.id.foto);
        Switch switchSeleccionar = vista.findViewById(R.id.switch1);
        switchSeleccionar.setChecked(listFoto.get(i).isSelect());

        switchSeleccionar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                listFoto.get(i).setSelect(isChecked);
                b.notifyDataSetChanged();

            }
        });

        Picasso.with(context).load(listFoto.get(i).getUrl()).into(imgPorcino);


        return vista;
    }
}
