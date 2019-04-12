/* package com.looigi.spiatore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class Galleria extends Activity implements View.OnTouchListener {
	private int SchermoX;
	private int SchermoY;
	private Gallery gallery;
	private Context context;
	private ImageView iView;
	private Matrix matrix;
	private Matrix savedMatrix;
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	private Double oldDist = 1d;
	private PointF start = new PointF();
	private PointF mid = new PointF();

    public Galleria(Context c, Gallery gall, final ImageView iv, final int sX, final int sY) {
		SchermoX=sX;
		SchermoY=sY;
		gallery=gall;
		context=c;
		iView=iv;
		matrix=new Matrix();
		savedMatrix=new Matrix();
		iView.setOnTouchListener(this);

		RefreshFiles();
    }

    public void RefreshFiles() {
		VariabiliStatiche.getInstance().NomeFileSelezionato="";

		iView.setImageResource(android.R.color.transparent);

		LeggeFilettiInCartella();

		gallery.setAdapter(new ImageAdapter(context));
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				VariabiliStatiche.getInstance().NomeFileSelezionato=ListaFiletti.get(position);
				Bitmap i=getPreview(ListaFiletti.get(position));
				iView.setImageBitmap(i);
				int height=iView.getDrawable().getIntrinsicHeight();
				int width=iView.getDrawable().getIntrinsicWidth();
				CentraEZooma(iView,width,height);
			}
		});
	}

	public void RuotaImmagine(String Imma, int Quanto) {
		Bitmap Immagine = null;
		Immagine=getPreview(Imma);
		Matrix matrix = new Matrix();
		matrix.postRotate(Quanto);

		Bitmap rotated = Bitmap.createBitmap(Immagine, 0, 0, Immagine.getWidth(), Immagine.getHeight(),
				matrix, true);

		String Percorso="";
		String NomeFile="";

		for (int i=Imma.length()-1;i>0;i--) {
			if (Imma.substring(i,i+1).equals("/")==true) {
				Percorso=Imma.substring(0, i);
				NomeFile=Imma.substring(i+1, Imma.length());
				break;
			}
		}
		saveBitmap(rotated, Percorso, NomeFile);
		iView.setImageBitmap(rotated);
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
			} catch (Exception e) {
			}
		}
	}

	private Bitmap getPreview(String uri) {
		File image = new File(uri);

		BitmapFactory.Options bounds = new BitmapFactory.Options();
		bounds.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(image.getPath(), bounds);
		if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
			return null;

		int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
				: bounds.outWidth;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = originalSize/SchermoY;

		return BitmapFactory.decodeFile(image.getPath(), opts);
	}

	private void CentraEZooma(ImageView iv, int width, int height) {
		PointF start = new PointF();
		PointF mid = new PointF();
		Matrix matrix = new Matrix();
		Matrix savedMatrix = new Matrix();

		iv.setScaleType(ImageView.ScaleType.MATRIX);
		start = new PointF();
		mid = new PointF();
		matrix = new Matrix();
		savedMatrix = new Matrix();

		matrix.set(savedMatrix);

		float ww;
		float hh;

		ww=width;
		hh=height;
		ww/=2;
		hh/=2;

		if (width>SchermoX || height>SchermoY) {
			float dx=(float) SchermoX/(float) width;
			float dy=(float) SchermoY/(float) height;
			float scale;
			if (dx>dy) {
				scale=dy;
			} else {
				scale=dx;
			}
			matrix.postScale(scale, scale, mid.x, mid.y);

			ww=(float) width * scale;
			hh=(float) height * scale;
			ww/=2;
			hh/=2;
		}

		matrix.postTranslate(((float) SchermoX/2f)-ww, ((float) SchermoY/2f)-hh);

		iv.setImageMatrix(matrix);
	}

	public void EliminaImmagine(String Immagine) {
		String Imma=Immagine;

		GestioneFilesCartelle gfc=new GestioneFilesCartelle();
		gfc.EliminaFile(Imma);
	}

	@SuppressWarnings("deprecation")
	public class ImageAdapter extends BaseAdapter {
        private Context context;
        //private int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            //TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
            //itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            //a.recycle();
        }
        // returns the number of images
        public int getCount() {
            return ListaFiletti.size();
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			Bitmap bitmap = BitmapFactory.decodeFile(ListaFiletti.get(position));
			imageView.setImageBitmap(bitmap);
			//imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));

            return imageView;
        }
    }

	List<String> ListaFiletti;

  	private void LeggeFilettiInCartella() {
		String Origine= Environment.getExternalStorageDirectory().getAbsolutePath();
		String Cartella="/LooigiSoft/Spiator/DB/";

		ListaFiletti=new ArrayList<String>();
		LeggeFilettiDentroCartella(Origine+"/"+Cartella);
	}

	private void LeggeFilettiDentroCartella(String Percorso) {
		File root = new File(Percorso);
		File [] files = root.listFiles();

		if (files!=null) {
			ScannaFilettiDentroCartella(files);
		}
	}

	@SuppressLint("DefaultLocale")
	private void ScannaFilettiDentroCartella(File[] files) {
		String NomeFile="";
		String Estensione="";

		for (int num=0;num<files.length;num++) {
			File file=files[num];
			if (file.isDirectory()==false) {
				NomeFile=file.getPath().toString();
				Estensione=PrendeEstensione(NomeFile.toUpperCase());

				if (Estensione.equals("JPG")==true || Estensione.equals("DBF")==true ) {
					ListaFiletti.add(NomeFile);
				}
			}
		}
	}

	@SuppressLint("DefaultLocale")
	public String PrendeEstensione(String NomeFile) {
		String Ritorno=NomeFile;
		String Carattere="";

		for (int i=Ritorno.length()-1;i>0;i--) {
			Carattere=Ritorno.substring(i, i+1);
			if (Carattere.equals(".")==true) {
				Ritorno=Ritorno.substring(i+1, Ritorno.length());
				Ritorno=Ritorno.trim().toUpperCase();
				break;
			}
		}

		return Ritorno;
	}
	// Parte che zooma

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		ImageView view = (ImageView) v;
		view.setScaleType(ImageView.ScaleType.MATRIX);
		Double scale;

		dumpEvent(event);
		// Handle touch events here...

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:   // first finger down only
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());
				mode = DRAG;
				break;

			case MotionEvent.ACTION_UP: // first finger lifted

			case MotionEvent.ACTION_POINTER_UP: // second finger lifted
				mode = NONE;
				//Log.d(TAG, "mode=NONE");
				break;

			case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
				oldDist = spacing(event);
				//Log.d(TAG, "oldDist=" + oldDist);
				if (oldDist > 5f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;
					//Log.d(TAG, "mode=ZOOM");
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
				}
				else if (mode == ZOOM) {
					// pinch zooming
					Double newDist1 = spacing(event);

					if (newDist1 > 5f) {
						matrix.set(savedMatrix);
						scale = newDist1 / oldDist; // setting the scaling of the
						// matrix...if scale > 1 means
						// zoom in...if scale < 1 means
						// zoom out
						float s = scale.floatValue();
						matrix.postScale(s, s , mid.x, mid.y);
					}
				}
				break;
		}

		view.setImageMatrix(matrix); // display the transformation on screen

		return true; // indicate event was handled
	}

	private Double spacing(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);

		return Math.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event)
	{
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);

		point.set(x / 2, y / 2);
	}

	// Show an event in the LogCat view, for debugging
	private void dumpEvent(MotionEvent event)
	{
	}

}
*/