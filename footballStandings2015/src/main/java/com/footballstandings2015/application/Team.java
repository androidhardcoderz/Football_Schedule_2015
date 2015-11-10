package com.footballstandings2015.application;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {
	private String id;
	private String name;
	private String market;
	private String wins;
	private String losses;
	private String ties;
	private String win_percentage;

	public Team() {
		// TODO Auto-generated constructor stub
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
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @return the wins
	 */
	public String getWins() {
		return wins;
	}

	/**
	 * @return the losses
	 */
	public String getLosses() {
		return losses;
	}

	/**
	 * @return the ties
	 */
	public String getTies() {
		return ties;
	}

	/**
	 * @return the win_percentage
	 */
	public String getWin_percentage() {
		return win_percentage;
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
	 * @param market
	 *            the market to set
	 */
	public void setMarket(String market) {
		this.market = market;
	}

	/**
	 * @param wins
	 *            the wins to set
	 */
	public void setWins(String wins) {
		this.wins = wins;
	}

	/**
	 * @param losses
	 *            the losses to set
	 */
	public void setLosses(String losses) {
		this.losses = losses;
	}

	/**
	 * @param ties
	 *            the ties to set
	 */
	public void setTies(String ties) {
		this.ties = ties;
	}

	/**
	 * @param win_percentage
	 *            the win_percentage to set
	 */
	public void setWin_percentage(String win_percentage) {
		this.win_percentage = win_percentage;
	}

	protected Team(Parcel in) {
		id = in.readString();
		name = in.readString();
		market = in.readString();
		wins = in.readString();
		losses = in.readString();
		ties = in.readString();
		win_percentage = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(market);
		dest.writeString(wins);
		dest.writeString(losses);
		dest.writeString(ties);
		dest.writeString(win_percentage);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
		@Override
		public Team createFromParcel(Parcel in) {
			return new Team(in);
		}

		@Override
		public Team[] newArray(int size) {
			return new Team[size];
		}
	};
}
/*
 * teams": [ { "id": "NYJ", "name": "Jets", "market": "New York", "overall": {
 * "wins": 1, "losses": 0, "ties": 0, "wpct": 1 }, "in_conference": { "wins": 1,
 * "losses": 0, "ties": 0, "wpct": 1 }, "non_conference": { "wins": 0, "losses":
 * 0, "ties": 0, "wpct": 0 }, "in_division": { "wins": 0, "losses": 0, "ties":
 * 0, "wpct": 0 }, "home": { "wins": 1, "losses": 0, "ties": 0, "wpct": 1 },
 * "away": { "wins": 0, "losses": 0, "ties": 0, "wpct": 0 }, "overtime": {
 * "wins": 0, "losses": 0, "ties": 0, "wpct": 0 }, "grass": { "wins": 0,
 * "losses": 0, "ties": 0, "wpct": 0 }, "turf": { "wins": 1, "losses": 0,
 * "ties": 0, "wpct": 1 }, "scored_first": { "wins": 0, "losses": 0, "ties": 0
 * }, "decided_by_7_points": { "wins": 0, "losses": 0, "ties": 0 },
 * "leading_at_half": { "wins": 1, "losses": 0, "ties": 0 }, "last_5": { "wins":
 * 1, "losses": 0, "ties": 0 }, "points": { "for": 31, "against": 10, "net": 21
 * }, "touchdowns": { "for": 4, "against": 1 }, "streak": { "type": "win",
 * "value": 1 } },
 */