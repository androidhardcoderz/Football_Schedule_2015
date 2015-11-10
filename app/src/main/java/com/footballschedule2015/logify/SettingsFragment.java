package com.footballschedule2015.logify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by Scott on 10/28/2015.
 */
public class SettingsFragment extends Fragment {

    SharedPreferences preferences;
    CheckBox gamesPlayed;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment,container,false);

        gamesPlayed = (CheckBox) view.findViewById(R.id.gamesPlayedCheckBox);
        gamesPlayed.setChecked(AppPreferences.showGamesPlayed(getActivity()));

        gamesPlayed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppPreferences.setShowGamesPlayed(getActivity(),isChecked);
            }
        });

        return view;
    }
}
