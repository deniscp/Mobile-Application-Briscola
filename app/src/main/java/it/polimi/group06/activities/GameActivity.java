package it.polimi.group06.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    List<String> settingsList;
    String cardbackstring;

    Animation robottomiddle;

    int cardatpositionplayed, color, cardback, numberoftimesplayerwon, numberoftimesrobotwon, numberofdraws;

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

        setContentView(R.layout.activity_game_actvity);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        humancard = findViewById(R.id.humanplayed);
        robotcard = findViewById(R.id.robotplayed);

        //remaining = findViewById(R.id.remaining);
        //winner = findViewById(R.id.winner);
        //saveandquit = findViewById(R.id.savequit_button);

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

        robottomiddle = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.robotcard);

        human = game.getPlayers()[0];
        robot = game.getPlayers()[1];

        getStatisticsFile();
        getSettingsFile();
        System.out.println("adssdaioadsjioafirst" + cardbackstring);
        //saveandquit.setOnClickListener(this);

        cardzero_image.setOnClickListener(this);
        cardone_image.setOnClickListener(this);
        cardtwo_image.setOnClickListener(this);

        briscola_image.setImageResource(getCardDrawable(game.getTable().getBriscola(), briscola_image.getContext()));
        System.out.println("zzzzzfirst" + getCardDrawable(game.getTable().getBriscola(), briscola_image.getContext()));

        setHandCardImages();

        humancard.setImageDrawable(null);
        robotcard.setImageDrawable(null);

        tStart = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();

        setStatisticsFile();
    }

    @Override
    protected void onStop() {
        super.onStop();

//        setStatisticsFile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        setStatisticsFile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case (R.id.savequit_button):
                saveGame();
                finish();
                break;*/
            case (R.id.cardzeroimage):
                amountpositionwasplayed[0] += 1;
                cardatpositionplayed = 0;
                playCard(0, cardzero_image.getContext());
                break;
            case (R.id.cardoneimage):
                amountpositionwasplayed[1] += 1;
                cardatpositionplayed = 1;
                playCard(1, cardone_image.getContext());
                break;
            case (R.id.cardtwoimage):
                amountpositionwasplayed[2] += 1;
                cardatpositionplayed = 2;
                playCard(2, cardtwo_image.getContext());
                break;
        }
    }

    void playCard(int positionofcard, Context cardcontext) {
        //set card to played card by human
        humancard.setImageResource(getCardDrawable(human.getHand().get(cardatpositionplayed), cardcontext));
        //actually play card
        game.playerPlaysCard(0, cardatpositionplayed);

        cardzero_image.setEnabled(false);
        cardone_image.setEnabled(false);
        cardtwo_image.setEnabled(false);

        if(game.getStartingPlayer()==0){
            //set card to played card by robot
            robotcard.setImageResource(getCardDrawable(robot.getHand().get(cardatpositionplayed), robotcard.getContext()));
            robotcard.startAnimation(robottomiddle);
            //actually play card
            game.playerPlaysCard(1, 0);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                humancard.setImageDrawable(null);
                robotcard.setImageDrawable(null);

                game.newRound();

                if (human.getHand().size() == 0 || robot.getHand().size() == 0) {
                    endofgame();
                }

                setHandCardImages();
                if(game.getStartingPlayer()==1){
                    //set card to played card by robot
                    robotcard.setImageResource(getCardDrawable(robot.getHand().get(cardatpositionplayed), robotcard.getContext()));
                    robotcard.startAnimation(robottomiddle);
                    //actually play card
                    game.playerPlaysCard(1, 0);
                }

                cardzero_image.setEnabled(true);
                cardone_image.setEnabled(true);
                cardtwo_image.setEnabled(true);
            }
        }, 1000);

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
                humanhand[i].setImageResource(getCardDrawable(humancard, cardzero_image.getContext()));
            }
        } else if (handsize == 2) {
            humanhand[2].setImageDrawable(null);
            for (int i = 0; i < 2; i++) {
                Card humancard = human.getHand().get(i);
                humanhand[i].setImageResource(getCardDrawable(humancard, cardone_image.getContext()));
            }
        } else if (handsize == 1) {
            Card humancard = human.getHand().get(0);
            for (int i = 0; i < 2; i++) {
                humanhand[i].setImageDrawable(null);
            }
            humanhand[0].setImageResource(getCardDrawable(humancard, cardtwo_image.getContext()));
        } else {
            for (int i = 0; i < 3; i++) {
                humanhand[i].setImageDrawable(null);
            }
        }
        //remaining.setText(String.valueOf(game.remainingCards()));
    }

    int getCardDrawable(Card cardatposition, Context whichcard) {
        System.out.println("zzzz" + cardatposition.toString() + " zzzzz " + whichcard.toString() + "zzzzz");
        switch (cardatposition.toString()) {
            case ("1B"):
                return whichcard.getResources().getIdentifier("bastoni1" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("2B"):
                return whichcard.getResources().getIdentifier("bastoni2" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("3B"):
                return whichcard.getResources().getIdentifier("bastoni3" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("4B"):
                return whichcard.getResources().getIdentifier("bastoni4" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("5B"):
                return whichcard.getResources().getIdentifier("bastoni5" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("6B"):
                return whichcard.getResources().getIdentifier("bastoni6" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("7B"):
                return whichcard.getResources().getIdentifier("bastoni7" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("JB"):
                return whichcard.getResources().getIdentifier("bastoni8" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("HB"):
                return whichcard.getResources().getIdentifier("bastoni9" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("KB"):
                return whichcard.getResources().getIdentifier("bastoni10" + cardbackstring, "drawable", whichcard.getPackageName());

            case ("1S"):
                return whichcard.getResources().getIdentifier("spade1" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("2S"):
                return whichcard.getResources().getIdentifier("spade2" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("3S"):
                return whichcard.getResources().getIdentifier("spade3" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("4S"):
                return whichcard.getResources().getIdentifier("spade4" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("5S"):
                return whichcard.getResources().getIdentifier("spade5" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("6S"):
                return whichcard.getResources().getIdentifier("spade6" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("7S"):
                return whichcard.getResources().getIdentifier("spade7" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("JS"):
                return whichcard.getResources().getIdentifier("spade8" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("HS"):
                return whichcard.getResources().getIdentifier("spade9" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("KS"):
                return whichcard.getResources().getIdentifier("spade10" + cardbackstring, "drawable", whichcard.getPackageName());

            case ("1C"):
                return whichcard.getResources().getIdentifier("coppe1" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("2C"):
                return whichcard.getResources().getIdentifier("coppe2" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("3C"):
                return whichcard.getResources().getIdentifier("coppe3" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("4C"):
                return whichcard.getResources().getIdentifier("coppe4" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("5C"):
                return whichcard.getResources().getIdentifier("coppe5" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("6C"):
                return whichcard.getResources().getIdentifier("coppe6" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("7C"):
                return whichcard.getResources().getIdentifier("coppe7" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("JC"):
                return whichcard.getResources().getIdentifier("coppe8" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("HC"):
                return whichcard.getResources().getIdentifier("coppe9" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("KC"):
                return whichcard.getResources().getIdentifier("coppe10" + cardbackstring, "drawable", whichcard.getPackageName());

            case ("1G"):
                return whichcard.getResources().getIdentifier("denari1" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("2G"):
                return whichcard.getResources().getIdentifier("denari2" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("3G"):
                return whichcard.getResources().getIdentifier("denari3" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("4G"):
                return whichcard.getResources().getIdentifier("denari4" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("5G"):
                return whichcard.getResources().getIdentifier("denari5" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("6G"):
                return whichcard.getResources().getIdentifier("denari6" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("7G"):
                return whichcard.getResources().getIdentifier("denari7" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("JG"):
                return whichcard.getResources().getIdentifier("denari8" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("HG"):
                return whichcard.getResources().getIdentifier("denari9" + cardbackstring, "drawable", whichcard.getPackageName());
            case ("KG"):
                return whichcard.getResources().getIdentifier("denari10" + cardbackstring, "drawable", whichcard.getPackageName());
        }
        System.out.println("not in switch");
        return 0;
    }

    public void endofgame() {
        String text;

        saveandquit.setEnabled(false);

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

        setStatisticsFile();

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

    void saveGame() {
        String towrite = game.toConfiguration();
        OutputHandler.writefile(towrite, "savedgame", getApplicationContext());
    }

    void setStatisticsFile() {
        String towrite = String.valueOf(amountpositionwasplayed[0]) + "," + String.valueOf(amountpositionwasplayed[1]) + "," + String.valueOf(amountpositionwasplayed[2])
                + "," + String.valueOf(elapsedSeconds) + "," + String.valueOf(numberoftimesplayerwon) + "," + String.valueOf(numberoftimesrobotwon) + "," +
                String.valueOf(numberofdraws);

        OutputHandler.writefile(towrite, "statistics", getApplicationContext());
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

    void getSettingsFile() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());
        settingsList = Arrays.asList(str.split(","));
        System.out.println("YYYY" + settingsList);
        if (!str.equals("")) {
            color = Integer.parseInt(settingsList.get(0));
            cardback = Integer.parseInt(settingsList.get(1));

            /*switch (color) {
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
            }*/

            switch (cardback) {
                case (0):
                    cardbackstring = "n";
                    break;
                case (1):
                    cardbackstring = "g";
                    break;
                case (2):
                    cardbackstring = "s";
                    break;
                default:
                    cardbackstring = "n";
            }

            System.out.println("XXXX" + color + cardback);
        }
    }
}

