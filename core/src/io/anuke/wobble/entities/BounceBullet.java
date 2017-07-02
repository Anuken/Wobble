package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Effects;
import io.anuke.ucore.entities.Damager;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.Vars;
import io.anuke.wobble.world.Tile;

public abstract class BounceBullet extends Bouncer implements Damager{
	public int damage = 2;
	public float speed = 2f;
	public Entity owner;
	public float life = 0f;
	public float lifetime = 200f;
	public String effect = "sploosh";
	public Color paintcolor;
	public static int[][] directions = {};
	
	public BounceBullet(float x, float y, float angle, Entity owner){
		set(x,y);
		eyecolor = Color.CLEAR;
		this.owner = owner;
		xdrag = 0.01f;
		ydrag = 0.01f;
		gravity = 0.4f;
		velocity.set(1f, 0f).setAngle(angle);
		maxvelocity = 10f;
		bounce = 0.9f;
		tilehitwidth = 4;
		tilehitheight = 4;
	}
	
	void checkTP(){
		if(y < tilehitheight+1){
			remove();
		}
	}
	
	public void added(){
		velocity.setLength(speed);
		fallheight = baseheight+1f;
		flopheight = baseheight-1f;
		jumpheight = baseheight+2f;
		spring.target = baseheight;
	}
	
	public void update(){
		if(paintcolor != null){
			Tile tile = Vars.control.tile(Mathf.scl2(x, Vars.tilesize), Mathf.scl2(y-tilehitheight-1, Vars.tilesize));
			
			if(tile != null && tile.solid()){
				tile.tint = paintcolor.cpy();
				tile.ctime = 0f;
			}
		}
		
		life += delta;
		
		if(life > lifetime){
			
			Timers.runFor(10f, ()->{
				baseheight -= delta/14f;
				spring.value -= delta/14f;
				spring.target = spring.value;
			}, ()->{
				remove();
				despawned();
			});
		}
		
		vector.set(0, 0);
		if(canJump() && life > 5f && jumpspeed > 0.2f){
			jumpspeed /=2f;
			jump();
		}
		
		velocity.add(vector);
		super.update();
	}
	
	void wobbleTiles(){}
	void despawned(){}
	
	@Override
	public void collision(SolidEntity other){
		Effects.effect(effect, this);
		remove();
	}
	
	@Override
	public boolean collides(SolidEntity other){
		return other instanceof Enemy;
	}

	@Override
	public int getDamage(){
		return damage;
	}
}
