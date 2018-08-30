package com.mygdx.hellinspace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.hellinspace.GameMain;

/**
 * THE ACTUAL LAUNCHER. RUN THIS.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 900;
		config.title = "Hell in Space";

        config.foregroundFPS = 0;
		new LwjglApplication(new GameMain(), config);
	}
}
