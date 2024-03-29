package com.example.weather.Details;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weather.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab3 extends Fragment {
    private String APP_ID = "4bc4569198b322379190faa7310c16c0";
    private RecyclerView recyclerView;
    private ImageView imageView;
   private TextView simple;
   ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        View rootview = inflater.inflate(R.layout.tab3, container, false);
        return rootview;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        progressBar = view.findViewById(R.id.spikit);

        imageView = view.findViewById(R.id.weatherimage);
        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();


                        final String baseurl = String.format("https://api.darksky.net/forecast/%s/%f,%f?exclude=[hourly,minutely,currently,flags]"
                                ,APP_ID, lat, lon);
                        Log.e("api", baseurl);

                        OkHttpClient client = new OkHttpClient();
                        final Request request = new Request.Builder().url(baseurl).build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                call.cancel();
                            }

                            @Override
                            public void onResponse(final Call call, final Response response) throws IOException {

                                final String myresponse = response.body().string();
                                Log.e("response", myresponse);
                                String result = myresponse;
                                Gson gson = new Gson();
                                final Root myobj = gson.fromJson(result, Root.class);
                                final List<Data> mylist = myobj.daily.data;

                                if(getActivity() == null)
                                    return;
//
                                getActivity().runOnUiThread(new Thread(new Runnable() {
                                    private Object RootAdapter;

                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);

                                        RecyclerView recyclerView =  view.findViewById(R.id.recylervv);
                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity() ,  LinearLayoutManager.VERTICAL,false);
                                        recyclerView.setLayoutManager(layoutManager);

                                        RootAdapter = new RootAdapter(mylist);
                                        RootAdapter myAdapter = new RootAdapter(mylist, getActivity());
//                                        Date now = new Date();
//                                        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
//                                        simple =  view.findViewById(R.id.day);
//                                        simple.setText(String.valueOf(simpleDateformat.format(now)));
                                        recyclerView.setAdapter(myAdapter);


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
//    @SuppressLint("ResourceType")
//    private void setWeatherIcon(Root myobj){
//        Drawable myDrawable = getResources().getDrawable(R.drawable.clearnight);
//        imageView.setImageDrawable(myDrawable);
//
//        List<Data> mylist = myobj.daily.data;
//
//        String id = "";
//        String icon = "";
//
//            switch(id) {
//                case "one" : getActivity().getDrawable(R.drawable.cloudrain);
//                    break;
//                case "two" : getActivity().getDrawable(R.drawable.cloudrain);
//                    break;
//                default:
//                    getActivity().getDrawable(R.id.weatherimage);
//
//            }
//        }


    }

