package com.app.oceanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EmbarcacionActivity extends AppCompatActivity {

    TextView txtFechaZarpado;
    TextView txtHoraZarpado;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embarcacion);

        txtFechaZarpado = findViewById(R.id.txtFechaZarpado);
        txtHoraZarpado = findViewById(R.id.txtHoraZarpado);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        txtFechaZarpado.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EmbarcacionActivity.this,
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(EmbarcacionActivity.this,
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
                txtFechaZarpado.setText(date);
            }
        };

        Spinner spnEmbarcacion = findViewById(R.id.spnEmbarcacion);
        ArrayAdapter<CharSequence> adapterSpnTurno = ArrayAdapter.createFromResource(EmbarcacionActivity.this, R.array.spnEmbarcacion,
                android.R.layout.simple_spinner_item);
        adapterSpnTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEmbarcacion.setAdapter(adapterSpnTurno);

        Spinner spnPuertoZarpe = findViewById(R.id.spnPuertoZarpe);
        ArrayAdapter<CharSequence> adapterPuertoZarpe = ArrayAdapter.createFromResource(EmbarcacionActivity.this, R.array.spnPuertoZarpe,
                android.R.layout.simple_spinner_item);
        adapterSpnTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPuertoZarpe.setAdapter(adapterPuertoZarpe);

        Spinner spnPuertoArribo = findViewById(R.id.spnPuertoArribo);
        ArrayAdapter<CharSequence> adapterPuertoArribo = ArrayAdapter.createFromResource(EmbarcacionActivity.this, R.array.spnPuertoArribo,
                android.R.layout.simple_spinner_item);
        adapterSpnTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPuertoArribo.setAdapter(adapterPuertoArribo);

        Spinner spnObjetivo = findViewById(R.id.spnObjetivo);
        ArrayAdapter<CharSequence> adapterObjetivo = ArrayAdapter.createFromResource(EmbarcacionActivity.this, R.array.spnObjetivo,
                android.R.layout.simple_spinner_item);
        adapterSpnTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnObjetivo.setAdapter(adapterObjetivo);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegisterThrow();
            }
        });
    }

    public void moveToRegisterThrow(){
        Intent intent = new Intent(EmbarcacionActivity.this, LanceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"BIENVENIDO", Toast.LENGTH_SHORT).show();
    }
}