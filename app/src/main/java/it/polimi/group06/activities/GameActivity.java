package it.polimi.group06.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.polimi.group06.domain.*;

import it.polimi.group06.R;

import static it.polimi.group06.domain.Constants.FIRSTPLAYER;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button cardzero_button;
    Button cardone_button;
    Button cardtwo_button;
    TextView humancard;
    TextView robotcard;
    TextView onecard;
    TextView twocard;
    TextView threecard;
    TextView remaining;
    TextView winner;
    int i;
    int j;

    Game game;
    Player human;
    Player robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        cardzero_button = findViewById(R.id.zero);
        cardone_button = findViewById(R.id.one);
        cardtwo_button = findViewById(R.id.two);
        humancard = findViewById(R.id.humancard);
        robotcard = findViewById(R.id.robotcard);
        onecard = findViewById(R.id.onecard);
        twocard = findViewById(R.id.twocard);
        threecard = findViewById(R.id.threecard);
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
        if(j==20){
            onecard.setText("");
            if (game.returnWinner()==FIRSTPLAYER){
                winner.setText("Player wins with " + String.valueOf(human.getPlayerPoints()));
            }else if(game.returnWinner()==FIRSTPLAYER){
                winner.setText("Robot wins with " + String.valueOf(robot.getPlayerPoints()));
            }else{
                winner.setText("Draw");
            }
            Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();
        }
    }


    void setText() {
        TextView[] humanhand = {onecard, twocard, threecard};

        if (human.getHand().size() == 3) {
            for (int i = 0; i < humanhand.length; i++) {
                humanhand[i].setText(human.getHand().get(i).toString());
            }
        } else if (human.getHand().size() == 2) {
            for (int i = 0; i < humanhand.length - 1; i++) {
                cardtwo_button.setEnabled(false);
                humanhand[i].setText(human.getHand().get(i).toString());
                humanhand[2].setText("");
            }
        } else if (human.getHand().size() == 1) {
            cardone_button.setEnabled(false);
            humanhand[1].setText("");
            humanhand[0].setText(human.getHand().get(0).toString());
        } else {
            cardzero_button.setEnabled(false);
        }

        remaining.setText(String.valueOf(game.getTable().getDeck().remaining()));
    }
}