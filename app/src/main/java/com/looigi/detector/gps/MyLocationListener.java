package com.looigi.detector.gps;
/*
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.looigi.detector.Variabili.VariabiliStatiche;

public class MyLocationListener implements LocationListener {
    private Location lastLoc=null;

    private float CalcolaM(Location l, Location l2) {
        if (l==null) {
            return 0;
        }

        Location locationA = new Location("A");
        locationA.setLatitude(l.getLatitude());
        locationA.setLongitude(l.getLongitude());

        Location locationB = new Location("B");
        locationB.setLatitude(l2.getLatitude());
        locationB.setLongitude(l2.getLongitude());

        return locationA.distanceTo(locationB);
    }

    @Override
    public void onLocationChanged(Location loc) {
        // editLocation.setText("");
        // pb.setVisibility(View.INVISIBLE);
        // Toast.makeText(
        //         VariabiliStatiche.getInstance().getContext(),
        //         "Location changed: Lat: " + loc.getLatitude() + " Lng: "
        //                 + loc.getLongitude(), Toast.LENGTH_SHORT).show();

        // if (loc.getAccuracy()<35) {
            if (lastLoc==null || (lastLoc!=null && lastLoc!=loc)) {
                // float diff = CalcolaM(lastLoc, loc);
                // if (diff>=5 || lastLoc==null) {
                 VariabiliStatiche.getInstance().ScriveValoriCoordinate(loc);
                 lastLoc = loc;

                 if (VariabiliStatiche.getInstance().isSeguePercorso()) {
                     VariabiliStatiche.getInstance().DisegnaPercorsoAttualeSuMappa();
                 }
                //}
            }
        // }

        // String longitude = "Longitude: " + loc.getLongitude();
        // Log.v(TAG, longitude);
        // String latitude = "Latitude: " + loc.getLatitude();
        // Log.v(TAG, latitude);

        // String cityName = null;
        // Geocoder gcd = new Geocoder(VariabiliStatiche.getInstance().getContext(), Locale.getDefault());
        // List<Address> addresses;
        // try {
        //     addresses = gcd.getFromLocation(loc.getLatitude(),
        //             loc.getLongitude(), 1);
        //     if (addresses.size() > 0) {
        //         System.out.println(addresses.get(0).getLocality());
        //         cityName = addresses.get(0).getLocality();
        //     }
        // }
        // catch (IOException e) {
        //     e.printStackTrace();
        // }
        // String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
        //         + cityName;
        // editLocation.setText(s);

        // Boolean isGPSEnabled = VariabiliStatiche.getInstance().getLocationManager()
        //         .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // if (isGPSEnabled) {
        //     try {
        //         VariabiliStatiche.getInstance().getLocationManager().requestLocationUpdates(
        //                 LocationManager.GPS_PROVIDER,
        //                 VariabiliStatiche.getInstance().TEMPO_GPS,
        //                 VariabiliStatiche.getInstance().DISTANZA_GPS,
        //                 VariabiliStatiche.getInstance().getLocationListener());
        //     } catch (SecurityException ignored) {
        //         int a=0;
        //     }
        // } else {
        //     Boolean isNetworkEnabled = VariabiliStatiche.getInstance().getLocationManager()
        //             .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        //     if (isNetworkEnabled) {
        //         try {
        //             VariabiliStatiche.getInstance().getLocationManager().requestLocationUpdates(
        //                     LocationManager.NETWORK_PROVIDER,
        //                     VariabiliStatiche.getInstance().TEMPO_GPS,
        //                     VariabiliStatiche.getInstance().DISTANZA_GPS,
        //                     VariabiliStatiche.getInstance().getLocationListener());
        //         } catch (SecurityException ignored) {
        //             int a = 0;
        //         }
        //     } else {
        //         Boolean isPassiveEnabled = VariabiliStatiche.getInstance().getLocationManager()
        //                 .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        //         if (isPassiveEnabled) {
        //             try {
        //                 VariabiliStatiche.getInstance().getLocationManager().requestLocationUpdates(
        //                         LocationManager.PASSIVE_PROVIDER,
        //                         VariabiliStatiche.getInstance().TEMPO_GPS,
        //                         VariabiliStatiche.getInstance().DISTANZA_GPS,
        //                         VariabiliStatiche.getInstance().getLocationListener());
        //             } catch (SecurityException ignored) {
        //                 int a = 0;
        //             }
        //         }
        //     }
        // }
    }

    @Override
    public void onProviderDisabled(String provider) {
        int a=0;
    }

    @Override
    public void onProviderEnabled(String provider) {
        int a=0;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        int a=0;
    }
}

*/