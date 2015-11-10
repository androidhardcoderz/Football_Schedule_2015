package com.footballstandings2015.application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConferenceFragment extends Fragment {

	private Conferences conference;
	private LinearLayout conferenceLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.conference_fragment, null);

		setConferenceLayout((LinearLayout) view
				.findViewById(R.id.conferenceLinearLayout));

		for (Division division : conference.getDivisions()) {
			getConferenceLayout().addView(
					new DivisionLayout(getActivity(), division));
		}

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		conference = (Conferences) getArguments().getParcelable("conference");
	}

	public Conferences getConference() {
		return conference;
	}

	public void setConference(Conferences conference) {
		this.conference = conference;
	}

	public LinearLayout getConferenceLayout() {
		return conferenceLayout;
	}

	public void setConferenceLayout(LinearLayout conferenceLayout) {
		this.conferenceLayout = conferenceLayout;
	}

}
