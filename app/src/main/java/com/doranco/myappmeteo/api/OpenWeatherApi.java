package com.doranco.myappmeteo.api;

import com.doranco.myappmeteo.models.Coord;
import com.doranco.myappmeteo.models.Weather_Total_Info;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherApi {


    @GET("weather")
    Call<JsonElement> getWeather(@Query("q") String ville,
                                    @Query(",") String pays,
                                    @Query("appid") String appid,
                                 @Query("lang") String langue,
                                 @Query("units") String unit
                                 );

    @GET("weather")
    Call<JsonElement> getWeather(@Query("q") String ville,
                                 @Query(",") String pays,
                                 @Query("appid") String appid);

    @GET("weather")
    Call<JsonElement> getWeather(@Query("q") String ville,
                                 @Query("appid") String appid,
                                 @Query("lang") String langue,
                                 @Query("units") String unit
                                 );

    @GET("weather")
    Call<JsonElement> getWeather(@Query("q") String ville,
                                 @Query("appid") String appid);


    @GET("weather")
    Call<JsonElement> getWeather(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("appid") String appid);

    @GET("weather")
    Call<JsonElement> getWeather(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("appid") String appid,
                                 @Query("lang") String langue,
                                 @Query("units") String unit);
}
