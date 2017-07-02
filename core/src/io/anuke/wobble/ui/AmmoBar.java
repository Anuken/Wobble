package io.anuke.wobble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.scene.Element;
import io.anuke.ucore.util.Spring1D;
import io.anuke.wobble.Vars;
import io.anuke.wobble.weapons.Weapon;

public class AmmoBar extends Element{
	Spring1D spring = new Spring1D(0.1f, 4f);
	
	public void draw(){
		if(Draw.getSurface("pixel") == Draw.currentSurface()) return;
		
		float frac = (float)Vars.control.getPlayer().ammo/Vars.control.getPlayer().weapon.ammo;
		spring.target = frac;
		if(Vars.control.getPlayer().weapon == Weapon.shooter){
			frac = 1f;
			return;
		}
		Draw.color(Color.DARK_GRAY);
		Draw.patch("ammobar", x, y, width, height);
		Draw.color(Color.ORANGE);
		Draw.patch("ammobar", x, y, width*spring.value, height);
		Draw.color();
		
		spring.update(Gdx.graphics.getDeltaTime());
	}
}
