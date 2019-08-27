/* package com.looigi.spiatore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Esporta {
	private List<String> ListaFiletti;
	private String Radice=VariabiliStatiche.getInstance().NomeSito + "Detector/service1.asmx/";
	private String RadiceImm=VariabiliStatiche.getInstance().NomeSito + "DetectorUO/Immagini/";

	private Handler handlerTimer;
	private Runnable rTimer;
	private int QualeFile=-1;
	public static boolean Continua;
	public static boolean Uploadato;

	public Esporta(Context context, boolean EffettuaOperazione) {
		String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
	    String Cartella="/LooigiSoft/Spiator/DB/";
		
	    ListaFiletti=new ArrayList<String>();
		LeggeFilettiDentroCartella(Origine+"/"+Cartella);
		
		if (EffettuaOperazione) {
			if (ListaFiletti.size()>0) {
				Continua=false;
				Uploadato=false;
				FaiPartireTimerUpload(context);
			}
		} else {
			VariabiliStatiche.getInstance().getTq().setText("Files: "+ListaFiletti.size());
		}
	}

	private void EliminaFile(String NomeFile) {
		try {
        	File file = new File(NomeFile);
        	@SuppressWarnings("unused")
			boolean deleted = file.delete();
    	} catch (Exception ignored) {
    		
    	}
	}
	
	private void FaiPartireTimerUpload(final Context context) {
    	handlerTimer = new Handler(); 
        rTimer = new Runnable() {
            public void run() {
            	if (!Continua) {
                	if (QualeFile>-1) {
            			if (Uploadato) {
            				EliminaFile(ListaFiletti.get(QualeFile));
            			}
            		}

                	QualeFile++;
	            	if (QualeFile<ListaFiletti.size()) {
		            	String NomeFile=ListaFiletti.get(QualeFile);
	            		
	            		Continua=true;
		            	
		            	UploadFile(context, NomeFile);
		            	
                        handlerTimer.postDelayed(rTimer, 100);
                	} else {
                		FermaTimerUpload();
                		
                		Utility u=new Utility();
                		u.VisualizzaPOPUP(context, "Export Done", false, 0);
                	}
            	} else {
                    handlerTimer.postDelayed(rTimer, 5000);
            	}
            }
        };
        handlerTimer.postDelayed(rTimer, 100);
	}
	
	private void FermaTimerUpload() {
		if (handlerTimer!=null) {
			try {
				handlerTimer.removeCallbacks(rTimer);
			} catch (Exception ignored) {
				
			}
			handlerTimer = null;
			rTimer = null;
		}
	}
	
    @SuppressLint("DefaultLocale")
	public String PrendeEstensione(String NomeFile) {
    	String Ritorno=NomeFile;
    	String Carattere="";
    	
    	for (int i=Ritorno.length()-1;i>0;i--) {
    		Carattere=Ritorno.substring(i, i+1);
    		if (Carattere.equals(".")) {
    			Ritorno=Ritorno.substring(i+1, Ritorno.length());
    			Ritorno=Ritorno.trim().toUpperCase();
    			break;
    		}
    	}
    	
    	return Ritorno;
    }
	
	public void UploadFile(Context context, String NomeFile){
		try {
			Utility u=new Utility();
			String DaSalvare=VariabiliStatiche.getInstance().idUtente +"-"+u.PrendeNomeImmagine()+".Jpg";
			
			FileInputStream fstrm = new FileInputStream(NomeFile);
		    HttpFileUpload hfu = new HttpFileUpload(VariabiliStatiche.getInstance().NomeSito + "DetectorUO/default.aspx",  DaSalvare, "Upload immagine");
		    hfu.Send_Now(context, fstrm, "");
		  } catch (FileNotFoundException e) {
      		Continua=false;
		  }
	}
	   
    private void LeggeFilettiDentroCartella(String Percorso) {
		File root = new File(Percorso);
		File [] files = root.listFiles();
		
		if (files!=null) {
			ScannaFilettiDentroCartella(files);	        
		}
	}
    
    @SuppressLint("DefaultLocale")
	private void ScannaFilettiDentroCartella(File[] files) {
		String NomeFile="";
		String Estensione="";
		
        for (int num=0;num<files.length;num++) {
        	File file=files[num];
        	if (!file.isDirectory()) {
        		NomeFile=file.getPath().toString();
    			Estensione=PrendeEstensione(NomeFile.toUpperCase());
    			
    			if (Estensione.equals("JPG") || Estensione.equals("DBF")) {
            		ListaFiletti.add(NomeFile);
    			}
        	}
        }
    }	
}
*/