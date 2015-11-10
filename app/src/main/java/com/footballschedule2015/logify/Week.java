package com.footballschedule2015.logify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Week implements Parcelable {
	
	private String number;
	private ArrayList<Game> games;

	public Week() {
		setGames(new ArrayList<Game>());
	}


    protected Week(Parcel in) {
        number = in.readString();
        if (in.readByte() == 0x01) {
            games = new ArrayList<Game>();
            in.readList(games, Game.class.getClassLoader());
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
        dest.writeString(number);
        if (games == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(games);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Week> CREATOR = new Creator<Week>() {
        @Override
        public Week createFromParcel(Parcel in) {
            return new Week(in);
        }

        @Override
        public Week[] newArray(int size) {
            return new Week[size];
        }
    };

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}


	/**
	 * @return the games
	 */
	public ArrayList<Game> getGames() {
		return games;
	}


	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}


	/**
	 * @param games the games to set
	 */
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
}
