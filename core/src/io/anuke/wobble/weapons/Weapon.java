package io.anuke.wobble.weapons;

import io.anuke.ucore.core.Effects;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Mathf;
import io.anuke.wobble.entities.BounceBullets.*;
import io.anuke.wobble.entities.Player;

public enum Weapon{
	shooter{
		{
			reload = 16;
		}
		public void shoot(Player player){
			new BlueBullet(player.x, player.y, angle(player), player).add();
		}
	},
	fastshooter{
		{
			reload = 7;
		}
		public void shoot(Player player){
			new FastBullet(player.x, player.y, angle(player), player).add();
		}
	},
	shotgun{
		{
			reload = 25;
			ammo = 50;
		}
		public void shoot(Player player){
			Effects.shake(3f, 3f);
			
			for(int i = 0; i < 5; i ++){
				float angle = angle(player) + Mathf.range(15);
				new ShotBullet(player.x, player.y, angle, player).add();
				//b.velocity.scl(Mathf.range(0.9f, 1.1f));
			}
		}
	},
	grenade{
		{
			reload = 30;
			ammo = 20;
		}
		public void shoot(Player player){
			Effects.shake(3f, 3f);
			new ShotGrenade(player.x, player.y, angle(player) + Mathf.range(15), player).add();
			
		}
	};
	public float reload = 10;
	public int ammo = 100;
	
	public void shoot(Player player){
		
	}
	
	public float angle(Player player){
		return Angles.mouseAngle(player.x, player.y);
	}
}
