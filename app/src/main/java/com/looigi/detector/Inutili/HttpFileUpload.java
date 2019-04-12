/* package com.looigi.spiatore;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
	
public class HttpFileUpload implements Runnable{
        URL connectURL;
        String responseString;
        String Title;
        String Utente;
        String Description;
        byte[ ] dataToServer;
        FileInputStream fileInputStream = null;
    	private ProgressDialog progressDialog;

        public HttpFileUpload(String urlString, String vTitle, String vDesc){
            try{
                connectURL = new URL(urlString);
                Title= vTitle;
                Description = vDesc;
                Utente=VariabiliStatiche.getInstance().Nick;
            }catch(Exception ex){
                Log.i("HttpFileUpload","URL Malformatted");
            }
        }
	
        public void Send_Now(Context context, FileInputStream fStream, String Urletto){
        	fileInputStream = fStream;

    		new BackgroundAsyncTask(context).execute(Urletto);
        }

    	
    	public class BackgroundAsyncTask extends AsyncTask<String, Integer, String> {
    		private Context context;
    		
    	    public BackgroundAsyncTask(Context cxt) {
    	        context = cxt;
    	    }
   		
    		@Override
    		protected void onPreExecute() {
    			super.onPreExecute();
    			
    			ApriDialog();
    		}
    		
    		private void ApriDialog() {
				try {
				   progressDialog = new ProgressDialog(context);
				   progressDialog.setMessage("Attendere Prego\nUpload file "+Title);
				   progressDialog.setCancelable(false);
				   progressDialog.setCanceledOnTouchOutside(false);
				   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				   progressDialog.show();
				} catch (Exception e) {
				   
				}
    		}
    		
    		private void ChiudeDialog() {
		        try {
		        	progressDialog.dismiss();
		        } catch (Exception e) {
		        }
    		}
    		
    		@Override
    		protected void onPostExecute(String p) {
    			super.onPostExecute(p);
    	        
    	        ChiudeDialog();
    		}
     		
    	    @Override
    	    protected String doInBackground(String... sUrl) {
                String iFileName = Title;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String Tag="fSnd";
                try
                {
                    // Open a HTTP connection to the URL
                    HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
                    // Allow Inputs
                    conn.setDoInput(true);
                    // Allow Outputs
                    conn.setDoOutput(true);
                    // Don't use a cached copy.
                    conn.setUseCaches(false);
                    // Use a post method.
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(Title);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(Description);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
	                    
	                dos.writeBytes("Content-Disposition: form-data; name=\"utente\""+ lineEnd);
	                dos.writeBytes(lineEnd);
	                Utility u=new Utility();
	                dos.writeBytes(u.SistemaNick(Utente));
	                dos.writeBytes(lineEnd);
	                dos.writeBytes(twoHyphens + boundary + lineEnd);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + iFileName +"\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    Log.e(Tag,"Headers are written");

                    // create a buffer of maximum size
                    int bytesAvailable = fileInputStream.available();
                        
                    int maxBufferSize = 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[ ] buffer = new byte[bufferSize];

                    // read file and write it into form...
                    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable,maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // close streams
                    fileInputStream.close();
                        
                    dos.flush();
                        
                    // Log.e(Tag,"File Sent, Response: "+String.valueOf(conn.getResponseCode()));
                         
                    int status = conn.getResponseCode();
                    BufferedInputStream in;
                    if (status >= 400 ) {
                        in = new BufferedInputStream( conn.getErrorStream() );
                        
                        Esporta.Uploadato = false;
                    } else {
                        in = new BufferedInputStream( conn.getInputStream() );
                        
                        Esporta.Uploadato = true;
                    }
                    // retrieve the response from server
                    int ch;

                    StringBuffer b =new StringBuffer();
                    while( ( ch = in.read() ) != -1 ){ b.append( (char)ch ); }
                    String s=b.toString();
                    Log.i("Response",s);
                    
                    dos.close();
                } catch (MalformedURLException ex) {
                    Log.e(Tag, "URL error: " + ex.getMessage(), ex);
                }
                catch (IOException ioe) {
                    Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
                }
                
                Esporta.Continua=false;
                
				return null;
    	    }
    	}
        
        @Override
        public void run() {
        }
}
*/