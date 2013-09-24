package com.scjci.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gdata.data.extensions.Image;

/**
 * Created by kurt.yang on 2013/8/22.
 */
public class eee extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abcd);

        LinearLayout ll = (LinearLayout)findViewById(R.id.mobilephonecontainer);
        /*ll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                eee.this.finish();
            }
        });*/

        final LinearLayout mpc = (LinearLayout)findViewById(R.id.mobilephonecall);
        final TextView mp = (TextView)findViewById(R.id.mobilephone);
        String a = mp.getText().toString();
        //final Drawable dr = mpc.getBackground();
        mpc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mpc.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mpc.setBackground(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        //set color back to default
                        mpc.setBackground(null);
                        Intent intentDial = new Intent("android.intent.action.CALL", Uri.parse(String.format("tel:%s", mp.getText().toString())));
                        startActivity(intentDial);
                        break;
                }
                return true;
            }
        });
        /*tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView vv = (TextView)view;
                Log.i("abc",vv.getText().toString());
                Intent intentDial = new Intent("android.intent.action.CALL", Uri.parse("tel:0955118-998"));
                startActivity(intentDial);
            }
        });*/
    }
}