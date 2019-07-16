package com.looigi.detector;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.looigi.detector.DetectPowerClick.LockService;
import com.looigi.detector.Receivers.Video;
import com.looigi.detector.TestMemory.DatiMemoria;
import com.looigi.detector.TestMemory.TestMemory;
import com.looigi.detector.Utilities.CheckURLFile;
import com.looigi.detector.Utilities.db_dati;
import com.looigi.detector.Utilities.DownloadImmagine;
import com.looigi.detector.Utilities.GestioneImmagini;
import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Utilities.Permessi;
import com.looigi.detector.Utilities.PrendeModelloTelefono;
import com.looigi.detector.Utilities.UploadFiles;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;
import com.looigi.detector.gps.LocationService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class bckService extends Service  implements OnMapReadyCallback {
    private Long datella1 = null;
    protected static FragmentActivity v;
    private TabHost tabHost;
    private Runnable rAttendeRispostaCheckURL1;
    private Handler hAttendeRispostaCheckURL1;
    private Runnable rAttendeRispostaCheckURL2;
    private Handler hAttendeRispostaCheckURL2;
    protected Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Activity act = VariabiliStatiche.getInstance().getFragmentActivityPrincipale();
        v = VariabiliStatiche.getInstance().getFragmentActivityPrincipale();
        context = this;

        // if (v == null || VariabiliStatiche.getInstance().getContext()==null || context == null) {
        //     RefreshActivity.getInstance().RilanciaActivity();
        //     v = RefreshActivity.getAct();
        //     context = RefreshActivity.getContext();
        //     VariabiliStatiche.getInstance().setContext(context);
        // }

        Location loc = new Location("dummyprovider");
        loc.setLatitude(41.8648184);
        loc.setLongitude(12.3593419);
        loc.setAltitude(50);
        VariabiliStatiche.getInstance().setLocGPS(loc);

        Switch sLog = (Switch) v.findViewById(R.id.sLog);
        Switch sGPSBetter = (Switch) v.findViewById(R.id.sGPSBetter);
        Switch sAccuracy = (Switch) v.findViewById(R.id.sAccuracy);
        EditText edtTempoGPS = (EditText) v.findViewById(R.id.edtTempoGPS);
        EditText edtMetriGPS = (EditText) v.findViewById(R.id.edtMetriGPS);
        EditText edtAccuracy = (EditText) v.findViewById(R.id.edtAccuracy);
        EditText edtDimThumbs = (EditText) v.findViewById(R.id.edtDimThumbs);
        EditText edtDimThumbsM = (EditText) v.findViewById(R.id.edtDimThumbsM);
        ImageView imgMappa = (ImageView) v.findViewById(R.id.imgMappa);
        ImageView imgChiudeImg = (ImageView) v.findViewById(R.id.imgChiudeImmagine);
        View fgmMappa = (View) v.findViewById(R.id.map);
        VariabiliStatiche.getInstance().setsLog(sLog);
        VariabiliStatiche.getInstance().setsGPSBetter(sGPSBetter);
        VariabiliStatiche.getInstance().setsAccuracy(sAccuracy);
        VariabiliStatiche.getInstance().setEdtTempoGPS(edtTempoGPS);
        VariabiliStatiche.getInstance().setEdtMetriGPS(edtMetriGPS);
        VariabiliStatiche.getInstance().setEdtAccuracy(edtAccuracy);
        VariabiliStatiche.getInstance().setEdtDimThumbs(edtDimThumbs);
        VariabiliStatiche.getInstance().setEdtDimThumbsM(edtDimThumbsM);
        VariabiliStatiche.getInstance().setImgMappa(imgMappa);
        VariabiliStatiche.getInstance().setImgChiudeImg(imgChiudeImg);
        Button btnSalvaAcc = (Button) v.findViewById(R.id.btnSalvaACC);
        VariabiliStatiche.getInstance().setBtnSalvaAcc(btnSalvaAcc);
        VariabiliStatiche.getInstance().setFgmMappa(fgmMappa);

        VariabiliStatiche.getInstance().getFgmMappa().setVisibility(LinearLayout.VISIBLE);
        VariabiliStatiche.getInstance().getImgMappa().setVisibility(LinearLayout.GONE);
        VariabiliStatiche.getInstance().getImgChiudeImg().setVisibility(LinearLayout.GONE);

        VariabiliStatiche.getInstance().getImgChiudeImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariabiliStatiche.getInstance().getFgmMappa().setVisibility(LinearLayout.VISIBLE);
                VariabiliStatiche.getInstance().getImgMappa().setVisibility(LinearLayout.GONE);
                VariabiliStatiche.getInstance().getImgChiudeImg().setVisibility(LinearLayout.GONE);
            }
        });

        TextView txtCoords = (TextView) v.findViewById(R.id.txtCoords);
        VariabiliStatiche.getInstance().setTxtCoords(txtCoords);

        VariabiliImpostazioni.getInstance().LeggeImpostazioni();

        VariabiliStatiche.getInstance().setLockService(new Intent(getApplicationContext(), LockService.class));
        VariabiliStatiche.getInstance().setLocationService(new Intent(getApplicationContext(), LocationService.class));

        Permessi pp = new Permessi();
        pp.ControllaPermessi(act);

        SupportMapFragment mapFragment = (SupportMapFragment) v.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PrendeModelloTelefono p = new PrendeModelloTelefono();
        String modello = p.getDeviceName();
        VariabiliStatiche.getInstance().setModelloTelefono(modello);

        VariabiliStatiche.getInstance().setLista((ListView) v.findViewById(R.id.lstRisoluzioni));

        VariabiliStatiche.getInstance().manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // final GestioneDB gdb = new GestioneDB();
        // gdb.CreaDB(context);
        CaricaSpinnerOrientamento();
        // LeggeValori();

        SistemaSchermata();

        // IMPOSTAZIONI
        sLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariabiliImpostazioni.getInstance().setFaiLog(VariabiliStatiche.getInstance().getsLog().isChecked());
            }
        });

        sGPSBetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariabiliImpostazioni.getInstance().setGPSBetter(VariabiliStatiche.getInstance().getsGPSBetter().isChecked(), false);
            }
        });

        Button btnSalvaTGPS = (Button) v.findViewById(R.id.btnSalvaTGPS);
        btnSalvaTGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(VariabiliStatiche.getInstance().getEdtTempoGPS().getText().toString());
                VariabiliImpostazioni.getInstance().setTEMPO_GPS(a);
            }
        });

        Button btnMetriGPS = (Button) v.findViewById(R.id.btnSalvaMGPS);
        btnMetriGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(VariabiliStatiche.getInstance().getEdtMetriGPS().getText().toString());
                VariabiliImpostazioni.getInstance().setDISTANZA_GPS(a,false);
            }
        });

        btnSalvaAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(VariabiliStatiche.getInstance().getEdtAccuracy().getText().toString());
                VariabiliImpostazioni.getInstance().setAccuracyValue(a, false);
            }
        });
        sAccuracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariabiliImpostazioni.getInstance().setAccuracy(VariabiliStatiche.getInstance().getsAccuracy().isChecked(), false);
                if (VariabiliStatiche.getInstance().getsAccuracy().isChecked()) {
                    VariabiliStatiche.getInstance().getBtnSalvaAcc().setEnabled(true);
                    VariabiliStatiche.getInstance().getEdtAccuracy().setEnabled(true);
                } else {
                    VariabiliStatiche.getInstance().getBtnSalvaAcc().setEnabled(false);
                    VariabiliStatiche.getInstance().getEdtAccuracy().setEnabled(false);
                }
            }
        });

        Button btnSalvaDT = (Button) v.findViewById(R.id.btnSalvaDT);
        btnSalvaDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(VariabiliStatiche.getInstance().getEdtDimThumbs().getText().toString());
                VariabiliImpostazioni.getInstance().setDimensioniThumbs(a);
            }
        });

        Button btnSalvaDTM = (Button) v.findViewById(R.id.btnSalvaDTM);
        btnSalvaDTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(VariabiliStatiche.getInstance().getEdtDimThumbsM().getText().toString());
                VariabiliImpostazioni.getInstance().setDimensioniThumbsM(a);
            }
        });
        // IMPOSTAZIONI


        ImageView imgItaliano = (ImageView) v.findViewById(R.id.imgItaliano);
        imgItaliano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariabiliImpostazioni.getInstance().setLingua("ITALIANO");

                ImpostaCampiTestoPerLingua();
            }
        });

        Button cmdVideo = (Button) v.findViewById(R.id.cmdEsegueVideo);
        cmdVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Folder = new Intent(VariabiliStatiche.getInstance().getFragmentActivityPrincipale(), Video.class);
                VariabiliStatiche.getInstance().getFragmentActivityPrincipale().startActivity(Folder);

                // finish();
            }
        });

        ImageView imgInglese = (ImageView) v.findViewById(R.id.imgInglese);
        imgInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariabiliImpostazioni.getInstance().setLingua("INGLESE");

                ImpostaCampiTestoPerLingua();
            }
        });

        // AUTOSCATTO - IMMEDIATO
        RadioButton AS = (RadioButton) v.findViewById(R.id.optAutoScatto);
        AS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton IM = (RadioButton) v.findViewById(R.id.optImmediato);
                IM.setChecked(false);
                EditText et = (EditText) v.findViewById(R.id.txtSecondi);
                et.setEnabled(true);
                Button bi = (Button) v.findViewById(R.id.cmdImposta);
                bi.setEnabled(true);

                Impostazioni i = new Impostazioni();
                i.ImpostaAutoScatto(context, et);
            }
        });

        RadioButton IM = (RadioButton) v.findViewById(R.id.optImmediato);
        IM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton AS = (RadioButton) v.findViewById(R.id.optAutoScatto);
                AS.setChecked(false);
                EditText et = (EditText) v.findViewById(R.id.txtSecondi);
                et.setEnabled(false);
                Button bi = (Button) v.findViewById(R.id.cmdImposta);
                bi.setEnabled(false);

                Impostazioni i = new Impostazioni();
                i.ImpostaImmediato(context);
            }
        });

        EditText et = (EditText) v.findViewById(R.id.txtSecondi);
        et.setText(Integer.toString(VariabiliImpostazioni.getInstance().getSecondi()));
        Button bi = (Button) v.findViewById(R.id.cmdImposta);
        bi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = (EditText) v.findViewById(R.id.txtSecondi);

                Impostazioni i = new Impostazioni();
                i.ImpostaAutoScatto(context, et);

                Utility u = new Utility();
                u.VisualizzaPOPUP("Saved", false, 0);
            }
        });

        EditText t = (EditText) v.findViewById(R.id.txtScatti);
        t.setText(Integer.toString(VariabiliImpostazioni.getInstance().getNumeroScatti()));

        if (VariabiliImpostazioni.getInstance().getTipologiaScatto() == 1) {
            AS.setChecked(false);
            IM.setChecked(true);
            et.setEnabled(false);
            bi.setEnabled(false);
        } else {
            AS.setChecked(true);
            IM.setChecked(false);
            et.setEnabled(true);
            bi.setEnabled(true);
        }

        // ORIGINALE - MASCHERATA
        RadioButton or = (RadioButton) v.findViewById(R.id.optOriginale);
        or.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton ma = (RadioButton) v.findViewById(R.id.optMascherata);
                ma.setChecked(false);

                Impostazioni i = new Impostazioni();
                i.ImpostaEstensioneOriginale(context);
            }
        });

        RadioButton ma = (RadioButton) v.findViewById(R.id.optMascherata);
        ma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton or = (RadioButton) v.findViewById(R.id.optOriginale);
                or.setChecked(false);

                Impostazioni i = new Impostazioni();
                i.ImpostaEstensioneMascherata(context);
            }
        });

        if (VariabiliImpostazioni.getInstance().getEstensione() == 1) {
            or.setChecked(true);
            ma.setChecked(false);
        } else {
            or.setChecked(false);
            ma.setChecked(true);
        }

        // VIBRAZIONE
        final CheckBox cb = (CheckBox) v.findViewById(R.id.chkVibrazione);
        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Impostazioni i = new Impostazioni();
                i.ImpostaVibrazione(context, cb);
            }
        });

        if (VariabiliImpostazioni.getInstance().getVibrazione().equals("S")) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }

        // ANTEPRIMA
        final CheckBox ca = (CheckBox) v.findViewById(R.id.chkAnteprima);
        ca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Impostazioni i = new Impostazioni();
                i.ImpostaAnteprima(context, ca);
            }
        });

        if (VariabiliImpostazioni.getInstance().getAnteprima().equals("S")) {
            ca.setChecked(true);
        } else {
            ca.setChecked(false);
        }

        // FRONTALE - RETRO
        RadioButton ff = (RadioButton) v.findViewById(R.id.optFrontale);
        ff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton fr = (RadioButton) v.findViewById(R.id.optRetro);
                fr.setChecked(false);

                TextView tv = (TextView) v.findViewById(R.id.txtRisoluzione);
                Impostazioni i = new Impostazioni();
                i.ImpostaFrontale(context, tv);
            }
        });

        RadioButton fr = (RadioButton) v.findViewById(R.id.optRetro);
        fr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton ff = (RadioButton) v.findViewById(R.id.optFrontale);
                ff.setChecked(false);

                TextView tv = (TextView) v.findViewById(R.id.txtRisoluzione);
                Impostazioni i = new Impostazioni();
                i.ImpostaRetro(context, tv);
            }
        });

        TextView tv = (TextView) v.findViewById(R.id.txtRisoluzione);
        tv.setText(VariabiliImpostazioni.getInstance().getRisoluzione());

        if (VariabiliImpostazioni.getInstance().getFotocamera() == 1) {
            ff.setChecked(true);
            fr.setChecked(true);
        } else {
            ff.setChecked(false);
            fr.setChecked(true);
        }

        Impostazioni i = new Impostazioni();
        i.PrendeRisoluzioni(context);

        ListView lvS = (ListView) v.findViewById(R.id.lstRisoluzioni);
        lvS.setClickable(true);
        lvS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String o = (String) VariabiliStatiche.getInstance().Dimensioni.get(position);
                TextView tv = (TextView) v.findViewById(R.id.txtRisoluzione);
                tv.setText(o);

                Impostazioni i = new Impostazioni();
                i.ImpostaRisoluzione(context, o);
            }
        });

        Button bs = (Button) v.findViewById(R.id.cmdImpostaScatti);
        bs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText t = (EditText) v.findViewById(R.id.txtScatti);

                Impostazioni i = new Impostazioni();
                i.ImpostaNumScatti(context, t);

                Utility u = new Utility();
                u.VisualizzaPOPUP("Saved", false, 0);
            }
        });

        ImpostaCampiTestoPerLingua();

        final ScrollView lPr = (ScrollView) v.findViewById(R.id.scrollViewPrincipale);
        lPr.setVisibility(LinearLayout.VISIBLE);

        TextView tTotale = (TextView) v.findViewById(R.id.txtMemoriaTotale);
        TextView tLibera = (TextView) v.findViewById(R.id.txtMemoriaLibera);
        TextView tUsata = (TextView) v.findViewById(R.id.txtMemoriaUsata);
        TextView tInfo = (TextView) v.findViewById(R.id.txtInfo);

        tLibera.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

                if (datella1 == null) {
                    Handler handlerTimer;
                    Runnable rTimer;

                    datella1 = System.currentTimeMillis();
                    try {
                        vibrator.vibrate(100);
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {

                    }

                    handlerTimer = new Handler();
                    rTimer = new Runnable() {
                        public void run() {
                            datella1 = null;
					/* try {
						vibrator.vibrate(300);
						Thread.sleep(300);
					} catch (InterruptedException ignored) {

					} */
                        }
                    };
                    handlerTimer.postDelayed(rTimer, 2000);
                } else {
                    long diff = System.currentTimeMillis() - datella1;

                    if (diff < 1950) {
                        lPr.setVisibility(LinearLayout.GONE);
                    }
                }

                return false;
            }
        });

        Button btnTornaIndietro = (Button) v.findViewById(R.id.btnTornaIndietro);
        btnTornaIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lPr.setVisibility(LinearLayout.VISIBLE);
            }
        });

        TextView tTitEstensione = (TextView) v.findViewById(R.id.txtTitoloEstensione);
        TextView txtKM = (TextView) v.findViewById(R.id.txtKM);
        VariabiliStatiche.getInstance().setTxtKM(txtKM);
        VariabiliStatiche.getInstance().setTxtImm((TextView) v.findViewById(R.id.txtImmagini));
        VariabiliStatiche.getInstance().setTxtNomeImm((TextView) v.findViewById(R.id.txtNomeImm));
        final LinearLayout lScatti = (LinearLayout) v.findViewById(R.id.layScatti);
        final LinearLayout lTasti = (LinearLayout) v.findViewById(R.id.layTasti);
        final LinearLayout lFrecce = (LinearLayout) v.findViewById(R.id.layFrecce);
        lScatti.setVisibility(LinearLayout.GONE);
        lTasti.setVisibility(LinearLayout.GONE);
        lFrecce.setVisibility(LinearLayout.GONE);
        VariabiliStatiche.getInstance().getTxtImm().setVisibility(LinearLayout.GONE);
        VariabiliStatiche.getInstance().getTxtNomeImm().setVisibility(LinearLayout.GONE);

        VariabiliStatiche.getInstance().setImg((ImageView) v.findViewById(R.id.imgScatto));
        VariabiliStatiche.getInstance().setAudio((ImageView) v.findViewById(R.id.imgPlayAudio));
        VariabiliStatiche.getInstance().setvView((VideoView) v.findViewById(R.id.videoView1));

        VariabiliStatiche.getInstance().getImg().setVisibility(LinearLayout.GONE);
        VariabiliStatiche.getInstance().getAudio().setVisibility(LinearLayout.GONE);
        VariabiliStatiche.getInstance().getvView().setVisibility(LinearLayout.GONE);

        VariabiliStatiche.getInstance().getvView().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility u = new Utility();
                if (!VariabiliStatiche.getInstance().StaVedendo) {
                    u.PlayVideo();
                } else {
                    u.StopVideo();
                }
            }
        });

        VariabiliStatiche.getInstance().getAudio().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility u = new Utility();
                if (!VariabiliStatiche.getInstance().StaSuonando) {
                    u.PlayAudio();
                } else {
                    u.StopAudio();
                }
            }
        });

        final Button bDecripta = (Button) v.findViewById(R.id.cmdDecript);
        final Button bCripta = (Button) v.findViewById(R.id.cmdCript);

        bCripta.setVisibility(LinearLayout.GONE);
        bDecripta.setVisibility(LinearLayout.GONE);

        bDecripta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility u = new Utility();
                u.DeCriptaFiles(VariabiliStatiche.getInstance().getContext());
            }
        });

        bCripta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility u = new Utility();
                u.CriptaFiles(VariabiliStatiche.getInstance().getContext());
            }
        });

        tTitEstensione.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

                if (datella1 == null) {
                    Handler handlerTimer;
                    Runnable rTimer;

                    datella1 = System.currentTimeMillis();
                    try {
                        vibrator.vibrate(100);
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {

                    }

                    handlerTimer = new Handler();
                    rTimer = new Runnable() {
                        public void run() {
                            datella1 = null;
                        }
                    };
                    handlerTimer.postDelayed(rTimer, 2000);
                } else {
                    long diff = System.currentTimeMillis() - datella1;

                    if (diff < 1950) {
                        lScatti.setVisibility(LinearLayout.VISIBLE);
                        lTasti.setVisibility(LinearLayout.VISIBLE);
                        lFrecce.setVisibility(LinearLayout.VISIBLE);
                        bCripta.setVisibility(LinearLayout.VISIBLE);
                        bDecripta.setVisibility(LinearLayout.VISIBLE);

                        VariabiliStatiche.getInstance().getTxtImm().setVisibility(LinearLayout.VISIBLE);
                        VariabiliStatiche.getInstance().getTxtNomeImm().setVisibility(LinearLayout.VISIBLE);

                        Utility uu = new Utility();
                        uu.CaricaMultimedia();
                        uu.VisualizzaMultimedia();

                        Handler handlerTimer;
                        Runnable rTimer;

                        handlerTimer = new Handler();
                        rTimer = new Runnable() {
                            public void run() {
                                VariabiliStatiche.getInstance().MascheraImmaginiMostrata = true;
                            }
                        };
                        handlerTimer.postDelayed(rTimer, 2000);
                    }
                }

                return false;
            }
        });

        tTitEstensione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliStatiche.getInstance().MascheraImmaginiMostrata) {
                    lScatti.setVisibility(LinearLayout.GONE);
                    lTasti.setVisibility(LinearLayout.GONE);
                    lFrecce.setVisibility(LinearLayout.GONE);

                    VariabiliStatiche.getInstance().getTxtImm().setVisibility(LinearLayout.GONE);

                    VariabiliStatiche.getInstance().MascheraImmaginiMostrata = false;
                }
            }
        });

        ImageView btnIndietro = (ImageView) v.findViewById(R.id.imgIndietro);
        ImageView btnAvanti = (ImageView) v.findViewById(R.id.imgAvanti);
        ImageView btnElimina = (ImageView) v.findViewById(R.id.imgElimina);
        ImageView btnRefresh = (ImageView) v.findViewById(R.id.imgRefresh);
        ImageView btnUpload = (ImageView) v.findViewById(R.id.imgInviaMappa);
        ImageView btnChiudeUpload = (ImageView) v.findViewById(R.id.imgChiudeUpload);
        ImageView btnEsegueUpload = (ImageView) v.findViewById(R.id.imgEffettuaUpload);
        ListView lstUpload = (ListView) v.findViewById(R.id.lstUpload);

        VariabiliStatiche.getInstance().setLstUpload(lstUpload);
        VariabiliStatiche.getInstance().setRltUpload((LinearLayout) v.findViewById(R.id.rltUpload));
        VariabiliStatiche.getInstance().getRltUpload().setVisibility(LinearLayout.GONE);

        VariabiliStatiche.getInstance().setBtnFlipX((ImageView) v.findViewById(R.id.imgFlipX));
        VariabiliStatiche.getInstance().setBtnFlipY((ImageView) v.findViewById(R.id.imgFlipY));
        VariabiliStatiche.getInstance().setBtnRuotaDes((ImageView) v.findViewById(R.id.imgRuotaDes));
        VariabiliStatiche.getInstance().setBtnRuotaSin((ImageView) v.findViewById(R.id.imgRuotaSin));

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility uu = new Utility();
                uu.CaricaMultimedia();
                uu.VisualizzaMultimedia();
            }
        });

        btnChiudeUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().getRltUpload().setVisibility(LinearLayout.GONE);
            }
        });

        btnEsegueUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new UploadFiles(context);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility uu = new Utility();
                uu.InviaFilesGPS();
            }
        });

        VariabiliStatiche.getInstance().getBtnFlipX().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GestioneImmagini u = new GestioneImmagini();
                u.FlipImmagine(true);

                int appo = VariabiliStatiche.getInstance().numMultimedia;
                Utility uu = new Utility();
                uu.CaricaMultimedia();
                VariabiliStatiche.getInstance().numMultimedia = appo;
                uu.VisualizzaMultimedia();
            }
        });

        VariabiliStatiche.getInstance().getBtnFlipY().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GestioneImmagini u = new GestioneImmagini();
                u.FlipImmagine(false);

                int appo = VariabiliStatiche.getInstance().numMultimedia;
                Utility uu = new Utility();
                uu.CaricaMultimedia();
                VariabiliStatiche.getInstance().numMultimedia = appo;
                uu.VisualizzaMultimedia();
            }
        });

        btnIndietro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliStatiche.getInstance().numMultimedia > 0) {
                    VariabiliStatiche.getInstance().numMultimedia--;
                } else {
                    VariabiliStatiche.getInstance().numMultimedia = VariabiliStatiche.getInstance().totImmagini - 1;
                }

                Utility uu = new Utility();
                uu.VisualizzaMultimedia();
            }
        });

        btnAvanti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliStatiche.getInstance().numMultimedia < VariabiliStatiche.getInstance().totImmagini - 1) {
                    VariabiliStatiche.getInstance().numMultimedia++;
                } else {
                    VariabiliStatiche.getInstance().numMultimedia = 0;
                }

                Utility uu = new Utility();
                uu.VisualizzaMultimedia();
            }
        });

        VariabiliStatiche.getInstance().getBtnRuotaSin().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GestioneImmagini u = new GestioneImmagini();
                u.RuotaImmagine(270);
            }
        });

        VariabiliStatiche.getInstance().getBtnRuotaDes().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GestioneImmagini u = new GestioneImmagini();
                u.RuotaImmagine(90);
            }
        });

        btnElimina.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Origine = Environment.getExternalStorageDirectory().getAbsolutePath();
                String Cartella = VariabiliStatiche.getInstance().PathApplicazione;
                String NomeImmagine = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

                try {
                    File file = new File(Origine + Cartella + NomeImmagine);
                    boolean deleted = file.delete();
                } catch (Exception ignored) {

                }

                try {
                    File file = new File(Origine + Cartella + NomeImmagine +".PV3");
                    boolean deleted = file.delete();
                } catch (Exception ignored) {

                }

                int appo = VariabiliStatiche.getInstance().numMultimedia;
                Utility uu = new Utility();
                uu.CaricaMultimedia();
                appo--;
                if (appo < 0) appo = 0;
                VariabiliStatiche.getInstance().numMultimedia = appo;
                uu.VisualizzaMultimedia();
                uu.VisualizzaPOPUP("File multimediale eliminato", false, 0);
            }
        });

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String oggi = formatter.format(todayDate);

        TextView txtDataMappa = (TextView) v.findViewById(R.id.txtDataMappa);
        txtDataMappa.setText(oggi);
        VariabiliStatiche.getInstance().setTxtDataMappa(txtDataMappa);

        ImageView imgRefreshMappa = (ImageView) v.findViewById(R.id.imgRefreshMappa);
        imgRefreshMappa.setBackgroundResource(R.drawable.ruotadx);
        imgRefreshMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().DisegnaPercorsoAttualeSuMappa();
            }
        });

        ImageView imgCentraMappa = (ImageView) v.findViewById(R.id.imgCentraMappa);
        imgCentraMappa.setBackgroundResource(R.drawable.adatta);
        imgCentraMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().CentraMappa();
            }
        });

        ImageView imgEliminaMappa = (ImageView) v.findViewById(R.id.imgEliminaMappa);
        imgEliminaMappa.setBackgroundResource(R.drawable.elimina);
        imgEliminaMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().getDbGpsPos().cancellaDatiGPSPerDataAttuale();
                VariabiliStatiche.getInstance().getDbGpsPos().cancellaDatiMultiMediaPerDataAttuale();

                VariabiliStatiche.getInstance().DisegnaPercorsoVecchioSuMappa();

                Utility u = new Utility();
                u.VisualizzaPOPUP("Dati eliminati", false,0);
            }
        });

        ImageView imgExportMappa = (ImageView) v.findViewById(R.id.imgExportMappa);
        imgExportMappa.setBackgroundResource(R.drawable.export);
        imgExportMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().EstraiDatiGPS();
            }
        });

        ImageView imgIndietroMappa = (ImageView) v.findViewById(R.id.imgIndietroMappa);
        imgIndietroMappa.setBackgroundResource(R.drawable.indietro);
        imgIndietroMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().DecrementaGiornoMappa();
            }
        });

        ImageView imgAvantiMappa = (ImageView) v.findViewById(R.id.imgAvantiMappa);
        imgAvantiMappa.setBackgroundResource(R.drawable.avanti);
        imgAvantiMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliStatiche.getInstance().AumentaGiornoMappa();
            }
        });

        final ImageView imgPlayMappa = (ImageView) v.findViewById(R.id.imgPlayPercorso);
        imgPlayMappa.setBackgroundResource(R.drawable.pausa);
        imgPlayMappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliStatiche.getInstance().isSeguePercorso()) {
                    VariabiliStatiche.getInstance().setSeguePercorso(false);
                    imgPlayMappa.setBackgroundResource(R.drawable.pausa);
                } else {
                    VariabiliStatiche.getInstance().setSeguePercorso(true);
                    imgPlayMappa.setBackgroundResource(R.drawable.play);
                }
            }
        });

        TestMemory tm = new TestMemory();
        DatiMemoria d = new DatiMemoria();
        d = tm.LeggeValori();

        tTotale.setText(Float.toString(d.getMemoriaTotale()));
        tLibera.setText(Float.toString(d.getMemoriaLibera()));
        tUsata.setText(Float.toString(d.getMemoriaUsata()));

        tInfo.setText(getCPUDetails());

        // if (!VariabiliStatiche.getInstance().getGiaEntrato()) {
        // 	VariabiliStatiche.getInstance().setGiaEntrato(true);

        // ImAliveThread.getInstance().start();

        ImageView imgGPS = (ImageView) v.findViewById(R.id.imgGPS);
        VariabiliStatiche.getInstance().setImgGps(imgGPS);

        db_dati dbgps = new db_dati();
        dbgps.CreazioneTabelle();
        VariabiliStatiche.getInstance().setDbGpsPos(dbgps);
        // dbgps.cancellaDatiGPS();
        // dbgps.cancellaDatiMultiMedia();

        int proGPS = dbgps.ottieniMassimoProgressivoGPSPerData(oggi);
        VariabiliStatiche.getInstance().setProgressivoDBGPS(proGPS);
        int proMM = dbgps.ottieniMassimoProgressivoMultimediaPerData(oggi);
        VariabiliStatiche.getInstance().setProgressivoDBMM(proMM);

        Cursor c1 = VariabiliStatiche.getInstance().getDbGpsPos().ottieniDistanzeData(oggi);
        Boolean ciSonoDistanze=false;
        if (c1!=null && c1.moveToFirst()) {
            do{
                ciSonoDistanze=true;
                VariabiliStatiche.getInstance().setKmPercorsi(Float.parseFloat(c1.getString(1)));
            } while (c1.moveToNext());
        }
        if (c1!=null) {
            c1.close();
        }
        if (!ciSonoDistanze) {
            VariabiliStatiche.getInstance().setKmPercorsi(0);
            VariabiliStatiche.getInstance().getDbGpsPos().inserisceNuovaDistanza(oggi, "0");
        }
        Utility u = new Utility();
        u.ScriveKM();

        VariabiliStatiche.getInstance().setDataDiVisualizzazioneMappa(todayDate);

        startForegroundService(VariabiliStatiche.getInstance().getLockService());
        startForegroundService(VariabiliStatiche.getInstance().getLocationService());

        v.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // if (service.ChiudiMaschera == null) {
        // 	service.ChiudiMaschera = false;
        // }
        // if (service.ChiudiMaschera) {
        // 	moveTaskToBack(true);
        // 	service.ChiudiMaschera = false;
        // }

        if (!VariabiliStatiche.getInstance().getGiaEntrato()) {
            VariabiliStatiche.getInstance().setGiaEntrato(true);
            v.moveTaskToBack(true);

            VariabiliStatiche.getInstance().EstraiTuttiIDatiGPS();
        }

        // RefreshActivity.getInstance().RilanciaServizio(context, v);

        return Service.START_STICKY;
    }

    private String getCPUDetails(){
        ProcessBuilder processBuilder;
        String cpuDetails = "";
        String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
        InputStream is;
        Process process ;
        byte[] bArray ;
        bArray = new byte[1024];

        try{
            processBuilder = new ProcessBuilder(DATA);

            process = processBuilder.start();

            is = process.getInputStream();

            while(is.read(bArray) != -1){
                cpuDetails = cpuDetails + new String(bArray);   //Stroing all the details in cpuDetails
            }
            is.close();

        } catch(IOException ex){
            ex.printStackTrace();
        }

        return cpuDetails;
    }

    private void ImpostaCampiTestoPerLingua() {
        RadioButton AS=(RadioButton) v.findViewById(R.id.optAutoScatto);
        RadioButton IM=(RadioButton) v.findViewById(R.id.optImmediato);
        RadioButton or=(RadioButton) v.findViewById(R.id.optOriginale);
        RadioButton ma=(RadioButton) v.findViewById(R.id.optMascherata);
        Button bi=(Button) v.findViewById(R.id.cmdImposta);
        RadioButton ff=(RadioButton) v.findViewById(R.id.optFrontale);
        RadioButton fr=(RadioButton) v.findViewById(R.id.optRetro);
        TextView tps=(TextView) v.findViewById(R.id.txtTitTipoScatto);
        // TextView t1=(TextView) findViewById(R.id.txtTitNick);
        // TextView t2=(TextView) findViewById(R.id.txtTitPass);
        TextView tpos=(TextView) v.findViewById(R.id.txtTitPos);
        TextView ts=(TextView) v.findViewById(R.id.textView22);
        TextView t4=(TextView) v.findViewById(R.id.txtTitoloEstensione);
        TextView tr=(TextView) v.findViewById(R.id.TextView01);
        CheckBox c=(CheckBox) v.findViewById(R.id.chkVibrazione);
        CheckBox ca=(CheckBox) v.findViewById(R.id.chkAnteprima);
        TextView tns=(TextView) v.findViewById(R.id.textView3);
        // TextView ttu=(TextView) findViewById(R.id.txtTitUte);
        // Button cme=(Button) findViewById(R.id.cmdEsportaFiles);
        // Button cmr=(Button) findViewById(R.id.cmdRetrieve);
        //TextView tcaf=(TextView) findViewById(R.id.txtTitolo5);
        TextView to=(TextView) v.findViewById(R.id.txtTitOrient);
        if (VariabiliImpostazioni.getInstance().getLingua().equals("INGLESE")) {
            // ttu.setText("Logged User:");
            tps.setText("Kind of shoot");
            // t1.setText("User");
            tpos.setText("Position of camera");
            // t2.setText("Password");
            t4.setText("File format");
            tr.setText("Resolution");
            ts.setText("Seconds");
            AS.setText("Auto Shoot");
            IM.setText("Immediate");
            or.setText("JPG");
            ma.setText("Masked");
            bi.setText("Save");
            ff.setText("Frontal");
            fr.setText("Retro");
            c.setText("Vibration");
            ca.setText("Preview");
            tns.setText("Number of shots: ");
            // cme.setText("Export");
            // cmr.setText("Retrieve Images");
            // VariabiliStatiche.getInstance().getTa().setText("Please login to export");
            // VariabiliStatiche.getInstance().getTai().setText("Please login to import");
            //tcaf.setText("If you want, please, offer me a coffe. Spiator DONATE");
            to.setText("Orientation");
        } else {
            // ttu.setText("Utente loggato:");
            tps.setText("Tipologia di scatto");
            // t1.setText("Utente");
            tpos.setText("Fotocamera");
            // t2.setText("Password");
            t4.setText("Formato del file");
            tr.setText("Risoluzione");
            ts.setText("Secondi");
            AS.setText("Auto scatto");
            IM.setText("Immediato");
            or.setText("JPG");
            ma.setText("Mascherata");
            bi.setText("Salva");
            ff.setText("Frontale");
            fr.setText("Retro");
            c.setText("Vibrazione");
            ca.setText("Anteprima");
            tns.setText("Numero scatti: ");
            // cme.setText("Esporta");
            // cmr.setText("Rileva Immagini");
            // VariabiliStatiche.getInstance().getTa().setText("Effettua il login per esportare");
            // VariabiliStatiche.getInstance().getTai().setText("Effettua il login per importare");
            //tcaf.setText("Se vuoi, offrimi un caff?. Spiator DONATE");
            to.setText("Orientamento");
        }

        ImpostaInfo();
    }

    private void SistemaSchermata() {
        String[] Titoli;
        Titoli=new String[8];
        if (VariabiliImpostazioni.getInstance().getLingua().equals("INGLESE")) {
            Titoli[0]="Video";
            Titoli[1]="Shoot Type";
            Titoli[2]="Camera";
            Titoli[3]="Image Extension";
            Titoli[4]="Photo Resolution";
            Titoli[5]="Info";
            Titoli[6]="Map";
            Titoli[7]="Settings";
        } else {
            Titoli[0]="Video";
            Titoli[1]="Tipologia di scatto";
            Titoli[2]="Fotocamera";
            Titoli[3]="Estensione";
            Titoli[4]="Risoluzione";
            Titoli[5]="Info";
            Titoli[6]="Mappa";
            Titoli[7]="Impostazioni";
        }

        tabHost = (TabHost) v.findViewById(R.id.tabGenerale1);

        tabHost.setup();
        // tabHost.addTab(tabHost.newTabSpec("tabview6").setContent(R.id.tab6).setIndicator(Titoli[1]));
        tabHost.addTab(tabHost.newTabSpec("tabview7").setContent(R.id.tab7).setIndicator(Titoli[0]));
        tabHost.addTab(tabHost.newTabSpec("tabview2").setContent(R.id.tab2).setIndicator(Titoli[1]));
        tabHost.addTab(tabHost.newTabSpec("tabview3").setContent(R.id.tab3).setIndicator(Titoli[2]));
        tabHost.addTab(tabHost.newTabSpec("tabview4").setContent(R.id.tab4).setIndicator(Titoli[3]));
        tabHost.addTab(tabHost.newTabSpec("tabview5").setContent(R.id.tab5).setIndicator(Titoli[4]));
        // tabHost.addTab(tabHost.newTabSpec("tabview9").setContent(R.id.tab9).setIndicator(Titoli[8]));
        tabHost.addTab(tabHost.newTabSpec("tabview1").setContent(R.id.tab1).setIndicator(Titoli[5]));
        tabHost.addTab(tabHost.newTabSpec("tabview10").setContent(R.id.tab6).setIndicator(Titoli[6]));
        tabHost.addTab(tabHost.newTabSpec("tabview8").setContent(R.id.tabI).setIndicator(Titoli[7]));

        DisplayMetrics metrics = new DisplayMetrics();
        v.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        TabWidget tw = (TabWidget)tabHost.findViewById(android.R.id.tabs);

        for (int i=0;i<7;i++) {
            View tabView = tw.getChildTabViewAt(i);
            // tabView.getLayoutParams().width = 500; // LinearLayout.LayoutParams.WRAP_CONTENT;

            TextView tv = (TextView)tabView.findViewById(android.R.id.title);
            tv.setText(Titoli[i]);
            tv.setTextSize(15);
//	        tv.setWidth(200);
        }

        ImpostaInfo();
    }

    private void ImpostaInfo() {
        String Messaggio="";

        if (VariabiliImpostazioni.getInstance().getLingua().equals("INGLESE")) {
            Messaggio+= "Detector: The app that will allow you to make photos directly from ";
            Messaggio+= "a dedicated widget to be placed on the home phone. You can, at any time and only by pressing on the ";
            Messaggio+= "relative, take a picture and trovarsela directly into the default directory ";
            Messaggio+= "which is /sdcard/LooigiSoft/Detector/DB without any other effort. \n \n";
            Messaggio+= "The procedure allows the configuration to use the front or back camera, for ";
            Messaggio+= "the resolution you want, ";
            Messaggio+= "to hide pictures to the gallery using an extension other than that of the ";
            Messaggio+= "known images, and even use a countdown. \n \n";
            Messaggio+= "For the sake hardware on some phone models the procedure fails to recognize the resolutions and ";
            Messaggio+= "will therefore not be possible to take pictures. However, we are trying to solve the problem. \n \n";
            Messaggio+="The new version will also allow the creation of video and the ability to save your images\n";
            Messaggio+="on a web space\n";
        } else {
            Messaggio+="Detector: L'app che Vi permetterà di effettuare foto direttamente da ";
            Messaggio+="un widget dedicato da posizionare nella home del telefono. Sarà possibile, in qualsiasi momento e soltanto tramite pressione sull'icona ";
            Messaggio+="relativa, scattare una foto e trovarsela direttamente nella directory preimpostata ";
            Messaggio+="che è /sdcard/LooigiSoft/Detector/DB senza nessun altro sforzo.\n\n";
            Messaggio+="La procedura permette la configurazione per utilizzare la fotocamera anteriore o posteriore, per ";
            Messaggio+="impostare la risoluzione voluta, ";
            Messaggio+="per nascondere le immagini alla galleria utilizzando un'estensione diversa da quella delle";
            Messaggio+="immagini conosciute e, addirittura, utilizzare un countdown.\n\n";
            Messaggio+="Per motivi hardware su alcuni modelli di telefono la procedura non riesce a riconoscere le risoluzioni e";
            Messaggio+="non sarà quindi possibile scattare foto. Stiamo comunque tentando di risolvere il problema.\n\n";
            Messaggio+="La nuova versione permetterà inoltre la creazione di video e la possibilità di salvare le proprie immagini\n";
            Messaggio+="su uno spazio web\n";
        }

        TextView tv=(TextView) v.findViewById(R.id.txtInfoHome);
        tv.setText(Messaggio);
    }

    private void CaricaSpinnerOrientamento() {
        String Cartell[]=new String[5];
        Cartell[0]=Integer.toString(VariabiliImpostazioni.getInstance().getOrientamento());
        Cartell[1]="0";
        Cartell[2]="90";
        Cartell[3]="180";
        Cartell[4]="270";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.spinner_text,
                Cartell
        );
        Spinner spinner=(Spinner) v.findViewById(R.id.spnOrient);
        spinner.setAdapter(adapter);
        spinner.setPrompt(Integer.toString(VariabiliImpostazioni.getInstance().getOrientamento()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,int pos, long id) {
                String selected ="";

                try {
                    selected = (String) adapter.getItemAtPosition(pos).toString().trim();
                    VariabiliImpostazioni.getInstance().setOrientamento(Integer.parseInt(selected));
                } catch (Exception e) {
                    selected="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // 	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    // 		// finish();
    // 		// System.exit(0);
    //     }
    //
    // 	return super.onKeyDown(keyCode, event);
    // }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag()!=null) {
                    String tag = marker.getTag().toString();
                    String c[] = tag.split(";",-1);
                    switch (c[0]) {
                        case "Immagine":
                            String NomeFiletto1 = c[1]+"/"+c[1]+c[2]+c[3]+".jpg";
                            String NomeFiletto2 = c[1]+c[2]+c[3]+".jpg";
                            final String link1 = "http://looigi.no-ip.biz:12345/gDrive/Pennetta/"+NomeFiletto1+".jpg";
                            final String link2 = "http://looigi.no-ip.biz:12345/gDrive/Pennetta/Yeah/"+NomeFiletto2+".jpg";

                            // final String link2 = "http://looigi.no-ip.biz:12345/gDrive/Pennetta/Yeah/201654-8854.jpg";

                            String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
                            String Cartella=VariabiliStatiche.getInstance().PathApplicazioneFuori+"Appo";
                            Utility u=new Utility();
                            u.CreaCartelle(Origine, Cartella+"/");
                            u.ControllaFileNoMedia(Origine, Cartella+"/");

                            final String PathFile = Origine+Cartella+"/Appoggio.jpg";

                            final CheckURLFile cuf = new CheckURLFile();
                            VariabiliStatiche.getInstance().setRitornoCheckFileURL("");
                            cuf.startControl(link1);

                            hAttendeRispostaCheckURL1 = new Handler();
                            hAttendeRispostaCheckURL1.postDelayed(rAttendeRispostaCheckURL1 = new Runnable() {
                                @Override
                                public void run() {
                                    if (VariabiliStatiche.getInstance().getRitornoCheckFileURL().isEmpty()) {
                                        hAttendeRispostaCheckURL1.postDelayed(rAttendeRispostaCheckURL1, 500);
                                    } else {
                                        hAttendeRispostaCheckURL1.removeCallbacks(rAttendeRispostaCheckURL1);

                                        if (VariabiliStatiche.getInstance().getRitornoCheckFileURL().contains("OK")) {
                                            DownloadImmagine d = new DownloadImmagine();
                                            d.setPath(PathFile);

                                            d.startDownload(link1);
                                        } else {
                                            VariabiliStatiche.getInstance().setRitornoCheckFileURL("");
                                            cuf.startControl(link2);

                                            hAttendeRispostaCheckURL2 = new Handler();
                                            hAttendeRispostaCheckURL2.postDelayed(rAttendeRispostaCheckURL2 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (VariabiliStatiche.getInstance().getRitornoCheckFileURL().isEmpty()) {
                                                        hAttendeRispostaCheckURL2.postDelayed(rAttendeRispostaCheckURL2, 500);
                                                    } else {
                                                        hAttendeRispostaCheckURL2.removeCallbacks(rAttendeRispostaCheckURL2);

                                                        if (VariabiliStatiche.getInstance().getRitornoCheckFileURL().contains("OK")) {
                                                            DownloadImmagine d = new DownloadImmagine();
                                                            d.setPath(PathFile);

                                                            d.startDownload(link2);
                                                        } else {
                                                            Utility u = new Utility();
                                                            u.VisualizzaPOPUP("Nessuna immagine rilevata",false,-1);
                                                        }
                                                    }
                                                }
                                            }, 500);
                                        }
                                    }
                                }
                            }, 500);
                            break;
                    }
                }

                return false;
            }
        });

        VariabiliStatiche.getInstance().setmMap(googleMap);

        VariabiliStatiche.getInstance().DisegnaPercorsoAttualeSuMappa();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Intent dialogIntent = new Intent(this, MainActivity.class);
        // dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity(dialogIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}