/* package com.looigi.spiatore;

import android.app.Activity;
import android.content.Context;

public class RitornoDaGestioneWS extends Activity {
	
	public void RitornaImmagini(Context context, String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);
		MainActivity a=new MainActivity();
				
		if (Appoggio.toUpperCase().indexOf("ERROR:")==-1) {
			String[] Nomi=Appoggio.split(";");
			
			a.RiempieListaImmagini(context, Nomi);
		} else {
			GestioneWEBServiceSOAP.visErrore=Appoggio;
		}
	}
	
	public void RitornoDownloadImmagine(Context context, String Ritorno) {
		MainActivity.Continua = false;
	}
	
	public void EliminaImmagini(Context context, String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);
		Utility u=new Utility();

		if (Appoggio.toUpperCase().indexOf("ERROR:")==-1) {
			u.VisualizzaPOPUP(context, "Import Done", false, 0);
			
			GestioneWEBServiceSOAP.Continua="RitornaImmagini";
		} else {
			GestioneWEBServiceSOAP.visErrore=Appoggio;
		}
	}
	
	public void RitornoInserisceUtente(Context context, String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);
		Utenti a=new Utenti();
				
		if (Appoggio.toUpperCase().indexOf("ERROR:")==-1) {
			int idUtente = 0;
			idUtente= Integer.parseInt(Appoggio);

			a.SalvaNuovoUtente(context, idUtente, VariabiliStatiche.getInstance().Nome);
		} else {
			a.SalvaNuovoUtente(context, -1, Appoggio);
		}
	}
	
	public void RitornaPassword(Context context, String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);
				
		if (!Appoggio.toUpperCase().contains("ERROR:")) {
			if (VariabiliStatiche.getInstance().Password.equals(Appoggio)) {
				GestioneWEBServiceSOAP.Continua="RitornaId";
			}
		} else {
			GestioneWEBServiceSOAP.visErrore=Appoggio;
		}
	}
	
	public void RitornaIdUtente(Context context, String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);
		Utenti a=new Utenti();
				
		if (!Appoggio.toUpperCase().contains("ERROR:")) {
			int idUtente = 0;
			idUtente= Integer.parseInt(Appoggio);

			a.SalvaNuovoUtente(context, idUtente, VariabiliStatiche.getInstance().Nome);
		} else {
			GestioneWEBServiceSOAP.visErrore=Appoggio;
		}
	}

	private String ToglieTag(String Cosa) {
		String Ritorno=Cosa;
		
		return Ritorno;
	}

	public void RitornoLetturaIdUtente(Context context, String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);
		
		if (!Appoggio.toUpperCase().contains("ERROR:")) {
			VariabiliStatiche.getInstance().idUtente=Integer.parseInt(Ritorno);
		} else {
			VisualizzaMessaggio(context, Appoggio);
		}
	}

	private void VisualizzaMessaggio(Context context, String Messaggio) {
        try {
        	GestioneWEBServiceSOAP.progressDialog.dismiss();
        } catch (Exception ignored) {
        }
		
		Utility u=new Utility();
		u.VisualizzaPOPUP(context, Messaggio, false, 0);
	}
}
*/