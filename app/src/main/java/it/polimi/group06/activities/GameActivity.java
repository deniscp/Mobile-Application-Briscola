package it.polimi.group06.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import it.polimi.group06.R;
import it.polimi.group06.domain.Game;
import it.polimi.group06.domain.Player;

import static it.polimi.group06.domain.Constants.FIRSTPLAYER;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button cardzero_button, cardone_button, cardtwo_button, saveandquit;
    TextView humancard, robotcard, remaining, winner;

    int i, j, color;

    long tStart;
    double elapsedSeconds;
    int[] amountpositionwasplayed = {0, 0, 0};

    Game game;
    Player human, robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make activity_main fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("keyMessage");

        cardzero_button = findViewById(R.id.zero);
        cardone_button = findViewById(R.id.one);
        cardtwo_button = findViewById(R.id.two);
        humancard = findViewById(R.id.humancard);
        robotcard = findViewById(R.id.robotcard);
        remaining = findViewById(R.id.remaining);
        winner = findViewById(R.id.winner);
        saveandquit = findViewById(R.id.savequit_button);
        if (msg == "fromsaved") {
            createGamefromConfig();
        } else {
            game = new Game();
        }
        human = game.getPlayers()[0];
        robot = game.getPlayers()[1];

        setText();
        getStatisticsFile();
        setSettings();

        cardzero_button.setClickable(true);
        cardone_button.setClickable(true);
        cardtwo_button.setClickable(true);

        cardzero_button.setOnClickListener(this);
        cardone_button.setOnClickListener(this);
        cardtwo_button.setOnClickListener(this);
        saveandquit.setOnClickListener(this);

        tStart = System.currentTimeMillis();
    }

    void createGamefromConfig() {
        FileInputStream fis;
        int n;
        StringBuffer fileContent = new StringBuffer("");
        try {
            fis = openFileInput("savedgame");

            byte[] buffer = new byte[1024];

            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Problem");
        }

        String config = fileContent.toString();
    }

    void setText() {
        Button[] humanhand = {cardzero_button, cardone_button, cardtwo_button};

        if (human.getHand().size() == 3) {
            for (int i = 0; i < humanhand.length; i++) {
                humanhand[i].setText(human.getHand().get(i).toString());
            }
        } else if (human.getHand().size() == 2) {
            for (int i = 0; i < humanhand.length - 1; i++) {
                cardtwo_button.setEnabled(false);
                humanhand[i].setText(human.getHand().get(i).toString());
                humanhand[2].setText("-");
            }
        } else if (human.getHand().size() == 1) {
            cardone_button.setEnabled(false);
            humanhand[1].setText("-");
            humanhand[0].setText(human.getHand().get(0).toString());
        } else {
            cardzero_button.setEnabled(false);
        }

        remaining.setText(String.valueOf(game.getTable().getDeck().remaining()));
    }

    @Override
    public void onClick(View v) {
        boolean cardplayed = false;
        switch (v.getId()) {
            case (R.id.savequit_button):
                saveGame();
                System.out.println("xxxxxxx");
                cardplayed=false;
                finish();
                break;
            case (R.id.zero):
                amountpositionwasplayed[0] += 1;
                i = 0;
                cardplayed=true;
                break;
            case (R.id.one):
                amountpositionwasplayed[1] += 1;
                i = 1;
                cardplayed=true;
                break;
            case (R.id.two):
                amountpositionwasplayed[2] += 1;
                i = 2;
                cardplayed=true;
                break;
        }
        /*
        if (v.getId() == R.id.savequit_button){
            saveGame();
            System.out.println("xxxxxxx");
            finish();
        }
        if (v.getId() == R.id.zero) {
            amountpositionwasplayed[0] += 1;
            i = 0;
        }
        if (v.getId() == R.id.one) {
            amountpositionwasplayed[1] += 1;
            i = 1;
        }
        if (v.getId() == R.id.two) {
            amountpositionwasplayed[2] += 1;
            i = 2;
        }*/
        if(cardplayed) {
            humancard.setText(human.getHand().get(i).toString());
            game.playerPlaysCard(0, i);
            robotcard.setText(robot.getHand().get(0).toString());
            game.playerPlaysCard(1, 0);
            game.newRound();
            setText();
            j++;
            if (j == 20) {
                endofgame();
            }
        }
    }

    void saveGame() {
        String towrite = game.toConfiguration();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput("savedgame", Context.MODE_PRIVATE);
            outputStream.write(towrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endofgame() {
        String text;
        cardzero_button.setText("-");
        if (game.returnWinner() == FIRSTPLAYER) {
            text = "Player wins with " + String.valueOf(human.getPlayerPoints());
        } else if (game.returnWinner() == FIRSTPLAYER) {
            text = "Robot wins with " + String.valueOf(robot.getPlayerPoints());
        } else {
            text = "Draw";
        }
        Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        elapsedSeconds += tDelta / 1000.0;
        elapsedSeconds = (double) Math.round(elapsedSeconds * 100d) / 100d;

        updateStatisticsFile();

        new AlertDialog.Builder(GameActivity.this)
                .setTitle("End of Game")
                .setMessage(text + "\nDo you want to start a new game?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .show();

    }

    void getStatisticsFile() {
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

        String stats = fileContent.toString();
        if (!stats.equals("")) {
            List<String> statList = Arrays.asList(stats.split(","));
            for (int i = 0; i < 3; i++) {
                amountpositionwasplayed[i] = Integer.parseInt(statList.get(i));
            }
            elapsedSeconds = Double.parseDouble(statList.get(3));
        } else {
            for (int i = 0; i < 3; i++) {
                amountpositionwasplayed[i] = 0;
            }
            elapsedSeconds = 0;
        }
    }

    void updateStatisticsFile() {
        String towrite = String.valueOf(amountpositionwasplayed[0]) + "," + String.valueOf(amountpositionwasplayed[1]) + "," + String.valueOf(amountpositionwasplayed[2])
                + "," + String.valueOf(elapsedSeconds);

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("statistics", Context.MODE_PRIVATE);
            outputStream.write(towrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSettings() {
        FileInputStream fis;
        int n;
        StringBuffer fileContent = new StringBuffer("");
        try {
            fis = openFileInput("settings");

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
        System.out.println("YYYY" + str);
        if (!str.equals("")) {
            color = Integer.parseInt(str);
            switch (color) {
                case (0):
                    remaining.setBackgroundResource(R.drawable.deck);
                    break;
                case (1):
                    remaining.setBackgroundResource(R.drawable.deck_orange);
                    break;
                case (2):
                    remaining.setBackgroundResource(R.drawable.deck_green);
                    break;
                case (3):
                    remaining.setBackgroundResource(R.drawable.deck_blue);
                    break;
                default:
                    System.out.println("This color doesn't exist");
            }
            System.out.println("XXXX" + color);
        }
    }
}
