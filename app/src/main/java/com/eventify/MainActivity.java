package com.eventify;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class MainActivity extends MaterialNavigationDrawer {


    @Override
    public void init(Bundle bundle) {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String id = s.getString("id",null);

        // create sections
        if(id==null)
        {      this.addSection(newSection("All Events", R.drawable.ic_event_available_black_24dp, new fragment_colleges()));

        Intent i4= new Intent(MainActivity.this,Destination.class);
        this.addSection(newSection("Tourist Attraction",R.drawable.ic_directions_transit_black_24dp,i4));
        }
        else{


            this.addSection(newSection("All Events", R.drawable.ic_event_available_black_24dp, new Fragment_Dashboard()));

            Intent i2 = new Intent(MainActivity.this,Login.class);
            i2.putExtra("signout", "1");
            this.addBottomSection(newSection("Sign Out", i2));

            Intent i4= new Intent(MainActivity.this,Destination.class);
            this.addSection(newSection("Tourist Attraction",R.drawable.ic_directions_transit_black_24dp,i4));

        }


        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.punjabtourism.gov.in/"));
        this.addSection(newSection("Punjab Tourism", R.drawable.ic_map_black_24dp, viewIntent));

        Intent viewIntent2 =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(
                                "http://www.punbusonline.com/"));

        this.addSection(newSection("Punjab Roadways", R.drawable.ic_directions_transit_black_24dp, viewIntent2));

        Intent i3 = new Intent(MainActivity.this,MyProfile.class);
        this.addSection(newSection("My Profile", R.drawable.ic_mood_black_24dp, i3));


        Intent i = new Intent(MainActivity.this,Emergency.class);
        this.addSubheader("Support");
        this.addSection(newSection("Emergency Contacts", R.drawable.ic_warning_black_24dp,i));

        this.disableLearningPattern();



        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, "Check out this awesome android app to know about all the " +
                "upcoming college events in our city\nCollegeGapp : https://goo.gl/S0cUIh ");


    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
