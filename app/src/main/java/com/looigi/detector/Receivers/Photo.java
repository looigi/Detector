package com.looigi.detector.Receivers;

// import android.content.BroadcastReceiver;
// import android.content.Context;
// import android.content.Intent;
//
// import com.looigi.detector.Utilities.Log;
// import com.looigi.detector.Fotocamera.UsaQuestoPerScattare;
//
// public class Photo extends BroadcastReceiver {
// 	@Override
// 	public void onReceive(Context context, Intent intent) {
// 		if(intent.getAction().equals("MAKE_CLICK")) {
// 			final Log l=new Log();
// 			l.PulisceFileDiLog();
//
// 			UsaQuestoPerScattare uq = new UsaQuestoPerScattare();
// 			uq.ScattaFoto(context, l);
// 		}
// 	}
// }


import android.app.Activity;
import android.os.Bundle;

import com.looigi.detector.Fotocamera.UsaQuestoPerScattare;
import com.looigi.detector.R;
import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Variabili.VariabiliImpostazioni;

public class Photo extends Activity {
	// private static Context context;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget);

		final Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());
		l.PulisceFileDiLog();

		UsaQuestoPerScattare uq = new UsaQuestoPerScattare();
		uq.ScattaFoto(l);

		this.finish();
	}
}
