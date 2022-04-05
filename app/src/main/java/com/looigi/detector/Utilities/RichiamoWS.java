package com.looigi.detector.Utilities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;

import com.looigi.detector.Variabili.VariabiliStatiche;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class RichiamoWS {
    private static BackgroundAsyncTask bckAsyncTask;
    private static String messErrore="";

    private String NAMESPACE;
    private int Timeout;
    private String SOAP_ACTION;
    private String tOperazione;
    private boolean ApriDialog;
    private String Urletto;

    public RichiamoWS(String urletto, String TipoOperazione,
                      String NS, String SA, int Timeout,
                      boolean ApriDialog) {

        this.NAMESPACE = NS;
        this.Timeout = Timeout;
        this.SOAP_ACTION = SA;
        this.tOperazione = TipoOperazione;
        this.ApriDialog = ApriDialog;
        this.Urletto = urletto;
    }

    public void Esegue() {
        // boolean ceRete = VariabiliStaticheGlobali.getInstance().getNtn().isOk();
//
        // if (ceRete) {
        // if (bckAsyncTask==null) {
        bckAsyncTask = new BackgroundAsyncTask(NAMESPACE, Timeout, SOAP_ACTION, tOperazione,
                ApriDialog, Urletto);
        bckAsyncTask.execute(Urletto);
        // }
        // }
    }

    public void StoppaEsecuzione() {
        bckAsyncTask.cancel(true);

        bckAsyncTask.ChiudeDialog();

        messErrore ="ESCI";

        bckAsyncTask.ControllaFineCiclo();
    }

    private static class BackgroundAsyncTask extends AsyncTask<String, Integer, String> {
        private String NAMESPACE;
        private String METHOD_NAME = "";
        private String[] Parametri;
        private Integer Timeout;
        private String SOAP_ACTION;
        private Boolean Errore;
        private String result="";
        // private int NumeroBrano;
        // private int NumeroOperazione;
        private String tOperazione;
        private int QuantiTentativi;
        private int Tentativo;
        private Handler hAttesaNuovoTentativo;
        private Runnable rAttesaNuovoTentativo;
        private int SecondiAttesa;
        private boolean ApriDialog;
        private ProgressDialog progressDialog;
        private String Urletto;

        private BackgroundAsyncTask(String NAMESPACE, int TimeOut,
                                    String SOAP_ACTION, String tOperazione,
                                    boolean ApriDialog, String Urletto) {
            this.NAMESPACE = NAMESPACE;
            // this.METHOD_NAME = METHOD_NAME;
            // this.Parametri = Parametri;
            this.Timeout = TimeOut;
            this.SOAP_ACTION = SOAP_ACTION;
            // this.NumeroOperazione = NumeroOperazione;
            this.tOperazione = tOperazione;
            this.ApriDialog = ApriDialog;
            this.Urletto = Urletto;

            // this.NumeroBrano = Utility.getInstance().ControllaNumeroBrano();

            this.QuantiTentativi = 3;
            this.Tentativo = 0;
        }

        private void SplittaCampiUrletto(String Cosa) {
            String Perc=Cosa;
            int pos;
            String Indirizzo="";
            String[] Variabili;
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
                Urletto=Indirizzo;
                METHOD_NAME = Funzione;
                SOAP_ACTION = NAMESPACE + Funzione;
                Perc=Perc.substring(pos+1, Perc.length());
                pos=Perc.indexOf("&");
                if (pos>-1) {
                    Variabili=Perc.split("&",-1);
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
                Urletto=Indirizzo;
                METHOD_NAME = Funzione;
                SOAP_ACTION = NAMESPACE + Funzione;
            }
        }

        private void ChiudeDialog() {
            if (ApriDialog) {
                try {
                    progressDialog.dismiss();
                } catch (Exception ignored) {
                }
            }
        }

        private void ApriDialog() {
            if (ApriDialog) {
                try {
                    progressDialog = new ProgressDialog(VariabiliStatiche.getInstance().getContext());
                    progressDialog.setMessage("Attendere Prego\n"+tOperazione);
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                } catch (Exception ignored) {

                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ApriDialog();
            SplittaCampiUrletto(this.Urletto);
        }

        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);

            ControllaFineCiclo();
        }

        @Override
        protected String doInBackground(String... sUrl) {
            Errore = false;
            result = "";
            Utility u = new Utility();

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            String Parametro="";
            String Valore="";

            if (Parametri!=null) {
                for (int i=0;i<Parametri.length;i++) {
                    if (Parametri[i] != null) {
                        int pos = Parametri[i].indexOf("=");
                        if (pos > -1) {
                            Parametro = Parametri[i].substring(0, pos);
                            Valore = Parametri[i].substring(pos + 1, Parametri[i].length());
                        }
                        Request.addProperty(Parametro, Valore);
                    }
                }
            }

            SoapSerializationEnvelope soapEnvelope = null;
            HttpTransportSE aht = null;
            messErrore="";
            try {
                soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);
                // conn.setRequestProperty("connection", "close");
                aht = new HttpTransportSE(Urletto, Timeout);
                aht.reset();
                aht.call(SOAP_ACTION, soapEnvelope);

                if(isCancelled()){
                } else {
                }
            } catch (SocketTimeoutException e) {
                Errore=true;
                messErrore = u.PrendeErroreDaException(e);
                if (messErrore!=null) {
                    messErrore=messErrore.toUpperCase().replace("LOOIGI.NO-IP.BIZ","Web Service");
                } else {
                    messErrore="Unknown";
                }
                result="ERROR: "+messErrore;
                messErrore = result;
                //Utility.getInstance().VisualizzaPOPUP(context, "Errore di socket sul DB:\n" + messErrore, false, 0, false);
            } catch (IOException e) {
                Errore=true;
                messErrore = u.PrendeErroreDaException(e);
                if (messErrore!=null)
                    messErrore=messErrore.toUpperCase().replace("LOOIGI.NO-IP.BIZ","Web Service");
                result="ERROR: "+messErrore;
                messErrore = result;
                //Utility.getInstance().VisualizzaPOPUP(context, "Errore di I/O dal DB:\n" + messErrore, false, 0, false);
            } catch (XmlPullParserException e) {
                Errore=true;
                messErrore = u.PrendeErroreDaException(e);
                if (messErrore!=null) {
                    messErrore=messErrore.toUpperCase().replace("LOOIGI.NO-IP.BIZ","Web Service");
                } else {
                    messErrore="Unknown";
                }
                result="ERRORE: "+messErrore;
                messErrore = result;
                //Utility.getInstance().VisualizzaPOPUP(context, "Errore di parsing XML:\n" + messErrore, false, 0, false);
            } catch (Exception e) {
                Errore=true;
                messErrore = u.PrendeErroreDaException(e);
                if (messErrore!=null)
                    messErrore=messErrore.toUpperCase().replace("LOOIGI.NO-IP.BIZ","Web Service");
                result="ERROR: "+messErrore;
                messErrore = result;
                //Utility.getInstance().VisualizzaPOPUP(context, "Errore generico di lettura sul DB:\n" + messErrore, false, 0, false);
            }
            if (!Errore && !isCancelled()) {
                try {
                    result = ""+soapEnvelope.getResponse();
                } catch (SoapFault e) {
                    Errore=true;
                    messErrore = u.PrendeErroreDaException(e);
                    if (messErrore!=null) {
                        messErrore=messErrore.toUpperCase().replace("LOOIGI.NO-IP.BIZ","Web Service");
                    } else {
                        messErrore="Unknown";
                    }
                    result="ERROR: "+messErrore;
                    messErrore = result;
                }
            } else {
                int a = 0;
            }
            if (aht!=null) {
                aht=null;
            }
            if (soapEnvelope!=null) {
                soapEnvelope=null;
            }
            if (isCancelled()) {
                messErrore="ESCI";
            }

            return null;
        }

        private void ControllaFineCiclo() {
            if (!messErrore.equals("ESCI")) {
                String Ritorno = result;

                if (Ritorno.contains("ERROR:")) {
                    messErrore = Ritorno;
                    Errore = true;
                }

                ChiudeDialog();

                wsRitorno rRit = new wsRitorno();
                boolean Ancora = true;

                if (!Errore) {
                    while (Ancora) {
                        if (tOperazione.equals("ScriveDatiDaTXT")) {
                            rRit.ScriveDatiDaTXT(Ritorno);
                            Ancora = false;
                            break;
                        }
                    }
                }
            }
            bckAsyncTask = null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

        }

        @Override
        protected void onCancelled(){
            messErrore="ESCI";
        }

    }
}
