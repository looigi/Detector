package com.looigi.detector.Variabili;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.looigi.detector.Utilities.Utility;

public class VariabiliImpostazioni {
    private static VariabiliImpostazioni instance = null;

    private VariabiliImpostazioni() {
    }

    public static VariabiliImpostazioni getInstance() {
        if(instance == null) {
            instance = new VariabiliImpostazioni();
        }

        return instance;
    }

    private Activity act;

    private int TipologiaScatto;
    private int Secondi;
    private int Fotocamera;
    private String Risoluzione;
    private int Estensione;
    private String Vibrazione;
    private int NumeroScatti;
    private String Anteprima;
    private int Orientamento;
    private String Lingua;

    private String NomeLogGPS="GPSLog.txt";
    private String NomeLog="log.txt";
    private int TEMPO_GPS = 1000;
    private int DISTANZA_GPS = 1;
    private int AccuracyValue=35;
    private boolean FaiLog=false;
    private boolean GPSBetter=true;
    private boolean Accuracy=true;
    private int DimensioniThumbs=70;
    private int DimensioniThumbsM=50;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public boolean isAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(boolean accuracy, boolean NonAvvisare) {
        Accuracy = accuracy;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Detector.Accuracy", Accuracy);
        editor.apply();

        if (!NonAvvisare) {
            VisualizzaMessaggio();
        }
    }

    public boolean isGPSBetter() {
        return GPSBetter;
    }

    public void setGPSBetter(boolean GPSBetter, boolean NonAvvisare) {
        this.GPSBetter = GPSBetter;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Detector.GPSBetter", GPSBetter);
        editor.apply();

        if (!NonAvvisare) {
            VisualizzaMessaggio();
        }
    }

    public int getDimensioniThumbsM() {
        return DimensioniThumbsM;
    }

    public void setDimensioniThumbsM(int dimensioniThumbsM) {
        DimensioniThumbsM = dimensioniThumbsM;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.DimensioniThumbsM", DimensioniThumbsM);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getDimensioniThumbs() {
        return DimensioniThumbs;
    }

    public void setDimensioniThumbs(int dimensioniThumbs) {
        DimensioniThumbs = dimensioniThumbs;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.DimensioniThumbs", DimensioniThumbs);
        editor.apply();

        VisualizzaMessaggio();
    }

    public boolean isFaiLog() {
        return FaiLog;
    }

    public void setFaiLog(boolean faiLog) {
        FaiLog = faiLog;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Detector.Log", FaiLog);
        editor.apply();

        VisualizzaMessaggio();
    }

    public String getNomeLogGPS() {
        return NomeLogGPS;
    }

    public void setNomeLogGPS(String nomeLogGPS) {
        NomeLogGPS = nomeLogGPS;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Detector.NomeLogGPS", NomeLogGPS);
        editor.apply();

        VisualizzaMessaggio();
    }

    public String getNomeLog() {
        return NomeLog;
    }

    public void setNomeLog(String nomeLog) {
        NomeLog = nomeLog;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Detector.NomeLog", NomeLog);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getTEMPO_GPS() {
        return TEMPO_GPS;
    }

    public void setTEMPO_GPS(int TEMPO_GPS) {
        this.TEMPO_GPS = TEMPO_GPS;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.TempoGPS", this.TEMPO_GPS);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getDISTANZA_GPS() {
        return DISTANZA_GPS;
    }

    public void setDISTANZA_GPS(int DISTANZA_GPS, boolean NonAvvisare) {
        this.DISTANZA_GPS = DISTANZA_GPS;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.DistanzaGPS", this.DISTANZA_GPS);
        editor.apply();

        if (!NonAvvisare) {
            VisualizzaMessaggio();
        }
    }

    public int getAccuracyValue() {
        return AccuracyValue;
    }

    public void setAccuracyValue(int accuracyValue, boolean NonAvvisare) {
        AccuracyValue = accuracyValue;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.AccuracyValue", this.AccuracyValue);
        editor.apply();

        if (!NonAvvisare) {
            VisualizzaMessaggio();
        }
    }

    public int getTipologiaScatto() {
        return TipologiaScatto;
    }

    public void setTipologiaScatto(int tipologiaScatto) {
        TipologiaScatto = tipologiaScatto;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.TipologiaScatto", this.TipologiaScatto);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getSecondi() {
        return Secondi;
    }

    public void setSecondi(int secondi) {
        Secondi = secondi;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.Secondi", this.Secondi);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getFotocamera() {
        return Fotocamera;
    }

    public void setFotocamera(int fotocamera) {
        Fotocamera = fotocamera;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.Fotocamera", this.Fotocamera);
        editor.apply();

        VisualizzaMessaggio();
    }

    public String getRisoluzione() {
        return Risoluzione;
    }

    public void setRisoluzione(String risoluzione) {
        Risoluzione = risoluzione;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Detector.Risoluzione", this.Risoluzione);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getEstensione() {
        return Estensione;
    }

    public void setEstensione(int estensione) {
        Estensione = estensione;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.Estensione", this.Estensione);
        editor.apply();

        VisualizzaMessaggio();
    }

    public String getVibrazione() {
        return Vibrazione;
    }

    public void setVibrazione(String vibrazione) {
        Vibrazione = vibrazione;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Detector.Vibrazione", this.Vibrazione);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getNumeroScatti() {
        return NumeroScatti;
    }

    public void setNumeroScatti(int numeroScatti) {
        NumeroScatti = numeroScatti;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.NumeroScatti", this.NumeroScatti);
        editor.apply();

        VisualizzaMessaggio();
    }

    public String getAnteprima() {
        return Anteprima;
    }

    public void setAnteprima(String anteprima) {
        Anteprima = anteprima;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Detector.Anteprima", this.Anteprima);
        editor.apply();

        VisualizzaMessaggio();
    }

    public int getOrientamento() {
        return Orientamento;
    }

    public void setOrientamento(int orientamento) {
        Orientamento = orientamento;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Detector.Orientamento", this.Orientamento);
        editor.apply();

        VisualizzaMessaggio();
    }

    public String getLingua() {
        return Lingua;
    }

    public void setLingua(String lingua) {
        Lingua = lingua;

        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Detector.Lingua", this.Lingua);
        editor.apply();

        VisualizzaMessaggio();
    }

    private void VisualizzaMessaggio() {
        Utility u = new Utility();
        u.VisualizzaPOPUP("Valore salvato", false, 0);
    }

    public void LeggeImpostazioni() {
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);

        this.FaiLog = sharedPref.getBoolean("Detector.Log", false);
        this.TEMPO_GPS = sharedPref.getInt("Detector.TempoGPS", 1000);
        this.DISTANZA_GPS = sharedPref.getInt("Detector.DistanzaGPS", 1);
        this.AccuracyValue = sharedPref.getInt("Detector.AccuracyValue", 35);
        this.TipologiaScatto = sharedPref.getInt("Detector.TipologiaScatto", 2);
        this.Secondi=sharedPref.getInt("Detector.Secondi", 3);
        this.Fotocamera=sharedPref.getInt("Detector.Fotocamera", 2);
        this.Risoluzione=sharedPref.getString("Detector.Risoluzione", "640x480");
        this.Estensione=sharedPref.getInt("Detector.Estensione", 2);
        this.Vibrazione=sharedPref.getString("Detector.Vibrazione", "S");
        this.NumeroScatti=sharedPref.getInt("Detector.NumeroScatti", 3);
        this.Anteprima=sharedPref.getString("Detector.Anteprima", "S");
        this.Orientamento=sharedPref.getInt("Detector.Orientamento", 0);
        this.Lingua=sharedPref.getString("Detector.Lingua", "ITALIANO");
        this.DimensioniThumbs=sharedPref.getInt("Detector.DimensioniThumbs", 70);
        this.DimensioniThumbsM=sharedPref.getInt("Detector.DimensioniThumbsM", 50);
        this.GPSBetter=sharedPref.getBoolean("Detector.GPSBetter", true);
        this.Accuracy=sharedPref.getBoolean("Detector.Accuracy", true);

        VariabiliStatiche.getInstance().getsLog().setChecked(this.FaiLog);
        VariabiliStatiche.getInstance().getsGPSBetter().setChecked(this.GPSBetter);
        VariabiliStatiche.getInstance().getsAccuracy().setChecked(this.Accuracy);
        VariabiliStatiche.getInstance().getEdtTempoGPS().setText(Integer.toString(TEMPO_GPS));
        VariabiliStatiche.getInstance().getEdtMetriGPS().setText(Integer.toString(DISTANZA_GPS));
        VariabiliStatiche.getInstance().getEdtAccuracy().setText(Integer.toString(AccuracyValue));
        VariabiliStatiche.getInstance().getEdtDimThumbs().setText(Integer.toString(DimensioniThumbs));
        VariabiliStatiche.getInstance().getEdtDimThumbsM().setText(Integer.toString(DimensioniThumbsM));

        if (VariabiliStatiche.getInstance().getsAccuracy().isChecked()) {
            VariabiliStatiche.getInstance().getBtnSalvaAcc().setEnabled(true);
            VariabiliStatiche.getInstance().getEdtAccuracy().setEnabled(true);
        } else {
            VariabiliStatiche.getInstance().getBtnSalvaAcc().setEnabled(false);
            VariabiliStatiche.getInstance().getEdtAccuracy().setEnabled(false);
        }
    }
}
