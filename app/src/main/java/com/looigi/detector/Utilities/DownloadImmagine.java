package com.looigi.detector.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import com.looigi.detector.R;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DownloadImmagine {
    private String Path;
    private String messErrore="";
    private Bitmap bitmap;
    private DownloadImageFile downloadFile;
    private String Url;

    public void setPath(String path) {
        Path = path;
    }

    public void startDownload(String sUrl) {
        Url=sUrl;

        ApriDialog();

        String sUrl2 = Url.substring(9, Url.length());
        Url=Url.substring(0,9);
        sUrl2=sUrl2.replace("//","/");
        Url+=sUrl2;

        downloadFile = new DownloadImageFile();
        downloadFile.execute(Url);
    }

    private void ChiudeDialog() {
    }

    private void ApriDialog() {
    }

    private class DownloadImageFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            messErrore="";
            bitmap=null;

            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(sUrl[0]).getContent());
            } catch (IOException e) {
                Utility u =new Utility();
                messErrore = u.PrendeErroreDaException(e);
                if (messErrore.contains("java.io.FileNotFoundException")) {
                } else {
                }
            }

            return null;
        }

        public void saveImageFile(Bitmap bitmap, String filename) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (FileNotFoundException e) {
                // e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (messErrore.isEmpty() && bitmap!=null) {
                VariabiliStatiche.getInstance().getFgmMappa().setVisibility(LinearLayout.GONE);
                VariabiliStatiche.getInstance().getImgMappa().setVisibility(LinearLayout.VISIBLE);
                VariabiliStatiche.getInstance().getImgChiudeImg().setVisibility(LinearLayout.VISIBLE);
                VariabiliStatiche.getInstance().getImgChiudeImg().setBackgroundResource(R.drawable.chiude);

                saveImageFile(bitmap, Path);

                VariabiliStatiche.getInstance().getImgMappa().setImageBitmap(BitmapFactory.decodeFile(Path));

                PhotoViewAttacher photoAttacher;
                photoAttacher = new PhotoViewAttacher(VariabiliStatiche.getInstance().getImgMappa());
                photoAttacher.update();
            } else {
                Utility u = new Utility();
                u.VisualizzaPOPUP(VariabiliStatiche.getInstance().getContext(), "Nessuna immagine rilevata",false,-1);
            }

            ChiudeDialog();
        }
    }
}