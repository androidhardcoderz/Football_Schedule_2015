package com.footballstandings2015.application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StandingsFragment extends Fragment {

	private Standings standings;

	private final int NUM_PAGES = 2;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	public StandingsFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStandings((Standings) getArguments().getParcelable("result"));

		System.out.println(standings.getConferences().size());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_standings, null);

		mPager = (ViewPager) view.findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);

		return view;
	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects,
	 * in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			//create new conference fragment
			ConferenceFragment cFragment = new ConferenceFragment();
			Bundle bundle = new Bundle(); //make bundle
			bundle.putParcelable("conference", getStandings().getConferences()
					.get(position));
			cFragment.setArguments(bundle); //attach bundle
			return cFragment;
		}

		@Override
		public int getCount() {
			return NUM_PAGES; // 2
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// return the name of conference in the pager index
			return standings.getConferences().get(position).getName();
		}
	}

	public Standings getStandings() {
		return standings;
	}

	public void setStandings(Standings standings) {
		this.standings = standings;
	}

}
