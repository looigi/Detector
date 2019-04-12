package com.looigi.detector.Receivers;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.looigi.detector.Fotocamera.GBTakePictureNoPreview;
import com.looigi.detector.R;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Video extends Activity implements Callback {
	private Context context;
    private MediaRecorder recorder;
	private boolean StaRegistrando=false;
	private int Fotocamera;
	private List<String> Dimensioni;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);

		context=this;
		
		SurfaceView surfaceView=(SurfaceView) findViewById(R.id.surfaceView1);
		
		LinearLayout lsv=(LinearLayout) findViewById(R.id.laySV);
		
		android.widget.LinearLayout.LayoutParams params = null;
	    
		// Utility u=new Utility();
		// if (VariabiliStatiche.getInstance().Anteprima == null) {
		// 	u.LeggeAnteprima(context);
		// }
		
		if (VariabiliImpostazioni.getInstance().getAnteprima().equals("S")) {
			params = new android.widget.LinearLayout.LayoutParams(90, 90);
		} else {
			params = new android.widget.LinearLayout.LayoutParams(5, 5);
		}
		lsv.setLayoutParams(params);
		
	    SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
        	holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    
        recorder = new MediaRecorder();
        
	    try {
		    recorder.setPreviewDisplay(holder.getSurface());
	    } catch (Exception e) {
		    recorder=null;
	    }

		ImpostaVideo();
		
		Button cmdVideo=(Button) findViewById(R.id.cmdChiude);
		cmdVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	RimetteAPostoFilesAudioInSystem();
            	
    			// finish();
            }
        });					        
		
		Button cmdAzione=(Button) findViewById(R.id.cmdAzione);
		cmdAzione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
		        AzionaFermaVideo(context);
            }
        });
		
		RinominaFilesAudioInSystem();
	}

//	private void LeggeOrientamento(Context context) {
//    	SQLiteDatabase myDB= null;
//    	int MODE_PRIVATE=0;
//
//    	try {
//			myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
//		   	String Sql="SELECT Orient FROM Orientamento;";
//			Cursor c = myDB.rawQuery(Sql , null);
//			c.moveToFirst();
//			MainActivity.Orient=c.getInt(0);
//			c.close();
//
//			myDB.close();
//    	} catch (Exception e) {
//			myDB.execSQL("Delete From Orientamento;");
//			myDB.execSQL("Insert Into Orientamento Values (0);");
//
//			myDB.close();
//
//			MainActivity.Orient=0;
//    	}
//	}

	private void RinominaFilesAudioInSystem() {
//        SuoniTelefono s=new SuoniTelefono();
//        s.Imposta_suoni("mute");
//        
//		rootChecker r=new rootChecker();
//		String Origine="/system/media/audio/ui/Cam_Start.ogg";
//		String Destinazione="/system/media/audio/ui/Cam_Start.bak";
//		r.RinominaFilesRoot(Origine, Destinazione);
//		Origine="/system/media/audio/ui/Cam_Stop.ogg";
//		Destinazione="/system/media/audio/ui/Cam_Stop.bak";
//		r.RinominaFilesRoot(Origine, Destinazione);
	}
	
	private void RimetteAPostoFilesAudioInSystem() {
//        SuoniTelefono s=new SuoniTelefono();
//        s.Imposta_suoni("");
//        
//		rootChecker r=new rootChecker();
//		String Origine="/system/media/audio/ui/Cam_Start.bak";
//		String Destinazione="/system/media/audio/ui/Cam_Start.ogg";
//		r.RinominaFilesRoot(Origine, Destinazione);
//		Origine="/system/media/audio/ui/Cam_Stop.bak";
//		Destinazione="/system/media/audio/ui/Cam_Stop.ogg";
//		r.RinominaFilesRoot(Origine, Destinazione);
	}
	
	public void AzionaFermaVideo(Context context) {
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
			} catch (IllegalStateException e) {
			    recorder=null;
			} catch (IOException ignored) {
			    recorder=null;
			}

			StaRegistrando=true;
	        try {
	        	recorder.start();
	        } catch (RuntimeException ignored) {
	        }		
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
						"V"
				);
				VariabiliStatiche.getInstance().setProgressivoDBMM(proMM);
			}
            VariabiliStatiche.getInstance().ScriveDatiAVideo();

			if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
			    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			    vibrator.vibrate(1000);
			}
			
			RimetteAPostoFilesAudioInSystem();

			finish();
		}
	}

	private String PrendeRisoluzioni() {
		String Ritorno="";

		final GBTakePictureNoPreview c = new GBTakePictureNoPreview();
	    c.ImpostaContext(context);
	    if (Fotocamera==1) {
		    c.setUseFrontCamera();
	    } else {
		    c.setUseBackCamera();
	    }
	    Dimensioni=c.RitornaRisoluzioniVideo();
	    Ritorno=Dimensioni.get(0);

	    return Ritorno;
	}

	private void ImpostaVideo() {
		// GestioneDB gdb=new GestioneDB();
		// String Ritorno=gdb.LeggeValori(context);
		// int pos=Ritorno.indexOf("*");
		// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
		// pos=Ritorno.indexOf("@");
		// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
		// pos=Ritorno.indexOf("§");
		Fotocamera=VariabiliImpostazioni.getInstance().getFotocamera(); // Integer.parseInt(Ritorno.substring(0,pos));
		// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
		// String RisolX;
		// pos=Ritorno.indexOf("§");
		// RisolX=Ritorno.substring(0,pos);
		// pos=RisolX.indexOf("x");
		// pos=Ritorno.indexOf("§");
		int Estensione=VariabiliImpostazioni.getInstance().getEstensione(); // Integer.parseInt(Ritorno.substring(pos+1,Ritorno.length()));
		String sEstensione;

		if (Estensione==2) {
			sEstensione="dbv";
		} else {
			sEstensione="mp4";
		}

		String ris=PrendeRisoluzioni();
		int pos=ris.indexOf("x");
		int X=Integer.parseInt(ris.substring(0, pos));
		int Y=Integer.parseInt(ris.substring(pos+1,ris.length()));

	    String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
	    String Cartella=VariabiliStatiche.getInstance().PathApplicazione;

	    Utility u=new Utility();
	    u.CreaCartelle(Origine, Cartella);
	    u.ControllaFileNoMedia(Origine, Cartella);

	    String fileName = Origine + Cartella + u.PrendeNomeImmagine()+"."+sEstensione;

	    recorder.setOutputFile(fileName);

	    recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
	    recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
	    recorder.setVideoSize(X, Y);
	    recorder.setVideoFrameRate(30);
	    recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

//	    LeggeOrientamento(context);

//	    if (MainActivity.Orient>0) {
//		    recorder.setOrientationHint(MainActivity.Orient);
//	    }
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
//		try {
//			recorder.prepare();
//		} catch (IllegalStateException e) {
//		    recorder=null;
//		} catch (IOException e) {
//		    recorder=null;
//		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

}
