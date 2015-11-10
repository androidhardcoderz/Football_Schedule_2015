package com.footballschedule2015.logify;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appbrain.AppBrainBanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Scott on 10/28/2015.
 */
public class TeamFragment extends Fragment {

    private RelativeLayout headerLayout;
    private TextView teamNameTextView;
    private ScrollView scrollView;
    private ValueAnimator colorAnimation;
    private LinearLayout gamesLayout;
    private int team_index;
    private String team_name;
    private ArrayList<Game> games;
    private TeamGameLoaderTask loaderTask;


    public TeamFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        team_index = getArguments().getInt(MainActivity.TEAM_INDEX);
        team_name = getArguments().getString(MainActivity.TEAM_NAME);

        loaderTask = new TeamGameLoaderTask();
        loaderTask.execute(team_index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_fragment,container,false);

        headerLayout = (RelativeLayout) view.findViewById(R.id.teamHeaderLayout);
        teamNameTextView = (TextView) view.findViewById(R.id.teamNameTextView);

        gamesLayout = (LinearLayout) view.findViewById(R.id.gamesLayout);

        Log.i("TAG",gamesLayout.getChildCount() + " GAMES LAYOUT SIZE");

        teamNameTextView.setText(team_name);

        scrollView = (ScrollView) view.findViewById(R.id.gamesScrollView);



        setUpdateColorAnimator(); //builds the color animations for the
        // header layout specific color scheme for each team

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        colorAnimation.start(); //start color animation after view has been drawn
    }

    private void setUpdateColorAnimator() {

        Integer colorFrom = (Color.WHITE);
        Integer colorTo = getActivity().getResources().getColor(new TeamColors().getTeamcolors().get(team_index));
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(2500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                headerLayout.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
    }

    private void addGamesToLayout(ArrayList<Game> games){
        for(Game game: games){
            gamesLayout.addView(new GameLayout(getActivity(),game),gamesLayout.getChildCount());
            Log.i("TAG", gamesLayout.getChildCount() + " GAMES LAYOUT SIZE");
        }

        Log.i("TAG",gamesLayout.getChildCount() + " GAMES LAYOUT SIZE");

        //scroll to current week

    }

    public void addBanner(ViewGroup parent) {
        AppBrainBanner banner = new AppBrainBanner(getActivity());
        parent.addView(banner,parent.getChildCount());
    }

    class TeamGameLoaderTask extends AsyncTask<Integer,Void,ArrayList<Game>>{

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<Game> doInBackground(Integer... params) {

            try {
                return parseResult(convertInputStreamToString(params[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String convertInputStreamToString(int index)
                throws IOException {

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("team_files/"+ new TeamFiles(getActivity()).getFiles().get(index))));
            String line = "";
            String result = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            return result;
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

                    game.setSchedule(gameObject.getString("date"));


                    if(AppPreferences.showGamesPlayed(getActivity()) == false){

                        if(new FormatGameStartTime().isGameUpcoming(game.getSchedule()) == -1){
                            continue;
                        }else{
                            //game.setId(gameObject.getString("id"));
                            game.setHome(gameObject.getString("home"));
                            game.setAway(gameObject.getString("visitor"));
                            game.setWeek(gameObject.getString("week"));
                            game.setTime(new FormatGameStartTime().getTimeOfGame(game.getSchedule()));
                            game.setDate(new FormatGameStartTime().getDateOfGame(game.getSchedule()));
                            game.setAwayColor(ContextCompat.getColor(getActivity(), new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getAway()))));
                            game.setHomeColor(ContextCompat.getColor(getActivity(), new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getHome()))));
                            game.setAwayIndex(new TeamNames().findTeamIndexByName(game.getAway()));
                            game.setHomeIndex(new TeamNames().findTeamIndexByName(game.getHome()));

                            Log.i("TAG", game.getDate() + " " + game.getTime());

                            games.add(game);
                        }
                    }else{
                        //game.setId(gameObject.getString("id"));
                        game.setHome(gameObject.getString("home"));
                        game.setAway(gameObject.getString("visitor"));
                        game.setWeek(gameObject.getString("week"));
                        game.setTime(new FormatGameStartTime().getTimeOfGame(game.getSchedule()));
                        game.setDate(new FormatGameStartTime().getDateOfGame(game.getSchedule()));
                        game.setAwayColor(ContextCompat.getColor(getActivity(), new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getAway()))));
                        game.setHomeColor(ContextCompat.getColor(getActivity(), new TeamColors().getTeamcolors().get(new TeamNames().findTeamIndexByName(game.getHome()))));
                        game.setAwayIndex(new TeamNames().findTeamIndexByName(game.getAway()));
                        game.setHomeIndex(new TeamNames().findTeamIndexByName(game.getHome()));

                        Log.i("TAG", game.getDate() + " " + game.getTime());

                        games.add(game);
                    }


                }
            } catch (JSONException jse) {
                jse.printStackTrace();
            }

            Log.i("TAG",games.size() + " SIZE OF TEAM ARRAY");
            return games;
        }

        @Override
        protected void onPostExecute(ArrayList<Game> games) {
            super.onPostExecute(games);

            addGamesToLayout(games);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(loaderTask != null || loaderTask.getStatus() == AsyncTask.Status.RUNNING){
            loaderTask.cancel(true);
        }
    }
}
