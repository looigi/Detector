package com.looigi.detector.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class db_dati {
    private String Origine = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String PathDB = Origine + VariabiliStatiche.getInstance().PathApplicazioneFuori+"DB_DATI/";
    private String NomeDB = "posizioni.db";
    private SQLiteDatabase myDB;
    private String DATABASE_TABELLA_GPS = "Posizioni";
    private String DATABASE_TABELLA_MULTIMEDIA = "Multimedia";
    private String DATABASE_TABELLA_DISTANZE = "Distanze";

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

    public db_dati() {
        File f = new File(PathDB);
        try {
            f.mkdirs();
        } catch (Exception ignored) {

        }
        myDB = ApreDB();
    }

    private SQLiteDatabase ApreDB() {
        SQLiteDatabase db = null;
        try {
            db = VariabiliStatiche.getInstance().getContext().openOrCreateDatabase(
                    PathDB + NomeDB, MODE_PRIVATE, null);
        } catch (Exception e) {
            int a = 0;
        }

        return  db;
    }

    public void CreazioneTabelle() {
        if (myDB != null) {
            try {
            // SQLiteDatabase myDB = ApreDB();
                myDB.execSQL(DATABASE_CREAZIONE_1);
            } catch (Exception ignored) {
                int a = 0;
            }

            try {
                myDB.execSQL(DATABASE_CREAZIONE_2);
            } catch (Exception ignored) {
                int a = 0;
            }

            try {
                myDB.execSQL(DATABASE_CREAZIONE_3);
            } catch (Exception ignored) {
                int a = 0;
            }

            try {
                myDB.execSQL("CREATE INDEX IF NOT EXISTS Posizioni_Index ON " + DATABASE_TABELLA_GPS + "(data, pro);");
            } catch (Exception ignored) {
                int a = 0;
            }

            try {
                myDB.execSQL("CREATE INDEX IF NOT EXISTS Multimedia_Index ON " + DATABASE_TABELLA_MULTIMEDIA + "(data, pro);");
            } catch (Exception ignored) {
                int a = 0;
            }

            try {
                myDB.execSQL("CREATE INDEX IF NOT EXISTS Distanze_Index ON " + DATABASE_TABELLA_DISTANZE + "(data);");
            } catch (Exception ignored) {
                int a = 0;
            }
        }
    }

    public void CompattaDB() {
        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            myDB.execSQL("VACUUM");
        }
    }

    public void PulisceDati() {
        if (myDB != null) {
            myDB.execSQL("Delete From " + DATABASE_TABELLA_GPS);
            myDB.execSQL("Delete From " + DATABASE_TABELLA_DISTANZE );
            myDB.execSQL("Delete From " + DATABASE_TABELLA_MULTIMEDIA);
        }
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

            if (myDB != null) {
                long Progressivo = 0;

                Cursor c1 = myDB.rawQuery("SELECT Max(pro) FROM " + DATABASE_TABELLA_GPS + " WHERE data = ?",
                        new String[]{data});
                c1.moveToFirst();
                if (c1.getCount() > 0) {
                    Progressivo = c1.getLong(0);
                }
                c1.close();

                Progressivo++;
                myDB.execSQL("INSERT INTO"
                        + " " + DATABASE_TABELLA_GPS + " "
                        + " (data, pro, lat, lon, alt, dat, vel)"
                        + " VALUES ('" + data + "', " + Progressivo + ", " + lat + ", " + lon + ", "
                        + alt + ", '" + dat + "', " + vel + ");");

                return 1;
            } else {
                Log l1=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
                l1.ScriveLog("Aggiungi posizione. ERROR: Db chiuso");

                return -1;
            }
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

            if (myDB != null) {
                long Progressivo = 0;

                Cursor c1 = myDB.rawQuery("SELECT Max(pro) FROM " + DATABASE_TABELLA_MULTIMEDIA + " WHERE data = ?",
                        new String[]{data});
                c1.moveToFirst();
                if (c1.getCount() > 0) {
                    Progressivo = c1.getLong(0);
                }
                c1.close();

                Progressivo++;
                myDB.execSQL("INSERT INTO"
                        + " " + DATABASE_TABELLA_MULTIMEDIA + " "
                        + " (data, pro, lat, lon, alt, dat, nfile, type)"
                        + " VALUES ('" + data + "', " + Progressivo + ", " + lat + ", "
                        + lon + ", " + alt + ", '" + dat + "', '" + nomeFile + "', '"+ type + "');");

                return 1;
            } else {
                Log l1=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
                l1.ScriveLog("Aggiungi multimedia. ERROR: Db chiuso");

                return -1;
            }
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Aggiungi multimedia. ERROR: "+errors.toString());

            return -1;
        }
    }

    public long inserisceNuovaDistanza(
            String data,
            String distanza)
    {
        try {
            if (myDB != null) {
                myDB.execSQL("INSERT INTO"
                        + " " + DATABASE_TABELLA_DISTANZE + " "
                        + " (data, distanza)"
                        + " VALUES ('" + data + "', " + distanza);

                return 1;
            } else {
                Log l1=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
                l1.ScriveLog("Aggiungi distanze. ERROR: Db chiuso");

                return -1;
            }
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Aggiungi distanze. ERROR: "+errors.toString());

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

            long l = myDB.update(DATABASE_TABELLA_DISTANZE, initialValues, whereClause, whereArgs);

            return l;
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Aggiorna distanze. ERROR: "+errors.toString());

            return -1;
        }
    }

    public boolean cancellaDatiGPSPerDataAttuale()
    {
        Date dataVisua = VariabiliStatiche.getInstance().getDataDiVisualizzazioneMappa();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                dataVisuaS
        };

        return myDB.delete(DATABASE_TABELLA_GPS, whereClause, whereArgs) > 0;
    }

    public boolean cancellaDatiGPSPerData(Date dataVisua)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                dataVisuaS
        };

        return myDB.delete(DATABASE_TABELLA_GPS, whereClause, whereArgs) > 0;
    }

    public List<Date> RitornaTutteLeDateInArchivio() {
        List<Date> lista = new ArrayList<>();

        String[] tableColumns = new String[] {
                "data",
                "pro",
                "lat",
                "lon",
                "alt",
                "dat",
                "vel"
        };

        Cursor c =null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            c = myDB.query(DATABASE_TABELLA_GPS, tableColumns, null, null,
                    "data", null, "data");
            if (c.moveToFirst()) {
                do {
                    String d = c.getString(0);
                    Date dd = dateFormat.parse(d);

                    lista.add(dd);
                } while (c.moveToNext());
            }
            if (c != null)
                c.close();

        } catch (Exception ignored) {

        }

        return lista;
    }

    public boolean cancellaDatiMultiMedia()
    {
        return myDB.delete(DATABASE_TABELLA_MULTIMEDIA, "", null) > 0;
    }

    public boolean cancellaDatiMultiMediaPerDataAttuale()
    {
        Date dataVisua = VariabiliStatiche.getInstance().getDataDiVisualizzazioneMappa();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                dataVisuaS
        };


        return myDB.delete(DATABASE_TABELLA_MULTIMEDIA, whereClause, whereArgs) > 0;
    }

    public boolean cancellaDatiMultiMediaPerData(Date dataVisua)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataVisuaS = formatter.format(dataVisua);

        String whereClause = "data = ?";
        String[] whereArgs = new String[] {
                dataVisuaS
        };


        return myDB.delete(DATABASE_TABELLA_MULTIMEDIA, whereClause, whereArgs) > 0;
    }

    public Cursor ottieniValoriGPS()
    {
        return myDB.query(DATABASE_TABELLA_GPS, new String[] {
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
            c = myDB.query(DATABASE_TABELLA_GPS, tableColumns, whereClause, whereArgs,
                    null, null, null);
        } catch (Exception ignored) {

        }
        return c;
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
            c = myDB.query(DATABASE_TABELLA_DISTANZE, tableColumns, whereClause, whereArgs,
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
            c = myDB.query(DATABASE_TABELLA_MULTIMEDIA, tableColumns, whereClause, whereArgs,
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
        Cursor c = myDB.query(DATABASE_TABELLA_GPS, tableColumns, whereClause, whereArgs,
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
        Cursor c = myDB.query(DATABASE_TABELLA_MULTIMEDIA, tableColumns, whereClause, whereArgs,
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
