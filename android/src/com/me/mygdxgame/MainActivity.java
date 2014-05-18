package com.me.mygdxgame;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.tests.GamepadTest;
import com.me.mygdxgame.core.Game1Screen;
import com.me.mygdxgame.core.Platform;
import com.me.mygdxgame.core.SensorUtil;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
//        cfg.useGL20 = false;
        cfg.useGL20 = true;
         
//        initialize(new MyGdxGame(), cfg);

//        GamepadTest game = new GamepadTest();
//        initialize(game, cfg);
        
        //for detecting BlueStacks
        SensorUtil.setSensors(getSensorList());

        Game1 game = new Game1();
        if(SensorUtil.getSensorStrings().toLowerCase().indexOf("bluestacks") > -1) {
    		game.setBackKeyAvailable(false);	//until bluestacks fix their back key issue (it wasn't working as at Beta version 2/16/2013)
    		//just for fun
    		Game1.setEdition("Bucit Baby Edition");
        } else {
        	game.setBackKeyAvailable(true);
        }
		game.setPaltformType(Platform.ANDROID);
        initialize(game, cfg);

        //initialize(new PhysicsDemo(), cfg);
        //handleAndroidScreenDensity();		//does not work, keep for debugging
    }
    
    /*
     * http://gamedev.stackexchange.com/questions/24638/resolution-independence-in-libgdx
     */
    private void handleAndroidScreenDensity() {
    	DisplayMetrics metrics = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	float scaledDensity = metrics.scaledDensity;
    	Game1Screen.setNativeScaledDensity(scaledDensity);
    }
    
	/**
	 * http://www.mobiledevmag.com/2011/07/using-available-sensors-in-the-android-platform-current-limitations-and-expected-improvements%E2%80%A8/
	 */
	public List<Sensor> getSensorList() {
		SensorManager myManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensorList = myManager.getSensorList(Sensor.TYPE_ALL);
//		StringBuilder sensorString = new StringBuilder("Sensors:\n");
//		for(int i=0; i<sensorList.size(); i++) {
//		    sensorString.append(sensorList.get(i).getName()).append(", \n");
//		}
		return sensorList;
	}
    
}