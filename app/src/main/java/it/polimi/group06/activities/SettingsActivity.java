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

    RadioGroup cardpicker;
    RadioButton naples, german, sicily;
    Button button;
    List<String> settingsList;
    int whichcarddeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity_game fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        cardpicker = findViewById(R.id.cardpicker_group);

        naples = findViewById(R.id.naples);
        german = findViewById(R.id.german);
        sicily = findViewById(R.id.sicily);

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
                System.out.print("this doesn't exist");
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

        button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String towrite = String.valueOf(whichcarddeck + ",");
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
        }
    }
}