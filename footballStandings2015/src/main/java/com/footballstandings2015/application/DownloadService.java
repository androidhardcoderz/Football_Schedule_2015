package com.footballstandings2015.application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

public class DownloadService extends IntentService {

	public static final int STATUS_RUNNING = 0;
	public static final int STATUS_FINISHED = 1;
	public static final int STATUS_ERROR = 2;

	private static final String TAG = "DownloadService";

	Standings standings;

	public DownloadService() {
		super(DownloadService.class.getName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d(TAG, "Service Started!");

		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
		String url = intent.getStringExtra("url");

		Bundle bundle = new Bundle();

		if (!TextUtils.isEmpty(url)) {
			/* Update UI: Download Service is Running */
			receiver.send(STATUS_RUNNING, Bundle.EMPTY);

			try {
				standings = downloadData(url);

				/* Sending result back to activity */
				if (standings.getConferences().size() > 0) {
					bundle.putParcelable("result", standings);
					receiver.send(STATUS_FINISHED, bundle);
				}
			} catch (Exception e) {

				/* Sending error message back to activity */
				bundle.putString(Intent.EXTRA_TEXT, e.toString());
				receiver.send(STATUS_ERROR, bundle);
			}
		}
		Log.d(TAG, "Service Stopping!");
		this.stopSelf();
	}

	private Standings downloadData(String requestUrl) throws IOException,
			DownloadException {

		InputStream inputStream = null;
		HttpURLConnection urlConnection = null;

		/* forming th java.net.URL object */
		URL url = new URL(requestUrl);
		urlConnection = (HttpURLConnection) url.openConnection();

		/* optional request header */
		urlConnection.setRequestProperty("Content-Type", "application/json");

		/* optional request header */
		urlConnection.setRequestProperty("Accept", "application/json");

		/* for Get request */
		urlConnection.setRequestMethod("GET");
		int statusCode = urlConnection.getResponseCode();

		/* 200 represents HTTP OK */
		if (statusCode == 200) {
			inputStream = new BufferedInputStream(
					urlConnection.getInputStream());
			String response = convertInputStreamToString(inputStream);
			//System.out.println(response);
			return parseResult(response);
		} else {
			throw new DownloadException("Failed to fetch data!!");
		}
	}

	private String convertInputStreamToString(InputStream inputStream)
			throws IOException {

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";

		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}

		/* Close Stream */
		if (null != inputStream) {
			inputStream.close();
		}

		return result;
	}

	private Standings parseResult(String result) {
		standings = new Standings();
		try {
			// create main JSON object from stream
			JSONObject obj = new JSONObject(result);
			standings.setSeason(obj.getString("season"));
			standings.setType(obj.getString("type"));

			JSONArray conferences = obj.getJSONArray("conferences");
			for (int c = 0; c < conferences.length(); c++) {
				// loop through each conference
				JSONObject cObject = conferences.getJSONObject(c);
				Conferences conference = new Conferences();
				conference.setId(cObject.getString("id"));
				conference.setName(cObject.getString("name"));

				// loop through each division
				JSONArray divisions = cObject.getJSONArray("divisions");
				for (int d = 0; d < divisions.length(); d++) {
					JSONObject dObject = divisions.getJSONObject(d);
					Division division = new Division();
					division.setId(dObject.getString("id"));
					division.setName(dObject.getString("name"));

					JSONArray teams = dObject.getJSONArray("teams");
					for (int t = 0; t < teams.length(); t++) {
						JSONObject tObject = teams.getJSONObject(t);
						Team team = new Team();
						team.setId(tObject.getString("id"));
						team.setName(tObject.getString("name"));
						team.setMarket(tObject.getString("market"));

						JSONObject overallObj = tObject
								.getJSONObject("overall");
						team.setWins(overallObj.getString("wins"));
						team.setLosses(overallObj.getString("losses"));
						team.setTies(overallObj.getString("ties"));
						team.setWin_percentage(overallObj.getString("wpct"));

						division.getTeams().add(team); // add team to division
					}

					// add division to conference
					conference.getDivisions().add(division);
				}

				// add conference to standings list
				standings.getConferences().add(conference);

			}
		} catch (JSONException jse) {

		}
		return standings;
	}

	public class DownloadException extends Exception {

		public DownloadException(String message) {
			super(message);
		}

		public DownloadException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
