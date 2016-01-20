package com.eventify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class MyProfile extends AppCompatActivity{

    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.fragment_mhprofile);
t1 = (TextView) findViewById(R.id.name);
        t2 = (TextView) findViewById(R.id.email);




        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MyProfile.this);
      //  String name = s.getString("username", "Dhruv");
        String email = s.getString("email","dhruv2295@gmail.com");

     //   t1.setText(name);
        t2.setText(email);
        setTitle("My Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}