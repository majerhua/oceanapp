package com.app.oceanapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.oceanapp.entity.Embarcacion;
import com.app.oceanapp.entity.Puerto;
import com.app.oceanapp.entity.PuertoZarpe;
import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.persistencia.PuertoZarpeDB;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.EmbarcacionService;
import com.app.oceanapp.repositories.remote.request.ZarpeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmbarcacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmbarcacionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmbarcacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmbarcacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmbarcacionFragment newInstance(String param1, String param2) {
        EmbarcacionFragment fragment = new EmbarcacionFragment();
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
    TextView txtFechaZarpado;
    TextView txtHoraZarpado;
    TextView txtMatricula;
    EditText editTxtComentario;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button btnRegistrar;
    String fechaZarpe = "";
    Spinner spnEmbarcacion;
    ArrayAdapter<Embarcacion> adapterEmbarcacion;
    List<Embarcacion> embs;
    List<Puerto> lstPuertoArribo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_embarcacion, container, false);

        txtFechaZarpado = v.findViewById(R.id.txtFechaZarpado);
        txtHoraZarpado = v.findViewById(R.id.txtHoraZarpado);
        txtMatricula = v.findViewById(R.id.txtMatricula);
        editTxtComentario = v.findViewById(R.id.editTxtComentario);
        btnRegistrar = v.findViewById(R.id.btnRegistrar);

        txtFechaZarpado.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        txtHoraZarpado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtHoraZarpado.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" +year ;
                fechaZarpe = year + "-"+month + "-" + dayOfMonth;
                txtFechaZarpado.setText(date);
            }
        };

        spnEmbarcacion = v.findViewById(R.id.spnEmbarcacion);

        List<Embarcacion> embarcaciones = new ArrayList<Embarcacion>();

        adapterEmbarcacion = new ArrayAdapter<Embarcacion>(getContext(),  android.R.layout.simple_spinner_dropdown_item, embarcaciones);
        adapterEmbarcacion.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spnEmbarcacion.setAdapter(adapterEmbarcacion);


        EmbarcacionService jsonPlaceHolderApi = ServiceFactory.retrofit.create(EmbarcacionService.class);

        showProgressDialog();

        Call<List<Embarcacion>> call = jsonPlaceHolderApi.get();
        call.enqueue(new Callback<List<Embarcacion>>() {
            public void onResponse(Call<List<Embarcacion>> call, Response<List<Embarcacion>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"No se pudo obtener la lista de embarcación",
                            Toast.LENGTH_LONG).show();
                }
                else{

                    embs = response.body();

                    embs.add(0, new Embarcacion(0, "--Seleccionar--", "......"));

                    adapterEmbarcacion.clear();
                    adapterEmbarcacion.addAll(embs);
                    adapterEmbarcacion.notifyDataSetChanged();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<Embarcacion>> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(),"No se pudo obtener la lista de embarcación",
                        Toast.LENGTH_LONG).show();
            }
        });


        spnEmbarcacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Embarcacion emb = (Embarcacion) parentView.getItemAtPosition(position);
                txtMatricula.setText(emb.getMatricula());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });



        Spinner spnPuertoZarpe = v.findViewById(R.id.spnPuertoZarpe);
        ArrayAdapter<String> adapterPuertoZarpe =  new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<String>());
        adapterPuertoZarpe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPuertoZarpe.setAdapter(adapterPuertoZarpe);


        List<PuertoZarpe> lstPuertoZarpe = PuertoZarpeDB.getPuertosZarpe();
        List<String> listStrPuertoZarpe = new ArrayList<String>();

        listStrPuertoZarpe.add(0, "---Seleccionar--");

        for(PuertoZarpe p: lstPuertoZarpe){
            listStrPuertoZarpe.add(p.getNombre());
        }

        adapterPuertoZarpe.clear();
        adapterPuertoZarpe.addAll(listStrPuertoZarpe);
        adapterPuertoZarpe.notifyDataSetChanged();


        Spinner spnPuertoArribo = v.findViewById(R.id.spnPuertoArribo);
        ArrayAdapter<String> adapterPuertoArribo = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<String>(Arrays.asList("--Seleccionar--")));
        adapterPuertoArribo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPuertoArribo.setAdapter(adapterPuertoArribo);

        spnPuertoZarpe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String strPuertoZarpe = (String) parentView.getItemAtPosition(position);
                lstPuertoArribo = null;

                for(PuertoZarpe p: lstPuertoZarpe){

                    if(p.getNombre().equals(strPuertoZarpe)) {
                        lstPuertoArribo = p.getPuertoArribo();
                        break;
                    }
                }

                List<String> lstStrPuertoArribo = new ArrayList<String>();

                if(lstPuertoArribo != null){
                    for(Puerto pt: lstPuertoArribo){
                        lstStrPuertoArribo.add(pt.getNombre());
                    }

                    lstStrPuertoArribo.add(0, "--Seleccionar--");

                    adapterPuertoArribo.clear();
                    adapterPuertoArribo.addAll(lstStrPuertoArribo);
                    adapterPuertoArribo.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        Spinner spnObjetivo = v.findViewById(R.id.spnObjetivo);
        ArrayAdapter<CharSequence> adapterObjetivo = ArrayAdapter.createFromResource(getContext(), R.array.spnObjetivo,
                android.R.layout.simple_spinner_item);
        adapterObjetivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnObjetivo.setAdapter(adapterObjetivo);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Embarcacion emb = (Embarcacion) spnEmbarcacion.getSelectedItem();
                String matricula = txtMatricula.getText().toString();
                String puertoZarpe = spnPuertoZarpe.getSelectedItem().toString();
                String horaZarpe = txtHoraZarpado.getText().toString();
                String puertoArribo = spnPuertoArribo.getSelectedItem().toString();
                String objetivo = spnObjetivo.getSelectedItem().toString();
                String comentario = editTxtComentario.getText().toString();

                if(emb.getId() == 0) {
                    Toast.makeText(getContext(),"Debe seleccionar una opción en el campo 'Embarcación'.",Toast.LENGTH_LONG).show();
                }else if(puertoZarpe.equals("--Seleccionar--")){
                    Toast.makeText(getContext(),"Debe seleccionar una opción en el campo 'Puerto de zarpe'.",Toast.LENGTH_LONG).show();
                }
                else if(fechaZarpe.equals("")) {
                    Toast.makeText(getContext(),"El campo fecha de zarpe no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(horaZarpe.equals("Clic aquí")) {
                    Toast.makeText(getContext(),"El campo hora de zarpe no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(puertoArribo.equals("--Seleccionar--")){
                    Toast.makeText(getContext(),"Debe seleccionar una opción en el campo 'Puerto de arribo'",Toast.LENGTH_LONG).show();
                } else{
                    showProgressDialog();
                    ZarpeService jsonPlaceHolderApi = ServiceFactory.retrofit.create(ZarpeService.class);
                    Call<RegisterResponse> call = jsonPlaceHolderApi.register(emb.getId(),puertoZarpe,fechaZarpe,horaZarpe,puertoArribo,objetivo,
                            comentario);
                    call.enqueue(new Callback<RegisterResponse>() {
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            if(!response.isSuccessful()){
                                Toast.makeText(getContext(),"Ocurrio un error en el servidor",Toast.LENGTH_LONG).show();
                            }
                            else{
                                RegisterResponse embarcacionResponse = response.body();

                                if(embarcacionResponse.getCode() == 1){
                                    Toast.makeText(getContext(),"Se registro la embarcación correctamente", Toast.LENGTH_LONG).show();
                                    txtFechaZarpado.setText("Clic aquí");
                                    fechaZarpe = "";
                                    txtHoraZarpado.setText("Clic aquí");
                                    editTxtComentario.setText("");
                                    spnEmbarcacion.setSelection(0);
                                    spnPuertoZarpe.setSelection(0);

                                    List<String> lstStrPuertoArribo = new ArrayList<String>();
                                    lstStrPuertoArribo.add(0, "--Seleccionar--");

                                    adapterPuertoArribo.clear();
                                    adapterPuertoArribo.addAll(lstStrPuertoArribo);
                                    adapterPuertoArribo.notifyDataSetChanged();

                                    SessionManagement sessionManagement = new SessionManagement(getContext());
                                    sessionManagement.setZarpeIdSession(embarcacionResponse.getId());

                                    ((MenuActivity)getActivity()).changeTextZarpe();

                                }else{
                                    Toast.makeText(getContext(),"No se pudo registrar la embarcación", Toast.LENGTH_LONG).show();
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(getContext(),"No se pudo registrar la embarcación",
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