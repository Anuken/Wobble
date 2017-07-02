package io.anuke.wobble;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.graphics.Cache;
import io.anuke.wobble.world.Tile;

public class Renderer{
	static Color light = Color.valueOf("5691c3aa"), dark = Color.valueOf("23387caa");
	static Cache foreground, background;

	public static void renderBackground(){
		/*
		Draw.end();
		
		
		if(foreground == null){
			Caches.begin();
			
			for(int x = 0; x < Vars.worldsize; x++){
				for(int y = 0; y < Vars.worldsize; y++){
					Caches.color(Color.DARK_GRAY);
					Tile tile = Vars.control.tile(x, y);
					tile.back.draw(tile, false, x, y);
				}
			}
			Caches.color();
			foreground = Caches.end();
		}

		foreground.render();
		
		Draw.begin();
		*/
		
		for(int x = 0; x < Vars.worldsize; x++){
			for(int y = 0; y < Vars.worldsize; y++){
				Tile tile = Vars.control.tile(x, y);
				tile.back.drawBack(tile, x, y);
			}
		}
	}

	public static void renderForeground(){
		/*
		Draw.end();

		if(background == null){
			Caches.begin();
			for(int x = 0; x < Vars.worldsize; x++){
				for(int y = 0; y < Vars.worldsize; y++){
					Tile tile = Vars.control.tile(x, y);
					tile.block.draw(tile, true, x, y);
				}
			}
			Caches.color();
			background = Caches.end();
		}

		background.render();
		
		Draw.begin();
		*/
		
		for(int x = 0; x < Vars.worldsize; x++){
			for(int y = 0; y < Vars.worldsize; y++){
				Tile tile = Vars.control.tile(x, y);
				tile.block.drawFore(tile, x, y);
			}
		}
	}

	public static void clearCache(){
		if(foreground == null || background == null) return;
		foreground.dispose();
		background.dispose();
		foreground = background = null;
	}
}
