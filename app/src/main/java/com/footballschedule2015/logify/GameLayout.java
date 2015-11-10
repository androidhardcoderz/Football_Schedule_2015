package com.footballschedule2015.logify;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Scott on 10/23/2015.
 */
public class GameLayout extends LinearLayout {

    RelativeLayout gameLayout;


    public GameLayout(Context context, Game game) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.game_row, this, true);


        TextView away = (TextView) this.findViewById(R.id.awayTeamTextView);
        TextView home = (TextView) this.findViewById(R.id.homeTeamTextView);
        TextView date = (TextView) this.findViewById(R.id.dateTextView);
        TextView time = (TextView) this.findViewById(R.id.timeTextView);
        TextView week = (TextView) this.findViewById(R.id.weekTextView);

        gameLayout = (RelativeLayout) this.findViewById(R.id.gameLayout);

        away.setText(game.getAway());
        home.setText(game.getHome());
        date.setText(game.getDate());
        time.setText(game.getTime());
        week.setText("Week " + game.getWeek());

        /*
        TeamFonts.setCustomTeamFont(away, game.getAwayIndex());
        TeamFonts.setCustomTeamFont(home, game.getHomeIndex());
        TeamFonts.setCustomTeamFont(date, TeamFonts.team_fonts_index);
        TeamFonts.setCustomTeamFont(time, TeamFonts.team_fonts_index);
        */

        //createCustomBackground(game.getAwayColor(), game.getHomeColor());
    }

    /*
    private void createCustomBackground(final int away, final int home) {
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, width, height,
                        new int[]{away, away, home, home},
                        new float[]{0,0.50f,.50f,1}, Shader.TileMode.REPEAT);
                return lg;
            }
        };

        PaintDrawable p=new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        gameLayout.setBackgroundDrawable((Drawable) p);
    }

    */


}
