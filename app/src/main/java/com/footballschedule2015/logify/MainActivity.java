package com.footballschedule2015.logify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appbrain.AppBrain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DownloadResultReceiver.Receiver {

    private final String TAG = "MainActivity";
    public static final String TEAM_INDEX = "index";
    public static final String TEAM_DATA = "data";
    public static final String TEAM_NAME = "name";
    private DownloadResultReceiver mReceiver;
    public static String week;
    private ArrayList<Conferences> standings;
    private ArrayList<Game> currentWeek;

    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppBrain.init(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pBar = (ProgressBar) findViewById(R.id.progressBar);

        //Starting Download Service
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        // Send optional extras to Download IntentService
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if (isFinishing()) {
                AppBrain.getAds().maybeShowInterstitial(this);
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.this_week_games) {
            //show fragment for games this week;
            if(currentWeek != null && currentWeek.size() > 0){
                //create current week fragment
                CurrentWeekFragment currentWeekFragment = new CurrentWeekFragment();
                Bundle bundle = new Bundle();
                bundle.putString("WEEK",week);
                bundle.putParcelableArrayList("CURRENT_WEEK", currentWeek);
                currentWeekFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.container,currentWeekFragment).commit();
            }
        }else if(id == R.id.standings){
            if(standings != null && standings.size() > 0){
                Log.i(TAG,standings.size() + " SIZE STANDINGS");
                StandingsFragment standingsFragment = new StandingsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("result",standings);
                standingsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, standingsFragment).commit();
            }
        }else{
            Log.i(TAG," SELECTED TEAM " + item.getTitle().toString());
            //show team schedule fragment
            TeamFragment fragment = new TeamFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(TEAM_INDEX,new TeamNames().findTeamIndexByName(item.getTitle().toString()));
            bundle.putString(TEAM_NAME,item.getTitle().toString());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                pBar.setVisibility(View.VISIBLE);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                pBar.setVisibility(View.INVISIBLE);
                week = resultData.getString("WEEK");
                currentWeek = (resultData.getParcelableArrayList("CURRENT_WEEK"));
                standings = resultData.getParcelableArrayList("STANDINGS");
                setProgressBarIndeterminateVisibility(false);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                showDialog();
                break;
        }
    }



    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Error");

        // set dialog message
        alertDialogBuilder
                .setMessage("Could not download data, please make sure you are connected to a network and launch application again")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
