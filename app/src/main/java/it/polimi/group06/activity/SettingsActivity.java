package it.polimi.group06.activity;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.Arrays;
import java.util.List;

import it.polimi.group06.InputHandler;
import it.polimi.group06.OutputHandler;
import it.polimi.group06.R;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup cardpicker, backpicker;
    RadioButton naples, german, sicily;
    RadioButton default_bg, green_bg, blue_bg, orange_bg;
    Button button;
    Switch mute;
    List<String> settingsList;
    int whichcarddeck, whichbackground;
    AudioManager backgroundaudio;

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

        default_bg = findViewById(R.id.default_bg);
        green_bg = findViewById(R.id.green_bg);
        orange_bg =findViewById(R.id.orange_bg);
        blue_bg = findViewById(R.id.light_blue_bg);

        mute = findViewById(R.id.sound);

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
                default_bg.setChecked(true);
                break;
            case (1):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                green_bg.setChecked(true);
                break;
            case(2):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.orange));
                orange_bg.setChecked(true);
                break;
            case (3):
                getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                blue_bg.setChecked(true);
                break;
            default:
                default_bg.setChecked(true);
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
                if(checkedId == R.id.default_bg){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                    whichbackground = 0;
                } else if (checkedId == R.id.green_bg){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                    whichbackground = 1;
                } else if (checkedId == R.id.orange_bg){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.orange));
                    whichbackground = 2;
                } else if(checkedId == R.id.light_blue_bg){
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                    whichbackground = 3;
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

        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                } else{

                }
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