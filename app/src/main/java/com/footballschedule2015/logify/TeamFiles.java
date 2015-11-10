package com.footballschedule2015.logify;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 10/5/2015.
 */
public class TeamFiles {

    private final String TAG = "TEAM FILES";

    public List<String> getFiles() {
        return files;
    }

    List<String> files = new ArrayList<>();

    public TeamFiles(Context context) throws IOException {
        //attach each filesname
        String[] fileNames =context.getAssets().list("team_files");
        for(String name:fileNames){
           files.add(name);
            Log.i(TAG,name);
        }

        Log.i(TAG,files.size() + " SIZE OF FILES");

    }
}
