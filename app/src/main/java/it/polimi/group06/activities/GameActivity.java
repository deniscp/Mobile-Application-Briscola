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
import it.polimi.group06.domain.Human;
import it.polimi.group06.domain.Player;

import static it.polimi.group06.domain.Constants.FIRSTCARD;
import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.SECONDCARD;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;
import static it.polimi.group06.domain.Constants.THIRDCARD;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveandquit;
    TextView remaining, winner;

    ImageView cardzero_image, cardone_image, cardtwo_image, briscola_image, humanplayed, robotplayed;
    ImageView robotcard1, robotcard2, robotcard3;
    ImageView robotwon, humanwon;

    List<String> settingsList;
    String cardbackstring;

    boolean cardSetFlag = false;
    int humanChosenCard;

    Animation robottomiddle;

    int color, cardback, numberoftimesplayerwon, numberoftimesrobotwon, numberofdraws;

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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        robotplayed = findViewById(R.id.robotplayed);
        humanplayed = findViewById(R.id.humanplayed);

        robotcard1 = findViewById(R.id.robothand1);
        robotcard2 = findViewById(R.id.robothand2);
        robotcard3 = findViewById(R.id.robothand3);

        robotwon = findViewById(R.id.robotwon);
        humanwon = findViewById(R.id.humanwon);

        remaining = findViewById(R.id.remaining);
        winner = findViewById(R.id.winner);

        saveandquit = findViewById(R.id.savequit_button);

        cardzero_image = findViewById(R.id.cardzeroimage);
        cardone_image = findViewById(R.id.cardoneimage);
        cardtwo_image = findViewById(R.id.cardtwoimage);
        briscola_image = findViewById(R.id.briscolaimage);

        cardSetFlag = false;

        Bundle extras = getIntent().getExtras();
        String msg = extras.getString("keyMessage");
        if (msg.equals("fromsaved")) {
            gamefromsavedfile();
        }
        if (msg.equals("newgame")) {
            game = new Game();
            human = game.getPlayers()[0];
            robot = game.getPlayers()[1];
        }

        robottomiddle = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.robotcard);

        getStatisticsFile();
        setSettingsfromFile();

        cardzero_image.setOnClickListener(this);
        cardone_image.setOnClickListener(this);
        cardtwo_image.setOnClickListener(this);
        saveandquit.setOnClickListener(this);

        setHandCardImages();
        setRobotCardImages();
        briscola_image.setImageResource(getCardDrawable(game.getTable().getBriscola(), briscola_image.getContext()));

        tStart = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTheGame();
    }

    private void startTheGame() {
        humanCardsClickable(false);
        if (!game.gameIsOver())
            playOneRound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setStatisticsFile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSettingsfromFile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.savequit_button):
                System.out.println("clicked!");
                String savedgame = InputHandler.getStringfromFile("savedgame", getApplicationContext());
                if (!savedgame.equals("")) {
                    new AlertDialog.Builder(GameActivity.this).setTitle("Overwrite Game").setMessage("There is already a game saved.\nDo you want to overwrite the save?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    saveGame();
                                    finish();
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).show();
                }
                break;
            case (R.id.cardzeroimage):
                amountpositionwasplayed[FIRSTCARD] += 1;
                humanChosenCard = FIRSTCARD;
                break;
            case (R.id.cardoneimage):
                amountpositionwasplayed[SECONDCARD] += 1;
                humanChosenCard = SECONDCARD;
                break;
            case (R.id.cardtwoimage):
                amountpositionwasplayed[THIRDCARD] += 1;
                humanChosenCard = THIRDCARD;
                break;
            default:
                System.err.println("What did you click on?");
                break;
        }

        // Set cards NOT clickable because the Human has chosen his card
        humanCardsClickable(false);

        // It will enter the playOneRound method we just left after we hit the break;
        // but now we skip to the next if with the humanChosenCard appropriately set
        playOneRound();

        // Prepare the new round
        if (game.roundIsOver())
            game.newRound();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                winnerandreset();
            }
        }, 1000);

        if (!game.gameIsOver()) {
            final Handler handlernewround = new Handler();
            handlernewround.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playOneRound();     // the next round
                }
            }, 1000);
        } else // Game is over, no more rounds to play!
        {
            endofgame();
        }

    }

    void playOneRound() {

        int currentPlayer, currentChoice;

        while (!game.roundIsOver()) {

            //It is the Human turn but Human has not chosen his card yet
            if (game.getCurrentPlayer() instanceof Human & !cardSetFlag) {

                // Set cards clickable
                humanCardsClickable(true);

                // The human is going to click on his chosen card,
                // we don't want this if branch to be executed again the very next time.
                // Instead the next if branch will be hit next time, after the Human has chosen his card.
                cardSetFlag = true;

                // Let's break to let human choose his card
                break;
            }

            // After Human has clicked his card we reach this point
            // skipping the previous if and entering this if branch
            if (game.getCurrentPlayer() instanceof Human & cardSetFlag)
                //set the flag as false for the next round
                // so the previous if can be reached again and human can click his next card
                cardSetFlag = false;


            currentPlayer = game.getCurrentPlayerPosition();
            currentChoice = game.getCurrentChoice(humanChosenCard); // new method!

            // Update the view
            launchAnimation(currentPlayer, currentChoice);

            // Update the model
            game.playerPlaysCard(currentPlayer, currentChoice);
        }
    }

    void humanCardsClickable(boolean clickable) {
        if (clickable) {
            cardzero_image.setClickable(true);
            cardone_image.setClickable(true);
            cardtwo_image.setClickable(true);
        } else {
            cardzero_image.setClickable(false);
            cardone_image.setClickable(false);
            cardtwo_image.setClickable(false);
        }
    }

    void launchAnimation(int currentPlayerPosition, int currentPlayedCard) {

        robotcard1.setImageDrawable(null);

        switch (currentPlayedCard) {
            case (0):
                cardzero_image.setImageDrawable(null);
                break;
            case (1):
                cardone_image.setImageDrawable(null);
                break;
            case (2):
                cardtwo_image.setImageDrawable(null);
                break;
        }

        robotplayed.setImageResource(getCardDrawable(robot.getHand().get(0), robotplayed.getContext()));
        robotplayed.startAnimation(robottomiddle);

        humanplayed.setImageResource(getCardDrawable(human.getHand().get(currentPlayedCard), humanplayed.getContext()));
    }

    void updateView() {
        setHandCardImages();
        setRobotCardImages();
    }

    void winnerandreset() {
        if (game.getStartingPlayer() == 0) {
            humanwon.setImageResource(R.drawable.checkmark);
        } else {
            robotwon.setImageResource(R.drawable.checkmark);
        }

        humanplayed.setImageDrawable(null);
        robotplayed.setImageDrawable(null);

        humanwon.setImageResource(R.drawable.checkmark_grey);
        robotwon.setImageResource(R.drawable.checkmark_grey);

        updateView();
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
            humanhand[2].setImageDrawable(null);
            humanhand[1].setImageDrawable(null);
            Card humancard = human.getHand().get(0);
            humanhand[0].setImageResource(getCardDrawable(humancard, cardtwo_image.getContext()));
        } else {
            for (int i = 0; i < 3; i++) {
                humanhand[i].setImageDrawable(null);
            }
        }
        remaining.setText(String.valueOf(game.remainingCards()));
    }

    void setRobotCardImages() {
        ImageView[] robotcards = {robotcard1, robotcard1, robotcard3};
        int handsize = robot.getHand().size();
        if (handsize == 3) {
            for (int i = 0; i < 3; i++) {
                robotcards[i].setImageResource(R.drawable.deck);
            }
        } else if (handsize == 2) {
            robotcards[2].setImageDrawable(null);
        } else if (handsize == 1) {
            for (int i = 2; i > 0; i--) {
                robotcards[i].setImageDrawable(null);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                robotcards[i].setImageDrawable(null);
            }
        }
    }

    int getCardDrawable(Card cardatposition, Context whichcard) {
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

        new AlertDialog.Builder(GameActivity.this).setTitle("End of Game").setMessage(text + "\nDo you want to start a new game?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
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
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        }).show();
    }

    void saveGame() {
        String towrite = game.toConfiguration();
        OutputHandler.writefile(towrite, "savedgame", getApplicationContext());
    }

    void setStatisticsFile() {
        String towrite = String.valueOf(amountpositionwasplayed[0]) + "," + String.valueOf(amountpositionwasplayed[1]) + "," + String.valueOf(amountpositionwasplayed[2]) + "," + String.valueOf(elapsedSeconds) + "," + String.valueOf(numberoftimesplayerwon) + "," + String.valueOf(numberoftimesrobotwon) + "," + String.valueOf(numberofdraws);
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

    void setSettingsfromFile() {
        String str = InputHandler.getStringfromFile("settings", getApplicationContext());
        settingsList = Arrays.asList(str.split(","));
        if (!str.equals("")) {
            cardback = Integer.parseInt(settingsList.get(0));
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
            int backgroundcolor = Integer.parseInt(settingsList.get(1));
            switch (backgroundcolor) {
                case (0):
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                    break;
                case (1):
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                    break;
                case (2):
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightblue));
                    break;
            }
        } else {
            cardbackstring = "n";
        }
    }

    void gamefromsavedfile() {
        String config = InputHandler.getStringfromFile("savedgame", getApplicationContext());
        if (!config.equals("")) {

            game = Game.initializeFromConf(config);

            human = game.getPlayers()[0];
            robot = game.getPlayers()[1];

            setRobotCardImages();

            //TODO
            if (game.getTable().getPlayedCardsAmount() == 1) {
                if (game.getStartingPlayer() == 0) {
                    humanplayed.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0), humanplayed.getContext()));
                } else {
                    robotplayed.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0), robotplayed.getContext()));
                }
            } else if (game.getTable().getPlayedCardsAmount() == 2) {
                if (game.getStartingPlayer() == 0) {
                    humanplayed.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0), humanplayed.getContext()));
                    robotplayed.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(1), robotplayed.getContext()));
                } else {
                    robotplayed.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0), robotplayed.getContext()));
                    humanplayed.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(1), humanplayed.getContext()));

                }
            }
        }
    }
}

