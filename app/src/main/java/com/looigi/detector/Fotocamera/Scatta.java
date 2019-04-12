package com.looigi.detector.Fotocamera;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

public class Scatta {
	Context context;
	Handler handler;
	Runnable r;
	int Contatore=0;
	int Secondi=5;
	static String NomeImmagineScattata;

	public Scatta(final Context context) {
        int Modalita=2;
		int Fotocamera=2;
		String sEstensione;
		
		final Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());

		//try {
			l.ScriveLog("Lettura valori DB 1");
			// GestioneDB gdb=new GestioneDB();
			// String Ritorno=gdb.LeggeValori(context);
			// int pos=Ritorno.indexOf("*");
			Modalita=VariabiliImpostazioni.getInstance().getTipologiaScatto(); //  Integer.parseInt(Ritorno.substring(0,pos));
			l.ScriveLog("Lettura valori DB 2");
			// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
			// pos=Ritorno.indexOf("@");
			Secondi=VariabiliImpostazioni.getInstance().getSecondi(); //  Integer.parseInt(Ritorno.substring(0,pos));
			// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
			// l.ScriveLog("Lettura valori DB 3");
			// pos=Ritorno.indexOf("§");
			Fotocamera=VariabiliImpostazioni.getInstance().getFotocamera(); //  Integer.parseInt(Ritorno.substring(0,pos));
			// Ritorno=Ritorno.substring(pos+1,Ritorno.length());
			// l.ScriveLog("Lettura valori DB 4");
			final String RisoluzioneX;
			final String RisoluzioneY;
			String RisolX;
			// pos=Ritorno.indexOf("§");
			RisolX=VariabiliImpostazioni.getInstance().getRisoluzione(); //  Ritorno.substring(0,pos);
			// l.ScriveLog("Lettura valori DB 5");
			int pos=RisolX.indexOf("x");
			RisoluzioneY=RisolX.substring(pos+1,RisolX.length());
			RisoluzioneX=RisolX.substring(0, pos);
			// pos=Ritorno.indexOf("§");
			int Estensione=VariabiliImpostazioni.getInstance().getEstensione(); // Integer.parseInt(Ritorno.substring(pos+1,Ritorno.length()));
			l.ScriveLog("Lettura valori DB 6");
			
			if (Estensione==2) {
				sEstensione="dbf";	
			} else {
				sEstensione="jpg";	
			}

			l.ScriveLog("Lettura valori DB:");
			l.ScriveLog("Modalita:"+Modalita);
			l.ScriveLog("Secondi:"+Secondi);
			l.ScriveLog("Fotocamera:"+Fotocamera);
			l.ScriveLog("Estensione:"+Estensione);
			l.ScriveLog("Risoluzione:"+RisoluzioneX+"x"+RisoluzioneY);
			
			final GBTakePictureNoPreview c = new GBTakePictureNoPreview();
		    c.ImpostaContext(context);
		    if (Fotocamera==1) {
			    c.setUseFrontCamera();
		    } else {
			    c.setUseBackCamera();
		    }
		    
			l.ScriveLog("Impostata fotocamera");

			String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
		    String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		    
		    Utility u=new Utility();
		    u.CreaCartelle(Origine, Cartella);
		    u.ControllaFileNoMedia(Origine, Cartella);
		    
		    String fileName = Origine + Cartella + u.PrendeNomeImmagine() + "." + sEstensione;
		    c.setFileName(Origine + Cartella, u.PrendeNomeImmagine() + "." + sEstensione);
		    
		    NomeImmagineScattata=fileName;
		    
			l.ScriveLog("Creato nome file:"+fileName);

			if (c.cameraIsOk()) {
		    	//if (Modalita==2) {
					l.ScriveLog("Scatto foto");
		    		c.takePicture(RisoluzioneX, RisoluzioneY);
					l.ScriveLog("Foto scattata");
		    	/* } else {
		    	   	handler = new Handler();
		            r = new Runnable() {
		                public void run() {
							Contatore=Secondi+1;
							l.ScriveLog("Contatore:"+Contatore);
		                	Contatore--;
		                	if (Contatore==0) {
								Contatore=Secondi+1;
		    					l.ScriveLog("Scatto foto");
		    		    		c.takePicture(RisoluzioneX, RisoluzioneY);
		    					l.ScriveLog("Foto scattata");
		                	} else {
//			        			Toast toast=Toast.makeText(context, ""+Contatore ,Toast.LENGTH_SHORT);
//			        			toast.show();
			        			
			    		        handler.postDelayed(r, 1000);
		                	}
		                }
		            };
		            handler.postDelayed(r, 1000);
		    	} */
		    }
		/* } catch (Exception e) {
			l.ScriveLog("Errore:"+e.getMessage());
			Toast toast=Toast.makeText(context, "Errore: "+e.getMessage(),Toast.LENGTH_LONG);
			toast.show();
		} */
	}
}
