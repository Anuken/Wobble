package io.anuke.wobble.entities;

import static io.anuke.wobble.Vars.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.entities.DestructibleEntity;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Spring1D;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.Vars;
import io.anuke.wobble.world.Blocks;
import io.anuke.wobble.world.Tile;

public class Bouncer extends DestructibleEntity{
	static final int segments = 10;
	
	public Vector2 velocity = new Vector2();
	Color color = Color.valueOf("52baf6"), eyecolor = Color.GOLD;
	Spring1D spring = new Spring1D(0.1f, 4f);
	float xdrag = 0.4f, ydrag = 0.02f, maxvelocity = 6f;
	float speed = 0.6f;
	float jumpspeed = 4.8f;
	float height = 10f;
	float gravity = 1f;
	float bounce = 0f;
	int maxjumps = 1;
	int jumps = 0;
	
	float baseheight = 6f;
	float jumpheight;
	float flopheight;
	float fallheight;
	float side = 0f;
	
	boolean hitground = false;
	Tile lastTile;
	
	public Bouncer(){
		init();
		
		tilehitwidth = 6;
		tilehitheight = 8;
		spring.target = baseheight;
		
		maxhealth = 6;
		heal();
		
	}
	
	@Override
	public void collision(SolidEntity other){
		super.collision(other);
		Effects.sound("slimebounce");
	}
	
	public void init(){
		jumpheight = baseheight+4f;
		flopheight = baseheight/2f;
		fallheight = baseheight+2f;
	}
	
	@Override
	public void update(){
		
		Tile tile = onTile();
		
		if(tile != null && tile.block == Blocks.spikes){
			if(Timers.get(this, "hit", 15))
				damage(1);
			
			Effects.shake(3f, 3f);
			Effects.effect("slimeblood", this);
			vector.set(4f, 0f).setAngle(tile.rotation*90);
			velocity.set(vector);
			move(vector.x/5f, vector.y/5f);
		}
		
		velocity.y += Vars.gravity*delta*gravity;
		
		boolean ground = false;
		
		int seg = 10;
		
		for(int i = 0; i < seg; i ++){
			x += velocity.x/seg*delta;
			onHitSide();
			if(collidesTile()){
				x -= velocity.x/seg*delta;
				velocity.x = -velocity.x*bounce;
				
				break;
			}
		}
		
		for(int i = 0; i < seg; i ++){
			y += velocity.y/seg*delta;
			if(collidesTile()){
				y -= velocity.y/seg*delta;
				velocity.y = 0;
				
				ground = true;
				
				break;
			}
		}
		
		wobbleMove();
		
		if(ground && !hitground){
			spring.target = flopheight;
			
			Timers.run(10, ()->{
				spring.target = baseheight;
			});
			hitground = true;
		}else if(!ground){
			hitground = false;
		}
		
		if(velocity.y < -1.5f){
			spring.target = fallheight;
		}
		
		if(Math.abs(velocity.x) > speed/2f){
			if(velocity.x < 0){
				side = Mathf.lerp(side, -1f, 0.5f*delta);
			}else{
				side = Mathf.lerp(side, 1f, 0.5f*delta);
			}
		}
		
		velocity.scl(1f-xdrag*delta, 1f-ydrag*delta);
		
		if(vector.y < -maxvelocity)
			vector.y = -maxvelocity;
		
		wobbleTiles();
		
		checkTP();
	}
	
	void checkTP(){
		if(y < tilehitheight+1){
			set(worldsize*tilesize/2-4, worldsize*tilesize-12);
		}
	}
	
	void jump(){
		//if(Mathf.chance(0.1))
		//Effects.sound("slimebounce");
		vector.add(0, jumpspeed);
		
		spring.target = jumpheight;
		
		Timers.run(10, ()->{
			spring.target = baseheight;
		});
		
		jumps --;
	}
	
	void wobbleMove(){
		if(Timers.get(this, "wobble", 10) 
				&& Math.abs(velocity.x) > 0.1f && Math.abs(velocity.y) < 0.1f){
			spring.target = baseheight-1f;
			Timers.run(5f, ()->{
				spring.target = baseheight+1;
			});
			
			Timers.run(10f, ()->{
				if(Math.abs(velocity.y) < 0.2f)
				spring.target = baseheight;
			});
		}
	}
	
	void wobbleTiles(){
		Tile tile = Vars.control.tile(Mathf.scl2(x, Vars.tilesize), Mathf.scl2(y-tilehitheight-1, Vars.tilesize));
		
		if(tile != null && lastTile != null && tile.solid() && !lastTile.solid()){
			Effects.sound("land2");
		}
		
		if(tile != lastTile && tile != null){
			lastTile = tile;
			tile.spring.target = Vars.tilesize/2;
			Timers.run(10f, ()->{
				tile.spring.target = Vars.tilesize;
			});
		}
	}
	
	void onHitSide(){
		
	}
	
	boolean canJump(){
		
		y -= 1;
		boolean collides = collidesTile();
		if(collides){
			jumps = maxjumps;
		}
		y += 1;
		return jumps > 0;
	}
	
	Tile onTile(){
		return Vars.control.tile(Mathf.scl2(x, Vars.tilesize), Mathf.scl2(y, Vars.tilesize));
	}
	
	public void draw(){
		spring.update(delta/60f);
		height = spring.value;
		
		Draw.color(color);
		
		Draw.thick(2f);
		
		float width = baseheight*2.2f-height;
		
		drawCurves(width, height);
		
		Draw.thick(1f);
		
		Draw.color(eyecolor);
		Draw.lineAngleCenter(x+width/2.2f*side-1, y+height/2f-1, 90, 3f);
		Draw.lineAngleCenter(x+width/2.2f*side+1, y+height/2f-1, 90, 3f);
		
		Draw.reset();
	}
	
	public void drawCurves(float width, float height){
		for(int i = 0; i < 2; i ++){
			int s = Mathf.sign(i == 0);
			Draw.curve(x, y - height, x + s*width/2, y-height, x + s*width, y-height/2, x + s*width, y, segments);
		}
		
		for(int i = 0; i < 2; i ++){
			int s = Mathf.sign(i == 0);
			Draw.curve(x, y + height, x + s*width/2, y+height, x + s*width, y+height/2, x + s*width, y, segments);
		}
	}
}
