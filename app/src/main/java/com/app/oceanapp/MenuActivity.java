package com.app.oceanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.oceanapp.constantes.Utils;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        changeTextZarpe();

    }

    public void changeTextZarpe() {
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        View headerView = navigationView.getHeaderView(0);

        SessionManagement sessionManagement = new SessionManagement(this);




        System.out.println("NAME: "+sessionManagement.getNameUseSession());

        TextView navUsername = (TextView) headerView.findViewById(R.id.name_user);
        TextView txtRol = (TextView) headerView.findViewById(R.id.txtRol);
        TextView txtZarpeActual = (TextView) headerView.findViewById(R.id.txtZarpeActual);

        navUsername.setText(sessionManagement.getNameUseSession());

        String rol = "";

        if(sessionManagement.getRolUserSession().equals(Utils.rol_administrador)) {
            rol = "Administrador";
        }else if(sessionManagement.getRolUserSession().equals(Utils.rol_biologo_marino)) {
            rol = "Biólogo Marino";
        }else if(sessionManagement.getRolUserSession().equals(Utils.rol_tecnico_tripulante)) {
            rol = "Técnico Tripulante";
        }

        txtRol.setText(rol);
        if(sessionManagement.getZarpeIdSession() == 0){
            txtZarpeActual.setText("No existe Zarpe");
        }else {
            txtZarpeActual.setText("Esta en #Zarpe " + String.valueOf(sessionManagement.getZarpeIdSession()));
        }
    }
}