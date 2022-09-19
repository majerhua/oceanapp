package com.app.oceanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oceanapp.entity.Usuario;
import com.app.oceanapp.entity.UsuarioResponse;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView txtUsername;
    TextView txtPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSession();

        btnLogin = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userId = sessionManagement.getSession();
        if(userId != -1){
            moveToRegisterVessel();
        }
    }

    public void moveToRegisterVessel(){
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"BIENVENIDO", Toast.LENGTH_SHORT).show();
    }

    public void login(){

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if(username.equals("")){
            Toast.makeText(LoginActivity.this,"El campo email está vacio.",Toast.LENGTH_LONG).show();
        }else if(password.equals("")){
            Toast.makeText(LoginActivity.this,"El campo password está vacio.",Toast.LENGTH_LONG).show();
        }else{
            showProgressDialog();
            UsuarioService jsonPlaceHolderApi = ServiceFactory.retrofit.create(UsuarioService.class);
            //username,password
            Call<UsuarioResponse> call = jsonPlaceHolderApi.login(username,password);
            call.enqueue(new Callback<UsuarioResponse>() {
                public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {

                    if(!response.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                    }
                    else{
                        UsuarioResponse usuario = response.body();

                        if(usuario.getCode() == 1){
                            SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                            sessionManagement.saveSession(new Usuario(usuario.getMessage(),usuario.getUsername(),usuario.getCode(), usuario.getRol()
                            ,usuario.getId()));
                            moveToRegisterVessel();
                        }else{
                            Toast.makeText(LoginActivity.this,"USUARIO O PASSWORD INCORRECTO",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    hideProgressDialog();
                }

                @Override
                public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(LoginActivity.this,"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent
        );
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void hideProgressDialog(){
        progressDialog.dismiss();
    }

}