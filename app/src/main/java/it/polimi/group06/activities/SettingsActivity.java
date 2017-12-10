package it.polimi.group06.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import it.polimi.group06.InputHandler;
import it.polimi.group06.OutputHandler;
import it.polimi.group06.R;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup colorpicker;
    RadioButton red, orange, green, blue;
    Button button;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity_game fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);

        colorpicker = findViewById(R.id.colorpicker_group);

        red = findViewById(R.id.red);
        orange = findViewById(R.id.orange);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);

        getSettings();

        switch (i) {
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

        colorpicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.red) {
                    i = 0;
                } else if (checkedId == R.id.orange) {
                    i = 1;
                } else if (checkedId == R.id.green) {
                    i = 2;
                } else if (checkedId == R.id.blue) {
                    i = 3;
                }
            }
        });

        button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String towrite = String.valueOf(i);
                OutputHandler.writefile(towrite, "settings", getApplicationContext());
                System.out.println("saveSettings" + i);
                finish();
            }
        });
    }

    void getSettings() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());
        if(str.equals("")){
            i = 0;
        }else{
            i = Integer.parseInt(str);
        }
        System.out.println("GetSettings" + i);
    }

}
