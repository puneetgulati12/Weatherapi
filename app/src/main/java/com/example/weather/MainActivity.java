package com.example.weather;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.Details.Tab3;
import com.example.weather.WeatherAdapter.Tab1;
import com.example.weather.Weatherdetails.Tab2;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private static final String APP_ID = "352e84b0ebdd052bca879172b8cf1bae";
    private ViewPager viewPager;

    private GoogleApiClient googleApiClient;
    private TextView textView;
    private LocationManager locationManager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private prefManager prefManager;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new prefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        // Checking for first time launch - before calling setContentView()

        dotsLayout = findViewById(R.id.layoutDots);

        //Initializing viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.tab1,
                R.layout.tab2,
                R.layout.tab3};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
//        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        }

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();


        android.support.v7.widget.Toolbar t = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);
        //Initializing the tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);


//        //Adding the tabs using addTab() method
//        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//        //Creating our pager adapter
//
//
//        pager adapter = new pager(getSupportFragmentManager(), tabLayout.getTabCount());
//
//        //Adding adapter to pager
//        viewPager.setAdapter(adapter);
//
//        //Adding onTabSelectedListener to swipe views
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
  tabLayout.setVisibility(View.GONE);
        prefManager prefManager = new prefManager(getApplicationContext());

        // make first time launch TRUE
        prefManager.setFirstTimeLaunch(true);
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView time = findViewById(R.id.time);
                time.setText(new SimpleDateFormat("hh:mm a", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);
    }




    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        finish();
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    //Making notification bar transparent
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                case 2:
                    Tab3 tab3 = new Tab3();
                    return tab3;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.setting:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                return true;

            case R.id.share:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("plain/text");
                intent1.putExtra(Intent.EXTRA_TEXT, "something");
                intent1.putExtra(Intent.EXTRA_SUBJECT, "The title");
                startActivity(Intent.createChooser(intent1, "share"));
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager != null) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Toast.makeText(getApplication(), "latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_LONG).show();

                        }
                    }
                    // All good!
                } else {

                    boolean isGPSEnabled = locationManager
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (!isGPSEnabled) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                        // Setting Dialog Title
                        alertDialog.setTitle("GPS settings");

                        // Setting Dialog Message
                        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

                        // On pressing Settings button
                        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });

                        // on pressing cancel button
                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();

                    }

                }

                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i(MainActivity.class.getSimpleName(), "Connected to Google Play Services!");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

//            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
////
//            double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
//            String units = "imperial";
//            String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
//                    lat, lon, units, APP_ID);
//            new GetWeatherTask(textView).execute(url);


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.i(MainActivity.class.getSimpleName(), "Can't connect to Google Play Services!");

    }

    @Override
    public void onLocationChanged(Location location) {

//        TextView textView = findViewById(R.id.temp);
//        textView.setText("Latitude" + location.getLatitude() + ",Longitude"
//                + location.getLongitude());

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}


