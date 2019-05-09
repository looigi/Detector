package com.looigi.detector.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.looigi.detector.R;
import com.looigi.detector.Variabili.VariabiliStatiche;
import com.looigi.detector.adapters.adapterListaFiles;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Utility {
	/* public void LeggeOrientamento(Context context) {
    	SQLiteDatabase myDB= null;
    	int MODE_PRIVATE=0;
        
    	try {
			myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
		   	String Sql="SELECT Orient FROM Orientamento;";
			Cursor c = myDB.rawQuery(Sql , null);
			c.moveToFirst();
			VariabiliStatiche.getInstance().Orient=c.getInt(0);
			c.close();
			
			myDB.close();
    	} catch (Exception e) {
			myDB.execSQL("Delete From Orientamento;");
			myDB.execSQL("Insert Into Orientamento Values (0);");

			myDB.close();

			VariabiliStatiche.getInstance().Orient=0;
    	}
	}
	
	public void LeggeAnteprima(Context context) {
    	SQLiteDatabase myDB= null;
    	int MODE_PRIVATE=0;
        
    	try {
			myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
		   	String Sql="SELECT Ant FROM Anteprima;";
			Cursor c = myDB.rawQuery(Sql , null);
			c.moveToFirst();
			VariabiliStatiche.getInstance().Anteprima=c.getString(0);
			c.close();
			
			myDB.close();
    	} catch (Exception e) {
			myDB.execSQL("Delete From Anteprima;");
			myDB.execSQL("Insert Into Anteprima Values ('N');");

			myDB.close();

			VariabiliStatiche.getInstance().Anteprima="N";
    	}
	}
	
	public void LeggeNumeroScatti(Context context) {
    	SQLiteDatabase myDB= null;
    	int MODE_PRIVATE=0;
    	
    	try {
			myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
		   	String Sql="SELECT Numero FROM NumeroScatti;";
			Cursor c = myDB.rawQuery(Sql , null);
			c.moveToFirst();
			VariabiliStatiche.getInstance().numScatti=c.getInt(0);
			c.close();
			
			myDB.close();
    	} catch (Exception e) {
			myDB.execSQL("Delete From NumeroScatti;");
			myDB.execSQL("Insert Into NumeroScatti Values (1);");

			myDB.close();

			VariabiliStatiche.getInstance().numScatti=1;
    	}
	}

	public void LeggeVibrazione(Context context) {
    	SQLiteDatabase myDB= null;
    	int MODE_PRIVATE=0;
    	
    	try {
			myDB = context.openOrCreateDatabase("Spiator", MODE_PRIVATE, null);
		   	String Sql="SELECT Vibraz FROM Vibrazione;";
			Cursor c = myDB.rawQuery(Sql , null);
			c.moveToFirst();
			VariabiliStatiche.getInstance().Vibrazione=c.getString(0);
			c.close();
			
			myDB.close();
    	} catch (Exception e) {
			myDB.execSQL("Delete From Vibrazione;");
			myDB.execSQL("Insert Into Vibrazione Values ('S');");

			myDB.close();

			VariabiliStatiche.getInstance().Vibrazione="S";
    	}
	} */
	
	public String PrendeNomeFile(String Percorso) {
		String Ritorno=Percorso;
		
		for (int i=Ritorno.length()-1;i>0;i--) {
			if (Ritorno.substring(i, i+1).equals("/")) {
				Ritorno=Ritorno.substring(i+1,Ritorno.length());
				break;
			}
		}
		
		return Ritorno;
	}
	
	public String PrendeNomeCartella(String Percorso) {
		String Ritorno=Percorso;
		
		for (int i=Ritorno.length()-1;i>0;i--) {
			if (Ritorno.substring(i, i+1).equals("/")) {
				Ritorno=Ritorno.substring(0,i);
				break;
			}
		}
		
		return Ritorno;
	}
	
	public void RinominaFile(String Percorso, String VecchioNome, String NuovoNome) {
		try {
			File from = new File(Percorso, VecchioNome);
			File to = new File(Percorso, NuovoNome);
			from.renameTo(to);
    	} catch (Exception ignored) {
    	}
	}

	public void EliminaFile(String NomeFile) {
		try {
        	File file = new File(NomeFile);
        	@SuppressWarnings("unused")
			boolean deleted = file.delete();
    	} catch (Exception ignored) {
    		
    	}
	}

	public void DeCriptaFiles(Context context) {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		int cambiate = 0;

		File directory = new File(Origine + Cartella);
		File[] files = directory.listFiles();
		for  (File f : files)
		{
			String n = f.getName();
			if (n.toUpperCase().contains(".DBF") && !n.toUpperCase().contains(".PV3")) {
				n = n.substring(0, n.indexOf("."));
				n += ".jpg";

				File to = new File(Origine+Cartella, n);
				f.renameTo(to);
                removeKeyFromFile(Origine+Cartella, n, n);

				cambiate++;
			}
			if (n.toUpperCase().contains(".DBV") && !n.toUpperCase().contains(".PV3")) {
				n = n.substring(0, n.indexOf("."));
				n += ".mp4";

				File to = new File(Origine+Cartella, n);
				f.renameTo(to);
                removeKeyFromFile(Origine+Cartella, n, n);

				cambiate++;
			}
			if (n.toUpperCase().contains(".DBA") && !n.toUpperCase().contains(".PV3")) {
				n = n.substring(0, n.indexOf("."));
				n += ".3gp";

				File to = new File(Origine+Cartella, n);
				f.renameTo(to);
                removeKeyFromFile(Origine+Cartella, n, n);

				cambiate++;
			}
		}

		CaricaMultimedia();
		VisualizzaMultimedia();

		VisualizzaPOPUP(context, "Fatto. Immagini decriptate: "+cambiate, false, 0);
	}

	public void CriptaFiles(Context context) {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		int cambiate = 0;

		File directory = new File(Origine + Cartella);
		File[] files = directory.listFiles();
		for  (File f : files)
		{
			String n = f.getName();
			if (n.toUpperCase().contains(".JPG")) {
				n = n.substring(0, n.indexOf("."));
				n += ".dbf";

				File to = new File(Origine+Cartella, n);
				f.renameTo(to);
				addKeyToFile(Origine+Cartella, n);

				cambiate++;
			}
			if (n.toUpperCase().contains(".MP4")) {
				n = n.substring(0, n.indexOf("."));
				n += ".dbv";

				File to = new File(Origine+Cartella, n);
				f.renameTo(to);
				addKeyToFile(Origine+Cartella, n);

				cambiate++;
			}
			if (n.toUpperCase().contains(".3GP")) {
				n = n.substring(0, n.indexOf("."));
				n += ".dba";

				File to = new File(Origine+Cartella, n);
				f.renameTo(to);
				addKeyToFile(Origine+Cartella, n);

				cambiate++;
			}
		}

		CaricaMultimedia();
		VisualizzaMultimedia();

		VisualizzaPOPUP(context, "Fatto. Immagini criptate: "+cambiate, false, 0);
	}

    private void removeKeyFromFile(String Path, String Filetto, String FilettoNuovo) {
		if (!Filetto.toUpperCase().contains(".PV3")) {
			String Filetto2 = Filetto;
			Filetto2 = Filetto2.replace(".jpg", ".dbf");

			String datiExif = "";

			if (!Filetto2.toUpperCase().contains(".3GP") && !Filetto2.toUpperCase().contains(".MP4") && !FilettoNuovo.toUpperCase().contains("APPOGGIO")) {
				datiExif = LeggeFileDiTesto(Path + Filetto2 + ".PV3");
			}

			byte[] bytes = {};

			try {
				File f = new File(Path, Filetto);
				FileInputStream fis = new FileInputStream(f);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];

				try {
					for (int readNum; (readNum = fis.read(buf)) != -1; ) {
						bos.write(buf, 0, readNum);
					}
				} catch (IOException ignored) {

				}

				byte[] altro = {68, 69, 84, 69, 67, 84, 79, 82};
				byte[] bytesApp = bos.toByteArray();
				if (bytesApp[0] == altro[0] && bytesApp[1] == altro[1] && bytesApp[2] == altro[2]) {
					bytes = Arrays.copyOfRange(bytesApp, altro.length, bytesApp.length);
				} else {
					bytes = bytesApp;
				}
			} catch (FileNotFoundException ignored) {

			}

			File someFile = new File(Path, FilettoNuovo);
			try {
				someFile.delete();
			} catch (Exception ignored) {

			}

			try {
				FileOutputStream fos = new FileOutputStream(someFile);
				try {
					fos.write(bytes);
					fos.flush();
					fos.close();

					if (!datiExif.isEmpty()) {
						String c[] = datiExif.split(";", -1);
						ExifInterface exif = new ExifInterface(Path + FilettoNuovo);
						exif.setAttribute(ExifInterface.TAG_ARTIST, c[0]);
						exif.setAttribute(ExifInterface.TAG_MODEL, c[1]);
						exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, c[2]);
						exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, c[3]);
						exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, c[4]);
						exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, c[5]);
						exif.saveAttributes();

						File f = new File(Path + Filetto2 + ".PV3");
						if (f.exists()) {
							f.delete();
						}
					}
				} catch (IOException ignored) {

				}
			} catch (FileNotFoundException ignored) {

			}
		}
    }

	public void addKeyToFile(String Path, String Filetto) {
		byte[] bytes={};

		Boolean OkEXIF=false;
		String artista = "";
		String model = "";
		String lat = "";
		String latRef = "";
		String lon = "";
		String lonref = "";

		if (Filetto.toUpperCase().contains(".DBF")) {
            try {
                ExifInterface exif = new ExifInterface(Path + Filetto);
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
        }

		try {
			File f = new File(Path, Filetto);
			FileInputStream fis = new FileInputStream(f);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];

			try {
				for (int readNum; (readNum = fis.read(buf)) != -1; ) {
					bos.write(buf, 0, readNum);
				}
			} catch (IOException ignored) {

			}

			byte[] altro = {68,69,84,69,67,84,79,82};
			byte[] bytesApp = bos.toByteArray();
			bytes=new byte[bytesApp.length+altro.length];

			int i=0;
			for (byte b : altro) {
				bytes[i]=b;
				i++;
			}
			for (byte b : bytesApp) {
				bytes[i]=b;
				i++;
			}
		} catch (FileNotFoundException ignored) {

		}

		File someFile = new File(Path, Filetto);
		try {
			someFile.delete();
		} catch (Exception ignored) {

		}

		try {
			FileOutputStream fos = new FileOutputStream(someFile);
			try {
				fos.write(bytes);
				fos.flush();
				fos.close();

				if (OkEXIF) {
					String datiExif = artista + ";" + model + ";" + lat + ";" + latRef + ";" + lon + ";" + lonref + ";";
					CreaFileDiTesto(Path, Filetto + ".PV3", datiExif);
				}
			} catch (IOException ignored) {

			}
		} catch (FileNotFoundException ignored) {

		}
	}

	private String LeggeFileDiTesto(String path){
		StringBuilder text = new StringBuilder();

		File file = new File(path);
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;

				while ((line = br.readLine()) != null) {
					text.append(line);
				}
				br.close();
			} catch (IOException ignored) {

			}
		}

		return text.toString();
	}

	public void CreaFileDiTesto(String Percorso, String sFileName, String sBody) {
		try {
			File gpxfile = new File(Percorso, sFileName);
			if (gpxfile.exists()) {
				gpxfile.delete();
			}
			FileWriter writer = new FileWriter(gpxfile);
			writer.append(sBody);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	// public String SistemaNick(String Cosa) {
	// 	String Ritorno=Cosa;
	//
	// 	Ritorno=Ritorno.replace(" ", "_");
	// 	Ritorno=Ritorno.replace("/", "_");
	// 	Ritorno=Ritorno.replace("\\", "_");
	// 	Ritorno=Ritorno.replace("?", "_");
	// 	Ritorno=Ritorno.replace("*", "_");
	// 	Ritorno=Ritorno.replace(">", "_");
	// 	Ritorno=Ritorno.replace("<", "_");
	// 	Ritorno=Ritorno.replace("&", "_");
	// 	Ritorno=Ritorno.replace("%", "_");
	// 	Ritorno=Ritorno.replace("=", "_");
	// 	Ritorno=Ritorno.replace("\"", "_");
	//
	// 	return Ritorno;
	// }

	public String PrendeNomeImmagine() {
    	Calendar Oggi = Calendar.getInstance();

        int Giorno=Oggi.get(Calendar.DAY_OF_MONTH);
        int Mese=Oggi.get(Calendar.MONTH)+1;
        int Anno=Oggi.get(Calendar.YEAR);
        int Ora=Oggi.get(Calendar.HOUR_OF_DAY);
        int Minuti=Oggi.get(Calendar.MINUTE);
        int Secondi=Oggi.get(Calendar.SECOND);
        String Ritorno="";

        Ritorno=Integer.toString(Anno);
        Ritorno+=String.format("%02d", Mese); //Integer.toString(Mese);
        Ritorno+=String.format("%02d", Giorno); //Integer.toString(Giorno);
        Ritorno+="_"+String.format("%02d", Ora); // Integer.toString(Ora);
        Ritorno+=String.format("%02d", Minuti); //Integer.toString(Minuti);
        Ritorno+=String.format("%02d", Secondi); // Integer.toString(Secondi);
        
        return Ritorno;
	}
	

	public void VisualizzaPOPUP(final Context context, String Messaggio, final Boolean Tasti, final int QualeOperazione) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("");
		builder.setMessage(Messaggio);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });		
		if (Tasti) {
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	            }
	        });		
		}
		@SuppressWarnings("unused")
		AlertDialog dialog = builder.show();
	}
	
	public void CreaCartelle(String Origine, String Cartella) {
		for (int i=1;i<Cartella.length();i++) {
			if (Cartella.substring(i,i+1).equals("/")) {
				CreaCartella(Origine+Cartella.substring(0,i));
			}
		}
	}
	
    private void CreaCartella(String Percorso) {
		try {
			File dDirectory = new File(Percorso);
			dDirectory.mkdirs();
		} catch (Exception ignored) {
			
		}  
	}
	
	public void ControllaFileNoMedia(String Origine, String Cartella) {
		String NomeFile=".nomedia";
		
		File file=new File(Origine+"/"+Cartella+"/"+NomeFile);
		if (!file.exists()) {
	        File gpxfile = new File(Origine+"/"+Cartella, NomeFile);
	        FileWriter writer;
			try {
				writer = new FileWriter(gpxfile);
		        writer.append(".");
		        writer.flush();
		        writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void CaricaMultimedia() {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;

		File directory = new File(Origine + Cartella);
		File[] files = directory.listFiles();
		VariabiliStatiche.getInstance().setImmagini(new ArrayList<String>());
		VariabiliStatiche.getInstance().totImmagini=0;
		for  (File f : files)
		{
			String n = f.getName();
			if (n.toUpperCase().contains(".JPG") || n.toUpperCase().contains(".DBF") ||
					n.toUpperCase().contains(".MP4") || n.toUpperCase().contains(".DBV") ||
					n.toUpperCase().contains(".3GP") || n.toUpperCase().contains(".DBA")) {
				if (!n.toUpperCase().contains(".PV3")) {
					VariabiliStatiche.getInstance().getImmagini().add(n);
					VariabiliStatiche.getInstance().totImmagini++;
				}
			}
		}
		VariabiliStatiche.getInstance().numMultimedia =VariabiliStatiche.getInstance().totImmagini-1;
		VariabiliStatiche.getInstance().getImg().setImageDrawable(null);
		VariabiliStatiche.getInstance().getImg().setImageResource(0);
	}

	private void copyFile(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		try {
			OutputStream out = new FileOutputStream(dst);
			try {
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}

	public void VisualizzaMultimedia() {
		StopAudio();
		StopVideo();

		if (VariabiliStatiche.getInstance().numMultimedia <VariabiliStatiche.getInstance().getImmagini().size()) {
			String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
			String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
			if (VariabiliStatiche.getInstance().numMultimedia>-1) {
				String NomeMultimedia = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

				if (NomeMultimedia.toUpperCase().contains(".JPG") || NomeMultimedia.toUpperCase().contains(".DBF")) {
					File f = new File(Origine + Cartella, "Appoggio.jpg");
					File o = new File(Origine + Cartella, NomeMultimedia);

					try {
						copyFile(o, f);
					} catch (IOException ignored) {

					}
					removeKeyFromFile(Origine + Cartella, NomeMultimedia, "Appoggio.jpg");

					VariabiliStatiche.getInstance().getImg().setImageBitmap(BitmapFactory.decodeFile(Origine + Cartella + "Appoggio.jpg"));

					f.delete();

					PhotoViewAttacher photoAttacher;
					photoAttacher = new PhotoViewAttacher(VariabiliStatiche.getInstance().getImg());
					photoAttacher.update();

					VariabiliStatiche.getInstance().getImg().setVisibility(LinearLayout.VISIBLE);
					VariabiliStatiche.getInstance().getAudio().setVisibility(LinearLayout.GONE);
					VariabiliStatiche.getInstance().getvView().setVisibility(LinearLayout.GONE);

					if (NomeMultimedia.toUpperCase().contains(".DBF")) {
						VariabiliStatiche.getInstance().getBtnFlipX().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getBtnFlipY().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getBtnRuotaDes().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getBtnRuotaSin().setVisibility(LinearLayout.GONE);
					} else {
						VariabiliStatiche.getInstance().getBtnFlipX().setVisibility(LinearLayout.VISIBLE);
						VariabiliStatiche.getInstance().getBtnFlipY().setVisibility(LinearLayout.VISIBLE);
						VariabiliStatiche.getInstance().getBtnRuotaDes().setVisibility(LinearLayout.VISIBLE);
						VariabiliStatiche.getInstance().getBtnRuotaSin().setVisibility(LinearLayout.VISIBLE);
					}

					VariabiliStatiche.getInstance().getTxtImm().setText("File immagine " + (VariabiliStatiche.getInstance().numMultimedia + 1) +
							"/" + VariabiliStatiche.getInstance().totImmagini);
				} else {
					if (NomeMultimedia.toUpperCase().contains(".3GP") || NomeMultimedia.toUpperCase().contains(".DBA")) {
						VariabiliStatiche.getInstance().getImg().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getAudio().setVisibility(LinearLayout.VISIBLE);
						VariabiliStatiche.getInstance().getvView().setVisibility(LinearLayout.GONE);

						VariabiliStatiche.getInstance().getBtnFlipX().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getBtnFlipY().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getBtnRuotaDes().setVisibility(LinearLayout.GONE);
						VariabiliStatiche.getInstance().getBtnRuotaSin().setVisibility(LinearLayout.GONE);

						VariabiliStatiche.getInstance().getTxtImm().setText("File audio " + (VariabiliStatiche.getInstance().numMultimedia + 1) +
								"/" + VariabiliStatiche.getInstance().totImmagini);
					} else {
						if (NomeMultimedia.toUpperCase().contains(".MP4") || NomeMultimedia.toUpperCase().contains(".DBV")) {
							VariabiliStatiche.getInstance().getImg().setVisibility(LinearLayout.GONE);
							VariabiliStatiche.getInstance().getAudio().setVisibility(LinearLayout.GONE);
							VariabiliStatiche.getInstance().getvView().setVisibility(LinearLayout.VISIBLE);

							VariabiliStatiche.getInstance().getBtnFlipX().setVisibility(LinearLayout.GONE);
							VariabiliStatiche.getInstance().getBtnFlipY().setVisibility(LinearLayout.GONE);
							VariabiliStatiche.getInstance().getBtnRuotaDes().setVisibility(LinearLayout.GONE);
							VariabiliStatiche.getInstance().getBtnRuotaSin().setVisibility(LinearLayout.GONE);

							VariabiliStatiche.getInstance().getTxtImm().setText("File video " + (VariabiliStatiche.getInstance().numMultimedia + 1) +
									"/" + VariabiliStatiche.getInstance().totImmagini);
						}
					}
				}

				VariabiliStatiche.getInstance().getTxtNomeImm().setText(NomeMultimedia);
			} else {
				VariabiliStatiche.getInstance().getTxtNomeImm().setText("");
			}
		} else  {
			VariabiliStatiche.getInstance().getTxtImm().setText("Nessuna immagine rilevata");
			VariabiliStatiche.getInstance().getTxtNomeImm().setText("");
		}
	}

	public void PlayAudio() {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
		String NomeMultimedia = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

		try {
			VariabiliStatiche.getInstance().setMp(new MediaPlayer());
			VariabiliStatiche.getInstance().getMp().setDataSource(Origine+Cartella+NomeMultimedia);
			VariabiliStatiche.getInstance().getMp().prepare();
			VariabiliStatiche.getInstance().getMp().start();
			VariabiliStatiche.getInstance().getAudio().setImageResource(R.drawable.pausa);
			VariabiliStatiche.getInstance().StaSuonando = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StopAudio() {
		if (VariabiliStatiche.getInstance().getMp()!=null) {
			try {
				VariabiliStatiche.getInstance().getMp().stop();
				VariabiliStatiche.getInstance().getMp().release();
				VariabiliStatiche.getInstance().setMp(null);
				VariabiliStatiche.getInstance().StaSuonando = false;
				VariabiliStatiche.getInstance().getAudio().setImageResource(R.drawable.play);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void PlayVideo() {
		if (!VariabiliStatiche.getInstance().StaVedendo) {
			String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
			String Cartella=VariabiliStatiche.getInstance().PathApplicazione;
			String NomeMultimedia = VariabiliStatiche.getInstance().getImmagini().get(VariabiliStatiche.getInstance().numMultimedia);

			try {
				VariabiliStatiche.getInstance().getvView().setVideoURI(Uri.parse(Origine + Cartella + NomeMultimedia));

				VariabiliStatiche.getInstance().getvView().start();
				VariabiliStatiche.getInstance().StaVedendo = true;
			} catch (Exception ignored) {

			}
		}
	}

	public void StopVideo() {
		if (VariabiliStatiche.getInstance().StaVedendo) {
			VariabiliStatiche.getInstance().getvView().pause();
			VariabiliStatiche.getInstance().StaVedendo = false;
		}
	}

	public void getResizedBitmap(String simage, String filename, int newHeight, int newWidth) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap image = BitmapFactory.decodeFile(simage, options);
		int width = image.getWidth();
		int height = image.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
				matrix, false);
		try (FileOutputStream out = new FileOutputStream(filename)) {
			resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String TransformError(String error) {
		String Return=error;

		if (Return.length()>250) {
			Return=Return.substring(0,247)+"...";
		}
		Return=Return.replace("\n"," ");

		return Return;
	}

	public String PrendeErroreDaException(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		return TransformError(errors.toString());
	}

	public void ScriveKM() {
		float km = VariabiliStatiche.getInstance().getKmPercorsi();
		String s = "m.";
		if (km>1000) {
			km/=1000;
			s="km";
		}
		VariabiliStatiche.getInstance().getTxtKM().setText(Float.toString(km)+" "+s);
	}

	public void ScriveKMPassati(float km) {
		String s = "m.";
		if (km>1000) {
			km/=1000;
			s="km";
		}
		VariabiliStatiche.getInstance().getTxtKM().setText(Float.toString(km)+" "+s);
	}

	public boolean ePari(int numero) {
		if ((numero % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void InviaFilesGPS() {
		VariabiliStatiche.getInstance().getRltUpload().setVisibility(LinearLayout.VISIBLE);

		String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella=VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Paths";

		Utility u=new Utility();
		u.CreaCartelle(Origine, Cartella+"/");

		List<String> data=new ArrayList<String>();

		File directory = new File(Origine + "/" + Cartella);
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			String n = files[i].getName();
			n = n.replace("LL_", "");
			n = n.replace("MM_", "");
			n = n.replace(".txt", "");

			Boolean Ok = true;
			for (int k = 0; k < data.size(); k++) {
				if (n.equals(data.get(k))) {
					Ok = false;
					break;
				}
			}

			if (Ok) {
				data.add(n);
			}
		}

		if (data != null) {
			VariabiliStatiche.getInstance().setFilesToUpload(data);
			adapterListaFiles adapter = new adapterListaFiles(
					VariabiliStatiche.getInstance().getContext().getApplicationContext(),
					android.R.layout.simple_list_item_1,
					data);

			VariabiliStatiche.getInstance().getLstUpload().setAdapter(adapter);
		} else {
			VariabiliStatiche.getInstance().setFilesToUpload(null);
		}
	}
}
