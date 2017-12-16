package it.polimi.group06.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import it.polimi.group06.InputHandler;
import it.polimi.group06.OutputHandler;
import it.polimi.group06.R;
import it.polimi.group06.domain.Card;
import it.polimi.group06.domain.Game;
import it.polimi.group06.domain.Player;

import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveandquit;
    TextView remaining, winner;
    ImageView cardzero_image, cardone_image, cardtwo_image, briscola_image, humancard, robotcard;

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

        humancard = findViewById(R.id.humanplayed);
        robotcard = findViewById(R.id.robotplayed);

        remaining = findViewById(R.id.remaining);
        winner = findViewById(R.id.winner);
        saveandquit = findViewById(R.id.savequit_button);

        cardzero_image = findViewById(R.id.cardzeroimage);
        cardone_image = findViewById(R.id.cardoneimage);
        cardtwo_image = findViewById(R.id.cardtwoimage);

        briscola_image = findViewById(R.id.briscolaimage);

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("keyMessage");

        if (msg.equals("fromsaved")) {
            String config = InputHandler.getStringfromFile("savedgame", getApplicationContext());
            game = Game.initializeFromConf(config);
        }
        if (msg.equals("newgame")) {
            game = new Game();
        }

        human = game.getPlayers()[0];
        robot = game.getPlayers()[1];

        getStatisticsFile();
        setSettingsfromFile();

        saveandquit.setOnClickListener(this);

        cardzero_image.setOnClickListener(this);
        cardone_image.setOnClickListener(this);
        cardtwo_image.setOnClickListener(this);

        briscola_image.setImageDrawable(getCardDrawable(game.getTable().getBriscola()));

        setHandCardImages();

        humancard.setImageDrawable(null);
        robotcard.setImageDrawable(null);

        tStart = System.currentTimeMillis();
    }


    void setHandCardImages() {
        ImageView[] humanhand = {cardzero_image, cardone_image, cardtwo_image};
        int decksize = game.getTable().getDeck().remaining();
        int handsize = human.getHand().size();
        if (decksize == 0) {
            briscola_image.setImageDrawable(null);
        }
        if (handsize == 3) {
            for (int i = 0; i < 3; i++) {
                Card humancard = human.getHand().get(i);
                humanhand[i].setImageDrawable(getCardDrawable(humancard));
            }
        } else if (handsize == 2) {
            humanhand[2].setImageDrawable(null);
            for (int i = 0; i < 2; i++) {
                Card humancard = human.getHand().get(i);
                humanhand[i].setImageDrawable(getCardDrawable(humancard));
            }
        } else if (handsize == 1) {
            Card humancard = human.getHand().get(0);
            for (int i = 0; i < 2; i++) {
                humanhand[i].setImageDrawable(null);
            }
            humanhand[0].setImageDrawable(getCardDrawable(humancard));
        } else {
            for (int i = 0; i < 3; i++) {
                humanhand[i].setImageDrawable(null);
            }
        }
        remaining.setText(String.valueOf(game.remainingCards()));
    }

    Drawable getCardDrawable(Card cardatposition) {
        switch (cardatposition.toString()) {
            case ("1B"):
                return getResources().getDrawable(R.drawable.bastoni1);
            case ("2B"):
                return getResources().getDrawable(R.drawable.bastoni2);
            case ("3B"):
                return getResources().getDrawable(R.drawable.bastoni3);
            case ("4B"):
                return getResources().getDrawable(R.drawable.bastoni4);
            case ("5B"):
                return getResources().getDrawable(R.drawable.bastoni5);
            case ("6B"):
                return getResources().getDrawable(R.drawable.bastoni6);
            case ("7B"):
                return getResources().getDrawable(R.drawable.bastoni7);
            case ("JB"):
                return getResources().getDrawable(R.drawable.bastoni8);
            case ("HB"):
                return getResources().getDrawable(R.drawable.bastoni9);
            case ("KB"):
                return getResources().getDrawable(R.drawable.bastoni10);

            case ("1S"):
                return getResources().getDrawable(R.drawable.spade1);
            case ("2S"):
                return getResources().getDrawable(R.drawable.spade2);
            case ("3S"):
                return getResources().getDrawable(R.drawable.spade3);
            case ("4S"):
                return getResources().getDrawable(R.drawable.spade4);
            case ("5S"):
                return getResources().getDrawable(R.drawable.spade5);
            case ("6S"):
                return getResources().getDrawable(R.drawable.spade6);
            case ("7S"):
                return getResources().getDrawable(R.drawable.spade7);
            case ("JS"):
                return getResources().getDrawable(R.drawable.spade8);
            case ("HS"):
                return getResources().getDrawable(R.drawable.spade9);
            case ("KS"):
                return getResources().getDrawable(R.drawable.spade10);

            case ("1C"):
                return getResources().getDrawable(R.drawable.coppe1);
            case ("2C"):
                return getResources().getDrawable(R.drawable.coppe2);
            case ("3C"):
                return getResources().getDrawable(R.drawable.coppe3);
            case ("4C"):
                return getResources().getDrawable(R.drawable.coppe4);
            case ("5C"):
                return getResources().getDrawable(R.drawable.coppe5);
            case ("6C"):
                return getResources().getDrawable(R.drawable.coppe6);
            case ("7C"):
                return getResources().getDrawable(R.drawable.coppe7);
            case ("JC"):
                return getResources().getDrawable(R.drawable.coppe8);
            case ("HC"):
                return getResources().getDrawable(R.drawable.coppe9);
            case ("KC"):
                return getResources().getDrawable(R.drawable.coppe10);

            case ("1G"):
                return getResources().getDrawable(R.drawable.denari1);
            case ("2G"):
                return getResources().getDrawable(R.drawable.denari2);
            case ("3G"):
                return getResources().getDrawable(R.drawable.denari3);
            case ("4G"):
                return getResources().getDrawable(R.drawable.denari4);
            case ("5G"):
                return getResources().getDrawable(R.drawable.denari5);
            case ("6G"):
                return getResources().getDrawable(R.drawable.denari6);
            case ("7G"):
                return getResources().getDrawable(R.drawable.denari7);
            case ("JG"):
                return getResources().getDrawable(R.drawable.denari8);
            case ("HG"):
                return getResources().getDrawable(R.drawable.denari9);
            case ("KG"):
                return getResources().getDrawable(R.drawable.denari10);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        boolean cardplayed = false;
        switch (v.getId()) {
            case (R.id.savequit_button):
                saveGame();
                cardplayed = false;
                finish();
                break;
            case (R.id.cardzeroimage):
                amountpositionwasplayed[0] += 1;
                i = 0;
                cardplayed = true;
                break;
            case (R.id.cardoneimage):
                amountpositionwasplayed[1] += 1;
                i = 1;
                cardplayed = true;
                break;
            case (R.id.cardtwoimage):
                amountpositionwasplayed[2] += 1;
                i = 2;
                cardplayed = true;
                break;
        }
        if (cardplayed) {
            //set card to played card by human
            humancard.setImageDrawable(getCardDrawable(human.getHand().get(i)));
            //actually play card
            game.playerPlaysCard(0, i);
            //set card to played card by robot
            robotcard.setImageDrawable(getCardDrawable(robot.getHand().get(i)));
            //actually play card
            game.playerPlaysCard(1, 0);
            game.newRound();
            setHandCardImages();
            if (human.getHand().size() == 0 || robot.getHand().size() == 0) {
                endofgame();
            }

            //humancard.setImageDrawable(null);
            //robotcard.setImageDrawable(null);
        }
    }

    void saveGame() {
        String towrite = game.toConfiguration();
        OutputHandler.writefile(towrite, "savedgame", getApplicationContext());
    }

    void updateStatisticsFile() {
        String towrite = String.valueOf(amountpositionwasplayed[0]) + "," + String.valueOf(amountpositionwasplayed[1]) + "," + String.valueOf(amountpositionwasplayed[2])
                + "," + String.valueOf(elapsedSeconds) + "," + String.valueOf(numberoftimesplayerwon) + "," + String.valueOf(numberoftimesrobotwon) + "," +
                String.valueOf(numberofdraws);

        OutputHandler.writefile(towrite, "statistics", getApplicationContext());
    }

    public void endofgame() {
        String text;
        if (game.returnWinner() == FIRSTPLAYER) {
            text = "Player wins with " + String.valueOf(human.getPlayerPoints()) + " Points";
            numberoftimesplayerwon += 1;
        } else if (game.returnWinner() == SECONDPLAYER) {
            text = "Robot wins with " + String.valueOf(robot.getPlayerPoints()) + " Points";
            numberoftimesrobotwon += 1;
        } else {
            text = "Draw";
            numberofdraws += 1;
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
                        if (msg.equals("fromsaved")) {
                            msg = "newgame";
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
