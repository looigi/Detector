/* package com.looigi.detector.thread;

import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Variabili.VariabiliImpostazioni;

import java.util.Timer;
import java.util.TimerTask;

public class ImAliveThread {
    private boolean stopImAlive;
    private Timer tTmrImAlive;
    private Log l;

    private static ImAliveThread instance = null;

    private ImAliveThread() {
    }

    public static ImAliveThread getInstance() {
        if (instance == null) {
            instance = new ImAliveThread();
        }

        return instance;
    }

    public void start() {
        this.stopImAlive =false;
        l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());

        InternalThread();
    }

    private void InternalThread() {
        if (tTmrImAlive ==null) {
            tTmrImAlive = new Timer();
            tTmrImAlive.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    l.ScriveLog("Sono vivo");
                }
            }, 0, 10000);
        }
    }

    public void StopNetThread() {
        if (tTmrImAlive !=null) {
            stopImAlive = true;

            if (tTmrImAlive != null) {
                tTmrImAlive.cancel();
                tTmrImAlive.purge();
                tTmrImAlive = null;
            }
        }
    }
}
*/