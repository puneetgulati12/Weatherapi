package com.example.weather.WeatherAdapter;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weather.Colors;
import com.example.weather.R;
import com.example.weather.SharedPref;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.ALARM_SERVICE;

public class Tab1 extends Fragment implements GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks , Colors {

    public Context context;
    private static final String APP_ID = "352e84b0ebdd052bca879172b8cf1bae";
    private GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Location currentloc;
    private SharedPref sharedPref;
    private GifImageView imageView;
//    private SpinKitView spinKitView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes


        View rootview = inflater.inflate(R.layout.tab1, container, false);


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        sharedPref = new SharedPref(getActivity());
        if (!sharedPref.isFirstTime()) {


            sharedPref.SetFirstTime(false);
            Amanager();
        }

//        spinKitView = (SpinKitView) view.findViewById(R.id.spin_kit);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);

//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);

//        view.setBackgroundColor(colors[1 % colors.length]);
//        final int finalPosition = 1;
//        position = position % 15;
//        Style style = Style.values()[position];
//        Sprite drawable = SpriteFactory.create(style);
//        spinKitView.setIndeterminateDrawable(drawable);
//
//
//        animatedCircleLoadingView = (AnimatedCircleLoadingView) view.findViewById(R.id.circle_loading_view);
//
//
//       startLoading();
//        startPercentMockThread();

//        googleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        ;
//        googleApiClient.connect();

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();



                        String units = "imperial";

                        final String baseurl = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
                                lat, lon, units, APP_ID);
                        Log.e("api", baseurl);

                        OkHttpClient client = new OkHttpClient();

                        final Request request = new Request.Builder()
                                .url(baseurl).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                call.cancel();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                final String myresponse = response.body().string();
                                Log.e("response", myresponse);

                                String result = myresponse;

                                Gson gson = new Gson();


                                final weatherApi myobj = gson.fromJson(result, weatherApi.class);

//
                                if(getActivity() == null)
                                    return;

                                getActivity().runOnUiThread(new Thread(new Runnable() {
                                    public void run() {

                                        progressBar.setVisibility(View.GONE);

                                        float abcd = myobj.main.getTemp();

                                        TextView textView = view.findViewById(R.id.temp);

                                        textView.setText(String.valueOf(abcd));
                                        float abcde = myobj.main.getHumidity();

                                        TextView textVie = view.findViewById(R.id.humiditytemp);

                                        textVie.setText(String.valueOf(abcde));

                                        float abc = myobj.wind.getSpeed();

                                        TextView textVi = view.findViewById(R.id.wind);

                                        textVi.setText(String.valueOf(abc));

                                        String desc = myobj.weather[0].getMain();

                                        imageView = view.findViewById(R.id.icon);
                                        if (desc.matches("Rain")){
                                            imageView.setImageResource(R.drawable.cloudrain);
                                        }else if (desc.matches("Clear")){
                                            imageView.setImageResource(R.drawable.icsun);
                                        }else if (desc.matches("Clouds")){
                                            imageView.setImageResource(R.drawable.icsuncloud);
                                        }
                                        else if (desc.matches("Haze")){
                                            imageView.setImageResource(R.drawable.haze);
                                        }


                                        if (myobj.getname().isEmpty()) {

                                            try {
                                                String a = myobj.getname();

                                                TextView text = view.findViewById(R.id.city);
                                                text.setText(a);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
//                                        animatedCircleLoadingView.setVisibility(View.GONE);

                                    }


                                }));

                            }

                        });
                    }

                    ;

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

//        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        double lat = mCurrentLocation.getLatitude(), lon = mCurrentLocation.getLongitude();

//                getlocation(currentloc);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("error", "error");
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        // Note that this can be NULL if last location isn't already known.
        if (mCurrentLocation != null) {

            // Print current location if not null
            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
        }

        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setSmallestDisplacement(0.1F);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("location", "location");
                currentloc = location;
                getlocation(currentloc);
            }
        });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void getlocation(Location loc) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }




    }
    public void Amanager(){
        AlarmManager manager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getActivity() , MyBroadcastReceiver.class);
//        startActivity(intent);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity() , 1 , intent , PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setInexactRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() , 90*1000   , pendingIntent);




    }

//
//    private void startLoading() {
//        animatedCircleLoadingView.startDeterminate();
//    }
//
//    private void startPercentMockThread() {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1500);
//                    for (int i = 0; i <= 100; i++) {
//                        Thread.sleep(65);
//                        changePercent(i);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }
//
//    private void changePercent(final int percent) {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                animatedCircleLoadingView.setPercent(percent);
//            }
//        });
//    }
//
//    public void resetLoading() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                animatedCircleLoadingView.resetLoading();
//            }
//        });
//    }



}
