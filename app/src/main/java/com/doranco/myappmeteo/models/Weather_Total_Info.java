package com.doranco.myappmeteo.models;

public class Weather_Total_Info {

    private Coord coord;
    private Weather weather;
    private String station;
    private Main_Weather main;
    private long visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;

    public Coord getCoord() {
        return coord;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getStation() {
        return station;
    }

    public Main_Weather getMain() {
        return main;
    }

    public long getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}
