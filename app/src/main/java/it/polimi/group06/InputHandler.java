package it.polimi.group06;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InputHandler {

    public static String getStringfromFile(String kind, Context ctx){
        FileInputStream fis;
        int n;
        StringBuffer fileContent = new StringBuffer("");
        try {
            fis = ctx.openFileInput(kind);

            byte[] buffer = new byte[1024];

            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Problem");
        }
        return fileContent.toString();
    }
}
