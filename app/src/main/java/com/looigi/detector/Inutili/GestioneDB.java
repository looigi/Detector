/* package com.looigi.detector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.looigi.detector.Variabili.VariabiliStatiche;

public class GestioneDB  {

    public void CreaDB(Context context) {
    	SQLiteDatabase myDB= null;

    	try {
			myDB = context.openOrCreateDatabase("Detector", 0, null);
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Modalita (Valore Int(1), Secondi Int(2), Fotocamera Int(1), Risoluzione Text, Estensione Int(1));");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Opzioni2 (SalvaOnLine Text, NomeUtente Text);");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Vibrazione (Vibraz Text);");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS NumeroScatti (Numero Int(2));");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Anteprima (Ant Text);");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Orientamento (Orient Int(3));");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Lingua (Lingua Text);");
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Utenza (idUtente int, Nick Text);");
			myDB.close();
			
		  } catch(Exception ignored) {

		  } finally {
		   if (myDB != null){
		    myDB.close();
		   }
		}
    	
    }
    
	public void SalvaLingua(Context context, String Lingua) {
		SQLiteDatabase myDB= null;
    	
		myDB = context.openOrCreateDatabase("Detector", 0, null);
	   	String Sql="Delete From Lingua;";
	   	myDB.execSQL(Sql);
	   	
	   	Sql="Insert Into Lingua Values ('" + Lingua + "');";
	   	myDB.execSQL(Sql);

	   	myDB.close();

		VariabiliStatiche.getInstance().Lingua=Lingua;
	}
    	
    public String LeggeValori(Context context) {
    	SQLiteDatabase myDB= null;
    	String Ritorno="";
    	
		myDB = context.openOrCreateDatabase("Detector", 0, null);
	   	String Sql="SELECT Valore, Secondi, Fotocamera, Risoluzione, Estensione FROM Modalita;";
		Cursor c = myDB.rawQuery(Sql , null);
		c.moveToFirst();
		int Modalita=c.getInt(0);
		int Secondi=c.getInt(1);
		int Fotocamera=c.getInt(2);
		String Risoluzione=c.getString(3);
		int Estensione=c.getInt(4);
		c.close();
		Ritorno=""+Modalita+"*"+Secondi+"@"+Fotocamera+"§"+Risoluzione+"§"+Estensione;
		
	   	Sql="SELECT * FROM Lingua;";
		c = myDB.rawQuery(Sql , null);
		c.moveToFirst();
		try {
			String Ritorno2;
			
			Ritorno2=c.getString(0);
			VariabiliStatiche.getInstance().Lingua=Ritorno2;
		} catch (Exception e) {
		   	Sql="Delete From Lingua";
		   	myDB.execSQL(Sql);

		   	Sql="Insert Into Lingua Values ('ITALIANO')";
		   	myDB.execSQL(Sql);

			VariabiliStatiche.getInstance().Lingua="ITALIANO";
		}
		c.close();

		myDB.close();

		return Ritorno;
    }
}
*/