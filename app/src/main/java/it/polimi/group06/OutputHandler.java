package it.polimi.group06;

import android.content.Context;

import java.io.FileOutputStream;

public class OutputHandler {

    public static void writefile(String towrite, String kind, Context ctx) {
        String filename = "";
        FileOutputStream outputStream;
        switch (kind) {
            case ("savedgame"):
                filename = "savedgame";
                break;
            case ("settings"):
                filename = "settings";
                break;
            case ("statistics"):
                filename = "statistics";
                break;
        }
        try {
            outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(towrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}