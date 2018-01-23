package com.example.lequan.lichvannien.location;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public class GPSTracker extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 60000;
    boolean canGetLocation = false;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    private final Context mContext;

    class C12971 implements OnClickListener {
        C12971() {
        }

        public void onClick(DialogInterface dialog, int which) {
            GPSTracker.this.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    class C12982 implements OnClickListener {
        C12982() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            try {
                this.locationManager = (LocationManager) this.mContext.getSystemService(Param.LOCATION);
                this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
                this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
                if (this.isGPSEnabled || this.isNetworkEnabled) {
                    this.canGetLocation = true;
                    if (this.isNetworkEnabled) {
                        this.locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 10.0f, this);
                        if (this.locationManager != null) {
                            this.location = this.locationManager.getLastKnownLocation("network");
                            if (this.location != null) {
                                this.latitude = this.location.getLatitude();
                                this.longitude = this.location.getLongitude();
                            }
                        }
                    }
                    if (this.isGPSEnabled && this.location == null) {
                        this.locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 10.0f, this);
                        if (this.locationManager != null) {
                            this.location = this.locationManager.getLastKnownLocation("gps");
                            if (this.location != null) {
                                this.latitude = this.location.getLatitude();
                                this.longitude = this.location.getLongitude();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions((Activity) this.mContext, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
        }
        return this.location;
    }

    public void stopUsingGPS() {
        if ((ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) && this.locationManager != null) {
            this.locationManager.removeUpdates(this);
        }
    }

    public double getLatitude() {
        if (this.location != null) {
            this.latitude = this.location.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        if (this.location != null) {
            this.longitude = this.location.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        Builder alertDialog = new Builder(this.mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new C12971());
        alertDialog.setNegativeButton("Cancel", new C12982());
        alertDialog.show();
    }

    public void onLocationChanged(Location location) {
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
