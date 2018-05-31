package com.trivediinfoway.dailyquotes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    int MID = 0;
    Context con;
    String quote;
    Bitmap bitmap,bitmap1;
    int [] images = {R.drawable.aa,R.drawable.bb,R.drawable.cc,R.drawable.ff,R.drawable.ee,R.drawable.ff,R.drawable.gg
    ,R.drawable.mm,R.drawable.ll,R.drawable.kk,R.drawable.jj,R.drawable.ii,R.drawable.hh,R.drawable.oo};

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Random r = new Random();
        int i2 = r.nextInt(13 - 0 + 1) + 0;

        con = context;
        bitmap = BitmapFactory.decodeResource(con.getResources(),
                images[i2]);
        bitmap1 = BitmapFactory.decodeResource(con.getResources(),
                R.drawable.b);
        long when = System.currentTimeMillis();
        JsonFromAssets();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, DetailActivity.class);
        notificationIntent.putExtra("quote",quote+"");
        notificationIntent.putExtra("position",i2);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Daily Inspirational Quotes")
                .setContentText(quote+"").setSound(alarmSound)
                .setSmallIcon(R.drawable.b)
                .setLargeIcon(bitmap1)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setSummaryText(quote+""));
               // .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;
    }
    void JsonFromAssets()
    {
        try {
            Random r = new Random();
            int i1 = r.nextInt(13 - 0 + 1) + 0;
            Log.e("i1", i1+"i1");

            JSONObject obj = new JSONObject(readJSONFromAsset());
            JSONArray array = obj.getJSONArray("diwali");
            JSONObject jObj = array.getJSONObject(i1);
            quote = jObj.getString("quote");
            Log.e("quote", jObj.getString("quote"));

        } catch (Exception e) {
            Log.e("Error...", e.getMessage() + "");
        }
    }
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = con.getAssets().open("greetings.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
