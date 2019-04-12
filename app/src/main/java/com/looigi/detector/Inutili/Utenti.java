/* package com.looigi.spiatore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;

import static android.content.Context.MODE_PRIVATE;

public class Utenti {
    public void SalvaNuovoUtente(Context context, int idUtente, String Nick) {
        SQLiteDatabase myDB= null;
        Utility u=new Utility();

        if (idUtente == -1) {
            u.VisualizzaPOPUP(context, Nick, false, 0);
        } else {
            try {
                myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
                myDB.execSQL("Delete From Utenza;");
                myDB.execSQL("Insert Into Utenza Values ("+Integer.toString(idUtente)+", '"+Nick+"');");
                myDB.close();

                // VariabiliStatiche.getInstance().idUtente=idUtente;
                // VariabiliStatiche.getInstance().Nick=Nick;

                // VariabiliStatiche.getInstance().getTu().setText(Nick);
                // VariabiliStatiche.getInstance().getLu().setVisibility(LinearLayout.VISIBLE);
                // VariabiliStatiche.getInstance().getLe().setVisibility(LinearLayout.VISIBLE);
                // VariabiliStatiche.getInstance().getLi().setVisibility(LinearLayout.VISIBLE);
                // VariabiliStatiche.getInstance().getLa().setVisibility(LinearLayout.GONE);
                VariabiliStatiche.getInstance().getTa().setVisibility(LinearLayout.GONE);
                VariabiliStatiche.getInstance().getTai().setVisibility(LinearLayout.GONE);
            } catch (Exception e) {

                u.VisualizzaPOPUP(context, "Error", false, 0);
            }
        }
    }
}
*/