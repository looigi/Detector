/* package com.looigi.spiatore;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Calendar;

public class GestioneWEBService extends ListActivity {
	static ProgressDialog progressDialog;
	String tOperazione;
	Boolean Errore;
  	Context conx;
  	String Urletto;
  	Boolean TimerActivityFermato;
  	int SecondiPassati;
  	static Boolean DeveContinuare;
  	static String Continua;
  	String NomeFileDest;
	String visErrore;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	public GestioneWEBService(Context context, String urletto, String TipoOperazione, String FileDest) {
		tOperazione=TipoOperazione;
		visErrore="";

		conx=context;
		Urletto=urletto;
		NomeFileDest=FileDest;
		
		ApriDialog();
		
		SecondiPassati = 0;
		
		Errore=false;
		DeveContinuare=true;
		Continua="";
		
		Esegue(conx, Urletto);
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
			progressDialog.setMessage("Please Wait\nDownload "+NomeFileDest);
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
			URL url;
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection =null;
        	Calendar c = Calendar.getInstance(); 
        	int secondiPartenza = c.get(Calendar.SECOND);
        	int secondiAttuali = c.get(Calendar.SECOND);
        	int diffe=0;
        	@SuppressWarnings("unused")
			String MsgErrore="";
			Errore=false;
        	            
            System.setProperty("java.net.preferIPv4Stack", "true");
            System.setProperty("http.keepAlive", "false");
            
			try {
				String path=sUrl[0];
				path=sUrl[0];
				url = new URL(path);
				connection = getConnection(path);
				if (connection!=null) {
		            input = new BufferedInputStream(url.openStream());
		            output = new FileOutputStream(NomeFileDest);
		
					byte data[] = new byte[1024];
		            int count;
		            while ((count = input.read(data)) != -1 && DeveContinuare==true) {
		            	c = Calendar.getInstance(); 
		            	secondiAttuali = c.get(Calendar.SECOND);
		            	diffe=secondiAttuali-secondiPartenza;
		            	if (diffe<0) {
		            		diffe=60-diffe;
		            	}
		            	
		            	if (isCancelled() || diffe>10) {
		            		break;
		            	}
		            	
		                output.write(data, 0, count);
		            }
					
					output.flush();
				} else {
				}
			} catch (MalformedURLException e) {
				MsgErrore=e.getMessage();
				Errore=true;
				e.printStackTrace();
	    	}  catch (IOException e) {
				MsgErrore=e.getMessage();
				Errore=true;
				e.printStackTrace();
	    	}  catch (Exception e) {
				MsgErrore=e.getMessage();
				Errore=true;
				e.printStackTrace();
	    	}
			
            try {
            	if (input != null) {
            		input.close();
            	}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            try {
            	if (output != null) {
            		output.close();
            	}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            connection.disconnect();
            connection=null;
            
			return null;
	    }

	    private HttpURLConnection getConnection(String url) throws SocketTimeoutException, IOException{
			@SuppressWarnings("unused")
			String MsgErrore="";
			
			HttpURLConnection con = null;
	    	try {
	    		con = (HttpURLConnection) new URL(url).openConnection();
	    		con.setRequestMethod("HEAD");
	    		
	    		con.setUseCaches(false); 
	    		con.setDoOutput(true); 
	    		con.setDoInput(true); 

				con.setConnectTimeout(5000);
				con.setReadTimeout(15000);
    		   
//	            con.connect();
				MsgErrore="";
    		} catch (java.net.SocketTimeoutException e) {
    		   e.printStackTrace();
    		   MsgErrore=e.getMessage();
    		} catch (java.io.IOException e) {
    		    e.printStackTrace();
     		   MsgErrore=e.getMessage();
    		}

			return con;
	    }
	 	
	    private void ControllaFineCiclo() {
			String Ritorno="";
			
	        ChiudeDialog();
	        
			if (Errore==false) {
				RitornoDaGestioneWS r=new RitornoDaGestioneWS();
				
				Boolean Ancora=true;
				while (Ancora==true) {
					if (tOperazione.equals("DownloadImmagine")==true) {
						r.RitornoDownloadImmagine(context, Ritorno);
						Ancora=false;
						break;
					}
				}
			} else {
				// Errore
			}
			
			if (Continua.equals("")==false) {
			}
	        
	        if (visErrore.equals("")==false) {
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
		if (b==true) {
		}
	}

}
*/