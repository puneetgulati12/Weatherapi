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
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    int firstcompletevisibleitem;
    RelativeLayout relLayout;
    ArrayList<Integer> Visiblity;

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
        relLayout = view.findViewById(R.id.root);


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
//        xAxis.setValueFormatter(new MyXAxisValueFormatter(lists) {
//            @Override
//            public String getFormattedValue(String value, AxisBase axis) {
//
//                return super.getFormattedValue(value, axis);
//            }
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                do {
//                    return String.valueOf(lists(value));
//                }while (value !=24);
//                }
//
//
//
//            private float lists(float value) {
//                return value;
//            }
//        });

YAxis y12 = Chart.getAxisRight();
y12.setEnabled(false);

LineData data1 = Chart.getData();
if (data1 != null){
    LineData set = Chart.getData();
    set.setValueTextColor(Color.WHITE);
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


                                if (getActivity() == null) {
                                    Toast.makeText(getActivity() , "wait for sometime"  , Toast.LENGTH_SHORT).show();
                                    return;
                                }

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
                                        firstcompletevisibleitem=0;
                                       Visiblity = new ArrayList<>();
                                       Visiblity.add(0 , 1);


                                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                                            @Override
                                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                super.onScrollStateChanged(recyclerView, newState);
                                            }

                                            @Override
                                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


// Add this to your Recycler view


// To check if at the top of recycler view
                                                if(layoutManager.findFirstCompletelyVisibleItemPosition()!=firstcompletevisibleitem ){
                                                    // Its at top
                                                    firstcompletevisibleitem = layoutManager.findFirstCompletelyVisibleItemPosition();
                                                    if (firstcompletevisibleitem>=0) {
                                                    ArrayList<Float>  pospoints = com.github.mikephil.charting.renderer.LineChartRenderer.DrawCircle(firstcompletevisibleitem);

                                                        //to remove



                                                        ImageView newview = new ImageView(getActivity());

                                                         RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(40,40);
                                                        lp.leftMargin = Math.round(pospoints.get(0)-20);
                                                        lp.topMargin = Math.round(pospoints.get(1)-20);
                                                        newview.setLayoutParams(lp);
                                                        newview.setImageResource(R.drawable.rounded);
                                                        newview.setId(firstcompletevisibleitem);
                                                        relLayout.addView(newview);
                                                        Visiblity.add(firstcompletevisibleitem, newview.getId());

                                                        try {
                                                            ImageView toremove = view.findViewById(Visiblity.get(firstcompletevisibleitem-1));

                                                            toremove.setVisibility(View.GONE);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }






                                                    }

                                                }


                                            }
                                        });
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
        set.setCircleRadius(0);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        Chart.setData(d);

        Chart.getAnimation();


        return d;
    }

    private void getLineEntriesData(Api myObj){


        ArrayList<com.github.mikephil.charting.data.Entry> entries = new ArrayList<>();

            int i=0;
            boolean flag = false;
        String k = myObj.list[i].getDt_txt();
        String initial = k.substring(k.indexOf(' ') + 1);



            while (!(myObj.list[i+1].getDt_txt().substring(myObj.list[i+1].getDt_txt().indexOf(' ')+1).equals(initial))){


                String s = String.valueOf(myObj.list[i].main.getTemp());
                String a = s.substring(0, s.indexOf("."));
                int temp = Integer.parseInt(a);
                String b = myObj.list[i].getDt_txt();
                String c =  b.substring(b.indexOf(' ')+1);
                int time = Integer.parseInt(c.substring(0,c.indexOf(":")));
                if (time == 0 ){
                    flag = true;
                }
                if (flag) {
                    entries.add(new Entry(time+24, temp));
                }
                else
                {
                    entries.add(new Entry(time, temp));
                }
                i++;
            }



//            final lists mylist[];


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
    TranslateAnimation animation = new TranslateAnimation(220, 80, 300, 80); //(float From X,To X, From Y, To Y)
    int   currentRotation = 0;
//    animation = new RotateAnimation(currentRotation, (360*4), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//    currentRotation = (currentRotation + 45) % 360;
//        animation.setDuration(1000);
//        animation.setFillAfter(false);
//        animation.setAnimationListener(new MyAnimationListener());




}
