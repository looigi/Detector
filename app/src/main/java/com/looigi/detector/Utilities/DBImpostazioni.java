/* package com.looigi.detector.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.looigi.detector.Variabili.VariabiliImpostazioni;

public class DBImpostazioni {
    private String TAG = "GestioneDBImpostazioni";
    private String DATABASE_NOME = "Impostazioni";
    private String DATABASE_TABELLA_IMPOSTAZIONI = "Impostazioni";
    private int DATABASE_VERSIONE = 1;

    private String DATABASE_CREAZIONE =
            "CREATE TABLE " + DATABASE_TABELLA_IMPOSTAZIONI + " ("+
                    "Valore Int, " +
                    "Secondi Int, " +
                    "Fotocamera Int, " +
                    "Risoluzione Text, " +
                    "Estensione Int, " +
                    "Vibrazione Text, " +
                    "Numero Int, " +
                    "Anteprima Text, " +
                    "Orientamento Int, " +
                    "Lingua Text " +
                    ");";

    private Context context;
    private DatabaseHelperGPS DBImpostazioni;
    private SQLiteDatabase db;

    public DBImpostazioni(Context ctx)
    {
        this.context = ctx;
        DBImpostazioni = new DatabaseHelperGPS(context);
    }

    private class DatabaseHelperGPS extends SQLiteOpenHelper
    {
        DatabaseHelperGPS(Context context)
        {
            super(context, DATABASE_NOME, null, DATABASE_VERSIONE);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREAZIONE);
            }
            catch (SQLException e) {
                // e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABELLA_IMPOSTAZIONI);
            onCreate(db);
        }
    }

    public DBImpostazioni open() throws SQLException
    {
        db = DBImpostazioni.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBImpostazioni.close();
    }

    public long ScriveImpostazioni() {
        cancellaDati();

        VariabiliImpostazioni v = VariabiliImpostazioni.getInstance();

        ContentValues initialValues = new ContentValues();
        try {
            initialValues.put("Valore", v.getTipologiaScatto());
            initialValues.put("Secondi", v.getSecondi());
            initialValues.put("Fotocamera", v.getFotocamera());
            initialValues.put("Risoluzione", v.getRisoluzione());
            initialValues.put("Estensione", v.getEstensione());
            initialValues.put("Vibrazione", v.getVibrazione());
            initialValues.put("Numero", v.getNumeroScatti());
            initialValues.put("Anteprima", v.getAnteprima());
            initialValues.put("Orientamento", v.getOrientamento());
            initialValues.put("Lingua", v.getLingua());
        } catch (Exception ignored) {
            int a = 0;
        }

        long l = db.insert(DATABASE_TABELLA_IMPOSTAZIONI, null, initialValues);

        return l;
    }

    public boolean cancellaDati()
    {
        return db.delete(DATABASE_TABELLA_IMPOSTAZIONI, "", null) > 0;
    }

    public void ottieniImpostazioni()
    {
        String[] tableColumns = new String[] {
            "Valore",
            "Secondi",
            "Fotocamera",
            "Risoluzione",
            "Estensione",
            "Vibrazione",
            "Numero",
            "Anteprima",
            "Orientamento",
            "Lingua"
        };

        VariabiliImpostazioni v = VariabiliImpostazioni.getInstance();
        Cursor c = db.query(DATABASE_TABELLA_IMPOSTAZIONI, tableColumns, null, null,
                null, null, null);
        if (c.moveToFirst()) {
            do {
                v.setTipologiaScatto(c.getInt(0));
                v.setSecondi(c.getInt(1));
                v.setFotocamera(c.getInt(2));
                v.setRisoluzione(c.getString(3));
                v.setEstensione(c.getInt(4));
                v.setVibrazione(c.getString(5));
                v.setNumeroScatti(c.getInt(6));
                v.setAnteprima(c.getString(7));
                v.setOrientamento(c.getInt(8));
                v.setLingua(c.getString(9));
            } while (c.moveToNext());

            if (v.getLingua()!=null) {
                // VariabiliStatiche.getInstance().setImpostazioni(v);
            } else {
                SettaValoriDiDefault();

                ScriveImpostazioni();
            }
        } else {
            SettaValoriDiDefault();

            ScriveImpostazioni();
        }
    }

    private void SettaValoriDiDefault() {
        VariabiliImpostazioni v = VariabiliImpostazioni.getInstance();
        v.setTipologiaScatto(2);
        v.setSecondi(3);
        v.setFotocamera(2);
        v.setRisoluzione("640x480");
        v.setEstensione(2);
        v.setVibrazione("S");
        v.setNumeroScatti(3);
        v.setAnteprima("S");
        v.setOrientamento(0);
        v.setLingua("ITALIANO");
    }
}
*/