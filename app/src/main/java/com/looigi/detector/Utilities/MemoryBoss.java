package com.looigi.detector.Utilities;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.media.MediaPlayer;

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
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());
            l.ScriveLog("onTrimMemory MemoryBoss");
        }
        // you might as well implement some memory cleanup here and be a nice Android dev.
    }
}