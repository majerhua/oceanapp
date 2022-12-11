package com.app.oceanapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oceanapp.entity.Foto;
import com.app.oceanapp.entity.Procesamiento;
import com.app.oceanapp.entity.ProcesamientoBody;
import com.app.oceanapp.entity.ResponseBody;
import com.app.oceanapp.entity.Usuario;
import com.app.oceanapp.entity.UsuarioResponse;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.FotoService;
import com.app.oceanapp.repositories.remote.request.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadoProcesamientoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadoProcesamientoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultadoProcesamientoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadoProcesamientoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadoProcesamientoFragment newInstance(String param1, String param2) {
        ResultadoProcesamientoFragment fragment = new ResultadoProcesamientoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    String id = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id = getArguments().getString("id");
        }
    }

    ProgressDialog progressDialog;
    ListView lvResultadoProcesamiento;
    AdapterProcesamiento adapter;
    List<Procesamiento> listProcesamiento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resultado_procesamiento, container, false);

        lvResultadoProcesamiento = v.findViewById(R.id.lvResultadoProcesamiento);
        listProcesamiento = new ArrayList<Procesamiento>();
        adapter = new AdapterProcesamiento(getContext(),listProcesamiento);

        showProgressDialog();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://sjoq9cew7k.execute-api.us-east-1.amazonaws.com/dev/v1/api/")
                        .addConverterFactory(GsonConverterFactory.create()).build();

        FotoService jsonPlaceHolderApi = retrofit.create(FotoService.class);
        Call<ResponseBody> call = jsonPlaceHolderApi.processPhotos(id);
        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                }
                else{
                    for(Procesamiento procesamiento: response.body().getData()){
                        listProcesamiento.add(procesamiento);
                    }

                    lvResultadoProcesamiento.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                hideProgressDialog();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(),"NO SE PUDO PROCESAR TODAS LAS FOTOS",Toast.LENGTH_LONG).show();
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