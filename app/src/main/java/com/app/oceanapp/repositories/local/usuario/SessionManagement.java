package com.app.oceanapp.repositories.local.usuario;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.oceanapp.entity.Usuario;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "id";
    String NAME_USER = "name";
    String NAME_ROL = "rol";
    String ZARPE_ID = "zarpe_id";
    String LANCE_ID = "lance_id";


    public SessionManagement(Context contex){
        sharedPreferences = contex.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Usuario user){
        int id = user.getId();
        String name = user.getUsername();
        String rol = user.getRol();
        editor.putInt(SESSION_KEY,id).commit();
        editor.putString(NAME_USER,name).commit();
        editor.putString(NAME_ROL,rol).commit();
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }

    public String getNameUseSession(){
        return sharedPreferences.getString(NAME_USER,"usuario");
    }

    public String getRolUserSession(){
        return sharedPreferences.getString(NAME_ROL,"rol");
    }

    public void setZarpeIdSession(int id) {
        editor.putInt(ZARPE_ID,id).commit();
    }

    public int getZarpeIdSession() {
        return sharedPreferences.getInt(ZARPE_ID,0);
    }

    public void setLanceIdSession(int id) {
        editor.putInt(LANCE_ID,id).commit();
    }

    public int getLanceIdSession() {
        return sharedPreferences.getInt(LANCE_ID,0);
    }

    public int getIdUserSession(){
        return sharedPreferences.getInt(SESSION_KEY,0);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
