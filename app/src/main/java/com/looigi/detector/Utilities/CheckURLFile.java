package com.looigi.detector.Utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.looigi.detector.Variabili.VariabiliStatiche;

import java.net.HttpURLConnection;
import java.net.URL;

public class CheckURLFile {
    private String Path;
    private Context context;
    private String messErrore = "";
    private CheckFile downloadFile;
    private String Url;

    public void setContext(Context context) {
        this.context = context;
    }

    public void startControl(String sUrl) {
        Url = sUrl;
        messErrore="";

        downloadFile = new CheckFile();
        downloadFile.execute(Url);
    }

    private class CheckFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            try {
                HttpURLConnection.setFollowRedirects(false);
                // note : you may also need
                // HttpURLConnection.setInstanceFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) new URL(sUrl[0])
                        .openConnection();
                con.setRequestMethod("HEAD");

                int c = con.getResponseCode();
                if (c == HttpURLConnection.HTTP_OK) {
                    messErrore="OK";
                } else {
                    messErrore="ERROR: " + Integer.toString(c);
                }
            } catch (Exception e) {
                Utility u = new Utility();
                messErrore="ERROR: "+u.PrendeErroreDaException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            VariabiliStatiche.getInstance().setRitornoCheckFileURL(messErrore);
        }
    }
}