package com.looigi.detector.DetectPowerClick;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LockService extends Service {
    // private boolean GiaRegistrato=false;
    private BroadcastReceiver mReceiver;
    private Log l;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(2, new Notification());
    }

    private void AzionaServizio() {
        // if (!GiaRegistrato) {
        VariabiliStatiche.getInstance().setServizioLock(true);
        l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());

        try {
            final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            mReceiver = new ScreenReceiver();
            registerReceiver(mReceiver, filter);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            l.ScriveLog("onStartCommand: Errore register receiver: "+errors.toString());
        }
        //     GiaRegistrato = true;
        // }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AzionaServizio();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
        l.ScriveLog("Distruggo receiver");
        VariabiliStatiche.getInstance().setServizioLock(false);
    }

    // public class LocalBinder extends Binder {
    //     LockService getService() {
    //         return LockService.this;
    //     }
    // }
}
