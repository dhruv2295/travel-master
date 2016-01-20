package com.eventify;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import func.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class DestinationFragment extends Fragment {

    public DestinationFragment() {
    }


    ListView lv;
    String cat;
    static List<String> list = new ArrayList<String>();
    static List<String> list1 = new ArrayList<String>();
    static List<String> list2 = new ArrayList<String>();
    static List<String> list5 = new ArrayList<String>();
    static List<String> list6 = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    Activity activity;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_colleges, container, false);
        if(activity!=null) {
            InputMethodManager input = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(activity.getCurrentFocus()!=null)
                input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }





        lv = (ListView) rootView.findViewById(R.id.list);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());


        if(Utils.isNetworkAvailable(activity))
            new DownloadWebPageTask().execute();
        else
            Toast.makeText(activity, "Check Your internet connection", Toast.LENGTH_SHORT).show();



        return rootView;
    }

    String data;
    int iszer=0;
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            iszer=0;
            list.clear();
            list1.clear();
            list2.clear();
            list5.clear();
            list6.clear();

        };

        @Override
        protected String doInBackground(String... urls) {


            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            // String aloo = s.getString("id", "0");
            String  URL = "http://csinsit.org/prabhakar/aloogobhi/get-attractions.php";

            Log.e("this", URL + " ");
            HttpClient Client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try
            {
                data = Client.execute(httpget, responseHandler);
                Log.d("EWFU","doing");
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Log.e("here",e.getMessage());
            }
            Log.d("Data is:",data);
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("YO", "Done");
            Log.e("yrs",data+" ");
            JSONObject ob;
            JSONArray arr;
            if(data!=null)
                try {
                    ob = new JSONObject(data);
                    arr = ob.getJSONArray("events");
                    if(arr.length()==0){
                        iszer=1;
                    }
                    Log.d("maadarchod-tatti", " " + arr.toString());
                    Log.d("maadarchod", " " + arr.length());
                    for(int i = 0; i < arr.length(); i++){
                        try {
                            list.add(arr.getJSONObject(i).getString("id"));
                            list1.add(arr.getJSONObject(i).getString("name"));
                            list2.add(arr.getJSONObject(i).getString("city_name"));
                            list5.add(arr.getJSONObject(i).getString("latitude"));
                            list6.add(arr.getJSONObject(i).getString("longitude"));

                            Log.d("yolo",arr.getJSONObject(i).getString("name"));

                        } catch (JSONException e) {
                            Log.e("Fuck this shit",e.getMessage());
                        }
                    }

                    show();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void show(){
//Log.d("Plz kuch ho ja", list1.get(2));
        TouristAdapter adapter = new TouristAdapter(activity, list,list1,list2,list5,list6);
        lv.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        show();
    }


}
