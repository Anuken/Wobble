package io.anuke.wobble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.scene.Element;
import io.anuke.ucore.util.Spring1D;
import io.anuke.wobble.Vars;
import io.anuke.wobble.entities.Player;

public class HealthBar extends Element{
	Spring1D spring = new Spring1D(0.1f, 4f);
	
	public void draw(){
		if(Draw.getSurface("pixel") == Draw.currentSurface()) return;
		
		Player player = Vars.control.getPlayer();
		
		float frac = (float)player.health/player.maxhealth;
		spring.target = frac;
		Draw.color(Color.DARK_GRAY);
		Draw.patch("ammobar", x, y, width, height);
		Draw.color(Color.RED);
		Draw.patch("ammobar", x, y, width*spring.value, height);
		Draw.color();
		
		spring.update(Gdx.graphics.getDeltaTime());
		/*
		int health = Math.round(player.health);
		int maxhealth = Math.round(player.maxhealth);
		
		int size = 8*4;
		float pad = 4;
		
		for(int i = 0; i < maxhealth; i ++){
			String rect = health > i ? "heart" : "emptyheart";
			Draw.rect(rect, x+(size+pad)*i + size/2, y-size/2+height, size, size);
		}
		*/
	}
}
