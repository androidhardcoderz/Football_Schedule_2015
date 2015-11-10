package com.footballstandings2015.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

public class StandingsActivity extends AppCompatActivity implements
		DownloadResultReceiver.Receiver {

	Toolbar toolbar;
	DownloadResultReceiver mReceiver;
	private String url;
	private ProgressBar pBar;
	Bundle savedState;
	View coordinatorLayoutView;

	final View.OnClickListener clickListener = new View.OnClickListener() {
		public void onClick(View v) {
			startIntentDownloadService();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standings);

		coordinatorLayoutView = (View) findViewById(R.id.snackbarPosition);
		pBar = (ProgressBar) findViewById(R.id.pBar);

		savedState = savedInstanceState;

		url = getResources().getString(R.string.API_CALL);

		// assign toolbar and set attributes
		toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		toolbar.setTitleTextColor(Color.WHITE);

		startIntentDownloadService();

	}

	/**
	 * 
	 */
	private void startIntentDownloadService() {
		/* Starting Download Service */
		mReceiver = new DownloadResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				DownloadService.class);

		/* Send optional extras to Download IntentService */
		intent.putExtra("url", url);
		intent.putExtra("receiver", mReceiver);
		intent.putExtra("requestId", 101);

		startService(intent);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {

		case DownloadService.STATUS_RUNNING:
			pBar.setVisibility(View.VISIBLE);
			;
			break;
		case DownloadService.STATUS_FINISHED:
			/* Hide progress & extract result from bundle */
			pBar.setVisibility(View.INVISIBLE);

			// invoke fragment manager on a new fragment creation
			// send data into fragment through bundling
			if (savedState == null) {
				StandingsFragment standingsFragment = new StandingsFragment();
				standingsFragment.setArguments(resultData);
				getSupportFragmentManager().beginTransaction()
						.add(R.id.container, standingsFragment).commit();
			}

			break;
		case DownloadService.STATUS_ERROR:
			/* Handle the error */
			String error = resultData.getString(Intent.EXTRA_TEXT);
			Snackbar.make(coordinatorLayoutView,
					"Could Not Connect To Data Server", Snackbar.LENGTH_LONG)
					.setAction("Try Again", clickListener).show();

			break;
		}
	}

}
