package com.footballstandings2015.application;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Conferences implements Parcelable {
	private String id;
	private String name;
	private ArrayList<Division> divisions;

	public Conferences() {
		divisions = new ArrayList<Division>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the divisions
	 */
	public ArrayList<Division> getDivisions() {
		return divisions;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param divisions
	 *            the divisions to set
	 */
	public void setDivisions(ArrayList<Division> divisions) {
		this.divisions = divisions;
	}

    protected Conferences(Parcel in) {
        id = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            divisions = new ArrayList<Division>();
            in.readList(divisions, Division.class.getClassLoader());
        } else {
            divisions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        if (divisions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(divisions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Conferences> CREATOR = new Parcelable.Creator<Conferences>() {
        @Override
        public Conferences createFromParcel(Parcel in) {
            return new Conferences(in);
        }

        @Override
        public Conferences[] newArray(int size) {
            return new Conferences[size];
        }
    };
}