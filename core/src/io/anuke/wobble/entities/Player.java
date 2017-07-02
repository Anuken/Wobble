package io.anuke.wobble.entities;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.DelayedRemovalArray;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Inputs;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.EffectLoader;
import io.anuke.wobble.Vars;
import io.anuke.wobble.powerups.Powerup;
import io.anuke.wobble.powerups.StatusEffect;
import io.anuke.wobble.weapons.Weapon;

public class Player extends Bouncer{
	private float invluntime = 0f;
	private DelayedRemovalArray<StatusEffect> effects = new DelayedRemovalArray<>();
	public Weapon weapon = Weapon.shooter;
	public int ammo = 0;
	
	public Player(){
		color = EffectLoader.player;
		eyecolor = Color.WHITE;
		hitsize = 8;
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
		this.ammo = weapon.ammo;
	}
	
	public Iterable<StatusEffect> getEffects(){
		return effects;
	}
	
	public void clearEffects(){
		effects.clear();
	}
	
	public void addEffect(Powerup type, float duration){
		effects.add(new StatusEffect(type, duration));
	}
	
	public boolean hasEffect(Powerup type){
		for(StatusEffect effect : effects){
			if(effect.type == type)
				return true;
		}
		
		return false;
	}
	
	public void onDeath(){
		Effects.effect("playerdie", this);
		remove();
		Timers.run(20, ()->{
			Vars.control.onDeath();
		});
	}
	
	@Override
	public boolean collides(SolidEntity other){
		return (super.collides(other) && Timers.time() - invluntime > 30f) || other instanceof PowerupBouncer;
	}
	
	@Override
	public void onHit(SolidEntity other){
		invluntime = Timers.time();
		vector.set(x - other.x, y - other.y).setLength(3f);
		velocity.add(vector);
		Effects.effect("slimeblood", this);
		Effects.shake(3f, 3f);
	}
	
	@Override
	public void update(){
		float speed = this.speed*delta;
		
		vector.set(0, 0);
		
		if(Inputs.keyUp("up") && canJump()){
			Effects.sound("jump");
			jump();
		}

		if(Inputs.keyDown("left")){
			vector.add(-speed, 0);
		}

		if(Inputs.keyDown("right")){
			vector.add(speed, 0);
		}
		
		if(Inputs.keyDown("down")){
			vector.add(0, -speed);
		}
		
		velocity.add(vector);
		
		super.update();
		
		if(Inputs.buttonDown(Buttons.LEFT) && Timers.get(this, "reload", weapon.reload)){
			Effects.sound("land");
			weapon.shoot(this);
			ammo --;
			
			if(ammo <= 0 && weapon != Weapon.shooter){
				weapon = Weapon.shooter;
			}
		}
		
		if(hasEffect(Powerup.doublejump)){
			maxjumps = 2;
		}else{
			maxjumps = 1;
		}
		
		if(hasEffect(Powerup.speed)){
			this.speed = 0.9f;
		}else{
			this.speed = 0.6f;
		}
		
		effects.begin();
		
		for(StatusEffect effect : effects){
			effect.duration -= delta;
			if(effect.duration <= 0){
				effects.removeValue(effect, true);
			}
		}
		
		effects.end();
	}
	
	@Override
	public void draw(){
		
		float height = spring.value;
		float width = baseheight*2.2f-height;
		
		Draw.thick(3f);
		Draw.color(Color.BLACK);
		drawCurves(width+2, height+2);
		Draw.color();
		Draw.thick(1f);
		drawCurves(width+4, height+4);
		Draw.reset();
		
		super.draw();
	}
}
