package com.looigi.detector.Fotocamera;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

public class UsaQuestoPerScattare {
    private Handler handlerTimer;
    private Runnable rTimer;
    private int qualeVolta=0;

    public void ScattaFoto(final Log l) {
        final Context context = VariabiliStatiche.getInstance().getContext();
        // Utility u=new Utility();

        // l.ScriveLog("LeggeVibrazione");
        // if (VariabiliStatiche.getInstance().Vibrazione==null) {
        //     u.LeggeVibrazione(context);
        // }

        if (VariabiliImpostazioni.getInstance().getVibrazione()!=null && VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(200);
        }

        // l.ScriveLog("Legge numero scatti");
//
        // if (VariabiliStatiche.getInstance().numScatti==0) {
        //     u.LeggeNumeroScatti(context);
        // }

        // if (VariabiliStatiche.getInstance().getImpostazioni().getVibrazione().equals("S")) {
        //     Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //     vibrator.vibrate(100);
        // }
//
        // l.ScriveLog("Parte timer vibrazione");
//
        // GestioneDB gdb=new GestioneDB();
        // String Ritorno=gdb.LeggeValori(context);
        // int pos=Ritorno.indexOf("*");
        //int Modalita=Integer.parseInt(Ritorno.substring(0,pos));
        // Ritorno=Ritorno.substring(pos+1,Ritorno.length());
        // pos=Ritorno.indexOf("@");
        final int Secondi=VariabiliImpostazioni.getInstance().getSecondi(); //  Integer.parseInt(Ritorno.substring(0,pos));
        //Ritorno=Ritorno.substring(pos+1,Ritorno.length());

        VariabiliStatiche.getInstance().StoScattando=false;
        qualeVolta=0;

        handlerTimer = new Handler();
        rTimer = new Runnable() {
            public void run() {
                if (!VariabiliStatiche.getInstance().StoScattando) {
                    if (qualeVolta<VariabiliImpostazioni.getInstance().getNumeroScatti()) {
                        if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
                            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                        }

                        Toast toast=Toast.makeText(context, Integer.toString(qualeVolta+1)+"/"+Integer.toString(VariabiliImpostazioni.getInstance().getNumeroScatti()) ,Toast.LENGTH_LONG);
                        toast.show();

                        //StoScattando=false;

                        qualeVolta++;
                        l.ScriveLog("ScattaInWidget: "+qualeVolta);

                        ScattaInWidget(context);

                        if (qualeVolta==VariabiliImpostazioni.getInstance().getNumeroScatti()) {
                            FermaTimerVibrazione();

                            toast=Toast.makeText(context, "Ok... Done!" ,Toast.LENGTH_LONG);
                            toast.show();

                            l.ScriveLog("Fine scatti");

                            if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
                                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);
                            }
                        } else {
                            handlerTimer.postDelayed(rTimer, Secondi*1000);
                        }
                    }
                } else {
                    l.ScriveLog("Attesa");

                    handlerTimer.postDelayed(rTimer, Secondi*1000);
                }
            }
        };
        handlerTimer.postDelayed(rTimer, Secondi*1000);
    }

    private void ScattaInWidget(Context context) {
        // try {
        //     RemoteViews remoteViews;
        //     remoteViews= new RemoteViews(context.getPackageName(), R.layout.widget);
        //     remoteViews.setOnClickPendingIntent(R.id.imgScatta, ProviderPhoto.buildButtonPendingIntent(context));
//
             new Scatta(context);
//
        //     ProviderPhoto.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
        // } catch (Exception e) {
        //     Toast toast=Toast.makeText(context, "Errore: "+e.getMessage(),Toast.LENGTH_LONG);
        //     toast.show();
        // }
    }

    private void FermaTimerVibrazione() {
        if (handlerTimer!=null) {
            try {
                handlerTimer.removeCallbacks(rTimer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            handlerTimer = null;
            rTimer = null;
        }

        //StoScattando=false;
    }
}
