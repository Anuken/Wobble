package io.anuke.wobble.world;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.util.Spring1D;
import io.anuke.wobble.Vars;

public class Tile{
	public int x, y;
	public Block block = Blocks.air;
	public Block back = Blocks.air;
	public int rotation = 0;
	public Spring1D spring = new Spring1D(0.1f, 4f);
	public Color tint = null;
	public float ctime = 0f;
	
	{
		spring.value = Vars.tilesize;
		spring.target = Vars.tilesize;
	}
	
	public boolean solid(){
		return block.solid;
	}
}
