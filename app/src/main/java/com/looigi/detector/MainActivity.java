package com.looigi.detector;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.location.Location;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.looigi.detector.DetectPowerClick.LockService;
import com.looigi.detector.Receivers.Video;
import com.looigi.detector.TestMemory.DatiMemoria;
import com.looigi.detector.TestMemory.TestMemory;
import com.looigi.detector.Utilities.CheckURLFile;
import com.looigi.detector.Utilities.MemoryBoss;
import com.looigi.detector.Utilities.db_dati;
import com.looigi.detector.Utilities.DownloadImmagine;
import com.looigi.detector.Utilities.ExitActivity;
import com.looigi.detector.Utilities.GestioneImmagini;
import com.looigi.detector.Utilities.Permessi;
import com.looigi.detector.Utilities.PrendeModelloTelefono;
import com.looigi.detector.Utilities.UploadFiles;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;
import com.looigi.detector.gps.LocationService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// import com.looigi.detector.gps.PrendeCoordinateGPS;

public class MainActivity extends FragmentActivity {
	private boolean CiSonoPermessi;
    private MemoryBoss mMemoryBoss;

	@Override
	protected void onStop() {
		super.onStop();
	}

	// @Override
	// public void onBackPressed() {
	// 	ExitActivity.exitApplicationAndRemoveFromRecent(MainActivity.this);
//
	// 	super.onBackPressed();
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Permessi pp = new Permessi();
		CiSonoPermessi = pp.ControllaPermessi(this);

		if (CiSonoPermessi) {
			EsegueEntrata();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mMemoryBoss = new MemoryBoss();
                registerComponentCallbacks(mMemoryBoss);
            }
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		if (!CiSonoPermessi) {
			int index = 0;
			Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
			for (String permission : permissions) {
				PermissionsMap.put(permission, grantResults[index]);
				index++;
			}

			EsegueEntrata();
		}
	}

	private void EsegueEntrata() {
		VariabiliStatiche.getInstance().setFragmentActivityPrincipale(this);
		VariabiliStatiche.getInstance().setContext(this);
		VariabiliImpostazioni.getInstance().setAct(this);

		Intent i= new Intent(VariabiliStatiche.getInstance().getFragmentActivityPrincipale(), bckService.class);
		VariabiliStatiche.getInstance().setiServizio(i);
		VariabiliStatiche.getInstance().getFragmentActivityPrincipale().startService(
				VariabiliStatiche.getInstance().getiServizio());

		// String AutomaticReload = getIntent().getStringExtra("AUTOMATIC RELOAD");
		// if (AutomaticReload !=null && AutomaticReload.equals("YES")) {
		//     moveTaskToBack(true);
		// }
	}
}
