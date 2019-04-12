package com.looigi.detector.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DBGps {
    private String TAG = "GestioneDBPosizioni";
    private String DATABASE_NOME = "Posizioni";
    private String DATABASE_TABELLA_GPS = "Posizioni";
    private String DATABASE_TABELLA_MULTIMEDIA = "Multimedia";
    private String DATABASE_TABELLA_DISTANZE = "Distanze";
    private int DATABASE_VERSIONE = 3;

    private String DATABASE_CREAZIONE_1 =
            "CREATE TABLE " + DATABASE_TABELLA_GPS + " ("+
                    "data text not null, " +
                    "pro text not null, " +
                    "lat text not null, "+
                    "lon text not null, "+
                    "alt text not null, "+
                    "dat text not null, "+
                    "vel text not null"+
                    ");";
    private String DATABASE_CREAZIONE_2 =
            "CREATE TABLE " + DATABASE_TABELLA_MULTIMEDIA + " ("+
                    "data text not null, " +
                    "pro text not null, " +
                    "lat text not null, "+
                    "lon text not null, "+
                    "alt text not null, "+
                    "dat text not null, "+
                    "nfile text not null, "+
                    "type text not null "+
                    ");";
    private String DATABASE_CREAZIONE_3 =
            "CREATE TABLE " + DATABASE_TABELLA_DISTANZE + " ("+
                    "data text not null, " +
                    "distanza text not null "+
                    ");";

    private Context context;
    private DatabaseHelperGPS DBHelper;
    private SQLiteDatabase db;

    public DBGps(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelperGPS(context);
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
                db.execSQL(DATABASE_CREAZIONE_1);
            }
            catch (SQLException e) {
                // e.printStackTrace();
            }
            try {
                db.execSQL(DATABASE_CREAZIONE_2);
            }
            catch (SQLException e) {
                // e.printStackTrace();
            }
            try {
                db.execSQL(DATABASE_CREAZIONE_3);
            }
            catch (SQLException e) {
                // e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
            // db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABELLA_GPS);
            // db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABELLA_MULTIMEDIA);

            // boolean Ancora=false;

            // if ((oldVersion==1 && newVersion==2) || Ancora) {
            //     db.execSQL("ALTER TABLE "+DATABASE_TABELLA_GPS+" Add vel text");
            //     db.execSQL("Update "+DATABASE_TABELLA_GPS+" set vel = '30'");
            //     Ancora=true;
            // }
            // if ((oldVersion==2 && newVersion==3) || Ancora) {
            //     db.execSQL(DATABASE_CREAZIONE_3);
            // }

            // onCreate(db);
        }
    }

    public DBGps open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long aggiungiPosizione(
            String data,
            String pro,
            String lat,
            String lon,
            String alt,
            String vel)
    {
        try {
            NumberFormat f2 = new DecimalFormat("00");
            NumberFormat f3 = new DecimalFormat("000");
            Calendar c = Calendar.getInstance();
            String hour = f2.format(c.get(Calendar.HOUR_OF_DAY));
            String minutes = f2.format(c.get(Calendar.MINUTE));
            String seconds = f2.format(c.get(Calendar.SECOND));
            String mseconds = f3.format(c.get(Calendar.MILLISECOND));

            String dat = hour + minutes + seconds + mseconds;

            ContentValues initialValues = new ContentValues();
            initialValues.put("data", data);
            initialValues.put("pro", pro);
            initialValues.put("lat", lat);
            initialValues.put("lon", lon);
            initialValues.put("alt", alt);
            initialValues.put("dat", dat);
            initialValues.put("vel", vel);

            long l = db.insert(DATABASE_TABELLA_GPS, null, initialValues);

            return l;
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Aggiungi posizione. ERROR: "+errors.toString());

            return -1;
        }
    }

    public long aggiungiMultimedia(
            String data,
            String pro,
            String lat,
            String lon,
            String alt,
            String nomeFile,
            String type)
    {
        try {
            NumberFormat f2 = new DecimalFormat("00");
            NumberFormat f3 = new DecimalFormat("000");
            Calendar c = Calendar.getInstance();
            String hour = f2.format(c.get(Calendar.HOUR_OF_DAY));
            String minutes = f2.format(c.get(Calendar.MINUTE));
            String seconds = f2.format(c.get(Calendar.SECOND));
            String mseconds = f3.format(c.get(Calendar.MILLISECOND));

            String dat = hour + minutes + seconds + mseconds;

            ContentValues initialValues = new ContentValues();
            initialValues.put("data", data);
            initialValues.put("pro", pro);
            initialValues.put("lat", lat);
            initialValues.put("lon", lon);
            initialValues.put("alt", alt);
            initialValues.put("dat", dat);
            initialValues.put("nfile", nomeFile);
            initialValues.put("type", type);

            long l = db.insert(DATABASE_TABELLA_MULTIMEDIA, null, initialValues);

            return l;
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Aggiungi multimedia. ERROR: "+errors.toString());

            return -1;
        }
    }

    public boolean cancellaDatiGPS()
    {
        return db.delete(DATABASE_TABELLA_GPS, "", null) > 0;
    }

    public boolean cancellaDatiGPSPerData()
    {
        Date dataVisua = VariabiliStatiche.getInstance().getDataDiVisualizzazioneMappa();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                dataVisuaS
        };

        return db.delete(DATABASE_TABELLA_GPS, whereClause, whereArgs) > 0;
    }

    public boolean cancellaDatiMultiMedia()
    {
        return db.delete(DATABASE_TABELLA_MULTIMEDIA, "", null) > 0;
    }

    public boolean cancellaDatiMultiMediaPerData()
    {
        Date dataVisua = VariabiliStatiche.getInstance().getDataDiVisualizzazioneMappa();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                dataVisuaS
        };


        return db.delete(DATABASE_TABELLA_MULTIMEDIA, whereClause, whereArgs) > 0;
    }

    public Cursor ottieniValoriGPS()
    {
        return db.query(DATABASE_TABELLA_GPS, new String[] {
                "data",
                "pro",
                "lat",
                "lon",
                "alt",
                "dat",
                "vel"
        }, null,null, null, null, null);
    }

    public Cursor ottieniValoriGPSPerData(String oggi)
    {
        String[] tableColumns = new String[] {
                "data",
                "pro",
                "lat",
                "lon",
                "alt",
                "dat",
                "vel"
        };
        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                oggi
        };

        Cursor c =null;
        try {
            c = db.query(DATABASE_TABELLA_GPS, tableColumns, whereClause, whereArgs,
                    null, null, null);
        } catch (Exception ignored) {

        }
        return c;
    }

    public long inserisceNuovaDistanza(
            String data,
            String distanza)
    {
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("data", data);
            initialValues.put("distanza", distanza);

            long l = db.insert(DATABASE_TABELLA_DISTANZE, null, initialValues);

            return l;
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Inserisci distanze. ERROR: "+errors.toString());

            return -1;
        }
    }

    public long aggiornaDistanza(
            String data,
            String distanza)
    {
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("data", data);
            initialValues.put("distanza", distanza);

            String whereClause = "data = ?";
            String[] whereArgs = new String[] {
                    data
            };

            // long l = db.insert(DATABASE_TABELLA_DISTANZE, null, initialValues);
            long l = db.update(DATABASE_TABELLA_DISTANZE, initialValues, whereClause, whereArgs);

            return l;
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Aggiorna distanze. ERROR: "+errors.toString());

            return -1;
        }
    }

    public Cursor ottieniDistanzeData(String oggi)
    {
        String[] tableColumns = new String[] {
                "data",
                "distanza"
        };
        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                oggi
        };

        Cursor c = null;
        try {
            c = db.query(DATABASE_TABELLA_DISTANZE, tableColumns, whereClause, whereArgs,
                    null, null, null);
        } catch (Exception ignored) {

        }

        return c;
    }

    public Cursor ottieniValoriMultiMediaPerData(String oggi)
    {
        String[] tableColumns = new String[] {
                "data",
                "pro",
                "lat",
                "lon",
                "alt",
                "dat",
                "nFile",
                "type"
        };
        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                oggi
        };

        Cursor c = null;
        try {
            c = db.query(DATABASE_TABELLA_MULTIMEDIA, tableColumns, whereClause, whereArgs,
                    null, null, null);
        } catch (Exception ignored) {

        }
        return c;
    }

    public int ottieniMassimoProgressivoGPSPerData(String oggi)
    {
        String[] tableColumns = new String[] {
                "data",
                "pro"
        };
        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                oggi
        };
        int pro=0;
        Cursor c = db.query(DATABASE_TABELLA_GPS, tableColumns, whereClause, whereArgs,
                null, null, null);
        if (c.moveToFirst()) {
            do {
                int p = c.getInt(1);
                if (p>pro) {
                    pro=p;
                }
            } while (c.moveToNext());
        }
        if(c != null)
            c.close();

        return pro;
    }

    public int ottieniMassimoProgressivoMultimediaPerData(String oggi)
    {
        String[] tableColumns = new String[] {
                "data",
                "pro"
        };
        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                oggi
        };
        int pro=0;
        Cursor c = db.query(DATABASE_TABELLA_MULTIMEDIA, tableColumns, whereClause, whereArgs,
                null, null, null);
        if (c.moveToFirst()) {
            do {
                int p = c.getInt(1);
                if (p>pro) {
                    pro=p;
                }
            } while (c.moveToNext());
        }
        if(c != null)
            c.close();

        return pro;
    }
}
