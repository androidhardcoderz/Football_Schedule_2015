package com.footballschedule2015.logify;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {

    @SuppressWarnings("unused")
    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
    private Season season;

    public Schedule() {
        // TODO Auto-generated constructor stub
    }

    protected Schedule(Parcel in) {
        season = (Season) in.readValue(Season.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(season);
    }

 /**
     * @return the season
     */
    public Season getSeason() {
        return season;
    }


    /**
     * @param season the season to set
     */
    public void setSeason(Season season) {
        this.season = season;
    }
}
