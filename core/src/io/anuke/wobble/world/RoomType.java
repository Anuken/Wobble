package io.anuke.wobble.world;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ObjectMap;

import io.anuke.ucore.util.Mathf;
import io.anuke.wobble.Vars;

public enum RoomType{
	spawn;
	
	public boolean[] reqDirections = null;//{true, false, true, false};
	
	private RoomType(){}
	
	private RoomType(boolean... req){
		this.reqDirections = req;
	}
	
	public void generate(Room room){
		Tile[][] tiles = room.tiles;
		
		Pixmap foremap = null, backmap = null;
		
		if(foremaps.containsKey(this)){
			foremap = foremaps.get(this);
			backmap = backmaps.get(this);
		}else{
			foremap = new Pixmap(Gdx.files.internal("rooms/" + name() + ".png"));
			foremaps.put(this, foremap);
			
			backmap = new Pixmap(Gdx.files.internal("rooms/" + name() + "-back.png"));
			backmaps.put(this, backmap);
		}
		
		for(int x = 0; x < Vars.worldsize; x ++){
			for(int y = 0; y < Vars.worldsize; y ++){
				
				int forec = foremap.getPixel(x, Vars.worldsize-1-y);
				int backc = backmap.getPixel(x, Vars.worldsize-1-y);
				
				Tile tile = tiles[x][y];
				
				Block fore = blockmap.get(forec, Blocks.air);
				Block back = blockmap.get(backc, Blocks.air);
				
				if(fore != Blocks.air)
					tile.block = fore;
				
				tile.back = back;
			}
		}
		
		for(int x = 0; x < Vars.worldsize; x ++){
			for(int y = 0; y < Vars.worldsize; y ++){
				Tile tile = tiles[x][y];
				
				if(tile.block == Blocks.spikes){
					if(solid(room, x+1, y))
						tile.rotation = 2;
					if(solid(room, x-1, y))
						tile.rotation = 0;
					if(solid(room, x, y+1))
						tile.rotation = 3;
					if(solid(room, x, y-1))
						tile.rotation = 1;
					
					tile.back = Blocks.stone2;
				}
			}
		}
	}
	
	private static boolean solid(Room room, int x, int y){
		return !Mathf.inBounds(x, y, room.tiles) || room.tiles[x][y].block.solid;
	}
	
	private static ObjectMap<RoomType, Pixmap> foremaps = new ObjectMap<>();
	private static ObjectMap<RoomType, Pixmap> backmaps = new ObjectMap<>();
	private static ObjectMap<Integer, Block> blockmap = map(
		0x71b362, Blocks.grass,
		0x9a9a9a, Blocks.stone,
		0x767676, Blocks.stone2,
		0xefefef, Blocks.spikes,
		0x8c764a, Blocks.box,
		0xa79064, Blocks.frame,
		0xb0f0f8, Blocks.ice,
		0x8491cf, Blocks.blob,
		0xffdd53, Blocks.blobyellow
	);
	
	private static ObjectMap<Integer, Block> map(Object...objects){
		ObjectMap<Integer, Block> map = new ObjectMap<>();
		
		for(int i = 0; i < objects.length; i += 2){
			int c = (int)objects[i];
			int out = (c << 8) | 0x000000ff;
			map.put(out, (Block)objects[i+1]);
		}
		
		return map;
	}
	
}
