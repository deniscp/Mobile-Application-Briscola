package it.polimi.group06.activity.helper;

/**
 * Created by denis on 02/01/18.
 */

public class Constants {
    public static final String TAG = "debug";
    public static final int DELAY1= 0; //Human will play, either as first or as second
    public static final int DELAY2= 3500; //Robot will play, Robot will start the round (DELAY2 >= DELAY4)
    public static final int DELAY3= 600; //Robot will play, Robot will end the round    (DELAY3 >= humancard animation duration)
    public static final int DELAY4= 1000; //The view will be updated                    (DELAY4 >= DELAY3+robotcard animation duration)
    public static final int DELAY5= 1000; //The end of game notification will be displayed
}
