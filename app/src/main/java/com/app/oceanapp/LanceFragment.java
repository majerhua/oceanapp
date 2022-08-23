package com.app.oceanapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.oceanapp.entity.Embarcacion;
import com.app.oceanapp.persistencia.EmbarcacionPersistencia;

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

    TextView txtMatriculaLance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lance, container, false);

        txtMatriculaLance = v.findViewById(R.id.txtMatriculaLance);

        Spinner spnEmbarcacion = v.findViewById(R.id.spnEmbarcacionLance);
        ArrayAdapter<CharSequence> adapterSpnTurno = ArrayAdapter.createFromResource(getContext(), R.array.spnEmbarcacion,
                android.R.layout.simple_spinner_item);
        adapterSpnTurno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEmbarcacion.setAdapter(adapterSpnTurno);

        spnEmbarcacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String embarcacion = parentView.getItemAtPosition(position).toString();

                for(Embarcacion embar: EmbarcacionPersistencia.getEmbarcaciones()) {
                    if (embar.getNombre().equals(embarcacion)) {
                        txtMatriculaLance.setText(embar.getMatricula());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return v;
    }
}