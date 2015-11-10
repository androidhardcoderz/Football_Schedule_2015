package com.footballschedule2015.logify;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";


    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();


            /* Update UI: Download Service is Running */
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);

        try {


            /* Sending result back to activity */

            String totalResponse;
            totalResponse = convertInputStreamToString();
            System.out.println(totalResponse);
            bundle.putString("WEEK",downloadData("https://nflstandingsapp.s3.amazonaws.com/week.txt"));
            bundle.putParcelableArrayList("CURRENT_WEEK",findCurrentWeekGames(totalResponse,bundle.getString("WEEK")));

            /*
            TeamNames names = new TeamNames();
            for(String s: names.getTeamnames()){
                mCreateAndSaveFile(s,parseForSpecificTeam(s,totalResponse));
            }

            */

            bundle.putParcelableArrayList("STANDINGS",parseStandingsResult(downloadData("https://nflstandingsapp.s3.amazonaws.com/nflstandings.txt")));

            //bundle.putParcelable("SCHEDULE", "TESTER");
            receiver.send(STATUS_FINISHED, bundle);

        } catch (Exception e) {
                /* Sending error message back to activity */
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        }

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }
    private ArrayList<Game> findCurrentWeekGames(String string,String currentWeek){
        ArrayList<Game> currentWeekGames = new ArrayList<>();

        JSONObject mainObject = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            // create main JSON object from stream and convert to array
            JSONArray weeksLists = new JSONObject(string).getJSONObject("regular").getJSONArray("weeks");

            for (int a = 0; a < weeksLists.length(); a++) {

                JSONArray gamesLists = weeksLists.getJSONObject(a).getJSONArray("games");
                String week = weeksLists.getJSONObject(a).getString("number");

                if(week.equals(currentWeek)){

                    for(int i = 0; i < gamesLists.length();i++){
                        Game game = new Game();
                        JSONObject gameObject = gamesLists.getJSONObject(i);

                        game.setSchedule(gameObject.getString("date"));

                        //check if game in in the current week
                        game.setWeek(week);
                        //game.setId(gameObject.getString("id"));
                        game.setHome(gameObject.getString("home"));
                        game.setAway(gameObject.getString("visitor"));
                        game.setTime(new FormatGameStartTime().getTimeOfGame(game.getSchedule()));
                        game.setDate(new FormatGameStartTime().getDateOfGame(game.getSchedule()));
                        game.setAwayColor(ContextCompat.getColor(this, new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getAway()))));
                        game.setHomeColor(ContextCompat.getColor(this, new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getHome()))));
                        game.setAwayIndex(new TeamNames().findTeamIndexByName(game.getAway()));
                        game.setHomeIndex(new TeamNames().findTeamIndexByName(game.getHome()));

                        Log.i("TAG", game.getDate() + " " + game.getTime());

                        currentWeekGames.add(game);
                    }
                }

            }

        }catch(JSONException jse){
            jse.printStackTrace();
        }

        return currentWeekGames;
    }

    private ArrayList<Conferences> parseStandingsResult(String result) {
        ArrayList<Conferences> standings = new ArrayList<Conferences>();
        try {
            // create main JSON object from stream
            JSONObject obj = new JSONObject(result);
            //standings.setSeason(obj.getString("season"));
           // standings.setType(obj.getString("type"));

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
               standings.add(conference);

            }
        } catch (JSONException jse) {

        }
        return standings;
    }


    private String downloadData(String requestUrl) throws IOException,
            DownloadException {

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;


        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();


        urlConnection.setRequestProperty("Content-Type", "application/json");


        urlConnection.setRequestProperty("Accept", "application/json");


        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();

		/* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(
                    urlConnection.getInputStream());
            String response = convertInputStreamToString(inputStream);
            return response;
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }


    private String convertInputStreamToString()
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getApplicationContext().getAssets().open("fullschedulenfl.txt")));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }



        return result;
    }

    private String convertInputStreamToString(InputStream is)
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }



        return result;
    }

    private String parseForSpecificTeam(String name,String result) {

        String response = "";
        JSONObject mainObject = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            // create main JSON object from stream and convert to array
            JSONArray weeksLists = new JSONObject(result).getJSONObject("regular").getJSONArray("weeks");

            for(int a = 0; a < weeksLists.length();a++){

                JSONArray gamesLists = weeksLists.getJSONObject(a).getJSONArray("games");

                String week = weeksLists.getJSONObject(a).getString("number");

                for (int g = 0; g < gamesLists.length(); g++) {
                    //loop through each array index
                    Game game = new Game();
                    JSONObject gameObject = gamesLists.getJSONObject(g);
                    game.setSchedule(gameObject.getString("date"));
                    game.setAway(gameObject.getString("visitor"));
                    game.setHome(gameObject.getString("home"));

                    if (game.getAway().equals(name) || game.getHome().equals(name)) {
                        JSONObject teamObject = new JSONObject();
                        teamObject.put("date",game.getSchedule());
                        teamObject.put("home",game.getHome());
                        teamObject.put("visitor",game.getAway());
                        teamObject.put("week",week);

                        array.put(teamObject);

                        break;
                    }

                    continue;
                }

                mainObject.putOpt("games", array);

            }

        } catch (JSONException jse) {
            jse.printStackTrace();
        }

        return mainObject.toString();
    }


    //used to build json files for each team from main schedule file
    public void mCreateAndSaveFile(String fileName, String mJsonResponse) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mJsonResponse);
            fileWriter.flush();
            fileWriter.close();
            Log.i(TAG, "file written for: " + fileName + " " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Game> parseResult(String result) {

        ArrayList<Game> games = new ArrayList<>();

        try {
            // create main JSON object from stream and convert to array
            JSONArray gamesLists = new JSONObject(result).getJSONArray("games");
            for (int g = 0; g < gamesLists.length(); g++) {
                //loop through each array index
                Game game = new Game();
                JSONObject gameObject = gamesLists.getJSONObject(g);

                game.setSchedule(gameObject.getString("scheduled"));

                //check if game in in the current week

                game.setId(gameObject.getString("id"));
                game.setHome(gameObject.getString("home"));
                game.setAway(gameObject.getString("visitor"));
                game.setTime(new FormatGameStartTime().getTimeOfGame(game.getSchedule()));
                game.setDate(new FormatGameStartTime().getDateOfGame(game.getSchedule()));
                game.setAwayColor(ContextCompat.getColor(this, new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getAway()))));
                game.setHomeColor(ContextCompat.getColor(this, new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getHome()))));
                game.setAwayIndex(new TeamNames().findTeamIndexByName(game.getAway()));
                game.setHomeIndex(new TeamNames().findTeamIndexByName(game.getHome()));

                Log.i("TAG", game.getDate() + " " + game.getTime());

                games.add(game);
            }
        } catch (JSONException jse) {
            jse.printStackTrace();
        }
        return games;
    }

    public String convertInputStreamToString(String filename,String s)
            throws IOException {

        InputStreamReader isr = new InputStreamReader(getApplicationContext().getAssets().open("team_files/" + filename));
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
            Log.i(TAG, line);

        }

		/* Close Stream */
        if (null != isr) {
            isr.close();
        }

        return result;
    }

    public ArrayList<Game> parseSpecificTeamData(String jsonString) throws JSONException {

        ArrayList<Game> games = new ArrayList<>();
        JSONArray gamesArray = new JSONObject(jsonString).getJSONArray("games");


        Log.i(TAG, "STARTING PARSING!");
        Log.i(TAG, gamesArray.length() + " LENGTH OF ARRAY FOR TEAM");

        for (int i = 0; i < gamesArray.length(); i++) {

            JSONObject gameObject = gamesArray.getJSONObject(i);

            if(new FormatGameStartTime().isGameUpcoming(gameObject.getString("scheduled")) == -1){
                continue;
            }

            Game game = new Game();
            game.setId(gameObject.getString("id"));
            game.setSchedule(gameObject.getString("date"));
            game.setHome(gameObject.getString("home"));
            game.setAway(gameObject.getString("visitor"));
            games.add(game);
        }

        return games;
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
