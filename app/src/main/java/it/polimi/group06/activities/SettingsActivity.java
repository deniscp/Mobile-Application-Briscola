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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput("settings", Context.MODE_PRIVATE);
                    outputStream.write(towrite.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("saveSettings" + i);
                finish();
            }
        });
    }

    void getSettings() {
        FileInputStream fis;
        int n;
        StringBuffer fileContent = new StringBuffer("");
        try {
            fis = openFileInput("settings");

            byte[] buffer = new byte[1024];

            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
            String str = fileContent.toString();
            i = Integer.parseInt(str);
            System.out.println("GetSettings" + i);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            i=0;
        } catch (IOException e) {
            System.out.println("Problem");
        }

    }

}
