package io.anuke.wobble.world;

import com.badlogic.gdx.utils.Array;

import io.anuke.ucore.entities.Entity;
import io.anuke.wobble.Vars;

public class Room{
	public Array<Entity> entities = new Array<Entity>();
	public Tile[][] tiles = new Tile[Vars.worldsize][Vars.worldsize];
	public RoomType type;
	public boolean[] directions = new boolean[4];
}
