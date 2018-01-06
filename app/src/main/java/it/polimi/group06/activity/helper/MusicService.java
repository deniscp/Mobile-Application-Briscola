package it.polimi.group06.activity.helper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import it.polimi.group06.R;


/**
 * Created by Timo Zandonella on 01.01.2018.
 */

public class MusicService extends Service {
    private MediaPlayer background;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        background = MediaPlayer.create(this, R.raw.background);
        background.setLooping(true);
        background.start();

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        background.stop();
    }


}
