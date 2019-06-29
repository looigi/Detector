/* package com.looigi.detector.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;

import com.looigi.detector.MainActivity;
import com.looigi.detector.Variabili.VariabiliStatiche;

public class RefreshActivity {
    private static final RefreshActivity ourInstance = new RefreshActivity();

    private Runnable runRiga;
    private Handler hSelezionaRiga;
    private int conta;
    protected static Context context;
    protected static FragmentActivity act;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        RefreshActivity.context = context;
    }

    public static FragmentActivity getAct() {
        return act;
    }

    public static void setAct(FragmentActivity act) {
        RefreshActivity.act = act;
    }

    public static RefreshActivity getInstance() {
        return ourInstance;
    }

    public void RilanciaServizio(final Context context, final FragmentActivity v) {
        this.context = context;
        this.act = v;
        conta=0;

        hSelezionaRiga = new Handler(Looper.getMainLooper());
        hSelezionaRiga.postDelayed(runRiga = new Runnable() {
            @Override
            public void run() {
                conta++;
                if (conta>30) {
                    RilanciaActivity();
                } else {
                    VariabiliStatiche.getInstance().setContext(context);
                    VariabiliStatiche.getInstance().setFragmentActivityPrincipale(v);

                    hSelezionaRiga.postDelayed(runRiga, 60000);
                }
            }
        },60000);
    }

    public void RilanciaActivity() {
        conta=0;

        if (runRiga!=null && hSelezionaRiga!=null) {
            hSelezionaRiga.removeCallbacks(runRiga);
            runRiga = null;
        }

        if (VariabiliStatiche.getInstance().getContext()==null ||
                VariabiliStatiche.getInstance().getFragmentActivityPrincipale()==null) {
            if (context!=null) {
                VariabiliStatiche.getInstance().setContext(context);
            }
            if (act!=null) {
                VariabiliStatiche.getInstance().setFragmentActivityPrincipale(act);
            }
            if (VariabiliStatiche.getInstance().getContext()==null ||
                    VariabiliStatiche.getInstance().getFragmentActivityPrincipale()==null) {
                if (context != null) {
                    Intent dialogIntent = new Intent(context, MainActivity.class);
                    dialogIntent.putExtra("AUTOMATIC RELOAD", "YES");
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    act.startActivity(dialogIntent);
                } else {
                    if (hSelezionaRiga != null && runRiga != null) {
                        hSelezionaRiga.postDelayed(runRiga, 60000);
                    }
                }
            } else {
                if (hSelezionaRiga != null && runRiga != null) {
                    hSelezionaRiga.postDelayed(runRiga, 60000);
                }
            }
        } else {
            if (hSelezionaRiga != null && runRiga != null) {
                hSelezionaRiga.postDelayed(runRiga, 60000);
            }
        }
    }
}
*/