package io.anuke.wobble;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.entities.Effect;
import io.anuke.ucore.util.Geometry;

public class EffectLoader{
	public static Color player = Color.WHITE;
	
	public static void load(){
		
		Effect.create("heal", 18, e->{
			Draw.color(Color.RED);
			Draw.alpha(e.fract());
			float s = 4f+16*e.ifract();
			Draw.rect("health", e.x, e.y, s, s);
			Draw.reset();
		});
		
		Effect.create("powerup", 18, e->{
			Draw.color(Color.GREEN);
			Draw.alpha(e.fract());
			Draw.spikes(e.x, e.y, e.ifract()*10+3, 2f, 8);
			Draw.reset();
		});
		
		Effect.create("slimeblood", 14, e->{
			Draw.color(player, Color.WHITE, e.ifract());
			Geometry.randVectors(e.id, 5, e.ifract()*40f, (x, y)->{
				Draw.circle(e.x+x, e.y+y, 1f+4*e.fract());
			});
			Draw.reset();
		});
		
		Effect.create("enemydie", 13, e->{
			Draw.thick(2f);
			Draw.color(Color.PURPLE, Color.WHITE, e.ifract());
			Geometry.randVectors(e.id, 10, e.ifract()*55f, (x, y)->{
				Draw.circle(e.x+x, e.y+y, 1f+6*e.fract());
			});
			Draw.reset();
		});
		
		Effect.create("playerdie", 13, e->{
			Draw.thick(2f);
			Draw.color(Color.BLACK, Color.WHITE, e.ifract());
			Geometry.randVectors(e.id, 10, e.ifract()*55f, (x, y)->{
				Draw.circle(e.x+x, e.y+y, 1f+6*e.fract());
			});
			Draw.reset();
		});
		
		Effect.create("sploosh", 10, e->{
			Draw.thickness(1f);
			
			Draw.color(Color.PURPLE, Color.PURPLE, e.ifract());
			Geometry.randVectors(e.id, 4, e.ifract()*30f, (x, y)->{
				Draw.circle(e.x+x, e.y+y, e.ifract()*5f);
			});
			Draw.reset();
		});
		
	}
	
}
