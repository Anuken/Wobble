package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.entities.SolidEntity;

public class BounceGib extends BounceBullet{

	public BounceGib(float x, float y, float angle, Entity owner, Color color) {
		super(x, y, angle, owner);
		paintcolor = color;
		this.color = color.cpy().mul(0.4f);
		this.color.a = 1f;
		baseheight = 2f;
		lifetime = 100f;
	}
	
	@Override
	public boolean collides(SolidEntity other){
		return false;
	}

}
