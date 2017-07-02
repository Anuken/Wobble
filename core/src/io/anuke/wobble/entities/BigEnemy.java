package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

public class BigEnemy extends Enemy{
	
	public BigEnemy(){
		color = Color.ROYAL;
		eyecolor = Color.SCARLET;
		gibcolor = color;
		setMaxHealth(9);
		speed = 0.3f;
		jumpheight = 2f;
		jumpspacing = 60;
		powerupchance = 0.1f;
	}

	public void init(){
		baseheight = 7;
		hitsize = 12;
		
		super.init();
	}
}

