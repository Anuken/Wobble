package io.anuke.wobble.world;

import static io.anuke.wobble.Vars.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.graphics.Caches;
import io.anuke.ucore.graphics.Hue;
import io.anuke.ucore.util.Mathf;
import io.anuke.wobble.Vars;

public class Block{
	static Color toptmp = new Color();
	static Color bottmp = new Color();
	
	public final String name;
	public boolean edge = true;
	public Color topcolor = Color.WHITE;
	public Color botcolor = Color.WHITE;
	public Color tint = Color.WHITE;
	public boolean solid = true;
	
	public Block(String name){
		this.name = name;
	}
	
	public void draw(Tile tile, boolean fore, int x, int y){
		if(tile.tint != null && !tile.solid()){
			tile.ctime += Mathf.delta()*0.005f;
			toptmp = Hue.mix(tile.tint, tint, Mathf.clamp(tile.ctime-1f), toptmp);
			if(tile.ctime > 2f){
				tile.tint = null;
				tile.ctime = 0f;
			}
		}else{
			toptmp.set(tint);
		}

		toptmp.mul(0.25f);
		toptmp.a = 1f;
		Caches.color(toptmp);
		Caches.draw(name, x*tilesize, y*tilesize);
		
		Caches.color();
		
		if(!edge) return;
		
		toptmp.set(topcolor);
		bottmp.set(botcolor);
		
		if(!fore){
			toptmp.mul(0.5f);
			bottmp.mul(0.5f);
		}
		
		toptmp.a = bottmp.a = 1f;
		
		Caches.color(toptmp);
		if(!same(fore, x+1, y)) 
			Caches.draw("edge", x*tilesize, y*tilesize, 0);
		if(!same(fore, x, y+1)) 
			Caches.draw("edge", x*tilesize, y*tilesize, 90);
		
		Caches.color(bottmp);
		if(!same(fore, x-1, y)) 
			Caches.draw("edge", x*tilesize, y*tilesize, 180);
		if(!same(fore, x, y-1)) 
			Caches.draw("edge", x*tilesize, y*tilesize, 270);
		
		Caches.color();
	}
	
	public void drawBack(Tile tile, int x, int y){
		toptmp.set(tint).mul(0.25f);
		toptmp.a = 1f;
		
		Draw.color(toptmp);
		Draw.rect(name, x*tilesize, y*tilesize, Vars.tilesize, Vars.tilesize);
		Draw.color();
	}
	
	public void drawFore(Tile tile, int x, int y){
		float h = tile.spring.value, w = Vars.tilesize*2-h;
		Draw.color(tint);
		if(tile.tint != null){
			tile.ctime += Mathf.delta()*0.005f;
			Draw.color(tile.tint, tint, Mathf.clamp(tile.ctime-1f));
			if(tile.ctime > 2f){
				tile.tint = null;
				tile.ctime = 0f;
			}
		}
		Draw.rect(name, x*tilesize, y*tilesize, w, w);
		
		tile.spring.update(Gdx.graphics.getDeltaTime());
		Draw.color();
	}
	
	boolean same(boolean fore, int x, int y){
		Tile tile = control.tile(x, y);
		return tile != null && ((fore ? tile.block : tile.back) == this);
	}
}
