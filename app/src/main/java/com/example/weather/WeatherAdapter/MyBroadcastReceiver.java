package com.example.weather.WeatherAdapter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.weather.MainActivity;
import com.example.weather.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    private static final  String Channel1 = "channel1";
    private static final String APP_ID = "352e84b0ebdd052bca879172b8cf1bae";


public void onReceive(final Context context , Intent intent){




        mp = MediaPlayer.create(context , Settings.System.DEFAULT_RINGTONE_URI);
        mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();


//        NotificationChannel channel1 = new NotificationChannel(Channel1 , "channel 1" , NotificationManager.IMPORTANCE_HIGH);
//        channel1.setDescription("This is channel1");



        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

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
                                if(context == null)
                                    return;

//                                new Thread(new Runnable() {
//                                    public void run() {
                                        float abcd = myobj.main.getTemp();
                                final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                                final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);

  remoteViews.setTextViewText(R.id.notitemp, String.valueOf(abcd)+"°C");
                                remoteViews.setImageViewResource(R.id.imagenoty , R.drawable.index);
                                Intent notificationIntent = new Intent(context, MainActivity.class);

                                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                final Notification notification = new NotificationCompat.Builder(context  , Channel1)
                                        .setSmallIcon(R.drawable.ic_action_name)
                                        .setAutoCancel(false)
//                .setContentTitle("weather" )
                                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())

                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCustomContentView(remoteViews)
                                        .setCustomBigContentView(remoteViews)



                                        .setCategory(NotificationCompat.CATEGORY_SERVICE)
                                        .setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                                        .build();




                                notificationManager.notify(1 , notification);


//                                        TextView textView = ((MainActivity)context).findViewById(R.id.notitemp);
//
//                                        textView.setText(String.valueOf(abcd));
//                                        float abcde = myobj.main.getHumidity();
//
//                                        TextView textVie = ((MainActivity)context).findViewById(R.id.humiditytemp);
//
//                                        textVie.setText(String.valueOf(abcde));
//
//                                        float abc = myobj.wind.getSpeed();
//
//                                        TextView textVi = ((MainActivity)context).findViewById(R.id.wind);
//
//                                        textVi.setText(String.valueOf(abc));
//
//
//                                        if (myobj.getname().isEmpty()) {
//
//                                            try {
//                                                String a = myobj.getname();
//
//                                                TextView text = ((MainActivity)context).findViewById(R.id.city);
//                                                text.setText(a);
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                    }
//
//                                });

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


}

}
