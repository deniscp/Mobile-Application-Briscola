package it.polimi.group06.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import it.polimi.group06.InputHandler;
import it.polimi.group06.R;
import it.polimi.group06.activity.helper.MusicService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Make activity_main fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        setBackgroundColor();

        startService(new Intent(this, MusicService.class));

        final Button start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String msg = "newgame";
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                gameIntent.putExtra("keyMessage", msg);
                MainActivity.this.startActivity(gameIntent);
            }
        });

        final Button load_button = findViewById(R.id.load_button);
        load_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String config = InputHandler.getStringfromFile("savedgame", getApplicationContext());
                if (config.equals("filenotfound") || config.equals("problem") || config.equals("")) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("No Saved Game")
                            .setMessage("There is no saved game yet!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    System.out.println("aisjidj");
                                }
                            }).show();
                } else {
                    String msg = "fromsaved";
                    Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                    gameIntent.putExtra("keyMessage", msg);
                    MainActivity.this.startActivity(gameIntent);
                }

            }
        });

        final Button statistics_button = findViewById(R.id.statistics_button);
        statistics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsIntent = new Intent(MainActivity.this, StatisticsActivity.class);
                MainActivity.this.startActivity(statisticsIntent);
            }
        });

        final Button settings_button = findViewById(R.id.settings_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(settingsIntent);
            }
        });

        final Button exit_button = findViewById(R.id.quit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Exit")
                        .setMessage("Do you want to close the App?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackgroundColor();
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

    void setBackgroundColor(){
        switch(getSettings()){
            case (1):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                break;
            case (2):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                break;
            default:
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
        }
    }
}