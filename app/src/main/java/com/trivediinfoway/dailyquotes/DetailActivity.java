package com.trivediinfoway.dailyquotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Bundle bn;
    String quote = "";
    int position;
    TextView textView2;
    ImageView rl;
    int [] images = {R.drawable.aa,R.drawable.bb,R.drawable.cc,R.drawable.ff,R.drawable.ee,R.drawable.ff,R.drawable.gg
            ,R.drawable.mm,R.drawable.ll,R.drawable.kk,R.drawable.jj,R.drawable.ii,R.drawable.hh,R.drawable.oo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView2 = (TextView) findViewById(R.id.textView2);
        rl = (ImageView)findViewById(R.id.rl);

        bn = getIntent().getExtras();
        if (bn != null) {
            quote = bn.getString("quote");
            position = bn.getInt("position");
        }
        textView2.setText(quote + "");
        rl.setImageResource(images[position]);
    }
}
