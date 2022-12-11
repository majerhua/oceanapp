package com.app.oceanapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.ZarpeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CerrarZarpeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CerrarZarpeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CerrarZarpeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CerrarZarpeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CerrarZarpeFragment newInstance(String param1, String param2) {
        CerrarZarpeFragment fragment = new CerrarZarpeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cerrar_zarpe, container, false);

        SessionManagement sessionManagement = new SessionManagement(getContext());
        Button btnCerrarZarpe = v.findViewById(R.id.btnCerrarZarpe);

        btnCerrarZarpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sessionManagement.getZarpeIdSession() == 0) {
                    Toast.makeText(getContext(),"El zarpe ya fue cerrado.",Toast.LENGTH_LONG).show();
                } else {
                    showProgressDialog();
                    ZarpeService jsonPlaceHolderApi = ServiceFactory.retrofit.create(ZarpeService.class);
                    Call<RegisterResponse> call = jsonPlaceHolderApi.close(sessionManagement.getZarpeIdSession());
                    call.enqueue(new Callback<RegisterResponse>() {
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            if(!response.isSuccessful()){
                                Toast.makeText(getContext(),"Ocurrio un error en el servidor",Toast.LENGTH_LONG).show();
                            }
                            else{
                                RegisterResponse embarcacionResponse = response.body();

                                if(embarcacionResponse.getCode() == 1){
                                    Toast.makeText(getContext(),"Se cerro el Zarpe correctamente", Toast.LENGTH_LONG).show();
                                    sessionManagement.setZarpeIdSession(0);

                                }else{
                                    Toast.makeText(getContext(),"No se pudo cerrar el Zarpe", Toast.LENGTH_LONG).show();
                                }
                            }
                            hideProgressDialog();
                        }
                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(getContext(),"No se pudo cerrar el Zarpe",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return v;
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getContext());

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