package com.looigi.detector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.looigi.detector.Fotocamera.UsaQuestoPerScattare;
import com.looigi.detector.Utilities.DialogMessaggio;
import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

public class ActionReceiver extends BroadcastReceiver {
    private Log l;
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.looigi.detector.scattafoto".equalsIgnoreCase(intent.getAction())) {
            Toast toast = Toast.makeText(
                    VariabiliStatiche.getInstance().getContext(),
                    "Rilevato tasto cuffie Detector",
                    Toast.LENGTH_SHORT);
            toast.show();

            if (!VariabiliStatiche.getInstance().StoScattando) {
                hSelezionaRiga = new Handler();
                hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                    @Override
                    public void run() {
                        l = new Log(VariabiliImpostazioni.getInstance().getNomeLog());

                        VariabiliStatiche.getInstance().StoScattando = true;
                        UsaQuestoPerScattare uq = new UsaQuestoPerScattare();
                        uq.ScattaFoto(l);
                    }
                }, 50);
            }
        }
    }
}