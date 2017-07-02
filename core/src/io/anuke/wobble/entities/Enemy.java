package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Effects;
import io.anuke.ucore.entities.Damager;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.Vars;
import io.anuke.wobble.powerups.Powerup;

public class Enemy extends Bouncer implements Damager{
	boolean direction = Mathf.randBool();
	float powerupchance = 0.05f;
	float jumpspacing = 40;
	Color gibcolor = Color.PURPLE;
	
	public Enemy(){
		color = Color.PURPLE;
		eyecolor = Color.RED;
		speed = 0.4f;
		jumpspeed = 3f;
	}
	
	@Override
	public void update(){
		vector.set(0, 0);
		
		if(canJump() && Timers.get(this, "jump", jumpspacing)){
			jump();
		}
		
		vector.add(Mathf.sign(direction)*speed, 0f);
		
		if(Mathf.chance(0.005))
			direction = !direction;
		
		velocity.add(vector);
		
		super.update();
		
	}
	
	@Override
	public void onDeath(){
		Effects.effect("enemydie", this);
		for(int i = 0; i < 6; i ++){
			BounceGib gib = new BounceGib(x, y, Mathf.random(360f), this, gibcolor);
			gib.speed = Mathf.random(0.2f, 3f);
			gib.add();
		}
		
		if(Mathf.chance(powerupchance)){
			new PowerupBouncer(Mathf.select(Powerup.values())).set(x, y).add();
		}
		
		Effects.sound("slimedie");
		
		Vars.control.addKill();
		remove();
	}
	
	@Override
	public boolean collides(SolidEntity other){
		return other instanceof Player || other instanceof BounceBullet;
	}
	
	@Override
	void onHitSide(){
		direction = !direction;
	}

	@Override
	public int getDamage(){
		return 1;
	}
}
