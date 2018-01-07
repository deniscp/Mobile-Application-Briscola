package it.polimi.group06.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import it.polimi.group06.activity.helper.LaunchCard;
import it.polimi.group06.activity.helper.UpdateView;
import it.polimi.group06.domain.Card;
import it.polimi.group06.domain.Game;
import it.polimi.group06.domain.Human;
import it.polimi.group06.domain.Player;

import static it.polimi.group06.activity.helper.Constants.TAG;
import static it.polimi.group06.domain.Constants.FIRSTCARD;
import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.SECONDCARD;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;
import static it.polimi.group06.domain.Constants.THIRDCARD;

/**
 * This class implements the game card activity which provides users to play cards
 * by interacting with all available components in layout of game activity.
 * Moreover this class is connected to activity "activity_game.xml"
 * @author Group6
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveandquit;
    TextView winner;
    public TextView remaining;
    public ImageView briscola_image, humancard, robotcard;
    public ImageView[] humanHand = new ImageView[3];
    public ImageView robotcard1, robotcard2, robotcard3, pile;
    public TextView humanPoints, robotPoints;

    List<String> settingsList;
    String cardbackstring;

    final Handler handler = new Handler();

    boolean cardSetFlag = false;
    int humanChosenCard;

    public Animation robottomiddle, briscolaTaken, showBriscola;

    int cardback, numberoftimesplayerwon, numberoftimesrobotwon, numberofdraws;

    long tStart;
    double elapsedSeconds;
    int[] amountpositionwasplayed = {0, 0, 0};

    public Game game;
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

        /**
         * initializes image views and text views.
         */
        humancard = findViewById(R.id.humanplayed);
        robotcard = findViewById(R.id.robotplayed);

        humanPoints = findViewById(R.id.humanpoints);
        robotPoints = findViewById(R.id.robotpoints);

        robotcard1 = findViewById(R.id.robothand1);
        robotcard2 = findViewById(R.id.robothand2);
        robotcard3 = findViewById(R.id.robothand3);


        remaining = findViewById(R.id.remaining);
        winner = findViewById(R.id.winner);

        saveandquit = findViewById(R.id.savequit_button);

        briscola_image = findViewById(R.id.briscolaimage);
        pile=findViewById(R.id.pile);

        humanHand[FIRSTCARD]=findViewById(R.id.cardzeroimage);
        humanHand[SECONDCARD]=findViewById(R.id.cardoneimage);
        humanHand[THIRDCARD]=findViewById(R.id.cardtwoimage);

        // At the beginning of the game the human player starts
        // but has not chosen his card yet
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
        briscolaTaken = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadebriscola);
        showBriscola = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_briscola);



        getStatisticsFile();
        setSettingsfromFile();

        // Set the back of the card according to the deckSet preference in settings
        final int id = getApplicationContext().getResources().getIdentifier("back" + cardbackstring, "drawable", getApplicationContext().getPackageName());
        robotcard1.setImageResource(id);
        robotcard2.setImageResource(id);
        robotcard3.setImageResource(id);
        pile.setBackgroundResource(id);

        pile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pile.startAnimation(showBriscola);
                remaining.startAnimation(showBriscola);
            }
        });

        for (int position = 0; position < 3; position++)
            humanHand[position].setOnClickListener(this);

        saveandquit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View saveAndQuitButton){
                Log.d(TAG, "Save and Quit button Clicked!");
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
            }
        });

        briscola_image.setImageResource(getCardDrawable(game.getTable().getBriscola()));

        tStart = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTheGame();
    }

    private void startTheGame() {
        handler.post(new UpdateView(this ));
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

        //Detect which card Human clicked
        //Set the humanChosenCard accordingly,
        //will be used next in the game.playerPlaysCard(currentPlayer, currentChoice); method
        switch (v.getId()) {
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
                Log.d(TAG,"What did you click on?");
                break;
        }

        Log.d(TAG,"Human card at pos "+humanChosenCard+" Clicked!");

        // Set cards NOT clickable because the Human has chosen his card
        humanCardsClickable(false);


        // The human clicked on his chosen card, we don't want the first if branch to be executed again
        // as soon as the execution in the playOneRound() method will be resumed.
        // Instead the next if branch will be hit next time, because the Human has just chosen his card.
        cardSetFlag = true;


        // It will enter the playOneRound method we just left after we hit the break;
        // but now we skip to the next if with the humanChosenCard appropriately set
        playOneRound();


        if (!game.gameIsOver())
            playOneRound();     // the next round
        else // Game is over, no more rounds to play!
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endofgame();
                }
            },
            3500);
        }

    }

    void playOneRound() {

        int currentPlayer, currentChoice;

        int delay=0;

        Log.d("debug", "Round " + game.getRound());
        while (!game.roundIsOver()) {

            //It is the Human turn but Human has not chosen his card yet
            if (game.getCurrentPlayer() instanceof Human & !cardSetFlag) {

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


            Card currentCard = game.getCurrentPlayer().getHand().get(currentChoice);


            if(currentPlayer==FIRSTPLAYER) //Human will will play, either as first or as second
                delay=0;
            else //Robot will play
                if(currentPlayer==game.getStartingPlayer()) { //Robot will start the round
                    Log.d(TAG, " ----------- Robot will start the round");
                    delay = 3500;
                }
                else if(currentPlayer!=game.getStartingPlayer()) //Robot will end the round
                    delay=1000;

            Log.d("debug", "Player " + currentPlayer + " Card " + currentCard);

            handler.postDelayed(
                    new LaunchCard(currentPlayer, currentChoice, currentCard, this),
                    delay
            );

            // Update the model
            game.playerPlaysCard(currentPlayer, currentChoice);
        }

            // Prepare the new round
            if (game.roundIsOver()) {
                game.newRound();

            // updateView();
            handler.postDelayed(
                    new UpdateView(this),
                    3000
            );
        }

    }

    public void humanCardsClickable(boolean clickable) {
        if (clickable) {
            humanHand[FIRSTCARD].setClickable(true);
            humanHand[SECONDCARD].setClickable(true);
            humanHand[THIRDCARD].setClickable(true);
        } else {
            humanHand[FIRSTCARD].setClickable(false);
            humanHand[SECONDCARD].setClickable(false);
            humanHand[THIRDCARD].setClickable(false);
        }
    }

    /**
     * Id and type of cards in string will return by given card object this method
     */

    public int getCardDrawable(Card cardatposition) {

        Resources resources = getApplicationContext().getResources();
        String packageName = getApplicationContext().getPackageName();

        switch (cardatposition.toString()) {
            case ("1B"):
                return resources.getIdentifier("bastoni1" + cardbackstring, "drawable", packageName);
            case ("2B"):
                return resources.getIdentifier("bastoni2" + cardbackstring, "drawable", packageName);
            case ("3B"):
                return resources.getIdentifier("bastoni3" + cardbackstring, "drawable", packageName);
            case ("4B"):
                return resources.getIdentifier("bastoni4" + cardbackstring, "drawable", packageName);
            case ("5B"):
                return resources.getIdentifier("bastoni5" + cardbackstring, "drawable", packageName);
            case ("6B"):
                return resources.getIdentifier("bastoni6" + cardbackstring, "drawable", packageName);
            case ("7B"):
                return resources.getIdentifier("bastoni7" + cardbackstring, "drawable", packageName);
            case ("JB"):
                return resources.getIdentifier("bastoni8" + cardbackstring, "drawable", packageName);
            case ("HB"):
                return resources.getIdentifier("bastoni9" + cardbackstring, "drawable", packageName);
            case ("KB"):
                return resources.getIdentifier("bastoni10" + cardbackstring, "drawable", packageName);
            case ("1S"):
                return resources.getIdentifier("spade1" + cardbackstring, "drawable", packageName);
            case ("2S"):
                return resources.getIdentifier("spade2" + cardbackstring, "drawable", packageName);
            case ("3S"):
                return resources.getIdentifier("spade3" + cardbackstring, "drawable", packageName);
            case ("4S"):
                return resources.getIdentifier("spade4" + cardbackstring, "drawable", packageName);
            case ("5S"):
                return resources.getIdentifier("spade5" + cardbackstring, "drawable", packageName);
            case ("6S"):
                return resources.getIdentifier("spade6" + cardbackstring, "drawable", packageName);
            case ("7S"):
                return resources.getIdentifier("spade7" + cardbackstring, "drawable", packageName);
            case ("JS"):
                return resources.getIdentifier("spade8" + cardbackstring, "drawable", packageName);
            case ("HS"):
                return resources.getIdentifier("spade9" + cardbackstring, "drawable", packageName);
            case ("KS"):
                return resources.getIdentifier("spade10" + cardbackstring, "drawable", packageName);
            case ("1C"):
                return resources.getIdentifier("coppe1" + cardbackstring, "drawable", packageName);
            case ("2C"):
                return resources.getIdentifier("coppe2" + cardbackstring, "drawable", packageName);
            case ("3C"):
                return resources.getIdentifier("coppe3" + cardbackstring, "drawable", packageName);
            case ("4C"):
                return resources.getIdentifier("coppe4" + cardbackstring, "drawable", packageName);
            case ("5C"):
                return resources.getIdentifier("coppe5" + cardbackstring, "drawable", packageName);
            case ("6C"):
                return resources.getIdentifier("coppe6" + cardbackstring, "drawable", packageName);
            case ("7C"):
                return resources.getIdentifier("coppe7" + cardbackstring, "drawable", packageName);
            case ("JC"):
                return resources.getIdentifier("coppe8" + cardbackstring, "drawable", packageName);
            case ("HC"):
                return resources.getIdentifier("coppe9" + cardbackstring, "drawable", packageName);
            case ("KC"):
                return resources.getIdentifier("coppe10" + cardbackstring, "drawable", packageName);
            case ("1G"):
                return resources.getIdentifier("denari1" + cardbackstring, "drawable", packageName);
            case ("2G"):
                return resources.getIdentifier("denari2" + cardbackstring, "drawable", packageName);
            case ("3G"):
                return resources.getIdentifier("denari3" + cardbackstring, "drawable", packageName);
            case ("4G"):
                return resources.getIdentifier("denari4" + cardbackstring, "drawable", packageName);
            case ("5G"):
                return resources.getIdentifier("denari5" + cardbackstring, "drawable", packageName);
            case ("6G"):
                return resources.getIdentifier("denari6" + cardbackstring, "drawable", packageName);
            case ("7G"):
                return resources.getIdentifier("denari7" + cardbackstring, "drawable", packageName);
            case ("JG"):
                return resources.getIdentifier("denari8" + cardbackstring, "drawable", packageName);
            case ("HG"):
                return resources.getIdentifier("denari9" + cardbackstring, "drawable", packageName);
            case ("KG"):
                return resources.getIdentifier("denari10" + cardbackstring, "drawable", packageName);
        }
        System.out.println("not in switch");
        return 0;
    }

    /**
     * This method returns the result of game in every round in order to show the winner
     * or the game ends in draw.
     */
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
            Log.d(TAG, stats + "yyyyyy" + statList + "xxxxxxxxxx");
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

    /**
     * Set the background for the back of cards according to
     * the selection of background preference in the Setting activity.
     */
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

            /**
             * Set the color background for the main activity according to
             * the selection of background preference in Setting activity.
             */
            int backgroundcolor = Integer.parseInt(settingsList.get(1));
            switch (backgroundcolor) {
                case (0):
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                    break;
                case (1):
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgreen));
                    break;
                case (2):
                    getWindow().getDecorView().setBackgroundColor(getApplicationContext().getResources().getColor(R.color.orange));
                    break;
                case (3):
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

            /**
             * setRobotCardImages();
             */

            //TODO
            if (game.getTable().getPlayedCardsAmount() == 1) {
                if (game.getStartingPlayer() == 0) {
                    humancard.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0)));
                } else {
                    robotcard.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0)));
                }
            } else if (game.getTable().getPlayedCardsAmount() == 2) {
                if (game.getStartingPlayer() == 0) {
                    humancard.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0)));
                    robotcard.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(1)));
                } else {
                    robotcard.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(0)));
                    humancard.setImageResource(getCardDrawable(game.getTable().getPlayedCards().get(1)));

                }
            }
        }
    }
}

