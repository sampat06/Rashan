package com.rashan.Activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.rashan.R;
import com.rashan.Utiles.Temperory_Storage;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeActivity extends AppCompatActivity  implements FragmentDrawer.FragmentDrawerListener{

    private AppBarConfiguration mAppBarConfiguration;
    FragmentDrawer drawerFragment;
    TextView address;
    ProgressBar progress;
    private LocationListener locListener = new MyLocationListener();
    LocationManager locationManager;
    String nearme,addresss="", city, landmark, postalCode, state, district, country;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        address=findViewById(R.id.address);
        progress=findViewById(R.id.progress);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);


        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawer, toolbar);
        drawerFragment.setDrawerListener(this);

        toolbar.setTitle(" ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION)) {
                Log.d("permission:", "is not granted");
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                boolean flag = displayGpsStatus();
                if (flag) {
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

                }
            }
        }

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeActivity.this,MapsActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("address",addresss);
                intent.putExtra("land",landmark);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);


    }

    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = HomeActivity.this
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

                Log.d("lat", latitude + "");
                Log.d("longi", londitude + "");
                Log.d("altitute", altitiude + "");
                Log.d("accury", accuracy + "");
                Log.d("time", time + "");

                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());

                try {

                    addresses = geocoder.getFromLocation(latitude, londitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    if (addresses != null && addresses.size() != 0) {

                        addresss = addresses.get(0).getAddressLine(0);
                        city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();
                        district = addresses.get(0).getSubAdminArea();
                        country = addresses.get(0).getCountryName();
                        postalCode = addresses.get(0).getPostalCode();
                        landmark = addresses.get(0).getSubLocality();
                        lat = addresses.get(0).getLatitude();
                        lng = addresses.get(0).getLongitude();

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


                        Temperory_Storage.set_lat(HomeActivity.this,lat+"");
                        Temperory_Storage.set_lng(HomeActivity.this,lng+"");
                        Temperory_Storage.set_address(HomeActivity.this,addresss+"");

                        progress.setVisibility(View.GONE);
                        address.setVisibility(View.VISIBLE);
                        address.setText(addresss);
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
                locationManager = (LocationManager) HomeActivity.this.getSystemService(Context.LOCATION_SERVICE);
                boolean flag = displayGpsStatus();
                if (flag) {
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

                }
            }



        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        addresss=Temperory_Storage.get_address(HomeActivity.this);

        progress.setVisibility(View.GONE);
        address.setVisibility(View.VISIBLE);
        address.setText(addresss);
    }
}
