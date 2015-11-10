package com.footballschedule2015.logify;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private String id;
    private String schedule;
    private String away;
    private String home;
    private String week;

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public int getAwayColor() {
        return awayColor;
    }

    public void setAwayColor(int awayColor) {
        this.awayColor = awayColor;
    }

    public int getAwayIndex() {
        return awayIndex;
    }

    public void setAwayIndex(int awayIndex) {
        this.awayIndex = awayIndex;
    }

    public static Creator<Game> getCREATOR() {
        return CREATOR;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public int getHomeColor() {
        return homeColor;
    }

    public void setHomeColor(int homeColor) {
        this.homeColor = homeColor;
    }

    public int getHomeIndex() {
        return homeIndex;
    }

    public void setHomeIndex(int homeIndex) {
        this.homeIndex = homeIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    private String date,time;
    private int awayColor,homeColor,awayIndex,homeIndex;

    public Game(){

    }

    protected Game(Parcel in) {
        id = in.readString();
        schedule = in.readString();
        away = in.readString();
        home = in.readString();
        week = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(schedule);
        dest.writeString(away);
        dest.writeString(home);
        dest.writeString(week);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}