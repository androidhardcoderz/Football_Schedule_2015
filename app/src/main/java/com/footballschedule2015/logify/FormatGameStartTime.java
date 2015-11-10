package com.footballschedule2015.logify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FormatGameStartTime {

	private String year, day, month,time;

	// 2015-04-17T02:15:00+00:00
	SimpleDateFormat parseFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	SimpleDateFormat timeFormatAP = new SimpleDateFormat("hh:mm a z");
	SimpleDateFormat dateFormatAP = new SimpleDateFormat("MM/dd/yyyy");

	public String parseAndFormatDate(String scheduledDT) {

		dateFormat.setTimeZone(TimeZone.getDefault());

		Date date = null;
		try {
			date = parseFormat.parse(scheduledDT);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		setYear(yearFormat.format(date));
		setDay(dayFormat.format(date));
		setMonth(monthFormat.format(date));
		setTime(timeFormatAP.format(date));
		
		return dateFormat.format(date);

	}

	public int isGameUpcoming(String scheduled){

		Date dateG = null;
		try {
			dateG = parseFormat.parse(scheduled);
		} catch (ParseException e) {
			e.printStackTrace();
		}



		return (dateG).compareTo(new Date());



	}

	public boolean isGameToday(String scheduled){
		Date dateG = null;
		try {
			dateG = parseFormat.parse(scheduled);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateFormatAP.format(dateG).equals(dateFormatAP.format(new Date()));
	}

	public String getDateOfGame(String s) {
		try {
			Date date = parseFormat.parse(s);

			return dateFormatAP.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public String getTimeOfGame(String dateString) {
		try {
			Date date = parseFormat.parse(dateString);

			return timeFormatAP.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateString;

	}

	public String formatByTimezone(String dateString) {
		return dateString;

	}

	public String parseGameForMonth(String dateString) {
		Date date = null;
		try {
			date = dateFormat.parse(dateString);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setYear(yearFormat.format(date));
		setDay(dayFormat.format(date));
		setMonth(monthFormat.format(date));

		return dateFormat.format(date);

	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
