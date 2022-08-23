package com.app.oceanapp.persistencia;

import com.app.oceanapp.entity.Embarcacion;

import java.util.ArrayList;
import java.util.List;

public class EmbarcacionPersistencia {

    public static List<Embarcacion> getEmbarcaciones() {

        List<Embarcacion> embarcaciones = new ArrayList<Embarcacion>();

        embarcaciones.add(new Embarcacion("ALESSANDRO","CO-22295-PM"));
        embarcaciones.add(new Embarcacion("ALEXANDRA","CO-10418-PM"));
        embarcaciones.add(new Embarcacion("CONSTANZA","CO-16681-PM"));
        embarcaciones.add(new Embarcacion("CORINA","CO-2660-PM"));
        embarcaciones.add(new Embarcacion("DANIELLA","CO-17997-PM"));
        embarcaciones.add(new Embarcacion("DON JUAN","CE-15791-PM"));
        embarcaciones.add(new Embarcacion("FABIOLA","CO-11394-PM"));
        embarcaciones.add(new Embarcacion("GABRIELA","CO-11054-PM"));
        embarcaciones.add(new Embarcacion("GIANNINA I","CE-0236-PM"));
        embarcaciones.add(new Embarcacion("GRACIELA","CO-23225-PM"));
        embarcaciones.add(new Embarcacion("ISABELLA","CO-11998-PM"));
        embarcaciones.add(new Embarcacion("MARIA JOSE","CO-19579-PM"));
        embarcaciones.add(new Embarcacion("NATALIA","CO-9906-PM"));
        embarcaciones.add(new Embarcacion("OLGA","CO-20863-PM"));
        embarcaciones.add(new Embarcacion("PATRICIA","CO-28488-PM"));
        embarcaciones.add(new Embarcacion("PISCO 1","CE-2888-PM"));
        embarcaciones.add(new Embarcacion("POLAR III","CO-10400-PM"));
        embarcaciones.add(new Embarcacion("POLAR IV","CO-22308-PM"));
        embarcaciones.add(new Embarcacion("POLAR VI","CO-4502-PM"));
        embarcaciones.add(new Embarcacion("POLAR VII","CO-13009-PM"));
        embarcaciones.add(new Embarcacion("SEBASTIAN","CO-24654-PM"));
        embarcaciones.add(new Embarcacion("STEFANO","CO-22658-PM"));

        return embarcaciones;

    }

}
