/* package com.looigi.spiatore;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class GestioneWEBServiceSOAP extends ListActivity {
	static ProgressDialog progressDialog;
	String tOperazione;
	Boolean Errore;
  	Handler handlerF;
  	Runnable rTimerF;
  	Context conx;
  	String Urletto;
  	int SecondiPassati;
  	static String Continua;
  	String messErrore;
	static String visErrore;
  	
    String NAMESPACE = "http://Detector.it/";
    String METHOD_NAME = "";
    String SOAP_ACTION = "http://Detector.it/";
    String sURL = "";
    String Parametri[];
    String result="";
 	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}
	
	public GestioneWEBServiceSOAP(Context context, String urletto, String TipoOperazione) {
		tOperazione=TipoOperazione;
		conx=context;
		visErrore="";
		
		ApriDialog();
		
		Urletto=urletto;
		
		SecondiPassati = 0;
		SplittaCampiUrletto(Urletto);
		
		Errore=false;
		Continua="";
		
		Esegue(conx, Urletto);
	}

	private void SplittaCampiUrletto(String Cosa) {
		String Perc=Cosa;
		int pos=-1;
		String Indirizzo="";
		String Variabili[];
		String Funzione="";
		
		pos=Perc.indexOf("?");
		if (pos>-1) {
			Indirizzo=Perc.substring(0, pos);
			for (int i=Indirizzo.length()-1;i>0;i--) {
				if (Indirizzo.substring(i, i+1).equals("/")) {
					Funzione=Indirizzo.substring(i+1, Indirizzo.length());
					Indirizzo=Indirizzo.substring(0, i);
					break;
				}
			}
			sURL=Indirizzo;
			METHOD_NAME = Funzione;
			SOAP_ACTION = NAMESPACE + Funzione;
			Perc=Perc.substring(pos+1, Perc.length());
			pos=Perc.indexOf("&");
			if (pos>-1) {
				Variabili=Perc.split("&");
			} else {
				Variabili=new String[1];
				Variabili[0]=Perc;
			}
			Parametri=Variabili;
		} else {
			Indirizzo=Perc;
			for (int i=Indirizzo.length()-1;i>0;i--) {
				if (Indirizzo.substring(i, i+1).equals("/")) {
					Funzione=Indirizzo.substring(i+1, Indirizzo.length());
					Indirizzo=Indirizzo.substring(0, i);
					break;
				}
			}
			sURL=Indirizzo;
			METHOD_NAME = Funzione;
			SOAP_ACTION = NAMESPACE + Funzione;
		}
	}
	
	private void Esegue(final Context context, final String Urletto) {
    	new BackgroundAsyncTask(context).execute(Urletto);
	}
	
	public void ChiudeDialog() {
        try {
        	progressDialog.dismiss();
        } catch (Exception ignored) {
        }
	}
	
	private void ApriDialog() {
		try {
			if (conx==null) {
				conx=VariabiliStatiche.getInstance().context;
			}
			progressDialog = new ProgressDialog(conx);
			progressDialog.setMessage("Attendere Prego");
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
		} catch (Exception ignored) {
		   
		}
	}
	
	public class BackgroundAsyncTask extends AsyncTask<String, Integer, String> {
		private Context context;
		
	    public BackgroundAsyncTask(Context cxt) {
	        context = cxt;
	    }
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(String p) {
			super.onPostExecute(p);
			
			ControllaFineCiclo();
		}

	    @Override
	    protected String doInBackground(String... sUrl) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            String Parametro="";
            String Valore="";
            
            if (Parametri!=null) {
	            for (int i=0;i<Parametri.length;i++) {
	            	int pos=Parametri[i].indexOf("=");
	            	if (pos>-1) {
	            		Parametro=Parametri[i].substring(0, pos);
	            		Valore=Parametri[i].substring(pos+1,Parametri[i].length());
	            	}
	            	Request.addProperty(Parametro, Valore);
	            }
            }

            SoapSerializationEnvelope soapEnvelope = null;
            HttpTransportSE aht = null;
            try {
                soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    			soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);
                aht = new HttpTransportSE(sURL, 7000);
                aht.call(SOAP_ACTION, soapEnvelope);
            } catch (SocketTimeoutException  e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null) {
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	} else {
            		messErrore="Unknown";
            	}
            	result="ERROR: "+messErrore;
           } catch (IOException e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null)
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	result="ERROR: "+messErrore;
            } catch (XmlPullParserException e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null) {
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	} else {
            		messErrore="Unknown";
            	}
            	result="ERRORE: "+messErrore;
            } catch (Exception e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null)
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	result="ERROR: "+messErrore;
            }
            if (!Errore) {
	            try {
	                result = ""+(SoapPrimitive) soapEnvelope.getResponse();
	            } catch (SoapFault e) {
	            	Errore=true;
	            	messErrore=e.getMessage();
	            	if (messErrore!=null) {
	            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
	            	} else {
	            		messErrore="Unknown";
	            	}
	            	result="ERRORE: "+messErrore;
	            }
            }
            if (aht!=null) {
            	aht=null;
            }
            if (soapEnvelope!=null) {
            	soapEnvelope=null;
            }

			return null;
	    }
	 	
	    private void ControllaFineCiclo() {
			String Ritorno=result;
			
	        ChiudeDialog();
	        
			if (!Errore) {
				RitornoDaGestioneWS r=new RitornoDaGestioneWS();
				
				Boolean Ancora=true;
				while (Ancora) {
					if (tOperazione.equals("InserisceUtente")) {
						r.RitornoInserisceUtente(context, Ritorno);
						Ancora=false;
						break;
					}
					if (tOperazione.equals("RitornaPassword")) {
						r.RitornaPassword(context, Ritorno);
						Ancora=false;
						break;
					}
					if (tOperazione.equals("RitornaIdUtente")) {
						r.RitornaIdUtente(context, Ritorno);
						Ancora=false;
						break;
					}
					if (tOperazione.equals("RitornaImmagini")) {
						r.RitornaImmagini(context, Ritorno);
						Ancora=false;
						break;
					}
					if (tOperazione.equals("EliminaImmagini")) {
						r.EliminaImmagini(context, Ritorno);
						Ancora=false;
						break;
					}
				}
			}

	        if (!Continua.equals("")) {
				if (Continua.equals("RitornaId")) {
			    	String Radice=VariabiliStatiche.getInstance().NomeSito + "Detector/service1.asmx/";
					String Urletto=Radice;
					Urletto+= "RitornaIDUtente?Nick=" + VariabiliStatiche.getInstance().Nome;
					
					new GestioneWEBServiceSOAP(context, Urletto, "RitornaIdUtente");
				}
				if (Continua.equals("RitornaId")) {
	            	String Radice=VariabiliStatiche.getInstance().NomeSito + "/Detector/service1.asmx/";
	        		String Urletto=Radice;
	        		Urletto+= "RitornaImmagini?Nick=" + VariabiliStatiche.getInstance().Nick;
	        		
	        		new GestioneWEBServiceSOAP(context, Urletto, "RitornaImmagini");
				}
			}
	        
	        if (!visErrore.equals("")) {
				Utility u=new Utility();
				u.VisualizzaPOPUP(context, visErrore, false, 0);
	        }
	    }
	    
	    @Override
	    protected void onProgressUpdate(Integer... progress) {
	        super.onProgressUpdate(progress);
	        
	    }

		@Override
		protected void onCancelled(){
		}
   
	}

	public void cancel(boolean b) {
		if (b) {
		}
	}
}
*/