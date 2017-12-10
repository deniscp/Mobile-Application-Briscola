package it.polimi.group06.activities;

import android.app.AlertDialog;
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

import java.util.Arrays;
import java.util.List;

import it.polimi.group06.InputHandler;
import it.polimi.group06.OutputHandler;
import it.polimi.group06.R;
import it.polimi.group06.domain.Game;
import it.polimi.group06.domain.Player;

import static it.polimi.group06.domain.Constants.DRAW;
import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button cardzero_button, cardone_button, cardtwo_button, saveandquit;
    TextView humancard, robotcard, remaining, winner, briscola;

    int i, color, numberoftimesplayerwon, numberoftimesrobotwon, numberofdraws;

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

        cardzero_button = findViewById(R.id.zero);
        cardone_button = findViewById(R.id.one);
        cardtwo_button = findViewById(R.id.two);
        humancard = findViewById(R.id.humancard);
        robotcard = findViewById(R.id.robotcard);
        remaining = findViewById(R.id.remaining);
        briscola = findViewById(R.id.briscola);
        winner = findViewById(R.id.winner);
        saveandquit = findViewById(R.id.savequit_button);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("keyMessage");

        if (msg.equals("fromsaved")) {
            String config = InputHandler.getStringfromFile("savedgame", getApplicationContext());
            game = Game.initializeFromConf(config);
        }
        if (msg.equals("newgame")){
            game = new Game();
        }

        human = game.getPlayers()[0];
        robot = game.getPlayers()[1];

        setText();
        getStatisticsFile();
        setSettingsfromFile();

        cardzero_button.setClickable(true);
        cardone_button.setClickable(true);
        cardtwo_button.setClickable(true);

        cardzero_button.setOnClickListener(this);
        cardone_button.setOnClickListener(this);
        cardtwo_button.setOnClickListener(this);
        saveandquit.setOnClickListener(this);

        tStart = System.currentTimeMillis();
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
            humanhand[1].setEnabled(false);
            humanhand[2].setEnabled(false);
            humanhand[1].setText("-");
            humanhand[2].setText("-");
            humanhand[0].setText(human.getHand().get(0).toString());
        } else {
            for(int i=0;i<3;i++){
                humanhand[i].setEnabled(false);
                humanhand[i].setText("-");
            }
        }
        remaining.setText(String.valueOf(game.remainingCards()));
        briscola.setText(String.valueOf(game.getTable().getBriscola()));
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
        if(cardplayed) {
            humancard.setText(human.getHand().get(i).toString());
            game.playerPlaysCard(0, i);
            robotcard.setText(robot.getHand().get(0).toString());
            game.playerPlaysCard(1, 0);
            game.newRound();
            setText();
            if (human.getHand().size() == 0 || robot.getHand().size() == 0) {
                endofgame();
            }
        }
    }

    void saveGame() {
        String towrite = game.toConfiguration();
        OutputHandler.writefile(towrite, "savedgame", getApplicationContext());
    }

    void updateStatisticsFile() {
        String towrite = String.valueOf(amountpositionwasplayed[0]) + "," + String.valueOf(amountpositionwasplayed[1]) + "," + String.valueOf(amountpositionwasplayed[2])
                + "," + String.valueOf(elapsedSeconds) + "," + String.valueOf(numberoftimesplayerwon) + "," + String.valueOf(numberoftimesrobotwon) +","+
                String.valueOf(numberofdraws);

        OutputHandler.writefile(towrite, "statistics", getApplicationContext());
    }

    public void endofgame() {
        String text;
        cardzero_button.setText("-");
        if (game.returnWinner() == FIRSTPLAYER) {
            text = "Player wins with " + String.valueOf(human.getPlayerPoints());
            numberoftimesplayerwon +=1;
        } else if (game.returnWinner() == FIRSTPLAYER) {
            text = "Robot wins with " + String.valueOf(robot.getPlayerPoints());
            numberoftimesrobotwon +=1;
        } else {
            text = "Draw";
            numberofdraws +=1;
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
                        String msg = intent.getExtras().getString("keyMessage");
                        if(msg.equals("fromsaved")){
                            msg="newgame";
                            intent.putExtra("keyMessage", msg);
                        }
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
        String stats = InputHandler.getStringfromFile("statistics", getApplicationContext());

        if (!stats.equals("")) {
            List<String> statList = Arrays.asList(stats.split(","));
            System.out.println(stats + "yyyyyy" + statList + "xxxxxxxxxx");
            for (int i = 0; i < 3; i++) {
                amountpositionwasplayed[i] = Integer.parseInt(statList.get(i));
            }
            elapsedSeconds = Double.parseDouble(statList.get(3));
            numberoftimesplayerwon = Integer.parseInt(statList.get(4));
            numberoftimesrobotwon = Integer.parseInt(statList.get(5));
            numberofdraws = Integer.parseInt(statList.get(6));
        } else {
            for (int i = 0; i < 3; i++) {
                amountpositionwasplayed[i] = 0;
            }
            elapsedSeconds = 0;
            numberoftimesplayerwon = 0;
            numberoftimesrobotwon = 0;
            numberofdraws = 0;
        }
    }

    void setSettingsfromFile() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());

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
