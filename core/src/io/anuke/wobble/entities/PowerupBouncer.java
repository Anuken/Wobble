package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.graphics.Hue;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.powerups.Powerup;

public class PowerupBouncer extends Bouncer{
	private final Powerup type;	
	
	public PowerupBouncer(Powerup type){
		eyecolor = Color.CLEAR;
		color = Color.WHITE;
		this.type = type;
		
		baseheight = 6;
		spring.target = baseheight;
		gravity = 0f;
		jumpspeed = 2f;
	}
	
	@Override
	public void update(){
		vector.set(0, 0);
		if(canJump() && Timers.get(this, "jump", 40)){
		//	jump();
		}
		
		velocity.add(vector);
		
		super.update();
		
		baseheight = 7f + Mathf.sin(Timers.time(), 14f, 1f);
		spring.target = baseheight;
		color = Hue.mix(Color.WHITE, Color.GREEN, Mathf.absin(Timers.time(), 14f, 1f));
		
		//y += Mathf.sin(Timers.time(), 20f, 1f);
	}
	
	@Override
	public void collision(SolidEntity e){
		type.onPickup(this, (Player)e);
		Effects.effect("powerup", this);
		remove();
	}
	
	@Override
	public void draw(){
		super.draw();
		type.draw(this);
		Draw.color();
	}
	
	@Override
	public boolean collides(SolidEntity other){
		return other instanceof Player;
	}
}
