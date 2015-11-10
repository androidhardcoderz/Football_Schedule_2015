package com.footballschedule2015.logify;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Season implements Parcelable {
	
	private ArrayList<Week> games;
	
	public Season() {
		games = new ArrayList<Week>();
	}

    protected Season(Parcel in) {
        if (in.readByte() == 0x01) {
            games = new ArrayList<Week>();
            in.readList(games, Week.class.getClassLoader());
        } else {
            games = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (games == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(games);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

	/**
	 * @return the games
	 */
	public ArrayList<Week> getGames() {
		return games;
	}

	/**
	 * @return the creator
	 */
	public static Creator<Season> getCreator() {
		return CREATOR;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(ArrayList<Week> games) {
		this.games = games;
	}
}
