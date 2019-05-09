package com.looigi.detector.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.looigi.detector.R;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.File;
import java.util.List;
public class adapterListaFiles extends ArrayAdapter
{
    private Context context;
    private List<String> lista;

    public adapterListaFiles(Context context, int textViewResourceId, List<String> objects)
    {
        super(context, textViewResourceId, objects);

        this.context = context;
        this.lista=objects;
    }

    @Override
    @Nullable
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_upload, null);

        Utility u = new Utility();
        if (u.ePari(position)) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.argb(255,230,230,230));
        }

        String NomeFile =lista.get(position);

        TextView txtNomeFile = convertView.findViewById(R.id.txtNomeFile);
        TextView txtDimensioni = convertView.findViewById(R.id.txtDimensioni);

        String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
        String Cartella= VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Paths";

        File f = new File(Origine + "/" + Cartella + "/LL_" + NomeFile + ".txt");
        long dimeFileL = f.length();
        float dimeFile = dimeFileL;
        String cosa = "b.";
        if (dimeFile > 1024) {
            dimeFile /= 1024;
            cosa = "kb.";
        }
        if (dimeFile > 1024) {
            dimeFile /= 1024;
            cosa = "mb.";
        }
        if (dimeFile > 1024) {
            dimeFile /= 1024;
            cosa = "gb.";
        }
        dimeFile = Math.round(dimeFile * 100) / 100;

        txtNomeFile.setText(NomeFile);
        txtDimensioni.setText(Float.toString(dimeFile) + " " + cosa);

        return convertView;
    }
}
