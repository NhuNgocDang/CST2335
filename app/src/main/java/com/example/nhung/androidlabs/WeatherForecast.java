package com.example.nhung.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {
    private static final String TAG = "WeatherForecast";
    private ProgressBar progressBar;
    public String urlString = "";
    public TextView speed;
    public TextView minView;
    public TextView maxView;
    public TextView currentTempView;
    public ImageView picCurrentWeather;


    // 4 string variables
    // 1 Bitmap picture
    String valueTemp = "";
    String min = "";
    String max = "";
    String valueSpeed = "";
    String icon = "";
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        speed = findViewById(R.id.wind);
        minView = findViewById(R.id.min);
        maxView = findViewById(R.id.max);
        currentTempView = findViewById(R.id.temperate);
        picCurrentWeather = findViewById(R.id.pictureWeather);

        new ForecastQuery().execute();
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {


        protected String doInBackground(String... args) {
            InputStream inputStream;


            //  URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
            // return null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                inputStream = conn.getInputStream();

                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(inputStream, "UTF-8");
                    parser.nextTag();

                    while (parser.next() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }

                        String name = parser.getName();
                        if (name.equals("temperature")) {
                            valueTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            min = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            max = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }

                        if (name.equals("speed")) {
                            valueSpeed = parser.getAttributeValue(null, "value");
                            publishProgress(100);
                        }

                        if (name.equals("weather")) {
                            icon = parser.getAttributeValue(null, "icon");
                            icon = icon + ".png";

                            if (fileExistance(icon)) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(icon);
                                    Log.i(TAG, "Can find the image locally?");
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();

                                }
                                image = BitmapFactory.decodeStream(fis);
                              //  publishProgress(100);
                            }
                            else {
                                Log.i(TAG, "Can not find the image locally?");
                                image = getImage(icon);
                                FileOutputStream outputStream = openFileOutput(icon, Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                            }
                        }
                    }

                } finally {
                    inputStream.close();
                }


            }
            catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
            return "";
        }

        public boolean fileExistance(String icon) {
            File file = getBaseContext().getFileStreamPath(icon);
            return file.exists();

        }

        // function for downloading image
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            String url = "http://openweathermap.org/img/w/";
            try {
                URL url1 = new URL(url + urlString);
                return getImage(url1);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        public void onProgressUpdate(Integer... value) //update your GUI
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }


        public void onPostExecute(String result)  // doInBackground has finished
        {
            currentTempView.setText("Current Temperate: " + valueTemp);
            minView.setText("Min: " + min);
            maxView.setText("Max: " + max);
            speed.setText("Speed: "+ valueSpeed);
            picCurrentWeather.setImageBitmap(image);
            progressBar.setVisibility(View.INVISIBLE);

        }

    }
}

