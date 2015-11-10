package com.footballschedule2015.logify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StandingsFragment extends Fragment {

	private ArrayList<Conferences> standings;

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
		standings = getArguments().getParcelableArrayList("result");

		System.out.println(standings.size());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_standings, null);

		mPager = (ViewPager) view.findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);

		//after setting the adapter
		mPager.post(new Runnable() {
			@Override
			public void run() {
				mPager.setCurrentItem(1, false);
				mPager.setCurrentItem(0, false);
				mPager.postInvalidate();
			}
		});

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
			bundle.putParcelable("conference", standings
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
			return standings.get(position).getName();
		}
	}



}
