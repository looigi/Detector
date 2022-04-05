package com.looigi.detector.Utilities;

import android.os.Handler;
import android.os.Looper;

import com.looigi.detector.Variabili.VariabiliStatiche;

public class wsRitorno {
    public void ScriveDatiDaTXT(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                    "Errore nel creare lo zip:\n" + Ritorno,
                    true, "Detector");
        }
    }
}
