package it.polimi.group06.activities;

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

    RadioGroup colorpicker, cardpicker;
    RadioButton red, orange, green, blue, naples, german, sicily;
    Button button;
    List<String> settingsList;
    int whichcolor, whichcardback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity_game fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        colorpicker = findViewById(R.id.colorpicker_group);
        cardpicker = findViewById(R.id.cardpicker_group);

        red = findViewById(R.id.red);
        orange = findViewById(R.id.orange);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);

        naples = findViewById(R.id.naples);
        german = findViewById(R.id.german);
        sicily = findViewById(R.id.sicily);

        getSettings();

        switch (whichcolor) {
            case (0):
                red.setChecked(true);
                break;
            case (1):
                orange.setChecked(true);
                break;
            case (2):
                green.setChecked(true);
                break;
            case (3):
                blue.setChecked(true);
                break;
            default:
                System.out.println("this doesn't exist");
        }

        switch (whichcardback) {
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
                System.out.print("this doesn't exist");
        }

        colorpicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.red) {
                    whichcolor = 0;
                } else if (checkedId == R.id.orange) {
                    whichcolor = 1;
                } else if (checkedId == R.id.green) {
                    whichcolor = 2;
                } else if (checkedId == R.id.blue) {
                    whichcolor = 3;
                }
            }
        });

        cardpicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.naples){
                    whichcardback = 0;
                } else if (checkedId == R.id.german){
                    whichcardback = 1;
                } else if (checkedId == R.id.sicily){
                    whichcardback = 2;
                }
            }
        });

        button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String towrite = String.valueOf(whichcolor + "," + whichcardback);
                OutputHandler.writefile(towrite, "settings", getApplicationContext());
                System.out.println("saveSettings" + whichcolor);
                finish();
            }
        });
    }

    void getSettings() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());
        if(str.equals("")){
            whichcolor = 0;
            whichcardback = 0;
        }else{
            settingsList = Arrays.asList(str.split(","));
            whichcolor = Integer.parseInt(settingsList.get(0));
            whichcardback = Integer.parseInt(settingsList.get(1));
        }
        System.out.println("GetSettings" + whichcolor + whichcardback);
    }

}
