package com.example.freeroad;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.GeoApiContext;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private GeoApiContext mGeoApiContext = null;
    private static final String TAG = "UserListFragment";

    //Pobieranie danych trasy
    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                //-1 koniec stringa - end of file
                while(data != -1){
                    char letter = (char) data;
                    json += letter;
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
//            Log.i("Trasa",json);

            try {
                //przekształcanie na tekst z jsona
                JSONObject jsonObject = new JSONObject(json);
                //"[" - use getJSONArray
                //"{" - use getJSONObject
                JSONArray routesArray = jsonObject.getJSONArray("routes");
                JSONObject arrayObject = routesArray.getJSONObject(0);
                JSONArray legsArray = arrayObject.getJSONArray("legs");
                JSONObject legsObject = legsArray.getJSONObject(0);
                JSONArray stepArray = legsObject.getJSONArray("steps");

                for(int i = 0; i < stepArray.length(); i++){
                JSONObject stepsObject = stepArray.getJSONObject(i);
                JSONObject startLocation = stepsObject.getJSONObject("start_location");
                JSONObject endLocation = stepsObject.getJSONObject("end_location");

                    Log.i("Punkt startowy - lat:",startLocation.getString("lat"));
                    Log.i("Punkt startowy - lng:",startLocation.getString("lng"));
                    Log.i("Punkt koncowy - lat:",endLocation.getString("lat"));
                    Log.i("Punkt koncowy - lng:",endLocation.getString("lng"));
                }

                //TextView pogodaTextVeiw = findViewById(R.id.pogodaTextView);
                //pogodaTextVeiw.setText(weather.getString("description"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        DownloadTask task = new DownloadTask();
//        task.execute("http://api.openweathermap.org/data/2.5/weather?q=gdansk&appid=2023ca940ee6a71d8121ed4fccd48abd");
        task.execute("https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=Chylonia, Gdynia&" +
                "destination=Rezerwat przyrody Cisowa, Gdynia&" +
                "waypoints=Łężyce, 84-207&" +
                "key=AIzaSyDAqU8VuZm3-D8hzdd9Uk_pXrvb9h0skI8");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Add map type (terrain, satellite)
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Toast.makeText(MapsActivity.this, location.toString(), Toast.LENGTH_LONG).show();
                // Add a marker in myLocation and move the camera
                mMap.clear();

                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(
                        new MarkerOptions()
                                .position(myLocation)
                                .title("Tutaj jestem")
                                //Change colour of marker
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                );

                mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(54.5332, 18.4443),
                                new LatLng(54.5279, 18.4288),
                                new LatLng(54.5289, 18.4238),
                                new LatLng(54.5316, 18.4280)));

                //newLatLngZoom - create zoom in your map, 0 - 20
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}