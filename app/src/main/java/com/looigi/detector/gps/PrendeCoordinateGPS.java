package com.looigi.detector.gps;

/*
import java.util.Calendar;

import com.google.android.gms.maps.model.LatLng;
import com.looigi.detector.Variabili.VariabiliStatiche;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;

public class PrendeCoordinateGPS implements LocationListener {
    private LocationManager mLoc;

    public void AttivaGPS(final Context context) {
        mLoc = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        ImpostaLM();
    }

    private void ImpostaLM() {
        try {
            if (mLoc != null) {
                if (mLoc.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    GestisceIconaGPS(true);

                    mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
                } else {
                    if (mLoc.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        GestisceIconaGPS(false);

                        mLoc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
                    } else {
                        if (mLoc.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                            GestisceIconaGPS(false);

                            mLoc.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 5, this);
                        }
                    }
                }
            }
        } catch (SecurityException ignored) {
            int a=0;
        }
    }

    private void GestisceIconaGPS(Boolean Acceso) {
        if (Acceso) {
            VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.VISIBLE);
        } else {
            VariabiliStatiche.getInstance().getImgGps().setVisibility(LinearLayout.GONE);
        }
    }

    public void DisattivaGPS() {
        mLoc=null;
    }

    @Override
    public void onLocationChanged(Location arg0) {
        // if (MainActivity.DevoMorire==true) {
        //     DisattivaGPS();
        //     System.exit(0);
        // } else {
//			MainActivity.l.ScriveLog2("Cambio posizione");

            // lat=arg0.getLatitude();
            // lon=arg0.getLongitude();

            VariabiliStatiche.getInstance().ScriveValoriCoordinate(arg0);

            if (VariabiliStatiche.getInstance().isSeguePercorso()) {
                VariabiliStatiche.getInstance().DisegnaPercorsoAttualeSuMappa();
            }

        //		String sBody=Double.toString(lat)+"-"+Double.toString(lon);
//			Boolean Ok=true;

            //		if (MainActivity.GPSAcceso == true) {
            //			if (sBody.length()<=20) {
            //				Ok=false;
            //			}
            //		} else {
            //			if (sBody.length()>15) {
            //				Ok=false;
            //			}
            //		}

//			if (Ok==true) {
            // if (VecchiaLat!=lat || VecchiaLon!=lon) {
            //     LatLng l=new LatLng(lat,lon);
            //     if (MainActivity.Segue==true || MainActivity.DisegnataMappa==false) {
            //         MainActivity.MappaPos=l;
            //     }
            //     MainActivity.DoveSono = l;
//
            //     GestioneMappa gm=new GestioneMappa();
            //     if (MainActivity.ProgrammaRidotto == false) {
            //         gm.PosizionaMappa();
            //         if (MainActivity.MappaSpostataPerRicerca==false) {
            //             gm.ChiudeAttesa();
            //         } else {
            //             AggiornaQuadratoPerSpostamento();
            //         }
//
            //         if (MainActivity.DisegnataMappa==false) {
            //             MainActivity.DisegnataMappa=true;
            //         }
            //     } else {
            //         AggiornaQuadratoPerSpostamento();
            //     }
//
            //     SpostaCoordinate();
//
            //     if (MainActivity.VisuaPercorso==null) {
            //         MainActivity.VisuaPercorso="S";
            //     }
            //     if (MainActivity.VisuaPercorso.equals("S")==true) {
            //         Utility u=new Utility();
            //         u.SalvaCoordinateSuFile(lat, lon);
            //     }
            // }
        // }
//		}
    }

    // public void AggiornaQuadratoPerSpostamento() {
    //     double xx1=MainActivity.DoveSono.latitude-(MainActivity.InizioQuadratoXPerSpostamentoNascosto);
    //     double yy1=MainActivity.DoveSono.longitude-(MainActivity.InizioQuadratoYPerSpostamentoNascosto);
    //     double xx2=MainActivity.DoveSono.latitude+(MainActivity.FineQuadratoXPerSpostamentoNascosto);
    //     double yy2=MainActivity.DoveSono.longitude+(MainActivity.FineQuadratoYPerSpostamentoNascosto);
//
    //     MainActivity.InizioQuadratoValido=new LatLng(xx1, yy1);
    //     MainActivity.FineQuadratoValido=new LatLng(xx2, yy2);
    // }

   //  public void SpostaCoordinate() {
//		String sBody=Double.toString(lat)+"-"+Double.toString(lon)+"\n";
//		Boolean Ok=true;

//		if (MainActivity.GPSAcceso == true) {
//			if (sBody.length()<=23) {
//				Ok=false;
//			}
//		} else {
//			if (sBody.length()>23) {
//				Ok=false;
//			}
//		}
       //  if (MainActivity.DevoMorire==true) {
       //      DisattivaGPS();
       //      System.exit(0);
       //  } else {
//
//	// 	if (Ok==true) {
       //      LatLng l=new LatLng(lat,lon);
//
       //      // Controlla se � passato un minuto ed eventualmente scrive
       //      // la posizione sul web
       //      if (VecchiaLat!=lat || VecchiaLon!=lon) {
       //          MainActivity.l.ScriveLog("Sposta Coordinate 1");
//
       //          Calendar c = Calendar.getInstance();
       //          int Minuto=c.get(Calendar.MINUTE);
       //          if (Minuto!=UltimoMinuto) {
       //              MainActivity.l.ScriveLog("Sposta Coordinate 2");
//
       //              VecchiaLat=lat;
       //              VecchiaLon=lon;
//
       //              UltimoMinuto=Minuto;
//
       //              String CoordX=Double.toString(l.latitude);
       //              String CoordY=Double.toString(l.longitude);
//
//	// 				if (MainActivity.StaLeggendoDalDB == false) {
       //              MainActivity.l.ScriveLog("Sposta Coordinate 3");
//
       //              MainActivity.Quantispostamenti++;
//
       //              MainActivity m=new MainActivity();
       //              m.ScriveDebug();
//
       //              MainActivity.ContatoreSpostamenti=0;
//
       //              DBRemoto dbr=new DBRemoto();
       //              dbr.SalvaPosizione(cnt, CoordX, CoordY);
//	// 				}
       //          }
       //      } else {
       //          MainActivity.ContatoreSpostamenti++;
       //          if (MainActivity.ContatoreSpostamenti>10) {
       //              MainActivity.ContatoreSpostamenti=0;
//
       //              String CoordX=Double.toString(l.latitude);
       //              String CoordY=Double.toString(l.longitude);
//
       //              MainActivity.l.ScriveLog("Sposta Coordinate auto");
//
       //              MainActivity.Quantispostamenti++;
//
       //              MainActivity m=new MainActivity();
       //              m.ScriveDebug();
//
       //              DBRemoto dbr=new DBRemoto();
       //              dbr.SalvaPosizione(cnt, CoordX, CoordY);
       //          }
       //      }
//	// 	}
       //  }
//
       //  MainActivity.l.ScriveLog("Sposta Coordinate 4");
    // }

    @Override
    public void onProviderDisabled(String arg0) {
        GestisceIconaGPS(false);

        try {
            if (mLoc.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mLoc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            } else {
                if (mLoc.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                    mLoc.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 5, this);
                }
            }
        } catch (SecurityException ignored) {
            int a=0;
        }
    }

    @Override
    public void onProviderEnabled(String arg0) {
        GestisceIconaGPS(true);

        try {
            if (mLoc.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            }
        } catch (SecurityException ignored) {
            int a=0;
        }
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        ImpostaLM();
    }
}
*/