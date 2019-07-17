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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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

       Chart.getDescription().setEnabled(false);

        Chart.setTouchEnabled(true);
        Chart.setDragEnabled(true);
        Chart.setScaleEnabled(true);
        Chart.setPinchZoom(true);



        Legend l = Chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis x1 = Chart.getXAxis();
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        x1.setTextColor(Color.WHITE);

        x1.setAvoidFirstLastClipping(true);

        YAxis y1 = Chart.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.getSpaceBottom();

        Chart.animateXY(3000 , 3000);

        final lists[] lists = new lists[]{};
        XAxis xAxis = Chart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(lists) {
            @Override
            public String getFormattedValue(String value, AxisBase axis) {

                return super.getFormattedValue(value, axis);
            }

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                do {
                    return String.valueOf(lists(value));
                }while (value !=24);

                }



            private float lists(float value) {
                return value;
            }
        });

YAxis y12 = Chart.getAxisRight();
y12.setEnabled(false);

LineData data1 = Chart.getData();
if (data1 != null){
    LineData set = Chart.getData();
}



        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);



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
                                float temp1 = myobj.list[0].main.temp;
                                Log.e("temp" , String.valueOf(temp1));
                                String time = myobj.list[0].getDt_txt();
                                Log.e("" , time);
                                getLineEntriesData(myobj);

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

    private LineData generateLineData(ArrayList<com.github.mikephil.charting.data.Entry> entries) {

        LineData d = new LineData();





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
        Chart.setData(d);
        return d;
    }

    private void getLineEntriesData(Api myObj){

        ArrayList<com.github.mikephil.charting.data.Entry> entries = new ArrayList<>();

        for (int i = 0; i < myObj.list.length; i++) {

//            final lists mylist[];
            String s = String.valueOf(myObj.list[i].main.getTemp());
            String a = s.substring(0, s.indexOf("."));
            int temp = Integer.parseInt(a);
            String b = myObj.list[i].getDt_txt();
            String c =  b.substring(b.indexOf(' ')+1);
            int time = Integer.parseInt(c.substring(0,c.indexOf(":")));
            entries.add(new Entry(temp , time));
        }
//        entries.add(new Entry(12, 300));
//        entries.add(new Entry(15, 310));
//        entries.add(new Entry(18, 318));
//        entries.add(new Entry(21, 320));
//        entries.add(new Entry(24, 329));
//        entries.add(new Entry(03, 299));
//        entries.add(new Entry(06, 307));
        generateLineData(entries);

        return ;
    }
    public abstract class MyXAxisValueFormatter implements IAxisValueFormatter, com.example.weather.Weatherdetails.MyXAxisValueFormatter {

        private lists[] mValues;

        public MyXAxisValueFormatter(lists[] values) {
            this.mValues = values;
        }

        public String getFormattedValue(String value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)

            return String.valueOf(mValues[Integer.parseInt(value)]);
        }

        /** this is only needed if numbers are returned, else return 0 */

        public int getDecimalDigits() {
            return 1;
        }

    }



}
