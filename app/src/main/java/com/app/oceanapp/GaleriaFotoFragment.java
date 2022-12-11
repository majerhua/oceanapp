package com.app.oceanapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.oceanapp.entity.Embarcacion;
import com.app.oceanapp.entity.Foto;
import com.app.oceanapp.entity.Lance;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.EmbarcacionService;
import com.app.oceanapp.repositories.remote.request.FotoService;
import com.app.oceanapp.repositories.remote.request.LanceService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GaleriaFotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GaleriaFotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GaleriaFotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GaleriaFotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GaleriaFotoFragment newInstance(String param1, String param2) {
        GaleriaFotoFragment fragment = new GaleriaFotoFragment();
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

    ListView listViewGaleriaFoto;
    AdapterListViewGaleriaFotoTwo adapter;
    List<Foto> listFoto;
    SessionManagement sessionManagement;
    Spinner spnLance;
    ArrayAdapter<Lance> adapterLance;
    ProgressDialog progressDialog;
    Button btnBuscar;
    List<Lance> listLances;
    Button btnProcesarFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_galeria_foto, container, false);

        sessionManagement = new SessionManagement(getContext());

        listViewGaleriaFoto = v.findViewById(R.id.listGaleriaFotosTwo);
        btnBuscar = v.findViewById(R.id.btnBuscar);
        btnProcesarFoto = v.findViewById(R.id.btnProcesarFoto);

        listFoto = new ArrayList<Foto>();
        adapter = new AdapterListViewGaleriaFotoTwo(getContext(),listFoto);

        FotoService jsonPlaceHolderApi = ServiceFactory.retrofit.create(FotoService.class);
        Call<List<Foto>> call = jsonPlaceHolderApi.getGaleriaFotosAll(sessionManagement.getSession());
        call.enqueue(new Callback<List<Foto>>() {
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                }
                else{
                    for(Foto foto: response.body()){
                        listFoto.add(new Foto(foto.getId(), foto.getUrl(), foto.getLance_id()));
                    }
                }
                listViewGaleriaFoto.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {
                Toast.makeText(getActivity(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        listLances = new ArrayList<Lance>();
        adapterLance = new ArrayAdapter<Lance>(getContext(),  android.R.layout.simple_spinner_dropdown_item, listLances);
        adapterLance.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spnLance = v.findViewById(R.id.spnLance);
        spnLance.setAdapter(adapterLance);

        showProgressDialog();

        Toast.makeText(getContext(),String.valueOf(sessionManagement.getZarpeIdSession()),
                Toast.LENGTH_LONG).show();

        LanceService jsonPlaceHolderApi_2 = ServiceFactory.retrofit.create(LanceService.class);
        Call<List<Lance>> call_2 = jsonPlaceHolderApi_2.getByZarpeId(sessionManagement.getZarpeIdSession());
        call_2.enqueue(new Callback<List<Lance>>() {
            public void onResponse(Call<List<Lance>> call_2, Response<List<Lance>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"No se pudo obtener la lista de lances",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    listLances = response.body();

                    adapterLance.clear();
                    adapterLance.addAll(listLances);
                    adapterLance.notifyDataSetChanged();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<Lance>> call_2, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(),t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        btnProcesarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ids = "";
                int cont = 0;
                for(int i=0; i< listFoto.size(); i++){
                    System.out.println("Photo =>"+listFoto.get(i).isSelect());
                    if(listFoto.get(i).isSelect()) {
                        ids += String.valueOf(listFoto.get(i).getId())+",";
                        cont++;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", ids.substring(0,ids.length() - 1));

                if(cont > 3) {
                    Toast.makeText(getContext(),"NO PUEDE PROCESAR MAS DE 3 FOTOS",
                            Toast.LENGTH_LONG).show();
                } else {
                    Navigation.findNavController(v).navigate(R.id.action_galeriaFotos_to_resultadoProcesamientoFragment,bundle);

                }

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lance lance = (Lance)spnLance.getSelectedItem();

                if(lance.getId() == 0) {
                    for(int i=0; i< listFoto.size(); i++){
                        listFoto.get(i).setSelect(true);
                    }
                } else {
                    for(int i=0; i< listFoto.size(); i++){
                        if(listFoto.get(i).getLance_id() == lance.getId()) {
                            listFoto.get(i).setSelect(true);
                        }else {
                            listFoto.get(i).setSelect(false);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
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