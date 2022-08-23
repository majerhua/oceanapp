package com.app.oceanapp.repositories.local.usuario;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.oceanapp.entity.Usuario;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String NAME_USER = "name";
    String NAME_ROL = "rol";


    public SessionManagement(Context contex){
        sharedPreferences = contex.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Usuario user){
        int id = user.getCode();
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

    public int getIdUserSession(){
        return sharedPreferences.getInt(SESSION_KEY,0);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
