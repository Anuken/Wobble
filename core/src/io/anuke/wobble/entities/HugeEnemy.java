package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

public class HugeEnemy extends Enemy{

	public HugeEnemy(){
		color = Color.PINK;
		eyecolor = Color.SCARLET;
		gibcolor = color;
		setMaxHealth(22);
		speed = 0.23f;
		jumpheight = 2f;
		jumpspacing = 70;
		powerupchance = 0.5f;
	}

	public void init(){
		baseheight = 9;
		hitsize = 14;
		tilehitwidth = 8;
		tilehitheight = 9;
		
		super.init();
	}
}
