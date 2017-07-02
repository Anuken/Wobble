package io.anuke.wobble.world;

import static io.anuke.wobble.Vars.worldsize;

public class RoomGenerator{
	
	public static Room generate(RoomType type){
		
		Room room = new Room();
		
		room.type = type;
		
		Tile[][] tiles = room.tiles;
		
		for(int x = 0; x < worldsize; x++){
			for(int y = 0; y < worldsize; y++){
				tiles[x][y] = new Tile();
			}
		}
		
		for(int x = 0; x < worldsize; x++){
			for(int y = 0; y < worldsize; y++){
				Block back = Blocks.air, fore = Blocks.air;
				
				tiles[x][y].back = back;
				tiles[x][y].block = fore;
			}
		}
		
		room.type.generate(room);
		
		return room;
	}
}
