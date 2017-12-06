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

import it.polimi.group06.R;
import it.polimi.group06.domain.Game;
import it.polimi.group06.domain.Player;

import static it.polimi.group06.domain.Constants.FIRSTPLAYER;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button cardzero_button;
    Button cardone_button;
    Button cardtwo_button;
    TextView humancard;
    TextView robotcard;
    TextView remaining;
    TextView winner;

    int i;
    int j;

    long tStart;
    double elapsedSeconds;

    Game game;
    Player human;
    Player robot;

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
        winner = findViewById(R.id.winner);

        game = new Game();
        human = game.getPlayers()[0];
        robot = game.getPlayers()[1];

        setText();

        cardzero_button.setClickable(true);
        cardone_button.setClickable(true);
        cardtwo_button.setClickable(true);

        cardzero_button.setOnClickListener(this);
        cardone_button.setOnClickListener(this);
        cardtwo_button.setOnClickListener(this);

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
        if (v.getId() == R.id.zero) {
            i = 0;
        }
        if (v.getId() == R.id.one) {
            i = 1;
        }
        if (v.getId() == R.id.two) {
            i = 2;
        }
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

        new AlertDialog.Builder(GameActivity.this)
                .setTitle("End of Game")
                .setMessage(text+"\nDo you want to start a new game?")
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

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        elapsedSeconds = tDelta / 1000.0;
    }

}
