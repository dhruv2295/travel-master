package com.eventify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dhruv on 05-09-2015.
 */
public class TouristAdapter extends ArrayAdapter<String> {
    Activity context;
    Typeface custom_font,custom_font2,custom_font3;
   // Button btn;

    private final List<String> ids,name,cityname,lat,lon;
    public TouristAdapter(Activity context, List<String> id, List<String> n, List<String> t, List<String> l
            , List<String> lo
    ){
        super(context, R.layout.message_layout, id);
        this.context = context;
        ids=id;
        name=n;
        cityname=t;
        lat=l;
        lon=lo;

        custom_font = Typeface.createFromAsset(context.getAssets(), "Lato-Light.ttf");
        custom_font2 = Typeface.createFromAsset(context.getAssets(), "Oregon LDO Bold.ttf");
        custom_font3 = Typeface.createFromAsset(context.getAssets(), "Oregon LDO Extended.ttf");


    }
    TextView more,Name,cname;

    @Override
    public View getView(final int position, View view2, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.message_layout2, null);

ButtonRectangle btn;
        btn = (ButtonRectangle) view.findViewById(R.id.button);
        Name = (TextView) view.findViewById(R.id.name);

        cname = (TextView) view.findViewById(R.id.city);


        Name.setTypeface(custom_font2);
//		dis.setTypeface(custom_font3);
//        Time.setTypeface(custom_font);
        cname.setTypeface(custom_font);



        Name.setText(name.get(position));
        cname.setText(cityname.get(position));


         Double OriginLat=0.0,OriginLong=0.0;
        GPSTracker tracker = new GPSTracker(context);
        if (tracker.canGetLocation() == false) {
            tracker.showSettingsAlert();
        } else {
            OriginLat = tracker.getLatitude();
            OriginLong = tracker.getLongitude();
        }


final Double olat=OriginLat, olong=OriginLong;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, DesActivity.class);
                i.putExtra("name", name.get(position));
                i.putExtra("eid", ids.get(position));
                i.putExtra("lat", lat.get(position));
                i.putExtra("lon", lon.get(position));
                i.putExtra("cnm", cityname.get(position));
                context.startActivity(i);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s,%s", olat, olong, lat.get(position), lon.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//// Verify that the intent will resolve to an activity
//
//                Intent sendIntent = new Intent(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, name.get(position)+'\n'+'\n'+"Check Out This Awesome Event Bitch!!");
//                //	sendIntent.putExtra(Intent.EXTRA_TEXT,"");
//                //	sendIntent.putExtra(Intent.EXTRA_TEXT,"");
//                //	sendIntent.putExtra(Intent.EXTRA_TEXT,"Check Out This Awesome Event Bitch!! ");
//                sendIntent.setType("text/plain");
//
//// This says something like "Share this photo with"
//                String title = "Share via";
//// Create intent to show the chooser dialog
//                Intent chooser = Intent.createChooser(sendIntent, title);
//
//// Verify the original intent will resolve to at least one activity
//                //	if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                context.startActivity(chooser);
//
//            }
//        });

        AnimationSet set = new AnimationSet(true);
        TranslateAnimation slide = new TranslateAnimation(-100, 0, -100, 0);
        slide.setInterpolator(new DecelerateInterpolator(5.0f));
        slide.setDuration(300);
        Animation fade = new AlphaAnimation(0, 1.0f);
        fade.setInterpolator(new DecelerateInterpolator(5.0f));
        fade.setDuration(300);
        set.addAnimation(slide);
        set.addAnimation(fade);
        view.startAnimation(set);
        return view;
    }




}
