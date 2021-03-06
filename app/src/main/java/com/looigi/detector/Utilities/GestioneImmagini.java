package com.looigi.detector.Utilities;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GestioneImmagini {
	public void Ruotaimmagine(String NomeFile, int Angolo) {
		try {
			rotateBitmap(NomeFile, Angolo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean rotateBitmap(String NomeFile, int angle) throws IOException {
		File inFile = new File(NomeFile);

		boolean OkEXIF=false;
		String artista = "";
		String model = "";
		String lat = "";
		String latRef = "";
		String lon = "";
		String lonref = "";

		try {
			ExifInterface exif = new ExifInterface(NomeFile);
			artista = exif.getAttribute(ExifInterface.TAG_ARTIST);
			model = exif.getAttribute(ExifInterface.TAG_MODEL);
			lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
			lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			lonref = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

			OkEXIF = true;
		} catch (IOException ignored) {
			OkEXIF = false;
		}

		File outFile = new File(NomeFile+".PPP");

	    FileInputStream inStream = null;
	    FileOutputStream outStream = null;

	    BitmapFactory.Options options = new BitmapFactory.Options();

	    Matrix matrix = new Matrix();
	    matrix.postRotate(angle);

	    for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
	        try {
	            inStream = new FileInputStream(inFile);
	            Bitmap originalBitmap = BitmapFactory.decodeStream(inStream, null, options);

	            Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

	            outStream = new FileOutputStream(outFile);
	            rotatedBitmap.compress(CompressFormat.JPEG, 95, outStream);
	            outStream.close();

	            originalBitmap.recycle();
	            originalBitmap = null;
	            rotatedBitmap.recycle();
	            rotatedBitmap = null;

	            return true;
	        } catch (OutOfMemoryError ignored) {
	        } finally {
	            if (outStream != null) {
	                try {
	                    outStream.close();

	                    Utility u=new Utility();
	                    u.EliminaFile(NomeFile);
	                    String Cartella=u.PrendeNomeCartella(NomeFile);
	                    String VecchioNome=u.PrendeNomeFile(NomeFile);
	                    VecchioNome+=".PPP";
	                    String NuovoNome=u.PrendeNomeFile(NomeFile);

	                    u.RinominaFile(Cartella, VecchioNome, NuovoNome);

	                    if (OkEXIF) {
							ExifInterface exif = new ExifInterface(NuovoNome);
							exif.setAttribute(ExifInterface.TAG_ARTIST, artista);
							exif.setAttribute(ExifInterface.TAG_MODEL, model);
							exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, lat);
							exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latRef);
							exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, lon);
							exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, lonref);
							exif.saveAttributes();
						}
	                } catch (IOException ignored) {
	                }
	            }
	        }
	    }

	    return false;
	}

	public void RuotaImmagine(int Quanto) {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		String NomeImmagine = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

		boolean OkEXIF=false;
		String artista = "";
		String model = "";
		String lat = "";
		String latRef = "";
		String lon = "";
		String lonref = "";

		try {
			ExifInterface exif = new ExifInterface(Origine+Cartella+NomeImmagine);
			artista = exif.getAttribute(ExifInterface.TAG_ARTIST);
			model = exif.getAttribute(ExifInterface.TAG_MODEL);
			lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
			lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			lonref = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

			OkEXIF = true;
		} catch (IOException ignored) {
			OkEXIF = false;
		}

		Bitmap Immagine = BitmapFactory.decodeFile(Origine+Cartella+NomeImmagine); // getPreview(Origine+Cartella+NomeImmagine);
		Matrix matrix = new Matrix();
		matrix.postRotate(Quanto);

		Bitmap rotated = Bitmap.createBitmap(Immagine, 0, 0, Immagine.getWidth(), Immagine.getHeight(),
				matrix, true);

		saveBitmap(rotated, Origine+Cartella, NomeImmagine);

		if (OkEXIF) {
			try {
				ExifInterface exif = new ExifInterface(Origine + Cartella + NomeImmagine);
				exif.setAttribute(ExifInterface.TAG_ARTIST, artista);
				exif.setAttribute(ExifInterface.TAG_MODEL, model);
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, lat);
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latRef);
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, lon);
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, lonref);
				exif.saveAttributes();
			} catch (IOException ignored) {

			}
		}

		Utility u = new Utility();
		u.VisualizzaMultimedia();
	}

	public void FlipImmagine(boolean Orizzontale) {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		String NomeImmagine = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

		boolean OkEXIF=false;
		String artista = "";
		String model = "";
		String lat = "";
		String latRef = "";
		String lon = "";
		String lonref = "";

		try {
			ExifInterface exif = new ExifInterface(Origine+Cartella+NomeImmagine);
			artista = exif.getAttribute(ExifInterface.TAG_ARTIST);
			model = exif.getAttribute(ExifInterface.TAG_MODEL);
			lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
			lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			lonref = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

			OkEXIF = true;
		} catch (IOException ignored) {
			OkEXIF = false;
		}

		Bitmap Immagine = BitmapFactory.decodeFile(Origine+Cartella+NomeImmagine); // getPreview(Origine+Cartella+NomeImmagine);
		Matrix matrix = new Matrix();
		int cx = Immagine.getWidth()/2;
		int cy = Immagine.getHeight()/2;
		if (Orizzontale) {
			matrix.postScale(-1, 1, cx, cy);
		} else {
			matrix.postScale(1, -1, cx, cy);
		}

		Bitmap rotated = Bitmap.createBitmap(Immagine, 0, 0, Immagine.getWidth(), Immagine.getHeight(),
				matrix, true);

		saveBitmap(rotated, Origine+Cartella, NomeImmagine);

		if (OkEXIF) {
			try {
				ExifInterface exif = new ExifInterface(Origine + Cartella + NomeImmagine);
				exif.setAttribute(ExifInterface.TAG_ARTIST, artista);
				exif.setAttribute(ExifInterface.TAG_MODEL, model);
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, lat);
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latRef);
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, lon);
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, lonref);
				exif.saveAttributes();
			} catch (IOException ignored) {

			}
		}

        Utility u = new Utility();
        u.VisualizzaMultimedia();
	}

	private void saveBitmap(Bitmap bm, String Percorso, String Nome)  {
		if (bm!=null) {
			OutputStream fOut = null;
			Uri outputFileUri;
			try {
				File root = new File(Percorso);
				File sdImageMainDirectory = new File(root, Nome);
				sdImageMainDirectory.delete();
				outputFileUri = Uri.fromFile(sdImageMainDirectory);
				fOut = new FileOutputStream(sdImageMainDirectory);
				bm.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
				fOut.flush();
				fOut.close();
			} catch (Exception ignored) {
			}
		}
	}

	public void CambiaContrastoLuminosita(float contrast, float brightness)
	{
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		String NomeImmagine = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

		Bitmap Immagine = BitmapFactory.decodeFile(Origine+Cartella+NomeImmagine);
		ColorMatrix cm = new ColorMatrix(new float[]
				{
						contrast, 0, 0, 0, brightness,
						0, contrast, 0, 0, brightness,
						0, 0, contrast, 0, brightness,
						0, 0, 0, 1, 0
				});

		Bitmap ret = Bitmap.createBitmap(Immagine.getWidth(), Immagine.getHeight(), Immagine.getConfig());

		Canvas canvas = new Canvas(ret);

		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		canvas.drawBitmap(Immagine, 0, 0, paint);

		saveBitmap(ret, Origine+Cartella, NomeImmagine);

		Utility u = new Utility();
		u.VisualizzaMultimedia();
	}

	/* private Bitmap getPreview(String uri) {
		File image = new File(uri);

		BitmapFactory.Options bounds = new BitmapFactory.Options();
		bounds.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(image.getPath(), bounds);
		if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
			return null;

		int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
				: bounds.outWidth;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = originalSize/1;

		return BitmapFactory.decodeFile(image.getPath(), opts);
	} */
}
