package it.polimi.group06.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Arrays;
import java.util.List;

import it.polimi.group06.InputHandler;
import it.polimi.group06.OutputHandler;
import it.polimi.group06.R;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup cardpicker, backpicker;
    RadioButton naples, german, sicily;
    RadioButton defaultback, greenback, blueback;
    Button button;
    List<String> settingsList;
    int whichcarddeck, whichbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity_game fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        cardpicker = findViewById(R.id.cardpicker_group);
        backpicker = findViewById(R.id.backgroundpicker_group);

        naples = findViewById(R.id.naples);
        german = findViewById(R.id.german);
        sicily = findViewById(R.id.sicily);

        defaultback = findViewById(R.id.defaultback);
        greenback = findViewById(R.id.lightgreen);
        blueback = findViewById(R.id.lightblue);

        getSettings();

        switch (whichcarddeck) {
            case (0):
                naples.setChecked(true);
                break;
            case (1):
                german.setChecked(true);
                break;
            case (2):
                sicily.setChecked(true);
                break;
            default:
                naples.setChecked(true);
                System.out.print("this doesn't exist");
        }

        switch (whichbackground) {
            case (0):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                defaultback.setChecked(true);
                break;
            case (1):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                greenback.setChecked(true);
                break;
            case (2):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                blueback.setChecked(true);
                break;
            default:
                defaultback.setChecked(true);
                System.out.println("this doesn't exist");
        }

        cardpicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.naples) {
                    whichcarddeck = 0;
                } else if (checkedId == R.id.german) {
                    whichcarddeck = 1;
                } else if (checkedId == R.id.sicily) {
                    whichcarddeck = 2;
                }
            }
        });

        backpicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.defaultback){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                    whichbackground = 0;
                } else if (checkedId == R.id.lightgreen){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                    whichbackground = 1;
                } else if(checkedId == R.id.lightblue){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                    whichbackground = 2;
                }
            }
        });

        button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String towrite = String.valueOf(whichcarddeck + "," + whichbackground);
                OutputHandler.writefile(towrite, "settings", getApplicationContext());
                finish();
            }
        });
    }

    void getSettings() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());
        if (str.equals("")) {
            whichcarddeck = 0;
        } else {
            settingsList = Arrays.asList(str.split(","));
            whichcarddeck = Integer.parseInt(settingsList.get(0));
            whichbackground = Integer.parseInt(settingsList.get(1));
        }
    }
}