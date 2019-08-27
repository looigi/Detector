package com.looigi.detector.Fotocamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.looigi.detector.Utilities.GestioneImmagini;
import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Utilities.Utility;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GBTakePictureNoPreview implements SurfaceHolder.Callback {
    private Camera myCamera = null;
    private int camId;
    private Context context=null;
    private String fileName = "";
    private String fileNameSimple = "";
    private String path="";
    private String nFile="";
    private boolean usingLandscape = false;
	private Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLog());

    public void ImpostaContext (Context context) {
        this.context = context;
        l.ScriveLog("Context impostato:"+context);
    }

    public void setUseFrontCamera() {
        try {
            int c = GBCameraUtil.findFrontFacingCamera();

            if (c != -1) {
                camId = c;
            }
            l.ScriveLog("Impostata camera frontale");
        } catch (Exception e) {
            l.ScriveLog("Impostata camera frontale. ERROR: "+e.getMessage());
        }
    }

    public void setUseBackCamera() {
        try {
            int c = GBCameraUtil.findBackFacingCamera();

            if (c != -1) {
                camId = c;
            }
            l.ScriveLog("Impostata camera retro");
        } catch (Exception e) {
            l.ScriveLog("Impostata camera retro. ERROR: "+e.getMessage());
        }
    }

    public void setFileName (String Path, String fileName) {
        this.fileNameSimple=fileName;
        this.fileName = Path+fileName;
        path=Path;
        nFile=fileName;
        l.ScriveLog("Impostato nome file");
    }

    public void setLandscape () {
        this.usingLandscape = true;
    }

    public void setPortrait () {
        this.usingLandscape = false;
    }

    public boolean cameraIsOk() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && camId > -1;
    }
    
    public List<String> RitornaRisoluzioni() {
        List<String> Dimens=new ArrayList<String>();
        
        if (cameraIsOk()) {
            if (myCamera == null)  {
                try {
                    myCamera = Camera.open(camId);
                } catch (Exception ignored) {
                    myCamera=null;
                }
            }
            
            if (myCamera != null) {
                Camera.Parameters parameters=null;

                try {
                    parameters = myCamera.getParameters();
                } catch (Exception ignored) {
                    parameters=null;
                }

                if (parameters!=null) {
                    List<Size> sizes = parameters.getSupportedPictureSizes();
                    int Quante = sizes.size();
                    for (int i = 0; i < Quante; i++) {
                        String dimeY = Integer.toString(sizes.get(i).height);
                        String dimeX = Integer.toString(sizes.get(i).width);

                        Dimens.add(dimeX + "x" + dimeY);
                    }
                    myCamera.release();
                    myCamera = null;
                }
            }
        }
        
		return Dimens;
    }
	
    public List<String> RitornaRisoluzioniVideo() {
        List<String> Dimens=new ArrayList<String>();
        
        if (cameraIsOk()) {
            if (myCamera == null)  {
                myCamera = Camera.open(camId);
            }
            
            if (myCamera != null) {
                Camera.Parameters parameters = null;
                try {
                    parameters = myCamera.getParameters();
                } catch (Exception ignored) {
                    parameters=null;
                }

                if (parameters!=null) {
                    List<Size> sizes = parameters.getSupportedVideoSizes();
                    int Quante = sizes.size();
                    for (int i = 0; i < Quante; i++) {
                        String dimeY = Integer.toString(sizes.get(i).height);
                        String dimeX = Integer.toString(sizes.get(i).width);

                        Dimens.add(dimeX + "x" + dimeY);
                    }
                    myCamera.release();
                    myCamera = null;
                }
            }
        }
        
		return Dimens;
    }
    
    @SuppressWarnings({ "deprecation", "static-access" })
	@SuppressLint("NewApi")
	public void takePicture (String RisoluzioneX, String RisoluzioneY) {
        // VariabiliStatiche.getInstance().StoScattando = true;
        try {
            l.ScriveLog("Controllo se la camera è ok");
	        if (cameraIsOk()) {
	            // Attende(2000);
	
	            l.ScriveLog("Apro camera");
	            if (myCamera == null)  {
	                try {
                        myCamera = Camera.open(camId);
                    } catch (Exception e) {
	                    myCamera=null;
                        StringWriter errors = new StringWriter();
                        e.printStackTrace(new PrintWriter(errors));
                        l.ScriveLog("Open: Errore: "+errors.toString());
                    }
	            }
	            
	            if (myCamera != null) {
	                l.ScriveLog("takePicture: Scatto 1");
	                
	                //SurfaceView surfaceView = new SurfaceView(context);
	                //surfaceView.setFocusable(true);
	
	                //SurfaceHolder holder = surfaceView.getHolder();
	                //holder.addCallback(this);
	                //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

                    Camera.Parameters parameters = null;
                    try {
                        parameters = myCamera.getParameters();
                    } catch (Exception e) {
                        StringWriter errors = new StringWriter();
                        e.printStackTrace(new PrintWriter(errors));
                        l.ScriveLog("Parameters: Errore: "+errors.toString());
                    }
                    
                    /*for (String f : parameters.getSupportedFocusModes()) {
                        if (f == Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) {
                        	parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                            break;
                        }
                    }*/

                    if (parameters!=null) {
                        try {
                            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        } catch (Exception e) {
                            StringWriter errors = new StringWriter();
                            e.printStackTrace(new PrintWriter(errors));
                            l.ScriveLog("takePicture: Errore su Focus: " + errors.toString());
                        }

                        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 15) {
                            parameters.setVideoStabilization(true);
                        }

                        List<Size> sizes = parameters.getSupportedPictureSizes();

                        Size optimalSize = GBCameraUtil.getOptimalPreviewSize(sizes, Integer.parseInt(RisoluzioneX), Integer.parseInt(RisoluzioneY));

                        int sdkBuildVersion = Integer.parseInt(android.os.Build.VERSION.SDK);

                        l.ScriveLog("takePicture: Scatto 2");

                        parameters.setFlashMode(parameters.FLASH_MODE_OFF);

                        if (sdkBuildVersion < 5 || usingLandscape) {
                            if (optimalSize.width < optimalSize.height || usingLandscape)
                                parameters.setPictureSize(optimalSize.height, optimalSize.width);
                            else
                                parameters.setPictureSize(optimalSize.width, optimalSize.height);
                        } else {
                            switch (context.getResources().getConfiguration().orientation) {
                                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                                    if (optimalSize.height > optimalSize.width) {
                                        parameters.setRotation(camId == GBCameraUtil.findFrontFacingCamera() ? 270 : 90);
                                    }

                                    break;
                                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                                    if (optimalSize.width > optimalSize.height) {
                                        parameters.setRotation(camId == GBCameraUtil.findFrontFacingCamera() ? 270 : 90);
                                    }

                                    break;
                            }

                            parameters.setPictureSize(optimalSize.width, optimalSize.height);
                        }

                        l.ScriveLog("takePicture: Scatto 3");

                        parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);

                        try {
                            myCamera.setParameters(parameters);
                        } catch (Exception e) {
                            StringWriter errors = new StringWriter();
                            e.printStackTrace(new PrintWriter(errors));
                            l.ScriveLog("takePicture: Errore su setParameters: " + errors.toString());
                        }

                        l.ScriveLog("takePicture: Scatto 4");

                        boolean Errore = false;

                        //try {
                        try {
                            myCamera.setPreviewTexture(new SurfaceTexture(10));
                        } catch (IOException e1) {
                            StringWriter errors = new StringWriter();
                            e1.printStackTrace(new PrintWriter(errors));
                            l.ScriveLog("takePicture: Errore setPreviewTexture: " + errors.toString());
                        }

                        //myCamera.setPreviewDisplay(surfaceView.getHolder());
                        try {
                            myCamera.startPreview();
                        } catch (Exception e1) {
                            StringWriter errors = new StringWriter();
                            e1.printStackTrace(new PrintWriter(errors));
                            l.ScriveLog("takePicture: Errore start preview: " + errors.toString());
                        }
                        //} catch (Exception e) {
                        //    Errore=true;
                        //    l.ScriveLog("takePicture: ERRORE PREVIEW");
                        //}

                        if (!Errore) {
                            l.ScriveLog("takePicture: Scatto 5");

                            // SuoniTelefono s=new SuoniTelefono();
                            // s.Imposta_suoni("mute");

                            // l.ScriveLog("takePicture: Scatto 6");

                            try {
                                myCamera.takePicture(null, null, getJpegCallback());
                                //Attende(1000);
                            } catch (Exception e) {
                                StringWriter errors = new StringWriter();
                                e.printStackTrace(new PrintWriter(errors));
                                l.ScriveLog("takePicture: Errore: " + errors.toString());
                            }

                            l.ScriveLog("takePicture: Scatto 6");

                            // s.Imposta_suoni("");
                        }
                    } else {
                        VariabiliStatiche.getInstance().StoScattando = false;
                        Toast toast=Toast.makeText(this.context, "Parametri non validi" ,Toast.LENGTH_LONG);
                        toast.show();
                    }
	            } else {
                    VariabiliStatiche.getInstance().StoScattando = false;
	    			Toast toast=Toast.makeText(this.context, "Camera non presente" ,Toast.LENGTH_LONG);
	    			toast.show();	
	            }
	        } else {
                VariabiliStatiche.getInstance().StoScattando = false;
				Toast toast=Toast.makeText(this.context, "Camera non OK" ,Toast.LENGTH_LONG);
				toast.show();				        	
	        }
            // Attende(3000);
        } catch (Exception e) {
            VariabiliStatiche.getInstance().StoScattando = false;

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            l.ScriveLog("takePicture: Errore: "+errors.toString());

            Toast toast=Toast.makeText(this.context, "Errore: "+errors.toString() ,Toast.LENGTH_LONG);
			toast.show();				
        }
    }

	private void Attende(long Quanto) {
        l.ScriveLog("Attende: "+Quanto);
		Thread.yield();
		try {
			Thread.sleep(Quanto);
		} catch (InterruptedException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            l.ScriveLog("Attende: Errore: "+errors.toString());
		}
        l.ScriveLog("Attende: Fine");
	}

    // private String getLatGeoCoordinates(Location location) {
    //     if (location == null) return "0/1,0/1,0/1000";
    //     String[] degMinSec = Location.convert(location.getLatitude(), Location.FORMAT_SECONDS).split(":");
    //     return degMinSec[0] + "/1," + degMinSec[1] + "/1," + degMinSec[2] + "/1000";
    // }
//
    // private String getLonGeoCoordinates(Location location) {
    //     if (location == null) return "0/1,0/1,0/1000";
    //     String[] degMinSec = Location.convert(location.getLongitude(), Location.FORMAT_SECONDS).split(":");
    //     return degMinSec[0] + "/1," + degMinSec[1] + "/1," + degMinSec[2] + "/1000";
    // }

    private String convert(double valore) {
        StringBuilder sb = new StringBuilder(20);

        valore = Math.abs(valore);
        final int degree = (int)valore;
        valore *= 60;
        valore -= degree * 60.0d;
        final int minute = (int)valore;
        valore *= 60;
        valore -= minute * 60.0d;
        final int second = (int)(valore * 1000.0d);

        sb.setLength(0);
        sb.append(degree);
        sb.append("/1,");
        sb.append(minute);
        sb.append("/1,");
        sb.append(second);
        sb.append("/1000,");

        return sb.toString();
    }

    private PictureCallback getJpegCallback(){
        PictureCallback jpeg=new PictureCallback() {   

        	@Override
            public void onPictureTaken(byte[] data, Camera camera) {
                l.ScriveLog("onPictureTaken: 1");

                // Attende(5000);
        		
                 if (data != null) {
                    boolean OkFoto=false;
                    FileOutputStream fos;

                    try {
                        l.ScriveLog("onPictureTaken: 2");

                        fos = new FileOutputStream(fileName);
                        fos.write(data);
                        fos.close();

                        OkFoto=true;
                    }  catch (IOException e) {
                        StringWriter errors = new StringWriter();
                        e.printStackTrace(new PrintWriter(errors));
                        l.ScriveLog("onPictureTaken: Errore: "+errors.toString());

                        Toast toast=Toast.makeText(context, "Errore" ,Toast.LENGTH_LONG);
                        toast.show();
                    }

                    if (OkFoto) {
                        String nome=fileNameSimple;
                        if (nome.contains(".")) {
                            int punto=nome.indexOf(".");
                            nome = nome.substring(0, punto);
                        }
                        String anno = nome.substring(0,4);
                        String mese = nome.substring(4,6);
                        String nome2 = nome.substring(6, nome.length());
                        String Origine=Environment.getExternalStorageDirectory().getAbsolutePath();
                        String Cartella=VariabiliStatiche.getInstance().PathApplicazioneFuori+"/Thumbs";

                        Utility u=new Utility();
                        u.CreaCartelle(Origine, Cartella+"/"+anno+"/"+mese+"/");
                        u.ControllaFileNoMedia(Origine, Cartella+"/"+anno+"/"+mese);
                        String fileDest = Origine +"/"+Cartella+"/" +  anno + "/" + mese+"/"+nome2 + ".dbf";

                        u.getResizedBitmap(fileName, fileDest, VariabiliImpostazioni.getInstance().getDimensioniThumbs(), VariabiliImpostazioni.getInstance().getDimensioniThumbs());

                        Location ll = VariabiliStatiche.getInstance().getLocGPS();

                        try {
                            if (l!=null) {
                                ExifInterface exif = new ExifInterface(fileName);
                                exif.setAttribute(ExifInterface.TAG_ARTIST, VariabiliStatiche.getInstance().getContext().getApplicationInfo().loadLabel(
                                        VariabiliStatiche.getInstance().getContext().getPackageManager()).toString());
                                exif.setAttribute(ExifInterface.TAG_MODEL, VariabiliStatiche.getInstance().getModelloTelefono());
                                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, convert(ll.getLatitude()));
                                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, ll.getLatitude() < 0 ? "S" : "N");
                                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, convert(ll.getLongitude()));
                                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, ll.getLongitude() < 0 ? "W" : "E");
                                exif.saveAttributes();
                            }
                        } catch (IOException e) {
                            StringWriter errors = new StringWriter();
                            e.printStackTrace(new PrintWriter(errors));
                            l.ScriveLog("EXIF: Errore: "+errors.toString());
                        }

                        Date todayDate = Calendar.getInstance().getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String oggi = formatter.format(todayDate);

                        int proMM = VariabiliStatiche.getInstance().getProgressivoDBMM()+1;
                        VariabiliStatiche.getInstance().getDbGpsPos().aggiungiMultimedia(
                                oggi,
                                Integer.toString(proMM),
                                Double.toString(ll.getLatitude()),
                                Double.toString(ll.getLongitude()),
                                Double.toString(ll.getAltitude()),
                                anno+";"+mese+";"+nome2,
                                "I"
                        );
                        VariabiliStatiche.getInstance().setProgressivoDBMM(proMM);

                        if (fileName.toUpperCase().contains(".DBF") ||
                                fileName.toUpperCase().contains(".DBA") ||
                                fileName.toUpperCase().contains(".DBV")
                                ) {
                            u.addKeyToFile(path, nFile);
                        }
                        VariabiliStatiche.getInstance().ScriveDatiAVideo();
                    }
                 } else {
        			Toast toast=Toast.makeText(context, "Dati camera nulli" ,Toast.LENGTH_LONG);
        			toast.show();				
                 }

                 l.ScriveLog("onPictureTaken: 3");

                 try {
                     myCamera.stopPreview();
    //                 mPreviewRunning = false;
                     myCamera.release();
                     myCamera = null;
                 } catch (Exception e) {
                     StringWriter errors = new StringWriter();
                     e.printStackTrace(new PrintWriter(errors));
                     l.ScriveLog("onPictureTaken: Errore stop preview: "+errors.toString());
                }
                
                 l.ScriveLog("onPictureTaken: 4");

                 // Utility u=new Utility();
    			 // u.LeggeOrientamento(context);
			 	 if (VariabiliImpostazioni.getInstance().getOrientamento() > 0) {
    			 	GestioneImmagini g=new GestioneImmagini();
    			 	g.Ruotaimmagine(Scatta.NomeImmagineScattata, VariabiliImpostazioni.getInstance().getOrientamento());
			 	 }

			 	 VariabiliStatiche.getInstance().StoScattando = false;
            }
        };

        return jpeg;
    }

    public void surfaceCreated(SurfaceHolder holder) 
    {
//        myCamera = Camera.open(camId);
//
//        try {
//            if (myCamera != null)
//                myCamera.setPreviewDisplay(holder);
//        } catch (IOException exception) {
//			Toast toast=Toast.makeText(MainActivity.context, "Errore su surface" ,Toast.LENGTH_LONG);
//			toast.show();				
//
//			myCamera.release();
//            myCamera = null;
//        }       
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        if (mPreviewRunning) {
//            myCamera.stopPreview();
//        }
//
//        Camera.Parameters parameters = myCamera.getParameters();
//
//        List<Size> sizes = parameters.getSupportedPreviewSizes();
//        Size optimalSize = GBCameraUtil.getOptimalPreviewSize(sizes, w, h);
//        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
//
//        myCamera.setParameters(parameters);
//        myCamera.startPreview();
//
//        mPreviewRunning = true;

        /* Camera.Parameters parameters = myCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        myCamera.setParameters(parameters);
        myCamera.startPreview(); */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (myCamera != null) {
//            myCamera.stopPreview();
//            myCamera.release();
//            myCamera = null;    
//        }
    }
}