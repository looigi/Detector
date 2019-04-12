package com.looigi.detector;

import android.media.AudioManager;

import com.looigi.detector.Variabili.VariabiliStatiche;

public class SuoniTelefono {
	public void Imposta_suoni(String muteMode) {
		AudioManager manager = null;
		if (VariabiliStatiche.getInstance().manager == null) {
			manager=null;
		} else {
			manager=VariabiliStatiche.getInstance().manager;
		}
		
		if (manager!=null) {
		    if(muteMode.equals("mute")){
		        manager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0 , AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		    }else{
		    	manager.setStreamVolume(AudioManager.STREAM_SYSTEM, manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) , AudioManager.FLAG_ALLOW_RINGER_MODES);
		    }
		}
	}
}
