package com.looigi.detector.Utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;

import com.looigi.detector.MainActivity;
import com.looigi.detector.R;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

public class MemoryBoss implements ComponentCallbacks2 {
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(final int level) {
        // if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
        //     Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());
        //     l.ScriveLog("onTrimMemory MemoryBoss");
        // }
        if (level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE) {
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());
            l.ScriveLog("Memory Boss. Riavvio l'applicazione");
            Context context = VariabiliStatiche.getInstance().getContext();
            Intent mStartActivity = new Intent(context, MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }

        // you might as well implement some memory cleanup here and be a nice Android dev.
    }
}