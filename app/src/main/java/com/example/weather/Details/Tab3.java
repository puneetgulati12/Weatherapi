package com.example.weather.Details;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab3 extends Fragment {
    private String APP_ID = "352e84b0ebdd052bca879172b8cf1bae";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        View rootview = inflater.inflate(R.layout.item_row1, container, false);
        return rootview;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        String units = "imperial";

                        final String baseurl = String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&units=%s&appid=%s",
                                lat, lon, units, APP_ID);
                        Log.e("api", baseurl);

                        OkHttpClient client = new OkHttpClient();
                        final Request request = new Request.Builder().url(baseurl).build();

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
                                final Root myobj = gson.fromJson(result, Root.class);

                                if(getActivity() == null)
                                    return;
//                                RecyclerView recyclerView = view.findViewById(R.id.recylervv);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                recyclerView.setAdapter(new RootAdapter() );
                                getActivity().runOnUiThread(new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        float abcd = myobj.main.getTemp_max();
                                        TextView textView = view.findViewById(R.id.tempmax);

                                        textView.setText(String.valueOf(abcd));

                                        float abc = myobj.main.getTemp_min();
                                        TextView textVie = view.findViewById(R.id.tempmin);

                                        textVie.setText(String.valueOf(abc));

                                        String a = myobj.name.getName();
                                        TextView text = view.findViewById(R.id.city);
                                        text.setText(a);
                                    }
                                }));


                            }


                        });


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
                });
        super.onViewCreated(view, savedInstanceState);
    }
}
