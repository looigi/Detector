package com.looigi.detector.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.LinearLayout;

import com.looigi.detector.Variabili.VariabiliStatiche;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadFiles {
    private ProgressDialog progressDialog;
    private Context conx;
    private String Messaggio;

    public UploadFiles(Context context) {
        conx = context;

        Messaggio="Attendere prego";
        ApriDialog();

        Esegue();
    }

    private void Esegue() {
        new BackgroundAsyncTask(conx).execute();
    }

    public void ChiudeDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception ignored) {
        }
    }

    private void ApriDialog() {
        try {
            // if (conx==null) {
            //     conx = VariabiliStatiche.getInstance().getContext();
            // }
            progressDialog = new ProgressDialog(VariabiliStatiche.getInstance().getFragmentActivityPrincipale());
            progressDialog.setMessage("Attendere prego...");
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

            // ChiudeDialog();
        }

        @Override
        protected String doInBackground(String... sUrl) {
            progressDialog.setMessage("Attendere prego\nLettura files");

            String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
            String Cartella=VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Paths";

            List<String> filetti = VariabiliStatiche.getInstance().getFilesToUpload();
            // List<String> toDo = new ArrayList<>();
            for (String filet: filetti) {
                if (!filet.toUpperCase().contains(".ZIP")) {
                    String file1 = "LL_" + filet + ".txt";
                    String file2 = "MM_" + filet + ".txt";
                    File f1 = new File(Origine + "/" + Cartella + "/" + file1);
                    if (f1.exists()) {
                        // toDo.add(Origine + "/" + Cartella + "/" + file1);
                        AggiungeAFTP(Origine + "/" + Cartella + "/" + file1, file1);
                    }
                    f1 = new File(Origine + "/" + Cartella + "/" + file2);
                    if (f1.exists()) {
                        // toDo.add(Origine + "/" + Cartella + "/" + file2);
                        AggiungeAFTP(Origine + "/" + Cartella + "/" + file2, file2);
                    }
                }
            }

            boolean Ok = true;
            /* String nomello = "UploadFile***.zip";
            String Destinazione = Origine + "/" + Cartella + "/";
            int numerello = 1;
            String n = Integer.toString(numerello);
            String nomello2 = nomello.replace("***", n);
            String nomello1="";

            File f = new File(Destinazione + nomello2);
            while (f.exists()) {
                nomello1 = nomello2;
                n = Integer.toString(numerello);
                nomello2 = nomello.replace("***", n);
                f=new File(Destinazione + nomello2);
                numerello++;
            }
            f = null;

            if (toDo.size()>0) {
                nomello = nomello2;
                Destinazione=Destinazione + nomello2;

                File d = new File(Destinazione);
                d.delete();

                ZipUnzip zu = new ZipUnzip();

                progressDialog.setMessage("Attendere prego\nZip files");

                try {
                    zu.zip(toDo, Destinazione);
                } catch (IOException ignored) {
                    Utility u = new Utility();
                    DialogMessaggio.getInstance().show(context, "Errore nel creare lo zip:\n" + u.PrendeErroreDaException(ignored),
                            false, "Detector");
                    Ok = false;
                }
            } else {
                nomello = nomello1;
                Destinazione=Destinazione + nomello1;
            } */

            // if (Ok) {
            String Urletto="ScriveDatiDaTXT";
            String RadiceWS = "http://looigi.ddns.net:1061/";
            String ws = "Service1.asmx/";
            String NS="http://detectorSQL.it/";
            String SA="http://detectorSQL.it/";

            RichiamoWS g = new RichiamoWS(
                    RadiceWS + ws + Urletto,
                    "ScriveDatiDaTXT",
                    NS,
                    SA,
                    5000,
                    false);
            g.Esegue();


                ChiudeDialog();

                /* try {
                    FileInputStream fstrm = new FileInputStream(ff);
                    HttpFileUpload hfu = new HttpFileUpload(progressDialog, nomello, toDo);
                    hfu.Send_Now(VariabiliStatiche.getInstance().getContext(), fstrm);
                } catch (FileNotFoundException ignored) {
                    int a = 0;
                } */
            // }

            return null;
        }

        void AggiungeAFTP(String Nome, String nomeFile) {
            File ff = new File(Nome);

            FTPClient con = null;
            boolean Errore = false;
            try
            {
                con = new FTPClient();
                con.connect("looigi.myftp.biz");

                if (con.login("looigi", "looigi227"))
                {
                    con.enterLocalPassiveMode(); // important!
                    con.setFileType(FTP.BINARY_FILE_TYPE);
                    // String data = "/sdcard/vivekm4a.m4a";

                    FileInputStream in = new FileInputStream(ff);
                    boolean result = con.storeFile("/PassaggioDetector/" + nomeFile, in);
                    in.close();
                    // if (result) Log.v("upload result", "succeeded");
                    con.logout();
                    con.disconnect();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Errore = true;

                DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                        "ERROR: " + e.getMessage(), true,"Detector");
            }

            if (!Errore) {
                // for (String filet: toDo) {
                    File f1 = new File(Nome);
                    f1.delete();
                // }

                /* String Origine2= Environment.getExternalStorageDirectory().getAbsolutePath();
                String Cartella2=VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Paths";
                String Destinazione2 = Origine + "/" + Cartella + "/";

                File f2 = new File(Destinazione2 + nomello);
                f2.delete(); */

                // VariabiliStatiche.getInstance().getRltUpload().setVisibility(LinearLayout.GONE);

                DialogMessaggio.getInstance().show(VariabiliStatiche.getInstance().getContext(),
                        "Invio effettuato", false,"Detector");
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
