package com.app.oceanapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.oceanapp.entity.Foto;
import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.FotoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EliminarFotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EliminarFotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public EliminarFotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EliminarFotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EliminarFotoFragment newInstance(String param1, String param2) {
        EliminarFotoFragment fragment = new EliminarFotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    int id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_eliminar_foto, container, false);

        Button btnEliminarFoto = v.findViewById(R.id.btnEliminarFoto);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_eliminarFotoFragment_to_registrarFotoFragment);
            }
        });

        btnEliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FotoService jsonPlaceHolderApi = ServiceFactory.retrofit.create(FotoService.class);
                Call<RegisterResponse> call = jsonPlaceHolderApi.eliminarFoto(id);
                call.enqueue(new Callback<RegisterResponse>() {
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getActivity(),"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(response.body().getCode() == 1){
                                Toast.makeText(getActivity(),"SE ELIMINO CORRECTAMENTE",Toast.LENGTH_LONG).show();
                                Navigation.findNavController(v).navigate(R.id.action_eliminarFotoFragment_to_registrarFotoFragment);
                            }else {
                                Toast.makeText(getActivity(),"NO SE PUDO ELIMINAR LA FOTO",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }
}