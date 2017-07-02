package io.anuke.wobble.powerups;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.entities.Player;
import io.anuke.wobble.entities.PowerupBouncer;
import io.anuke.wobble.weapons.Weapon;

public enum Powerup{
	health{
		public void draw(PowerupBouncer bouncer){
			Draw.color("red");
			Draw.rect("health", bouncer.x, bouncer.y);
			Draw.color();
		}
		
		public void onPickup(PowerupBouncer bouncer, Player player){
			player.health ++;
			player.clampHealth();
			Effects.effect("heal", bouncer);
		}
	}, 
	fastgun{
		public void draw(PowerupBouncer bouncer){
			Draw.color("orange");
			Draw.lineAngleCenter(bouncer.x, bouncer.y, Timers.time()*3f, 7);
			Draw.color();
		}
		
		public void onPickup(PowerupBouncer bouncer, Player player){
			player.setWeapon(Weapon.fastshooter);
		}
	},
	shotgun{
		public void draw(PowerupBouncer bouncer){
			Draw.color("yellow");
			Draw.lineAngle(bouncer.x, bouncer.y, Timers.time()*3f, 5);
			Draw.lineAngle(bouncer.x, bouncer.y, Timers.time()*3f+40, 5);
			Draw.lineAngle(bouncer.x, bouncer.y, Timers.time()*3f+80, 5);
			Draw.color();
		}
		
		public void onPickup(PowerupBouncer bouncer, Player player){
			player.setWeapon(Weapon.shotgun);
		}
	},
	grenade{
		public void draw(PowerupBouncer bouncer){
			Draw.color("lime");
			Draw.lineAngleCenter(bouncer.x, bouncer.y, Timers.time()*3f, 8);
			Draw.polygon(3, bouncer.x, bouncer.y, 3, Timers.time()*3f-90);
			Draw.color();
		}
		
		public void onPickup(PowerupBouncer bouncer, Player player){
			player.setWeapon(Weapon.grenade);
		}
	},
	speed{
		public void draw(PowerupBouncer bouncer){
			Draw.color("green");
			Draw.polygon(3, bouncer.x, bouncer.y, 3f, Timers.time()*3f);
			Draw.color();
		}
		
		public void onPickup(PowerupBouncer bouncer, Player player){
			player.addEffect(this, 1000);
		}
		
		public void drawUI(float x, float y){
			Draw.thick(3f);
			Draw.color(Color.GREEN);
			Draw.polygon(3, x, y, 20f, Timers.time()*3f);
			Draw.reset();
		}
	},
	doublejump{
		public void draw(PowerupBouncer bouncer){
			Draw.color(Color.SKY);
			Draw.polygon(4, bouncer.x, bouncer.y, 3f, Timers.time()*3f);
			Draw.color();
		}
		
		public void onPickup(PowerupBouncer bouncer, Player player){
			player.addEffect(this, 2000);
		}
		
		public void drawUI(float x, float y){
			Draw.thick(3f);
			Draw.color(Color.SKY);
			Draw.polygon(4, x, y, 20f, Timers.time()*3f);
			Draw.reset();
		}
	};
	
	public void drawUI(float x, float y){}
	public void draw(PowerupBouncer bouncer){}
	public void onPickup(PowerupBouncer bouncer, Player player){}
	
}
