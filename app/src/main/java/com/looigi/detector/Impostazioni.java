package com.looigi.detector;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.looigi.detector.Fotocamera.GBTakePictureNoPreview;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Impostazioni {

    public void PrendeRisoluzioni(Context context) {
        final GBTakePictureNoPreview c = new GBTakePictureNoPreview();
        c.ImpostaContext(context);
        if (VariabiliImpostazioni.getInstance().getFotocamera()==1) {
            c.setUseFrontCamera();
        } else {
            c.setUseBackCamera();
        }
        VariabiliStatiche.getInstance().Dimensioni=c.RitornaRisoluzioni();
        RiempieListaRisoluzioni(context, VariabiliStatiche.getInstance().Dimensioni);
    }

    private void RiempieListaRisoluzioni(Context context, List<String> Risoluzioni) {
        String sRisoluzioni[] = null;
        sRisoluzioni=new String[Risoluzioni.size()];
        for (int i=0;i<Risoluzioni.size();i++) {
            sRisoluzioni[i]=Risoluzioni.get(i);
        }

        ArrayList<String> ListaRis=new ArrayList<String>();
        String[] risol={};
        risol=new String[sRisoluzioni.length];
        for (int i=0;i<sRisoluzioni.length;i++) {
            risol[i]= new String(sRisoluzioni[i]);
            ListaRis.add(risol[i]);
        }

        ArrayList<HashMap<String, Object>>
                data=new ArrayList<HashMap<String,Object>>();

        HashMap<String,Object>
                ListaFinale=new HashMap<String, Object>();
        for(int i=0;i<ListaRis.size();i++){
            String p=ListaRis.get(i);

            ListaFinale=new HashMap<String, Object>();
            ListaFinale.put("Risoluzione", p);

            data.add(ListaFinale);
        }
        String[] from={"Risoluzione"};
        int[] to={R.id.Risoluzione};
        SimpleAdapter adapter=new SimpleAdapter(
                context.getApplicationContext(),
                data,
                R.layout.listview_records,
                from,
                to);

        VariabiliStatiche.getInstance().getLista().setAdapter(adapter);
    }

    private void SalvaValori() {
        // DBImpostazioni dbImpostazioni = new DBImpostazioni(VariabiliStatiche.getInstance().getContext());
        // dbImpostazioni.open();
        // dbImpostazioni.ScriveImpostazioni();
        // dbImpostazioni.close();
    }

    public void ImpostaEstensioneOriginale(Context context) {
        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Modalita Set Estensione=1");
        // myDB.close();
        VariabiliImpostazioni.getInstance().setEstensione(1);
        SalvaValori();
    }

    public void ImpostaEstensioneMascherata(Context context) {
        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Modalita Set Estensione=2");
        // myDB.close();
        VariabiliImpostazioni.getInstance().setEstensione(2);
        SalvaValori();
    }

    public void ImpostaRisoluzione(Context context, String Risol) {
        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Modalita Set Risoluzione='"+Risol+"'");
        // myDB.close();
        VariabiliImpostazioni.getInstance().setRisoluzione(Risol);
        SalvaValori();
    }

    public void ImpostaFrontale(Context context, TextView tv) {
        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Modalita Set Fotocamera=1");
        VariabiliImpostazioni.getInstance().setFotocamera(1);
        // myDB.close();
        ImpostaRisoluzione(context, "640x480");
        tv.setText("640x480");
        PrendeRisoluzioni(context);
        SalvaValori();
    }

    public void ImpostaRetro(Context context, TextView tv) {
        // SQLiteDatabase myDB= null;

        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Modalita Set Fotocamera=2");
        VariabiliImpostazioni.getInstance().setFotocamera(2);
        // myDB.close();
        ImpostaRisoluzione(context, "640x480");
        tv.setText("640x480");
        PrendeRisoluzioni(context);
        SalvaValori();
    }

    public void ImpostaAutoScatto(Context context, EditText et) {
        // SQLiteDatabase myDB= null;

        if (et != null) {
            String Secondi = et.getText().toString();

            // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
            // myDB.execSQL("Update Modalita Set Valore=1, Secondi="+Secondi);
            // myDB.close();

            if (!Secondi.isEmpty()) {
                VariabiliImpostazioni.getInstance().setTipologiaScatto(1);
                VariabiliImpostazioni.getInstance().setSecondi(Integer.parseInt(Secondi));
                SalvaValori();
            }
        }
    }

    public void ImpostaImmediato(Context context) {
        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Modalita Set Valore=2");
        // myDB.close();
        VariabiliImpostazioni.getInstance().setTipologiaScatto(2);
        SalvaValori();
    }

    public void ImpostaVibrazione(Context context, CheckBox c) {
        String Vibr="";

        if (c.isChecked()) {
            Vibr="S";
        } else {
            Vibr="N";
        }

        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Vibrazione Set Vibraz='"+Vibr+"';");
        // myDB.close();

        VariabiliImpostazioni.getInstance().setVibrazione(Vibr);
        SalvaValori();
    }

    public void ImpostaAnteprima(Context context, CheckBox c) {
        String Ant="";

        if (c.isChecked()) {
            Ant="S";
        } else {
            Ant="N";
        }

        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update Anteprima Set Ant='"+Ant+"';");
        // myDB.close();

        VariabiliImpostazioni.getInstance().setAnteprima(Ant);
        SalvaValori();
    }

    public void ImpostaNumScatti(Context context, EditText t) {
        int Scatti;

        String s=t.getText().toString();
        Scatti=Integer.parseInt(s);

        // SQLiteDatabase myDB= null;
//
        // myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
        // myDB.execSQL("Update NumeroScatti Set Numero="+Scatti+";");
        // myDB.close();

        VariabiliImpostazioni.getInstance().setNumeroScatti(Scatti);
        SalvaValori();
    }
}
