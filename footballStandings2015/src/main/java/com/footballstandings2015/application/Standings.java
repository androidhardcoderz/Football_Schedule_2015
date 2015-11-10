package com.footballstandings2015.application;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Standings implements Parcelable {

	private String season;
	private String type;
	private ArrayList<Conferences> conferences;

	public Standings() {
		conferences = new ArrayList<Conferences>();
	}

	/**
	 * @return the season
	 */
	public String getSeason() {
		return season;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the conferences
	 */
	public ArrayList<Conferences> getConferences() {
		return conferences;
	}

	/**
	 * @param season
	 *            the season to set
	 */
	public void setSeason(String season) {
		this.season = season;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param conferences
	 *            the conferences to set
	 */
	public void setConferences(ArrayList<Conferences> conferences) {
		this.conferences = conferences;
	}


    protected Standings(Parcel in) {
        season = in.readString();
        type = in.readString();
        if (in.readByte() == 0x01) {
            conferences = new ArrayList<Conferences>();
            in.readList(conferences, Conferences.class.getClassLoader());
        } else {
            conferences = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(season);
        dest.writeString(type);
        if (conferences == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(conferences);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Standings> CREATOR = new Parcelable.Creator<Standings>() {
        @Override
        public Standings createFromParcel(Parcel in) {
            return new Standings(in);
        }

        @Override
        public Standings[] newArray(int size) {
            return new Standings[size];
        }
    };
}