package it.polimi.group06.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

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

        readfromfile();
        setcontentofviews();

        Button clearstats = findViewById(R.id.clearstats);
        clearstats.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                clearfile();
                readfromfile();
                setcontentofviews();
            }
        });
    }

    void clearfile(){
        String towrite = 0 + "," + 0 + "," + 0 +","+0;

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("statistics", Context.MODE_PRIVATE);
            outputStream.write(towrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void readfromfile() {
        FileInputStream fis;
        int n;
        StringBuffer fileContent = new StringBuffer("");
        try {
            fis = openFileInput("statistics");

            byte[] buffer = new byte[1024];

            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Problem");
        }

        String str = fileContent.toString();
        statList = Arrays.asList(str.split(","));
    }

    void setcontentofviews(){
        TextView zero = findViewById(R.id.zero);
        TextView one = findViewById(R.id.one);
        TextView two = findViewById(R.id.two);

        TextView seconds = findViewById(R.id.timeplayed);

        zero.setText(statList.get(0));
        one.setText(statList.get(1));
        two.setText(statList.get(2));
        seconds.setText(statList.get(3));
    }
}
