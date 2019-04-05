package fi.tamk.sprintgarden;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

// This is AndroidLauncher class that you can find from every LibGDX sprintgarden.
// You have to do some changes to this one like we have done.

public class AndroidLauncher extends AndroidApplication {


	// Your main game class
	// You have to have your game class so you can call its methods.
	// Make sure you have named this correctly, so if you main glass is named example
	// MySuperAwesomeGame, then this should look like private MySuperAwesomeGame game;
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
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
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
