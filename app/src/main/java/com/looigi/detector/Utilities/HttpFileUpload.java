package com.looigi.detector.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;

import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HttpFileUpload implements Runnable {
        private URL connectURL;
        private String responseString;
        private byte[ ] dataToServer;
        private FileInputStream fileInputStream = null;
        private String NomeFile;
    	private ProgressDialog pd;
    	private boolean Ok;
    	private List<String> toDo;
        private String RadiceUpload = "http://looigi.no-ip.biz:12345/Detector/default.aspx";

        public HttpFileUpload(ProgressDialog pd, String vNomeFile, List<String> toDo){
            try{
                connectURL = new URL(RadiceUpload);
                this.pd = pd;
                this.toDo = toDo;
                NomeFile= vNomeFile;
            }catch(Exception ex){
                Log.i("HttpFileUpload","URL Malformatted");
            }
        }
	
        public void Send_Now(Context context, FileInputStream fStream){
        	fileInputStream = fStream;

    		new UploadImageSyncTask(context).execute();
        }

    	
    	public class UploadImageSyncTask extends AsyncTask<String, Integer, String> {
    		private Context context;
    		
    	    public UploadImageSyncTask(Context cxt) {
    	        context = cxt;
    	    }
   		
    		@Override
    		protected void onPreExecute() {
    			super.onPreExecute();
    		}

    		@Override
    		protected void onPostExecute(String p) {
    			super.onPostExecute(p);

    			if (Ok) {
                    pd.setMessage("Attendere prego\nEliminazione files");

                    for (String filet: toDo) {
                    	File f1 = new File(filet);
                    	f1.delete();
                    }

                    String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
                    String Cartella=VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Paths";
                    String Destinazione = Origine + "/" + Cartella + "/";

                    File f = new File(Destinazione + NomeFile);
                    f.delete();

                    VariabiliStatiche.getInstance().getRltUpload().setVisibility(LinearLayout.GONE);

                    pd.dismiss();
                }
    		}
    		 
    	    @Override
    	    protected String doInBackground(String... sUrl) {
    	        Ok = true;
                String iFileName = NomeFile;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String Tag="fSnd";

                pd.setMessage("Attendere prego\nInvio files");

                try
                {
                    HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();

                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                    conn.setChunkedStreamingMode(4096);

                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"nomefile\""+ lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(iFileName);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    String tipo="";

                    if (iFileName.toUpperCase().contains(".MP4")) {
                        tipo="video/mp4";
                    } else {
                        tipo="image/jpg";
                    }

                    dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\"" + iFileName +"\"" + lineEnd);
                    dos.writeBytes("Content-Type: " + tipo + lineEnd);
                    dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    int bytesAvailable = fileInputStream.available();
                        
                    int maxBufferSize = 1 * 1024 * 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[] buffer = new byte[bufferSize];

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
                    dos.close();

                    int status = conn.getResponseCode();
                    BufferedInputStream in;
                    boolean Errore=false;
                    if (status >= 400 ) {
                        in = new BufferedInputStream( conn.getErrorStream() );
                        Errore=true;
                    } else {
                        in = new BufferedInputStream( conn.getInputStream() );
                    }
                    // retrieve the response from server
                    int ch;

                    StringBuilder b =new StringBuilder();
                    while( ( ch = in.read() ) != -1 ){ b.append( (char)ch ); }
                    String s=b.toString();

                    if (Errore) {
                        Ok = false;
                        DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                                "ERRORE: "+s, true,"Detector");
                    }

                    conn.disconnect();
                } catch (MalformedURLException ex) {
                    pd.dismiss();
                    Ok = false;
                    DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                            "ERRORE: "+ex.getMessage(), true,"Detector");
                } catch (IOException ioe) {
                    pd.dismiss();
                    Ok = false;
                    DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                            "ERRORE: "+ioe.getMessage(), true,"Detector");
                } catch (Exception ignored) {
                    pd.dismiss();
                    Ok = false;
                    DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                            "ERRORE: "+ignored.getMessage(), true,"Detector");
                }

				return null;
    	    }
    	}
        
        @Override
        public void run() {

        }
}