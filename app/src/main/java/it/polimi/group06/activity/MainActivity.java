package it.polimi.group06.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

/**
 * Main activity of the app , This class represents main menu of the game which provides users to select
 * among available buttons in order to reach the relevant activity in the game.
 * Moreover this class is connected to activity "activity_main.xml"
 * @@author Group6
 */

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

        /**
         * initializes start button.
         * Run method setOnClickListener, whenever start_button is clicked,
         * Then it starts GameActivity.
         */
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

        /**
         * initializes statistics button.
         * Run method setOnClickListener, whenever statistics_button is clicked,
         * Then it starts StatisticsActivity.
         */

        final Button statistics_button = findViewById(R.id.statistics_button);
        statistics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsIntent = new Intent(MainActivity.this, StatisticsActivity.class);
                MainActivity.this.startActivity(statisticsIntent);
            }
        });

        /**
         * initializes settings button.
         * Run method setOnClickListener, whenever settings_button is clicked,
         * Then it starts StatisticsActivity.
         */
        final Button settings_button = findViewById(R.id.settings_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(settingsIntent);
            }
        });

        /**
         * initializes exit button and Run method setOnClickListener, whenever quite_button is clicked.
         * It appears exit dialog.
         * By clicking on "Exit" it close the game completely.
         * By clicking on "cancel" the dialog disappear without doing anything.
         */
        final Button exit_button = findViewById(R.id.quit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.app_quit_title)
                        .setMessage(R.string.app_quit_message)
                        .setIcon(R.drawable.sad_emoji)
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
        } else {
            return 0;
        }
    }

    /**
     * Set the color background for the main activity according to
     * the selection of background preference in Setting activity.
     */
    void setBackgroundColor() {
        switch (getSettings()) {
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