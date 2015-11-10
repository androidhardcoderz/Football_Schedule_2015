package com.footballschedule2015.logify;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Scott on 11/10/2015.
 */
public class CurrentWeekFragment extends Fragment {

    private RelativeLayout headerLayout;
    private TextView teamNameTextView;
    private ValueAnimator colorAnimation;
    private LinearLayout gamesLayout;
    private int team_index;
    private String team_name;
    private ArrayList<Game> games;

    public CurrentWeekFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        team_name = getArguments().getString("WEEK");
        games = getArguments().getParcelableArrayList("CURRENT_WEEK");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_week_fragment, container, false);

        headerLayout = (RelativeLayout) view.findViewById(R.id.teamHeaderLayout);
        teamNameTextView = (TextView) view.findViewById(R.id.teamNameTextView);

        gamesLayout = (LinearLayout) view.findViewById(R.id.gamesLayout);

        teamNameTextView.setText("Week: " + team_name);

        addGamesToLayout(games);

        return view;
    }


    private void addGamesToLayout(ArrayList<Game> games){
        for(Game game: games){
            gamesLayout.addView(new GameLayout(getActivity(),game),gamesLayout.getChildCount() - 1);
        }



    }
}
