package com.example.weather.Weatherdetails;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab2 extends Fragment {

    private String APP_ID = "352e84b0ebdd052bca879172b8cf1bae";
    private RecyclerView recyclerView;
    private LineChart Chart;
    private Object Entry;
    private ArrayList ArrayList;
    private Object lists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LineChartView lineChartView;
//        Returning the layout file after inflating
//        Change R.layout.tab1 in you classes
        View rootview = inflater.inflate(R.layout.tab2, container, false);

        return rootview;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        Chart = view.findViewById(R.id.chart);
//        Chart = new LineChart(getActivity());
        Chart.getDescription().setEnabled(false);
      //  Chart.setDrawGridBackground(false);
//        Chart.setDefaultFocusHighlightEnabled(true);
        Chart.setTouchEnabled(true);
        Chart.setDragEnabled(true);
        Chart.setScaleEnabled(true);
        Chart.setPinchZoom(true);
       // Chart.setBackgroundColor(Color.LTGRAY);

//        LineData data = new LineData();
//        data.setValueTextColor(Color.WHITE);
        Chart.setData(generateLineData());

        Legend l = Chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis x1 = Chart.getXAxis();
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        x1.setTextColor(Color.WHITE);
     //   x1.setDrawGridLines(false);
//        x1.setAxisMaxValue(400);
        x1.setAvoidFirstLastClipping(true);

        YAxis y1 = Chart.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.getSpaceBottom();
//        y1.setAxisMaxValue(24);
       // y1.setDrawGridLines(true);


YAxis y12 = Chart.getAxisRight();
y12.setEnabled(false);

LineData data1 = Chart.getData();
if (data1 != null){
   // LineDataSet  set = Chart.getData();
}


        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

//        LineChartView chart = new LineChartView(getActivity());
//        chart.addView(chart);
        generateLineData();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                double lat = location.getLatitude();
                double lon = location.getLongitude();

                final String baseurl = String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s"
                        , lat, lon, APP_ID);
                Log.e("api", baseurl);

                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder().url(baseurl).build();
                client.newCall(request).

                        enqueue(new Callback() {
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
                                final Api myobj = gson.fromJson(result, Api.class);
                                final lists mylist[] = myobj.list;

                                if (getActivity() == null)
                                    return;

                                getActivity().runOnUiThread(new Runnable() {
                                    private Object ApiAdapter;

                                    @Override
                                    public void run() {

                                        RecyclerView recyclerView = view.findViewById(R.id.recyler);
                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                        recyclerView.setLayoutManager(layoutManager);
                                        ApiAdapter = new ApiAdapter(Arrays.asList(mylist) , getActivity());

                                        ApiAdapter myAdapter = new ApiAdapter(Arrays.asList(mylist) , getActivity());
                                        recyclerView.setAdapter(myAdapter);
                                    }
                                });
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
    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries = getLineEntriesData(entries);

        LineDataSet set = new LineDataSet(entries, "Line");

        set.setColor(Color.rgb(240, 238, 70));
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private ArrayList<com.github.mikephil.charting.data.Entry> getLineEntriesData(ArrayList<Entry> entries){

//        for (int i = 0; i < ArrayList.size(); i++) {
//
//            final lists mylist[];
//            int temp = Integer.parseInt();
//            int time = Integer.parseInt();
//            entries.add(new Entry(temp , time));
//        }
        entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 10));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 20));
        entries.add(new Entry(5, 19));

        return entries;
    }



}
