package com.app.oceanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class LanceActivity extends AppCompatActivity {

    TextView txtFechaLance;
    TextView txtHoraLance;
    TextView txtPuertoEmbarcacion;
    TextView txtNombreEmbarcacion;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lance);

        txtFechaLance = findViewById(R.id.txtFechaLance);
        txtHoraLance = findViewById(R.id.txtHoraLance);
        txtPuertoEmbarcacion = findViewById(R.id.txtPuertoEmbarcacion);
        txtNombreEmbarcacion = findViewById(R.id.txtNombreEmbarcacion);

        txtFechaLance.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        LanceActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" +year ;
                txtFechaLance.setText(date);
            }
        };

        txtHoraLance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(LanceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtHoraLance.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        Spinner spnEmbarcacion = findViewById(R.id.spnCodigoEmbarcacion);
        ArrayAdapter<CharSequence> adapterSpnTurno = ArrayAdapter.createFromResource(LanceActivity.this, R.array.spnEmbarcacion,
                android.R.layout.simple_spinner_item);
        adapterSpnTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEmbarcacion.setAdapter(adapterSpnTurno);


        spnEmbarcacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String embarcacion = parentView.getItemAtPosition(position).toString();

                if( embarcacion.trim().equals("Embarcacion 1")){
                    txtPuertoEmbarcacion.setText("Puerto embarcacion 1");
                    txtNombreEmbarcacion.setText("Embarcacion 1");
                }else if(embarcacion.trim().equals("Embarcacion 2")){
                    txtPuertoEmbarcacion.setText("Puerto embarcacion 2");
                    txtNombreEmbarcacion.setText("Embarcacion 2");
                }else if(embarcacion.trim().equals("Embarcacion 3")){
                    txtPuertoEmbarcacion.setText("Puerto embarcacion 3");
                    txtNombreEmbarcacion.setText("Embarcacion 3");
                }else {
                    txtPuertoEmbarcacion.setText("Puerto embarcacion 4");
                    txtNombreEmbarcacion.setText("Embarcacion 4");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        

    }
}