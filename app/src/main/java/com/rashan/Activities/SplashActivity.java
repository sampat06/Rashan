package com.rashan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.rashan.MainActivity;
import com.rashan.R;
import com.rashan.databinding.ActivitySplashBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    LocationManager locationManager;
    private LocationListener locListener = new MyLocationListener();
    private String PermissionGranted="Yes";
   String nearme,address="", city, landmark, postalCode, state, district, country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION )) {
                Log.d("permission:", "is not granted");
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION}, 1);
            }
            else {
                Log.d("permission:", "granted");
                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                boolean flag = displayGpsStatus();
                if (flag) {
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
                    PermissionGranted="Yes";
                    StartMainCode();

                } else {
                    StartMainCode();
                    Toast.makeText(this, "GPS OFF", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            StartMainCode();
        }

    }

    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = SplashActivity.this
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;
        } else {
            return false;
        }

    }

    public void StartMainCode()
    {
        Thread background = new Thread() {
            public void run() {
                try {
                  /*  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);
                    splash_image1.startAnimation(animation);*/



                        sleep(3*1000);
                    Intent intent=new Intent(SplashActivity.this, IntroductionActivity.class);
                    startActivity(intent);
                    finish();

                        // fetchUserSegment = new FetchUserSegment();
                        // fetchUserSegment.execute(user_id+"");


                }
                catch (Exception e) {

                }
            }
        };
        background.start();
    }

    public void revokePermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        Log.d("request code",requestCode+":ll");

        if (requestCode == 1) {

            //Log.d("grant result",grantResults[0]+"::");
           /* if(String.valueOf(grantResults[1])!=null &&  grantResults.length>2 && grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {
                Log.d("write external","granted");
            }
            if(String.valueOf(grantResults[2])!=null && grantResults[2]==PackageManager.PERMISSION_GRANTED)
            {
                Log.d("read external","granteed");
            }*/


            if (grantResults !=null && grantResults.length!=0 && String.valueOf(grantResults[0])!=null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("permission:", "granted from allow");
                locationManager = (LocationManager) SplashActivity.this.getSystemService(Context.LOCATION_SERVICE);
                boolean flag = displayGpsStatus();
                if (flag) {
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
                    PermissionGranted="Yes";
                    //screenNavigateMethod();
                    StartMainCode();
                } else {

                    StartMainCode();
                    //screenNavigateMethod();
                    Toast.makeText(SplashActivity.this, "GPS OFF", Toast.LENGTH_SHORT).show();
                }
            }
            else if (grantResults !=null && grantResults.length!=0 &&String.valueOf(grantResults[0])!=null && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, ACCESS_FINE_LOCATION)) {
                    //Show an explanation to the user *asynchronously*
                    Log.d("permission:","granted from denied");
                    PermissionGranted="No";
                    //snackbar.show();
                    //screenNavigateMethod();
                    StartMainCode();
                }
                else
                {
                    StartMainCode();
                }
            }


        }

    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                // This needs to stop getting the location data and save the battery power.
                locationManager.removeUpdates(locListener);

                Double londitude = location.getLongitude();
                Double latitude = location.getLatitude();
                String altitiude = "Altitiude: " + location.getAltitude();
                String accuracy = "Accuracy: " + location.getAccuracy();
                String time = "Time: " + location.getTime();

                Log.d("lat", latitude  + "");
                Log.d("longi", londitude  + "");
                Log.d("altitute", altitiude  + "");
                Log.d("accury", accuracy  + "");
                Log.d("time", time  + "");

                Geocoder geocoder;
                List<Address> addresses=null;
                geocoder = new Geocoder(SplashActivity.this, Locale.getDefault());

                try {

                    addresses = geocoder.getFromLocation(latitude, londitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    if (addresses != null && addresses.size() != 0)
                    {

                        address = addresses.get(0).getAddressLine(0);
                        city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();
                        district = addresses.get(0).getSubAdminArea();
                        country = addresses.get(0).getCountryName();
                        postalCode = addresses.get(0).getPostalCode();
                        landmark = addresses.get(0).getFeatureName();

                        Log.d("addrrr", addresses + "");
                        nearme = addresses.get(0).getSubLocality();
                        Log.d("full add", address + "");
                        Log.d("city", city + "");
                        Log.d("subcity", nearme + "");
                        Log.d("district", district + "");
                        Log.d("state", state + "");
                        Log.d("country", country + "");
                        Log.d("postal", postalCode + "");
                        Log.d("landmark", landmark + "");


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }
}
