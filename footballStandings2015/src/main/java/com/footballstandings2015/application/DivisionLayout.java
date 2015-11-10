package com.footballstandings2015.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DivisionLayout extends LinearLayout {

	TextView name; // division name
	LinearLayout divisionlayout;

	public DivisionLayout(Context context, Division division) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.division_layout, this, true);
		
		divisionlayout = (LinearLayout)this.findViewById(R.id.divisionLinearLayout);

		name = (TextView) this.findViewById(R.id.divisionNameTextView);

		name.setText(division.getName());

		//loop through each team in the current division add the team layout view 
		for(int i = 0;i < division.getTeams().size();i++){
			divisionlayout.addView(new TeamLayout(getContext(),division.getTeams().get(i)), i + 1);
		}

	}

}
