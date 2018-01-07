package it.polimi.group06.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import it.polimi.group06.InputHandler;
import it.polimi.group06.OutputHandler;
import it.polimi.group06.R;

/**
 * This class represents the history and statistics of the played game in order to
 * tacking previous game's scores convenient.
 * Moreover this class is connected to activity "statistics_game.xml"
 * @author Group6
 */

public class StatisticsActivity extends AppCompatActivity {

    List<String> statList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity_game fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_statistics);

        readStatisticsfromFile();
        setcontentofviews();
        setBackgroundColor();

        /**
         *Initialize clear stats button and set Listener method in order to delete current
         *statistics and then reading and updating the content
         */

        Button clearstats = findViewById(R.id.clearstats);
        clearstats.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                deleteStatistics();
                readStatisticsfromFile();
                setcontentofviews();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackgroundColor();
    }

    void deleteStatistics(){
        String towrite = 0 + "," + 0 + "," + 0 +","+ 0+","+ 0+","+ 0+","+ 0;
        OutputHandler.writefile(towrite, "statistics", getApplicationContext());
    }

    void readStatisticsfromFile() {
        String str = InputHandler.getStringfromFile("statistics", getApplicationContext());

        if(!str.equals("")){
            statList = Arrays.asList(str.split(","));
        }
    }

    /**
     *Initialize and set Text method for all text views in Statistics activity.
     */
    void setcontentofviews(){
        TextView zero = findViewById(R.id.zero);
        TextView one = findViewById(R.id.one);
        TextView two = findViewById(R.id.two);
        TextView seconds = findViewById(R.id.timeplayed);
        TextView numberplayer = findViewById(R.id.playerwins_text);
        TextView numberrobot = findViewById(R.id.robotwins_text);
        TextView draws = findViewById(R.id.draws_text);

        if(statList!=null){
            zero.setText(statList.get(0));
            one.setText(statList.get(1));
            two.setText(statList.get(2));
            seconds.setText(statList.get(3) + " Seconds");
            numberplayer.setText(statList.get(4));
            numberrobot.setText(statList.get(5));
            draws.setText(statList.get(6));
        }

    }

    int getSettings() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());
        if (!str.equals("")) {
            List<String> settingsList = Arrays.asList(str.split(","));
            return Integer.parseInt(settingsList.get(1));
        } else{
            return 0;
        }
    }

    /**
     * Set the color background for the main activity according to
     * the selection of background preference in the Setting activity.
     */
    void setBackgroundColor(){
        switch(getSettings()){
            case (1):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                break;
            case (2):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.orange));
                break;
            case (3):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                break;
            default:
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
        }
    }
}
