package fi.tamk.project;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

// This is AndroidLauncher class that you can find from every LibGDX project.
// You have to do some changes to this one like we have done.

public class AndroidLauncher extends AndroidApplication implements SensorEventListener, StepListener {
	private StepDetector simpleStepDetector;
	private SensorManager sensorManager;
	private Sensor accel;
	private int numSteps;

	// Your main game class
	// You have to have your game class so you can call its methods.
	// Make sure you have named this correctly, so if you main glass is named example
	// MySuperAwesomeGame, then this should look like private MySuperAwesomeGame game;
	private MainGame game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Once again, make sure you have here your own class. MyGdxGame is just the class I use in this example;
		game = new MainGame();

		// Get an instance of the SensorManager
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		simpleStepDetector = new StepDetector();
		simpleStepDetector.registerListener(this);

		numSteps = 0;
		sensorManager.registerListener(AndroidLauncher.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

		// If you want to stop Listener, you can use:
		// sensorManager.unregisterListener(AndroidLauncher.this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			simpleStepDetector.updateAccel(
					event.timestamp, event.values[0], event.values[1], event.values[2]);

		}

	}

	@Override
	public void step(long timeNs) {
		numSteps++;

		// This one calls your method in core, that updates steps so you can use those in LibGDX
		game.receiveSteps(numSteps);
	}

}
