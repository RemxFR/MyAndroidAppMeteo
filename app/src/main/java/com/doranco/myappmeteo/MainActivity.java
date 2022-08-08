package com.doranco.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.doranco.myappmeteo.api.OpenWeatherApi;
import com.doranco.myappmeteo.models.GpsTacker;
import com.google.gson.JsonElement;

import java.text.DecimalFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText ville, pays;
    private TextView resultat;
    private Button btn;
    private final String url = "https://api.openweathermap.org/data/2.5/";
    private final String api = "YOUR API KEY";
    private final String unit = "metric";
    private final String langue = "fr";
    //private String output = "";

    //geoloc
    private double lat;
    private double lon;
    private GpsTacker gpsTracker;

    private OpenWeatherApi openWeatherApi;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ville = findViewById(R.id.etVille);
        pays = findViewById(R.id.etPays);

        resultat = findViewById(R.id.toResultat);

        btn = findViewById(R.id.etBtn);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherApi = retrofit.create(OpenWeatherApi.class);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //getWeatherDetails(ville, pays);
                //getLocation(view);

                    getWeatherDetails(ville, pays);




            }
        });

    }

    public void getLocation(View view){
        gpsTracker = new GpsTacker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            lat = gpsTracker.getLatitude();
            lon = gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public void getWeatherOnGeoLoc(){


        Call<JsonElement> call = openWeatherApi.getWeather(lat, lon, api, langue, unit);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code", String.valueOf(response.code()));
                    return;
                }


                JsonElement jSonresponse = response.body();


                Log.e("geo", jSonresponse.getAsJsonObject().get("coord").toString());
                Log.e("weather", jSonresponse.getAsJsonObject().get("weather").toString());
                Log.e("temp", jSonresponse.getAsJsonObject().get("main").toString());
                Log.e("sys", jSonresponse.getAsJsonObject().get("sys").toString());
                Log.e("ville", jSonresponse.getAsJsonObject().get("name").toString());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    public void getWeatherDetails(EditText ville, EditText pays) {

        //String tempUrl = "";

        String city = this.ville.getText().toString().trim();
        String country = this.pays.getText().toString().trim();

        if (city.equals("")) {
            resultat.setText("Veuillez renseigner une ville");
        } else {
            if (!country.equals("")) {
                Log.e("ville", city);
                Log.e("pays", country);
                //tempUrl = url + "?q" + ville + "," + pays + "&appid=" + api + "&units=metric";
                Call<JsonElement> call = openWeatherApi.getWeather(city, country, api, langue, unit);

                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        String output = "";

                        if (!response.isSuccessful()) {
                            Log.e("Code", String.valueOf(response.code()));
                            return;
                        }


                        JsonElement jSonresponse = response.body();


                        output += "La Météo actuelle de " + city + " (" + country+")"
                                + "\n Temperature: " + jSonresponse.getAsJsonObject().get("main").getAsJsonObject().get("temp").toString()
                                + "\n Ressenti: " + jSonresponse.getAsJsonObject().get("main").getAsJsonObject().get("feels_like").toString()
                                + "\n Le Temps: " + jSonresponse.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main")
                                + "\n Description: " + jSonresponse.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description");

                        resultat.setText(output);
                        resultat.setTextColor(Color.rgb(255,255,255));

                        Log.e("geo", jSonresponse.getAsJsonObject().get("coord").toString());
                        Log.e("weather", jSonresponse.getAsJsonObject().get("weather").toString());
                        Log.e("temp", jSonresponse.getAsJsonObject().get("main").toString());
                        Log.e("sys", jSonresponse.getAsJsonObject().get("sys").toString());
                        Log.e("ville", jSonresponse.getAsJsonObject().get("name").toString());
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
                Log.d("retour", "part1");
            } else {

                Log.e("ville", city);
                Call<JsonElement> call = openWeatherApi.getWeather(city, api, langue, unit);

                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        String output = "";
                        if (!response.isSuccessful()) {
                            Log.e("Code", String.valueOf(response.code()));
                            return;
                        }


                        JsonElement jSonresponse = response.body();

                        output += "La Météo actuelle de " + city.toUpperCase(Locale.ROOT)+ "(" +jSonresponse.getAsJsonObject().get("sys").getAsJsonObject().get("country").toString() +")"
                                + "\n Temperature: " + jSonresponse.getAsJsonObject().get("main").getAsJsonObject().get("temp").toString()
                                + "\n Ressenti: " + jSonresponse.getAsJsonObject().get("main").getAsJsonObject().get("feels_like").toString()
                                + "\n Le Temps: " + jSonresponse.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main")
                                + "\n Description: " + jSonresponse.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description");

                        resultat.setText(output);
                        resultat.setTextColor(Color.rgb(255,255,255));

                        //Log.e("reponse",jSonresponse.getAsJsonObject().toString());
                        Log.e("geo", jSonresponse.getAsJsonObject().get("coord").toString());
                        Log.e("weather", jSonresponse.getAsJsonObject().get("weather").toString());
                        Log.e("temp", jSonresponse.getAsJsonObject().get("main").getAsJsonObject().get("temp").toString() + "°C");
                        Log.e("sys", jSonresponse.getAsJsonObject().get("sys").getAsJsonObject().get("country").toString());
                        Log.e("ville", jSonresponse.getAsJsonObject().get("name").toString());

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
                Log.d("retour", "part2");
            }


        }

    }





}
