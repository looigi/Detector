package com.looigi.detector.gps;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.looigi.detector.Utilities.db_dati;
import com.looigi.detector.Utilities.Log;
import com.looigi.detector.Variabili.VariabiliImpostazioni;
import com.looigi.detector.Variabili.VariabiliStatiche;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocationService extends Service {
    public LocationManager locationManager;
    private Location previousBestLocation;
    public MyLocationListener listener;
    Context mContext;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(1, new Notification());
    }

    private void AzionaServizio() {
        VariabiliStatiche.getInstance().setServizioGPS(true);
        Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
        l.ScriveLog("Apertura servizio Location Service");
        mContext = getApplicationContext();
        if (locationManager == null) {
            try {
                locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                l.ScriveLog("Location Manager creato");
            } catch (Exception e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                l.ScriveLog("Create location manager: "+errors.toString());
            }
        }
        getCurrentLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AzionaServizio();

        return super.onStartCommand(intent, flags, startId);
    }

    private void getCurrentLocation() {
        Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
        l.ScriveLog("Get current location");
        try {
            if (VariabiliStatiche.getInstance().getImgGps()!=null) {
                VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.VISIBLE);
            }
            assert locationManager != null;
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            l.ScriveLog("Get current location OK. GPS enabled "+isGpsEnabled+" - Network enabled "+isNetworkEnabled);
        } catch (Exception ex) {
            l.ScriveLog("Get current location ERROR: "+ex.getMessage());
            if (VariabiliStatiche.getInstance().getImgGps()!=null) {
                VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.GONE);
            }
            ex.printStackTrace();
        }

        if (!isGpsEnabled && !isNetworkEnabled) {
            showSettingsAlert();
        }
        listener = new MyLocationListener();

        try {
            if (VariabiliStatiche.getInstance().getImgGps()!=null) {
                VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.VISIBLE);
            }
            if (isGpsEnabled) {
                try {
                    VariabiliImpostazioni.getInstance().setAccuracyValue(25, true);
                    VariabiliStatiche.getInstance().getsGPSBetter().setChecked(true);
                    VariabiliStatiche.getInstance().getsAccuracy().setChecked(true);
                    VariabiliStatiche.getInstance().getEdtAccuracy().setText("25");
                    VariabiliImpostazioni.getInstance().setDISTANZA_GPS(5, true);
                    VariabiliStatiche.getInstance().getEdtMetriGPS().setText("5");
                    VariabiliStatiche.getInstance().getsAccuracy().setChecked(VariabiliStatiche.getInstance().getsAccuracy().isChecked());
                    VariabiliImpostazioni.getInstance().setGPSBetter(VariabiliStatiche.getInstance().getsGPSBetter().isChecked(), true);
                    VariabiliImpostazioni.getInstance().setAccuracy(VariabiliStatiche.getInstance().getsAccuracy().isChecked(), true);
                    VariabiliStatiche.getInstance().getBtnSalvaAcc().setEnabled(true);
                    VariabiliStatiche.getInstance().getEdtAccuracy().setEnabled(true);
                } catch (Exception  ignored) {

                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        VariabiliImpostazioni.getInstance().getTEMPO_GPS(),
                        VariabiliImpostazioni.getInstance().getDISTANZA_GPS(), listener);
                l.ScriveLog("locationManager istanziato su GPS");

                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location!=null){
                    VariabiliStatiche.getInstance().setLocGPS(location);
                    l.ScriveLog("Ultima posizione: " + Double.toString(location.getLatitude()) + "-" + Double.toString(location.getLongitude()));
                }
            }
            if (isNetworkEnabled && !isGpsEnabled) {
                try {
                    VariabiliStatiche.getInstance().getsGPSBetter().setChecked(false);
                    VariabiliStatiche.getInstance().getsAccuracy().setChecked(false);
                    VariabiliImpostazioni.getInstance().setDISTANZA_GPS(35, true);
                    VariabiliStatiche.getInstance().getEdtMetriGPS().setText("75");
                    VariabiliStatiche.getInstance().getsAccuracy().setChecked(VariabiliStatiche.getInstance().getsAccuracy().isChecked());
                    VariabiliImpostazioni.getInstance().setGPSBetter(VariabiliStatiche.getInstance().getsGPSBetter().isChecked(), true);
                    VariabiliImpostazioni.getInstance().setAccuracy(VariabiliStatiche.getInstance().getsAccuracy().isChecked(), true);
                    VariabiliStatiche.getInstance().getBtnSalvaAcc().setEnabled(false);
                    VariabiliStatiche.getInstance().getEdtAccuracy().setEnabled(false);
                } catch (Exception  ignored) {

                }

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        VariabiliImpostazioni.getInstance().getTEMPO_GPS(),
                        VariabiliImpostazioni.getInstance().getDISTANZA_GPS(), listener);
                l.ScriveLog("locationManager istanziato su NETWORK");

                Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location!=null) {
                    VariabiliStatiche.getInstance().setLocGPS(location);
                    l.ScriveLog("Ultima posizione: " + Double.toString(location.getLatitude()) + "-" + Double.toString(location.getLongitude()));
                }
            }

            if (!isNetworkEnabled && !isGpsEnabled) {
                if (VariabiliStatiche.getInstance().getImgGps()!=null) {
                    VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.GONE);
                }
                l.ScriveLog("Nessun provider impostato");
            }
            VariabiliStatiche.getInstance().ScriveDatiAVideo();
        } catch (SecurityException e) {
            l.ScriveLog("locationManager ERROR: "+e.getMessage());
            if (VariabiliStatiche.getInstance().getImgGps()!=null) {
                VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.GONE);
            }
            e.printStackTrace();
        }

    }

    private void showSettingsAlert() {
        Toast.makeText(mContext, "GPS is disabled in your device. Please Enable it ?", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    // @Override
    // public int onStartCommand(Intent intent, int flags, int startId) {
    //     return START_STICKY;
    // }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > VariabiliImpostazioni.getInstance().getTEMPO_GPS();
        boolean isSignificantlyOlder = timeDelta < -VariabiliImpostazioni.getInstance().getTEMPO_GPS();
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
        l.ScriveLog("Distruggo locationManager");
        locationManager.removeUpdates(listener);
        VariabiliStatiche.getInstance().setServizioGPS(false);
    }

    private void EsegueScritturaValori(Location loc) {
        Log l = new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
        l.ScriveLog("onLocationChanged: " + Double.toString(loc.getLatitude()) + " - " + Double.toString(loc.getLongitude()));
        try {
            VariabiliStatiche.getInstance().ScriveValoriCoordinate(loc);
            VariabiliStatiche.getInstance().setLocGPS(loc);

            if (VariabiliStatiche.getInstance().isSeguePercorso()) {
                VariabiliStatiche.getInstance().DisegnaPercorsoAttualeSuMappa();
            }
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            l.ScriveLog("Scrittura valori a video: " + errors.toString());
        }
    }

    private void ScriveDistanze(Location loc) {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String oggi = formatter.format(todayDate);

        if (VariabiliStatiche.getInstance().getDbGpsPos()==null) {
            db_dati dbgps = new db_dati();
            dbgps.CreazioneTabelle();
            VariabiliStatiche.getInstance().setDbGpsPos(dbgps);
        }

        if (VariabiliStatiche.getInstance().getDbGpsPos()!=null) {
            if (loc != null && previousBestLocation != null) {
                float distanceInMeters = loc.distanceTo(previousBestLocation);
                float km = VariabiliStatiche.getInstance().getKmPercorsi() + distanceInMeters;
                VariabiliStatiche.getInstance().getDbGpsPos().aggiornaDistanza(oggi, Float.toString(km));
            }
        } else {
            Log l = new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Scrittura distanze su db: DB non aperto");
        }
    }

    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(final Location loc) {
            if (VariabiliImpostazioni.getInstance().isAccuracy()) {
                if (loc.getAccuracy() < VariabiliImpostazioni.getInstance().getAccuracyValue()) {
                    if (VariabiliImpostazioni.getInstance().isGPSBetter()) {
                        if (isBetterLocation(loc, previousBestLocation)) {
                            EsegueScritturaValori(loc);

                            ScriveDistanze(loc);
                            previousBestLocation = loc;
                        }
                    } else {
                        EsegueScritturaValori(loc);

                        ScriveDistanze(loc);
                        previousBestLocation = loc;
                    }
                }
            } else {
                if (VariabiliImpostazioni.getInstance().isGPSBetter()) {
                    if (isBetterLocation(loc, previousBestLocation)) {
                        EsegueScritturaValori(loc);

                        ScriveDistanze(loc);
                        previousBestLocation = loc;
                    }
                } else {
                    EsegueScritturaValori(loc);

                    ScriveDistanze(loc);
                    previousBestLocation = loc;
                }
            }
        }

        public void onProviderDisabled(String provider) {
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Provider disabled");

            getCurrentLocation();
        }

        public void onProviderEnabled(String provider) {
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Provider enabled");

            getCurrentLocation();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log l=new Log(VariabiliImpostazioni.getInstance().getNomeLogGPS());
            l.ScriveLog("Provider status changed: "+Integer.toString(status));
        }
    }
}