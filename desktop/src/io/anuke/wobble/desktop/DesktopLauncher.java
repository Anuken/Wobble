package io.anuke.wobble.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import io.anuke.wobble.Wobble;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Wobble");
		config.setMaximized(true);
		config.setWindowedMode(576, 576);
		//config.useVsync(false);
		new Lwjgl3Application(new Wobble(), config);
	}
}
