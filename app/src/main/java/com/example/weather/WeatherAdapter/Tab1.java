package com.example.weather.WeatherAdapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Tab1 extends Fragment implements GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks {
    private static final String APP_ID = "352e84b0ebdd052bca879172b8cf1ba";
    private GoogleApiClient googleApiClient;
    LocationRequest locationRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes


        View rootview = inflater.inflate(R.layout.tab1, container, false);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();;
        googleApiClient.connect();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }


     //   Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
       // double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
//        String units = "imperial";
//
//        final String baseurl = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
//                lat, lon, units, APP_ID);
//        Log.e("api", baseurl);
//
//        OkHttpClient client = new OkHttpClient();
//
//        final Request request = new Request.Builder()
//                .url(baseurl).build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String myresponse = response.body().string();
//                Log.e("response", myresponse);
//
//                String result = myresponse;
//
//                Gson gson = new Gson();
//
//                weatherApi myobj = gson.fromJson(result, weatherApi.class);
//                int abcd = myobj.main.getTemp();
//
//                TextView textView = getView().findViewById(R.id.temp);
//
//                textView.setText(String.valueOf(abcd));
//                int abcde = myobj.main.getTemp();
//
//
//                TextView textVie = getView().findViewById(R.id.humiditytemp);
//
//                textVie.setText(String.valueOf(abcde));
//
//                int abc = myobj.wind.getSpeed();
//
//                TextView textVi = getView().findViewById(R.id.wind);
//
//                textVi.setText(String.valueOf(abc));
//
//                String a = myobj.sys.getCountry();
//                TextView text = getView().findViewById(R.id.city);
//                text.setText(a);
//
//
//            }
//        });
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

        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setSmallestDisplacement(0.1F);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("location","location");
            }
        });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
