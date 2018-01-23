package com.sudharshan.user.anasnmnt19_1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Android system initiates its program with in an Activity starting with a call on onCreate() callback method.
public class MainActivity extends AppCompatActivity {
    //creating textview variable
    TextView tv;

    //  Called when the activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv =(TextView)findViewById(R.id.tv);

        new Myweather().execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");
    }

    //This class is responsible for fetching the weather data from the OpenWeatherMap API.
    //We use the HttpURLConnection class to make the remote request
    private class Myweather extends AsyncTask<String,Void,String>{


        //on implementing Asyntask it is an abstract class provided by Android
        //which gives us the liberty to perform heavy tasks in the background and keep the UI thread light
        //the methods used in this class are doInBackground(),onPreexcute(),onPostexecute(),onProgress()
        //here there are three generic types in asyntask there are params,progress,and result
        //we use string type
        @Override
        protected String doInBackground(String... strings) {
            //here in this doinbackground method contains the code which needs to be executed in background.
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                InputStream stream=conn.getInputStream();

                // using BufferedReader to read the API's response into a StringBuilder.
                // When we have the complete response, we convert it to a JSONObject object.
                // AsyncTask can be called only once. Executing it again will throw an exception
                //Using BufferReader we read the obj in the inputstream
                BufferedReader reader= new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String inputstring;
                //here it reads the inputstring if it is not equal to zero it append the inputstring
                while ((inputstring=reader.readLine())!=null){
                    builder.append(inputstring);

                }
                //here creating Json objects as in the yrl conn the viewer data will be in braces which is known as JSOn objects
                JSONObject json = new JSONObject(builder.toString());
                //now here in this Json obj we have main which contains json objects and initializing them in the getJSONobject string
                JSONObject main = json.getJSONObject("main");
                //here creating string type to get the temperature
                String temp = main.getString("temp");


                //reutrns the temp
                return temp;
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
        //This method is called after doInBackground method completes processing.
// //Result from doInBackground is passed to this method
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //it sets the text and displays the temp
            tv.setText("Temperature is "+s + (char) 0x00B0 );

        }
    }
}