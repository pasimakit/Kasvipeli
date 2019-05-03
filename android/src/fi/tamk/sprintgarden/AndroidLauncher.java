package fi.tamk.sprintgarden;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import fi.tamk.sprintgarden.game.MainGame;

/**
 * Creates the service that tracks the steps
 */
public class AndroidLauncher extends AndroidApplication {

	private MainGame game;
	MyService mService;
	boolean mBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = new MainGame();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);

        Intent intent = new Intent(this, MyService.class);

        if(Build.VERSION.SDK_INT>=26) {
            startForegroundService(intent);
        }else{
            startService(intent);
        }
	}

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            game.setGetSteps(mService);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
