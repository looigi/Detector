package com.looigi.detector.DetectPowerClick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;

import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Fotocamera.UsaQuestoPerScattare;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ScreenReceiver extends BroadcastReceiver {
    public static boolean wasScreenOn = true;
    private Long datella1 = null;
    private Long datella2 = null;
    private Context context;
    private Log l;

    private void CheckVelocity() {
        l = new Log(VariabiliImpostazioni.getInstance().getNomeLog());

        try {
            if (datella1 == null && datella2 == null) {
                Handler handlerTimer;
                Runnable rTimer;

                datella1 = System.currentTimeMillis();
                datella2 = null;

                handlerTimer = new Handler();
                rTimer = new Runnable() {
                    public void run() {
                        datella1 = null;
                        datella2 = null;
                    }
                };
                handlerTimer.postDelayed(rTimer, 2000);
            } else {
                if (datella1 != null && datella2 == null) {
                    long diff = System.currentTimeMillis() - datella1;
                    if (diff < 1950) {
                        Handler handlerTimer;
                        Runnable rTimer;

                        datella2 = System.currentTimeMillis();

                        handlerTimer = new Handler();
                        rTimer = new Runnable() {
                            public void run() {
                                datella1 = null;
                                datella2 = null;
                            }
                        };
                        handlerTimer.postDelayed(rTimer, 2000);
                    }
                } else {
                    long diff = System.currentTimeMillis() - datella2;
                    if (diff < 1950) {
                        datella1 = null;
                        datella2 = null;

                        if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
                            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            try {
                                vibrator.vibrate(100);
                                Thread.sleep(200);
                                vibrator.vibrate(500);
                                Thread.sleep(700);
                                vibrator.vibrate(100);
                                Thread.sleep(200);
                            } catch (InterruptedException ignored) {

                            }
                        }

                        if (!VariabiliStatiche.getInstance().StoScattando) {
                            VariabiliStatiche.getInstance().StoScattando = true;
                            UsaQuestoPerScattare uq = new UsaQuestoPerScattare();
                            uq.ScattaFoto(l);
                        }
                    }

                    datella1 = null;
                    datella2 = null;
                }
            }
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            l.ScriveLog("Screen Receiver. Errore: "+errors.toString());
        }
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        this.context = context;

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            wasScreenOn = false;
            CheckVelocity();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            wasScreenOn = true;
            // CheckVelocity();
            CheckVelocity();
        } else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            datella1 = null;
        }
    }
}