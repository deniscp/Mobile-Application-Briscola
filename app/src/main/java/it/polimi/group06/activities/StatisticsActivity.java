package it.polimi.group06.activities;

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

        Button clearstats = findViewById(R.id.clearstats);
        clearstats.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                deleteStatistics();
                readStatisticsfromFile();
                setcontentofviews();
            }
        });
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
            seconds.setText(statList.get(3) + "Seconds");
            numberplayer.setText(statList.get(4));
            numberrobot.setText(statList.get(5));
            draws.setText(statList.get(6));
        }

    }
}
