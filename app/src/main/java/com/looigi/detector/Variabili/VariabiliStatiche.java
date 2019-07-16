package com.looigi.detector.Variabili;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.looigi.detector.R;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Utilities.db_dati;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VariabiliStatiche {
    private static VariabiliStatiche instance = null;

    private VariabiliStatiche() {
    }

    public static VariabiliStatiche getInstance() {
        if(instance == null) {
            instance = new VariabiliStatiche();
        }

        return instance;
    }

    // private TextView tu;
    // private LinearLayout lu;
    // private LinearLayout le;
    // private LinearLayout la;
    // private LinearLayout li;
    // private TextView tq;
    // private TextView ta;
    // private TextView tai;
    // private ListView lImm;
    private Button cmdDown;

    private ListView Lista;

    public String NomeFileSelezionato="";
    protected static Context context;
    public AudioManager manager ;
    // public int Fotocamera=2;
    // public int Estensione=1;
    public List<String> Dimensioni;
    // public boolean LogAttivo=true;
    // public String Lingua="";
    // public String SalvaOnLine;
    // public String Vibrazione;
    // public String Anteprima;
    // public String NomeUtente;
    // public int numScatti=0;
    // public int idUtente;
    // public String Nick;
    // public String Password;
    // public String Nome;
    // public int Orient;
    public boolean StoScattando;
    private String[] NomiImm;
    // public String NomeSito = "http://looigi.no-ip.biz:12345/";
    // public Boolean Continua;
    // public String ImmDaEliminare;
    private List<String> Immagini;
    public Integer numMultimedia;
    public Integer totImmagini;
    private ImageView img;
    private ImageView audio;
    private TextView txtImm;
    private TextView txtNomeImm;
    public Boolean MascheraImmaginiMostrata = false;
    public String PathApplicazione = "/LooigiSoft/Detector/DB/";
    public String PathApplicazioneFuori = "/LooigiSoft/Detector/";
    public Boolean StaSuonando=false;
    private MediaPlayer mp;
    private ImageView btnRuotaSin;
    private ImageView btnRuotaDes;
    private ImageView btnFlipX;
    private ImageView btnFlipY;
    private VideoView vView;
    private ImageView imgGps;
    public Boolean StaVedendo=false;
    // private double lLat =0;
    // private double lLon =0;
    // private double lAltezza=0;
    private Location locGPS;
    private String ModelloTelefono="";
    private TextView txtCoords;
    private int ProgressivoDBGPS;
    private int ProgressivoDBMM;
    private db_dati dbGpsPos;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Boolean GiaEntrato=false;
    private GoogleMap mMap=null;
    private boolean SeguePercorso=false;
    private Date DataDiVisualizzazioneMappa;
    private TextView txtDataMappa;
    private Intent lockService;
    private Intent locationService;
    private Boolean ServizioLock=false;
    private Boolean ServizioGPS=false;
    private Switch sLog;
    private Switch sGPSBetter;
    private Switch sAccuracy;
    private EditText edtTempoGPS;
    private EditText edtMetriGPS;
    private EditText edtAccuracy;
    private EditText edtDimThumbs;
    private EditText edtDimThumbsM;
    private LatLngBounds.Builder builder;
    private boolean GiaEntratoInMappa=false;
    private int Punti;
    private Button btnSalvaAcc;
    private String RitornoCheckFileURL="";
    private ImageView imgMappa;
    private ImageView imgChiudeImg;
    private View fgmMappa;
    private float KmPercorsi;
    private TextView txtKM;
    private LinearLayout rltUpload;
    private ListView lstUpload;
    private List<String> filesToUpload;
    private FragmentActivity FragmentActivityPrincipale;
    private Intent iServizio;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public FragmentActivity getFragmentActivityPrincipale() {
        return FragmentActivityPrincipale;
    }

    public void setFragmentActivityPrincipale(FragmentActivity fragmentActivityPrincipale) {
        FragmentActivityPrincipale = fragmentActivityPrincipale;
    }

    public Intent getiServizio() {
        return iServizio;
    }

    public void setiServizio(Intent iServizio) {
        this.iServizio = iServizio;
    }

    public List<String> getFilesToUpload() {
        return filesToUpload;
    }

    public void setFilesToUpload(List<String> filesToUpload) {
        this.filesToUpload = filesToUpload;
    }

    public ListView getLstUpload() {
        return lstUpload;
    }

    public void setLstUpload(ListView lstUpload) {
        this.lstUpload = lstUpload;
    }

    public LinearLayout getRltUpload() {
        return rltUpload;
    }

    public void setRltUpload(LinearLayout rltUpload) {
        this.rltUpload = rltUpload;
    }

    public TextView getTxtKM() {
        return txtKM;
    }

    public void setTxtKM(TextView txtKM) {
        this.txtKM = txtKM;
    }

    public float getKmPercorsi() {
        return KmPercorsi;
    }

    public void setKmPercorsi(float kmPercorsi) {
        KmPercorsi = kmPercorsi;
    }

    public ImageView getImgChiudeImg() {
        return imgChiudeImg;
    }

    public void setImgChiudeImg(ImageView imgChiudeImg) {
        this.imgChiudeImg = imgChiudeImg;
    }

    public View getFgmMappa() {
        return fgmMappa;
    }

    public void setFgmMappa(View fgmMappa) {
        this.fgmMappa = fgmMappa;
    }

    public ImageView getImgMappa() {
        return imgMappa;
    }

    public void setImgMappa(ImageView imgMappa) {
        this.imgMappa = imgMappa;
    }

    public String getRitornoCheckFileURL() {
        return RitornoCheckFileURL;
    }

    public void setRitornoCheckFileURL(String ritornoCheckFileURL) {
        RitornoCheckFileURL = ritornoCheckFileURL;
    }

    public Date getDataDiVisualizzazioneMappa() {
        return DataDiVisualizzazioneMappa;
    }

    public EditText getEdtDimThumbsM() {
        return edtDimThumbsM;
    }

    public void setEdtDimThumbsM(EditText edtDimThumbsM) {
        this.edtDimThumbsM = edtDimThumbsM;
    }

    public Button getBtnSalvaAcc() {
        return btnSalvaAcc;
    }

    public void setBtnSalvaAcc(Button btnSalvaAcc) {
        this.btnSalvaAcc = btnSalvaAcc;
    }

    public Switch getsAccuracy() {
        return sAccuracy;
    }

    public void setsAccuracy(Switch sAccuracy) {
        this.sAccuracy = sAccuracy;
    }

    public EditText getEdtDimThumbs() {
        return edtDimThumbs;
    }

    public void setEdtDimThumbs(EditText edtDimThumbs) {
        this.edtDimThumbs = edtDimThumbs;
    }

    public Switch getsGPSBetter() {
        return sGPSBetter;
    }

    public void setsGPSBetter(Switch sGPSBetter) {
        this.sGPSBetter = sGPSBetter;
    }

    public Switch getsLog() {
        return sLog;
    }

    public void setsLog(Switch sLog) {
        this.sLog = sLog;
    }

    public EditText getEdtTempoGPS() {
        return edtTempoGPS;
    }

    public void setEdtTempoGPS(EditText edtTempoGPS) {
        this.edtTempoGPS = edtTempoGPS;
    }

    public EditText getEdtMetriGPS() {
        return edtMetriGPS;
    }

    public void setEdtMetriGPS(EditText edtMetriGPS) {
        this.edtMetriGPS = edtMetriGPS;
    }

    public EditText getEdtAccuracy() {
        return edtAccuracy;
    }

    public void setEdtAccuracy(EditText edtAccuracy) {
        this.edtAccuracy = edtAccuracy;
    }

    public int getProgressivoDBMM() {
        return ProgressivoDBMM;
    }

    public void setProgressivoDBMM(int progressivoDBMM) {
        ProgressivoDBMM = progressivoDBMM;
    }

    public Boolean getServizioLock() {
        return ServizioLock;
    }

    public void setServizioLock(Boolean servizioLock) {
        ServizioLock = servizioLock;
    }

    public Boolean getServizioGPS() {
        return ServizioGPS;
    }

    public void setServizioGPS(Boolean servizioGPS) {
        ServizioGPS = servizioGPS;
    }

    public Intent getLockService() {
        return lockService;
    }

    public void setLockService(Intent lockService) {
        this.lockService = lockService;
    }

    public Intent getLocationService() {
        return locationService;
    }

    public void setLocationService(Intent locationService) {
        this.locationService = locationService;
    }

    public ImageView getImgGps() {
        return imgGps;
    }

    public void setImgGps(ImageView imgGps) {
        this.imgGps = imgGps;
    }

    public void setTxtDataMappa(TextView txtDataMappa) {
        this.txtDataMappa = txtDataMappa;
    }

    public void setDataDiVisualizzazioneMappa(Date dataDiVisualizzazioneMappa) {
        DataDiVisualizzazioneMappa = dataDiVisualizzazioneMappa;
    }

    public boolean isSeguePercorso() {
        return SeguePercorso;
    }

    public void setSeguePercorso(boolean seguePercorso) {
        SeguePercorso = seguePercorso;
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public Boolean getGiaEntrato() {
        return GiaEntrato;
    }

    public void setGiaEntrato(Boolean giaEntrato) {
        GiaEntrato = giaEntrato;
    }

    public Location getLocGPS() {
        return locGPS;
    }

    public void setLocGPS(Location locGPS) {
        this.locGPS = locGPS;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public LocationListener getLocationListener() {
        return locationListener;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public db_dati getDbGpsPos() {
        return dbGpsPos;
    }

    public void setDbGpsPos(db_dati dbGpsPos) {
        this.dbGpsPos = dbGpsPos;
    }

    public int getProgressivoDBGPS() {
        return ProgressivoDBGPS;
    }

    public void setProgressivoDBGPS(int progressivoDBGPS) {
        ProgressivoDBGPS = progressivoDBGPS;
    }

    public TextView getTxtCoords() {
        return txtCoords;
    }

    public void setTxtCoords(TextView txtCoords) {
        this.txtCoords = txtCoords;
    }

    public String getModelloTelefono() {
        return ModelloTelefono;
    }

    public void setModelloTelefono(String modelloTelefono) {
        ModelloTelefono = modelloTelefono;
    }

    // public Double getlAltezza() {
    //     return lAltezza;
    // }
//
    // public void setlAltezza(Double lAltezza) {
    //     this.lAltezza = lAltezza;
    // }
//
    // public double getLat() {
    //     return lLat;
    // }
//
    // public void setlLat(double lLat) {
    //     this.lLat = lLat;
    // }
//
    // public double getlLon() {
    //     return lLon;
    // }
//
    // public void setlLon(double lLon) {
    //     this.lLon = lLon;
    // }

    public VideoView getvView() {
        return vView;
    }

    public void setvView(VideoView vView) {
        this.vView = vView;
    }

    public ImageView getBtnRuotaSin() {
        return btnRuotaSin;
    }

    public void setBtnRuotaSin(ImageView btnRuotaSin) {
        this.btnRuotaSin = btnRuotaSin;
    }

    public ImageView getBtnRuotaDes() {
        return btnRuotaDes;
    }

    public void setBtnRuotaDes(ImageView btnRuotaDes) {
        this.btnRuotaDes = btnRuotaDes;
    }

    public ImageView getBtnFlipX() {
        return btnFlipX;
    }

    public void setBtnFlipX(ImageView btnFlipX) {
        this.btnFlipX = btnFlipX;
    }

    public ImageView getBtnFlipY() {
        return btnFlipY;
    }

    public void setBtnFlipY(ImageView btnFlipY) {
        this.btnFlipY = btnFlipY;
    }

    public MediaPlayer getMp() {
        return mp;
    }

    public void setMp(MediaPlayer mp) {
        this.mp = mp;
    }

    public ImageView getAudio() {
        return audio;
    }

    public void setAudio(ImageView audio) {
        this.audio = audio;
    }

    public TextView getTxtNomeImm() {
        return txtNomeImm;
    }

    public void setTxtNomeImm(TextView txtNomeImm) {
        this.txtNomeImm = txtNomeImm;
    }

    public TextView getTxtImm() {
        return txtImm;
    }

    public void setTxtImm(TextView txtImm) {
        this.txtImm = txtImm;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public List<String> getImmagini() {
        return Immagini;
    }

    public void setImmagini(List<String> immagini) {
        Immagini = immagini;
    }

    public String[] getNomiImm() {
        return NomiImm;
    }

    public void setNomiImm(String[] nomiImm) {
        NomiImm = nomiImm;
    }

    public Button getCmdDown() {
        return cmdDown;
    }

    public void setCmdDown(Button cmdDown) {
        this.cmdDown = cmdDown;
    }

    /* public TextView getTu() {
        return tu;
    }

    public void setTu(TextView tu) {
        this.tu = tu;
    }

    public LinearLayout getLu() {
        return lu;
    }

    public void setLu(LinearLayout lu) {
        this.lu = lu;
    }

    public LinearLayout getLe() {
        return le;
    }

    public void setLe(LinearLayout le) {
        this.le = le;
    }

    public LinearLayout getLa() {
        return la;
    }

    public void setLa(LinearLayout la) {
        this.la = la;
    }

    public LinearLayout getLi() {
        return li;
    }

    public void setLi(LinearLayout li) {
        this.li = li;
    }

    public TextView getTq() {
        return tq;
    }

    public void setTq(TextView tq) {
        this.tq = tq;
    }

    public TextView getTa() {
        return ta;
    }

    public void setTa(TextView ta) {
        this.ta = ta;
    }

    public TextView getTai() {
        return tai;
    }

    public void setTai(TextView tai) {
        this.tai = tai;
    }

    public ListView getlImm() {
        return lImm;
    }

    public void setlImm(ListView lImm) {
        this.lImm = lImm;
    } */

    public ListView getLista() {
        return Lista;
    }

    public void setLista(ListView lista) {
        Lista = lista;
    }

    // public void ScriveValoriCoordinateSenzaSalvare() {
    //     if (locGPS!=null && txtCoords!=null) {
    //         txtCoords.setText(Integer.toString(ProgressivoDBGPS)+": "+Double.toString(locGPS.getLatitude()) + "," + Double.toString(locGPS.getLongitude()));
    //     }
    // }

    public void ScriveValoriCoordinate(Location loc) {
        locGPS = loc;

        ScriveDatiAVideo();

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String oggi = formatter.format(todayDate);

        long l = dbGpsPos.aggiungiPosizione(oggi,
                Integer.toString(ProgressivoDBGPS),
                Double.toString(loc.getLatitude()),
                Double.toString(loc.getLongitude()),
                Double.toString(loc.getAltitude()),
                Float.toString(loc.getSpeed()));
        ProgressivoDBGPS++;
    }

    public void DecrementaGiornoMappa() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        c.setTime(DataDiVisualizzazioneMappa);
        c.add(Calendar.DATE, -1);  // number of days to add
        // String dt = sdf.format(c.getTime());  // dt is now the new date
        DataDiVisualizzazioneMappa=c.getTime();

        DisegnaPercorsoVecchioSuMappa();
    }

    public void AumentaGiornoMappa() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        c.setTime(DataDiVisualizzazioneMappa);
        c.add(Calendar.DATE, 1);  // number of days to add
        // String dt = sdf.format(c.getTime());  // dt is now the new date
        DataDiVisualizzazioneMappa=c.getTime();

        DisegnaPercorsoVecchioSuMappa();
    }

    public void EstraiTuttiIDatiGPS() {
        List<Date> lista = VariabiliStatiche.getInstance().getDbGpsPos().RitornaTutteLeDateInArchivio();

        for (Date d : lista) {
            EsegueScaricoDati(d, false);
            VariabiliStatiche.getInstance().getDbGpsPos().cancellaDatiGPSPerData(d);
            VariabiliStatiche.getInstance().getDbGpsPos().cancellaDatiMultiMediaPerData(d);
        }

        /* Utility u=new Utility();
        u.VisualizzaPOPUP(VariabiliStatiche.getInstance().getContext(),
                "File salvati: " + Integer.toString(lista.size()-1),
                false, 0); */
    }

    public void EstraiDatiGPS() {
        Date dataVisua = DataDiVisualizzazioneMappa;

        EsegueScaricoDati(dataVisua, true);
    }

    private void EsegueScaricoDati(Date dataVisua, boolean Visualizza) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
        String Cartella=VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Paths";

        Utility u=new Utility();
        u.CreaCartelle(Origine, Cartella+"/");

        Cursor c1 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniValoriGPSPerData(dataVisuaS);
        if (c1!=null) {
            if (c1.moveToFirst()) {
                String fileDest = "LL_" + dataVisuaS.replace("/", "_") + ".txt";
                String body = "";

                do {
                    String lat = c1.getString(2);
                    String lon = c1.getString(3);
                    String ora = c1.getString(5);
                    String vel = c1.getString(6);

                    body += lat + ";" + lon + ";" + ora + ";" + vel + ";\n";
                } while (c1.moveToNext());

                u.CreaFileDiTesto(Origine + "/" + Cartella + "/", fileDest, body);
            }
            c1.close();
        }
        c1 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniValoriMultiMediaPerData(dataVisuaS);
        if (c1!=null) {
            if (c1.moveToFirst()) {
                do {
                    String fileDest = "MM_" + dataVisuaS.replace("/", "_") + ".txt";
                    String body = "";

                    do {
                        String lat = c1.getString(2);
                        String lon = c1.getString(3);
                        String ora = c1.getString(5);
                        String nome = c1.getString(6);
                        String tipo = c1.getString(7);

                        body += lat + ";" + lon + ";" + ora + ";" + nome + ";" + tipo + "\n";
                    } while (c1.moveToNext());

                    u.CreaFileDiTesto(Origine + "/" + Cartella + "/", fileDest, body);
                } while (c1.moveToNext());
            }
            c1.close();
        }

        if (Visualizza) {
            u.VisualizzaPOPUP( "File salvati", false, 0);
        }
    }

    public void DisegnaPercorsoVecchioSuMappa() {
        if (mMap!=null) {
            Date dataVisua = DataDiVisualizzazioneMappa;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dataVisuaS = formatter.format(dataVisua);

            txtDataMappa.setText(dataVisuaS);

            mMap.clear();

            Cursor c3 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniDistanzeData(dataVisuaS);
            float km = 0;
            if (c3.moveToFirst()) {
                do {
                    km = Float.parseFloat(c3.getString(1));
                } while (c3.moveToNext());
            }
            c3.close();
            Utility u = new Utility();
            u.ScriveKMPassati(km);

            Cursor cP=null;
            Cursor cM=null;
            Cursor c1 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniValoriGPSPerData(dataVisuaS);
            if (c1!=null && c1.moveToFirst()) {
                cP=c1;
            }
            Cursor c2 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniValoriMultiMediaPerData(dataVisuaS);
            if (c2!=null && c2.moveToFirst()) {
                cM=c2;
            }
            DisegnaMappa(false, cP, cM);
            if (c1!=null)
                c1.close();
            if (c1!=null)
                c2.close();
            if (cP!=null)
                cP.close();
            if (cM!=null)
                cM.close();
        }
    }

    public void DisegnaPercorsoAttualeSuMappa() {
        if (mMap!=null) {
            mMap.clear();

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String oggi = formatter.format(todayDate);
            DataDiVisualizzazioneMappa = Calendar.getInstance().getTime();

            txtDataMappa.setText(oggi);

            Utility u = new Utility();
            u.ScriveKM();

            Cursor cP=null;
            Cursor cM=null;
            Cursor c1 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniValoriGPSPerData(oggi);
            if (c1!=null && c1.moveToFirst()) {
                cP=c1;
            }
            Cursor c2 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniValoriMultiMediaPerData(oggi);
            if (c2!=null && c2.moveToFirst()) {
                cM=c2;
            }
            DisegnaMappa(true, cP, cM);
            if (c1!=null)
                c1.close();
            if (c2!=null)
                c2.close();
            if (cP!=null)
                cP.close();
            if (cM!=null)
                cM.close();
        }
    }

    private int ConvertSpeedToColor(float fspeed) {
        float speed = fspeed*3.6F;

        if (fspeed!=30) {
            int i=0;
        }
        int color = Color.TRANSPARENT;
        if (speed < 5) {
            color = Color.argb(255,0,0,200);
        } else if (speed < 25) {
            color = Color.argb(255,0,200,0);
        } else if (speed < 50) {
            color = Color.argb(255,200,200,0);
        } else {
            color = Color.argb(255,200,0,0);
        }

        return color;
    }

    private void DisegnaMappa(Boolean NuovaMappa, Cursor cursPercorso, Cursor cursMarkers) {
        // PolylineOptions puntiMappa = new PolylineOptions().width(7).color(Color.BLUE).geodesic(true);
        List<LatLng> currentSegment = new ArrayList<>();
        builder = new LatLngBounds.Builder();
        boolean primo=true;

        int height = 80;
        int width = 80;
        Punti=0;

        BitmapDrawable bitmapdraw=(BitmapDrawable) VariabiliStatiche.getInstance().getContext().getResources().getDrawable(R.drawable.partenza);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap markerPartenza = Bitmap.createScaledBitmap(b, width, height, false);

        bitmapdraw=(BitmapDrawable) VariabiliStatiche.getInstance().getContext().getResources().getDrawable(R.drawable.marker_fine);
        b=bitmapdraw.getBitmap();
        Bitmap markerFine = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng ultL=null;
        String ultOra="";
        String ultData="";
        int oldColor=-1;
        int actualColor=-1;

        if (cursPercorso!=null) {
            do {
                Double lat = Double.parseDouble(cursPercorso.getString(2));
                Double lon = Double.parseDouble(cursPercorso.getString(3));
                if (oldColor==-1) {
                    float f=30F;
                    try {
                        f = Float.parseFloat(cursPercorso.getString(6));
                    } catch (Exception ignored) {
                        int i=0;
                    }
                    oldColor = ConvertSpeedToColor(f);
                } else {
                    if (oldColor!=actualColor) {
                        oldColor=actualColor;

                        mMap.addPolyline(new PolylineOptions()
                                .addAll(currentSegment)
                                .color(actualColor)
                                .width(7));

                        currentSegment.clear();

                        currentSegment.add(ultL);
                        builder.include(ultL);
                        Punti++;
                    }
                }
                float f=30F;
                try {
                    f = Float.parseFloat(cursPercorso.getString(6));
                } catch (Exception ignored) {
                    int i=0;
                }
                actualColor = ConvertSpeedToColor(f);
                LatLng l = new LatLng(lat, lon);

                // puntiMappa.add(l);
                currentSegment.add(l);
                builder.include(l);
                Punti++;
                ultL = l;

                String ora = cursPercorso.getString(5);
                ora = ora.substring(0, 2) + ":" + ora.substring(2, 4) + ":" + ora.substring(4, 6) + "." + ora.substring(6, 9);
                ultOra = ora;
                ultData = cursPercorso.getString(0);

                if (primo) {
                    primo = false;

                    mMap.addMarker(new MarkerOptions()
                            .position(l)
                            .title(ora)
                            .snippet(cursPercorso.getString(0))
                            .zIndex(2F)
                            .icon(BitmapDescriptorFactory.fromBitmap(markerPartenza)));
                }
            } while (cursPercorso.moveToNext());

            if (ultL != null) {
                mMap.addMarker(new MarkerOptions()
                        .position(ultL)
                        .title(ultOra)
                        .snippet(ultData)
                        .zIndex(2F)
                        .icon(BitmapDescriptorFactory.fromBitmap(markerFine)));
            }

            mMap.addPolyline(new PolylineOptions()
                    .addAll(currentSegment)
                    .color(actualColor)
                    .width(7));
            currentSegment.clear();
        }

        if (cursMarkers!=null) {
            String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
            String Cartella=PathApplicazioneFuori+"/Thumbs";
            Utility u=new Utility();
            u.CreaCartelle(Origine, Cartella+"/");

            do {
                Double lat = Double.parseDouble(cursMarkers.getString(2));
                Double lon = Double.parseDouble(cursMarkers.getString(3));
                LatLng l = new LatLng(lat, lon);

                String ora = cursMarkers.getString(5);
                ora = ora.substring(0, 2) + ":" + ora.substring(2, 4) + ":" + ora.substring(4, 6) + "." + ora.substring(6, 9);

                String nome=cursMarkers.getString(6);
                String c[] = nome.split(";", -1);

                Bitmap markerImmagine;
                String type = cursMarkers.getString(7);
                int dim = VariabiliImpostazioni.getInstance().getDimensioniThumbsM();
                String Cosa="";
                switch(type) {
                    case "I":
                        String fileDest = Origine +"/"+Cartella+"/"+c[0]+ "/" + c[1]+"/"+c[2]+ ".dbf";
                        File f = new File(fileDest);
                        if (f.exists()) {
                            BitmapFactory.Options options2 = new BitmapFactory.Options();
                            options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            b = BitmapFactory.decodeFile(fileDest, options2);
                        } else {
                            bitmapdraw=(BitmapDrawable) VariabiliStatiche.getInstance().getContext().getResources().getDrawable(R.drawable.visualizzato);
                            b=bitmapdraw.getBitmap();
                        }
                        Cosa="Immagine;"+c[0]+";"+c[1]+";"+c[2];
                        break;
                    case "A":
                        bitmapdraw=(BitmapDrawable) VariabiliStatiche.getInstance().getContext().getResources().getDrawable(R.drawable.audio);
                        b=bitmapdraw.getBitmap();
                        Cosa="Audio;"+c[0]+";"+c[1]+";"+c[2];
                        break;
                    case "V":
                        bitmapdraw=(BitmapDrawable) VariabiliStatiche.getInstance().getContext().getResources().getDrawable(R.drawable.video);
                        b=bitmapdraw.getBitmap();
                        Cosa="Video;"+c[0]+";"; // +c[1]+";"+c[2];
                        break;
                    default:
                        bitmapdraw=(BitmapDrawable) VariabiliStatiche.getInstance().getContext().getResources().getDrawable(R.drawable.marker_percorso);
                        b=bitmapdraw.getBitmap();
                        Cosa="???;"+c[0]+";"+c[1]+";"+c[2];
                        break;
                }
                markerImmagine = Bitmap.createScaledBitmap(b, dim, dim, false);

                String stringhett;
                try {
                    stringhett = c[0]+"/"+c[1]+"/"+c[2];
                } catch (Exception ignored) {
                    stringhett = c[0];
                }

                Marker marker =  mMap.addMarker(new MarkerOptions()
                        .position(l)
                        .title(ora)
                        .snippet(stringhett)
                        .zIndex(1F)
                        .icon(BitmapDescriptorFactory.fromBitmap(markerImmagine)));
                marker.setTag(Cosa);
            } while (cursMarkers.moveToNext());
        }

        if (NuovaMappa && locGPS!=null) {
            if (locGPS.getLatitude()!=41.8648184 && locGPS.getLongitude()!=12.3593419 && locGPS.getAltitude()!=50) {
                if (ultL!=null) {
                    locGPS.setLongitude(ultL.longitude);
                    locGPS.setLatitude(ultL.latitude);

                    LatLng lActual = new LatLng(locGPS.getLatitude(), locGPS.getLongitude());
                    // puntiMappa.add(lActual);
                    builder.include(lActual);
                    Punti++;

                    mMap.addMarker(new MarkerOptions().position(lActual).title("Posizione Attuale"));
                }
            }
        }

        if (!GiaEntratoInMappa) {
            GiaEntratoInMappa=true;
            CentraMappa();
        }
    }

    public void ScriveDatiAVideo() {
        if (VariabiliStatiche.getInstance().getTxtCoords()!=null) {
            if (locGPS != null) {
                VariabiliStatiche.getInstance().getTxtCoords().setText("qCxCy: " +
                        Integer.toString(ProgressivoDBGPS) + "\nMm: " +
                        Integer.toString(ProgressivoDBMM) + "\nLP: " +
                        Double.toString(locGPS.getLatitude()) + " - " + Double.toString(locGPS.getLongitude())+
                        "\nm: "+Float.toString(VariabiliStatiche.getInstance().getKmPercorsi()));
            } else {
                VariabiliStatiche.getInstance().getTxtCoords().setText("qCxCy: " +
                        Integer.toString(ProgressivoDBGPS) + "\nMm: " +
                        Integer.toString(ProgressivoDBMM)+ "\nm: "+
                        Float.toString(VariabiliStatiche.getInstance().getKmPercorsi()));
            }
        }
    }

    public void CentraMappa() {
        if (builder!=null && mMap!=null && Punti>0) {
            LatLngBounds bounds = builder.build();
            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 15);

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(cu);
                }
            });
        }
    }
}
