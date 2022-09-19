package com.app.oceanapp.persistencia;

import com.app.oceanapp.entity.Puerto;
import com.app.oceanapp.entity.PuertoZarpe;

import java.util.ArrayList;
import java.util.List;

public class PuertoZarpeDB {


    public static List<PuertoZarpe> getPuertosZarpe() {

        List<Puerto> puertoArriboCallao = new ArrayList<Puerto>();
        List<Puerto> puertoArriboParacas = new ArrayList<Puerto>();


        puertoArriboCallao.add(new Puerto("CALLAO"));
        puertoArriboCallao.add(new Puerto("PARACAS(Pisco)"));
        puertoArriboCallao.add(new Puerto("TAMBO DE MORA"));
        puertoArriboCallao.add(new Puerto("CHIMBOTE"));
        puertoArriboCallao.add(new Puerto("MALABRIGO"));
        puertoArriboCallao.add(new Puerto("HUACHO"));
        puertoArriboCallao.add(new Puerto("CHANCAY"));
        puertoArriboCallao.add(new Puerto("CASMA"));
        puertoArriboCallao.add(new Puerto("ATICO"));
        puertoArriboCallao.add(new Puerto("CARQUIN"));
        puertoArriboCallao.add(new Puerto("COISHCO"));
        puertoArriboCallao.add(new Puerto("SECHURA"));
        puertoArriboCallao.add(new Puerto("BAYOVAR"));

        puertoArriboParacas.add(new Puerto("CALLAO"));
        puertoArriboParacas.add(new Puerto("PARACAS(Pisco)"));
        puertoArriboParacas.add(new Puerto("TAMBO DE MORA"));
        puertoArriboParacas.add(new Puerto("CHIMBOTE"));
        puertoArriboParacas.add(new Puerto("MALABRIGO"));
        puertoArriboParacas.add(new Puerto("HUACHO"));
        puertoArriboParacas.add(new Puerto("CHANCAY"));
        puertoArriboParacas.add(new Puerto("CASMA"));
        puertoArriboParacas.add(new Puerto("ATICO"));
        puertoArriboParacas.add(new Puerto("CARQUIN"));
        puertoArriboParacas.add(new Puerto("COISHCO"));
        puertoArriboParacas.add(new Puerto("SECHURA"));
        puertoArriboParacas.add(new Puerto("BAYOVAR"));


        List<PuertoZarpe> puertosZarpe = new ArrayList<PuertoZarpe>();


        puertosZarpe.add(new PuertoZarpe("CALLAO", puertoArriboCallao));
        puertosZarpe.add(new PuertoZarpe("PARACAS(Pisco)", puertoArriboParacas));

        return puertosZarpe;
    }

}
