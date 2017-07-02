package io.anuke.wobble.world;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.graphics.Caches;
import io.anuke.wobble.Vars;

public class Blocks{
	public static final Block
	
	air = new Block("air"){
		{
			solid = false;
		}
		@Override
		public void drawFore(Tile tile, int x, int y){}
		@Override
		public void drawBack(Tile tile, int x, int y){}
		@Override
		public void draw(Tile tile, boolean fore, int x, int y){}
	},
	grass = new Block("grass"){{
		topcolor = Color.valueOf("84c175");
		botcolor = Color.valueOf("629b54");
	}},
	stone = new Block("stone"){{
		topcolor = Color.valueOf("959a94");
		botcolor = Color.valueOf("6e726d");
	}},
	stone2 = new Block("stone2"){{
		topcolor = Color.valueOf("858585");
		botcolor = Color.valueOf("565a55"); //xdddddd
		//edge = false;
	}},
	box = new Block("box"){{
		edge = false;
	}},
	blob = new Block("blob-white"){{
		edge = false;
		tint = Color.valueOf("8491cf");
	}},
	blobyellow = new Block("blob-white"){{
		edge = false;
		tint = Color.valueOf("ffdd53");
	}},
	frame = new Block("frame"){{
		edge = false;
	}},
	spikes = new Block("spike"){
		{
			solid = false;
		}
		public void draw(Tile tile, boolean fore, int x, int y){
			Caches.draw(name, x*Vars.tilesize, y*Vars.tilesize, 90*(tile.rotation-1));
		}
	},
	ice = new Block("growth"){{
		topcolor = Color.valueOf("c1e4e7");
		botcolor = Color.valueOf("7cb8bd");
	}};
}
