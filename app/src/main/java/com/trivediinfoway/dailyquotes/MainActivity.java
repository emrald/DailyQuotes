package com.trivediinfoway.dailyquotes;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String quote = "";
    ArrayList<String> list;
    ListView listView;
    ProgressDialog pd;
    String q[] = {"A", "B", "C", "D", "E"};
    RelativeLayout rlmain;
    private Handler progressHandler = new Handler();
    private int progressCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        rlmain = (RelativeLayout) findViewById(R.id.rlmain);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 18);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
       /* for(int i=0;i<5;i++) {
            TextView txt = new TextView(MainActivity.this);
            txt.setText(q[i]+"");
            txt.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
           *//* txt.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            txt.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
            txt.setTextColor(Color.parseColor("#000000"));*//*
            rlmain.addView(txt);
            try {
                Thread.sleep(1000);
                //  Log.e("...QUOTES...",q[i]+"");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        new FetchQuotesAsynvTask(MainActivity.this).execute();
        //  JsonFromAssets();
    }

    class FetchQuotesAsynvTask extends AsyncTask<Void, Integer, Integer> {

        Activity activity;

        public FetchQuotesAsynvTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onPreExecute() {
            pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
            /*for (int i = 0; i < 5; i++) {
                pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
                try {
                    //   pd.setMessage(q[i]+"");//"Give whatever you are doing and whoever you are with the gift of your attention. (Jim Rohn)");
                    Thread.sleep(1000);
                    pd.setMessage(q[i] + "");
                    Log.e("...QUOTES...", q[i] + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("Error...", e.getMessage() + "");
                }
            }*/
           /* pd=new ProgressDialog(activity);
            pd.setTitle("Loading...");

            for (int i = 0; i < 5; i++) {
                try {
                    //   pd.setMessage(q[i]+"");//"Give whatever you are doing and whoever you are with the gift of your attention. (Jim Rohn)");
                    Thread.sleep(1000);
                    pd.setMessage(q[i] + "");
                    Log.e("...QUOTES...", q[i] + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("Error...", e.getMessage() + "");
                }
                pd.show();
            }
*/


        //    download();
            //fetchProgressDailog();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                  /*  try {
                        Thread.sleep(1000);
                      //  Log.e("...QUOTES...",q[i]+"");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
       //         fetchProgressDailog();
                Random r = new Random();
                int i1 = r.nextInt(2 - 0 + 1) + 0;
                Log.e("i1", i1 + "i1");
                list = new ArrayList<String>();
                JSONObject obj = new JSONObject(readJSONFromAsset());
                JSONArray array = obj.getJSONArray("diwali");
                JSONObject jObj = array.getJSONObject(i1);
                quote = jObj.getString("quote");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonobject = array.getJSONObject(i);
                    list.add(jsonobject.getString("quote"));
                }
                return 1;
            } catch (Exception e) {
                Log.e("Error...", e.getMessage() + "");
                return 0;
            }
        }

        @Override
        public void onPostExecute(Integer i) {
            super.onPostExecute(i);
            if ((pd != null) && pd.isShowing()) {
                pd.dismiss();
            }
            if (i == 1) {
                ArrayAdapter<String> listItemAdapter
                        = new ArrayAdapter<String>(
                        MainActivity.this,
                        R.layout.list_item, R.id.textView,
                        list);
                listView.setAdapter(listItemAdapter);
                //     Log.e("quote", jObj.getString("quote"));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("quote", list.get(i) + "");
                        intent.putExtra("position", i);
                        startActivity(intent);
                    }
                });
            } else {
                Log.e("Error...", "No data found.");
            }
        }
    }
    public void download(){
        /*pd=new ProgressDialog(this);
        pd.setMessage("Downloading Music");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();

        final int totalProgressTime = 1000;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(5000);
                        jumpTime += 5;
                        pd.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (progressCount < 100) {
                    progressCount += 2;
                    progressHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pd.show();
                            //  updateProgressBar.setText("Current Progress -"+ progressCount);
                            Log.e("progress...",progressCount+"");
                        }
                    });
                }
            }

        }).start();
    }
    void fetchProgressDailog() {
     //   for (int i = 0; i < 5; i++) {
            //       TextView txt = new TextView(MainActivity.this);
            //       txt.setText(q[i] + "");
            //    txt.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
       /* txt.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        txt.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        txt.setTextColor(Color.parseColor("#000000"));*/
            //      rlmain.addView(txt);
           /* try {
                Thread.sleep(1000);
                Log.e("...QUOTES...", q[i] + "");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("Error...", e.getMessage() + "");
            }*/
            for (int i = 0; i < 5; i++) {
                pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
                try {
                    //   pd.setMessage(q[i]+"");//"Give whatever you are doing and whoever you are with the gift of your attention. (Jim Rohn)");
                    Thread.sleep(1000);
                    pd.setMessage(q[i] + "");
                    Log.e("...QUOTES...", q[i] + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("Error...", e.getMessage() + "");
                }
        }
    }

    /* void JsonFromAssets()
     {
         try {
             Random r = new Random();
             int i1 = r.nextInt(2 - 0 + 1) + 0;
             Log.e("i1", i1+"i1");
             list = new ArrayList<String>();
             JSONObject obj = new JSONObject(readJSONFromAsset());
             JSONArray array = obj.getJSONArray("diwali");
             JSONObject jObj = array.getJSONObject(i1);
             quote = jObj.getString("quote");
             for(int i = 0 ;i<array.length();i++) {
                 JSONObject jsonobject = array.getJSONObject(i);
                 list.add(jsonobject.getString("quote"));
             }
             ArrayAdapter<String> listItemAdapter
                     = new ArrayAdapter<String>(
                     this,
                     R.layout.list_item,R.id.textView,
                     list);
             listView.setAdapter(listItemAdapter);
             Log.e("quote", jObj.getString("quote"));

             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                     intent.putExtra("quote",list.get(i)+"");
                     intent.putExtra("position",0);
                     startActivity(intent);
                 }
             });
         } catch (Exception e) {
             Log.e("Error...", e.getMessage() + "");
         }
     }*/
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("greetings.json");
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

