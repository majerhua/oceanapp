package com.app.oceanapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.entity.Zarpe;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.LanceService;
import com.app.oceanapp.repositories.remote.request.ZarpeService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LanceFragment newInstance(String param1, String param2) {
        LanceFragment fragment = new LanceFragment();
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
    EditText editTextNumeroLance;
    TextView txtFechaLance;
    TextView txtHoraLance;
    EditText editTextLatitud;
    EditText editTextLongitud;
    EditText editTextRumbo;
    TextView txtEmbarcacion;
    TextView txtMatriculaLance;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button btnRegistrarLance;
    String fechaLance = "";
    String horaLance = "";
    List<Zarpe> zarpes;
    ArrayAdapter<Zarpe> adapterSpnZonaEmbarcacion;
    List<Zarpe> zarps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lance, container, false);

        txtFechaLance = v.findViewById(R.id.txtFechaLance);
        txtHoraLance = v.findViewById(R.id.txtHoraLance);
        editTextLatitud = v.findViewById(R.id.editTextLatitud);
        editTextLongitud = v.findViewById(R.id.editTextLongitud);
        editTextRumbo = v.findViewById(R.id.editTextRumbo);
        txtEmbarcacion = v.findViewById(R.id.txtEmbarcacion);
        txtMatriculaLance = v.findViewById(R.id.txtMatriculaEmbarcacion);
        btnRegistrarLance = v.findViewById(R.id.btnRegistrarLance);

        List<Zarpe> zarpes = new ArrayList<Zarpe>();

        adapterSpnZonaEmbarcacion =  new ArrayAdapter<Zarpe>(getContext(),  android.R.layout.simple_spinner_dropdown_item,
                zarpes);
        adapterSpnZonaEmbarcacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ZarpeService jsonPlaceHolderApi = ServiceFactory.retrofit.create(ZarpeService.class);
        showProgressDialog();

        SessionManagement sessionManagement = new SessionManagement(getContext());

        Call<List<Zarpe>> call = jsonPlaceHolderApi.get(sessionManagement.getZarpeIdSession());
        call.enqueue(new Callback<List<Zarpe>>() {
            public void onResponse(Call<List<Zarpe>> call, Response<List<Zarpe>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"No se pudo obtener la lista de embarcación",
                            Toast.LENGTH_LONG).show();
                }
                else{

                    zarps = response.body();

                    if(!zarps.isEmpty()) {
                        Zarpe zarpe =  zarps.get(0);

                        txtEmbarcacion.setText(zarpe.getNombre());
                        txtMatriculaLance.setText(zarpe.getMatricula());
                    }

                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<Zarpe>> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(),"No se pudo obtener la lista de embarcación", Toast.LENGTH_LONG).show();
            }
        });


        txtFechaLance.setOnClickListener(new View.OnClickListener(){
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

        txtHoraLance.setOnClickListener(new View.OnClickListener() {
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
                                horaLance = hourOfDay + ":" + minute;
                                txtHoraLance.setText(hourOfDay + ":" + minute);
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
                fechaLance = year + "-" + month + "-" + dayOfMonth;
                txtFechaLance.setText(date);
            }
        };




        btnRegistrarLance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latitud = editTextLatitud.getText().toString();
                String longitud = editTextLongitud.getText().toString();
                String rumbo = editTextRumbo.getText().toString();

                if(fechaLance.equals("")) {
                    Toast.makeText(getContext(),"El campo fecha de lance no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(horaLance.equals("")) {
                    Toast.makeText(getContext(),"El campo hora de zarpe no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(latitud.equals("")) {
                    Toast.makeText(getContext(),"El campo latitud no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(longitud.equals("")) {
                    Toast.makeText(getContext(),"El campo longitud no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(rumbo.equals("")){
                    Toast.makeText(getContext(),"El campo rumbo no puede estar vacio",Toast.LENGTH_LONG).show();
                }else if(sessionManagement.getZarpeIdSession() == 0){
                    Toast.makeText(getContext(),"Debe registrar un Zarpe de embarcación primero",Toast.LENGTH_LONG).show();
                }else {
                    showProgressDialog();
                    LanceService jsonPlaceHolderApi = ServiceFactory.retrofit.create(LanceService.class);
                    Call<RegisterResponse> call = jsonPlaceHolderApi.register(
                                                                                fechaLance,
                                                                                horaLance,
                                                                                latitud,
                                                                                longitud,
                                                                                rumbo,
                                                                                sessionManagement.getZarpeIdSession());
                    call.enqueue(new Callback<RegisterResponse>() {
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            if(!response.isSuccessful()){
                                Toast.makeText(getContext(),"Ocurrio un error en el servidor",Toast.LENGTH_LONG).show();
                            }
                            else{
                                RegisterResponse embarcacionResponse = response.body();

                                if(embarcacionResponse.getCode() == 1){
                                    Toast.makeText(getContext(),"Se registro lance correctamente", Toast.LENGTH_LONG).show();
                                    SessionManagement sessionManagement = new SessionManagement(getContext());
                                    sessionManagement.setLanceIdSession(embarcacionResponse.getId());
                                    clearFormFields();
                                    Navigation.findNavController(v).navigate(R.id.action_lance_to_registrarFotoFragment);
                                }else{
                                    Toast.makeText(getContext(),"No se pudo registrar el lance", Toast.LENGTH_LONG).show();
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(getContext(),"No se pudo registrar el lance",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return v;
    }

    private void clearFormFields() {
        txtFechaLance.setText("Clic aquí");
        fechaLance = "";
        txtHoraLance.setText("Clic aquí");
        editTextLatitud.setText("");
        editTextLongitud.setText("");
        editTextRumbo.setText("");

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