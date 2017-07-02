package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

public class SmallEnemy extends Enemy{
	
	public SmallEnemy(){
		color = Color.BLUE;
		eyecolor = Color.SCARLET;
		gibcolor = color;
		setMaxHealth(2);
		speed = 0.45f;
		jumpheight = 4.5f;
	}
	
	public void init(){ 
		baseheight = 4f;
		super.init();
	}
}
