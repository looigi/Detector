package com.looigi.detector.Receivers;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.looigi.detector.R;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Audio extends Activity  {
	private static Context context;
    MediaRecorder recorder;
	boolean StaRegistrando=false;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);

		context=this;

		LinearLayout lsv=(LinearLayout) findViewById(R.id.laySV);

		LinearLayout.LayoutParams params = null;

		params = new LinearLayout.LayoutParams(5, 5);
		lsv.setLayoutParams(params);

        recorder = new MediaRecorder();
        
	    /* try {
		    recorder.setPreviewDisplay(holder.getSurface());
	    } catch (Exception e) {
		    recorder=null;
	    } */

		Button cmdChiude=(Button) findViewById(R.id.cmdChiude);
		cmdChiude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    			finish();
            }
        });					        
		
		Button cmdAzione=(Button) findViewById(R.id.cmdAzione);
		cmdAzione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
		        AzionaParteFermaAudio(context);
            }
        });

		ImpostaAudio();
	}

	public void AzionaParteFermaAudio(Context context) {
		if (!StaRegistrando) {
			Toast toast=Toast.makeText(context, "Ok 1!" ,Toast.LENGTH_LONG);
			toast.show();

			Button cmdVideo=(Button) findViewById(R.id.cmdChiude);
			cmdVideo.setVisibility(LinearLayout.GONE);
			
			// Utility u=new Utility();
			// u.LeggeVibrazione(context);
			
			if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
			    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			    vibrator.vibrate(500);
			}

			try {
				recorder.prepare();
				recorder.start();
			} catch (IOException ignored) {

			}

			StaRegistrando=true;
		} else {
	    	Toast toast=Toast.makeText(context, "Ok 2!" ,Toast.LENGTH_LONG);
			toast.show();

			StaRegistrando=false;
			
    		recorder.stop();
    		recorder.reset();
    		recorder.release();
    		
    		// Utility u=new Utility();
 			// if (VariabiliStatiche.getInstance().Vibrazione==null) {
			// 	u.LeggeVibrazione(context);
			// }

			Location l = VariabiliStatiche.getInstance().getLocGPS();

			if (l!=null) {
				Utility u = new Utility();
				Date todayDate = Calendar.getInstance().getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String oggi = formatter.format(todayDate);

				int proMM = VariabiliStatiche.getInstance().getProgressivoDBMM() + 1;
				VariabiliStatiche.getInstance().getDbGpsPos().aggiungiMultimedia(
						oggi,
						Integer.toString(proMM),
						Double.toString(l.getLatitude()),
						Double.toString(l.getLongitude()),
						Double.toString(l.getAltitude()),
						u.PrendeNomeImmagine(),
						"A"
				);
				VariabiliStatiche.getInstance().setProgressivoDBMM(proMM);
			}
			VariabiliStatiche.getInstance().ScriveDatiAVideo();

			if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
				Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(1000);
			}

			finish();
		}
	}

	int Fotocamera;
	List<String> Dimensioni;

	private void ImpostaAudio() {
		// GestioneDB gdb=new GestioneDB();
		// String Ritorno=gdb.LeggeValori(context);
		// int pos=Ritorno.indexOf("*");
		// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
		// pos=Ritorno.indexOf("@");
		// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
		// pos=Ritorno.indexOf("§");
		Fotocamera=VariabiliImpostazioni.getInstance().getFotocamera(); //  Integer.parseInt(Ritorno.substring(0,pos));
		// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
		// // String RisolX;
		// pos=Ritorno.indexOf("§");
		// String RisolX=Ritorno.substring(0,pos);
		// pos=RisolX.indexOf("x");
		// pos=Ritorno.indexOf("§");
		int Estensione=VariabiliImpostazioni.getInstance().getEstensione(); // Integer.parseInt(Ritorno.substring(pos+1,Ritorno.length()));
		String sEstensione;

		if (Estensione==2) {
			sEstensione="dba";
		} else {
			sEstensione="3gp";
		}

		String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;

		Utility u=new Utility();
		u.CreaCartelle(Origine, Cartella);
		u.ControllaFileNoMedia(Origine, Cartella);

		String fileName = Origine + Cartella + u.PrendeNomeImmagine()+"."+sEstensione;

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setOutputFile(fileName);

//	    LeggeOrientamento(context);

//	    if (MainActivity.Orient>0) {
//		    recorder.setOrientationHint(MainActivity.Orient);
//	    }
	}

}
