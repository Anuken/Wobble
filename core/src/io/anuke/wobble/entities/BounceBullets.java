package io.anuke.wobble.entities;

import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.Effects;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Mathf;

public class BounceBullets{
	
	public static class BlueBullet extends BounceBullet{

		public BlueBullet(float x, float y, float angle, Entity owner) {
			super(x, y, angle, owner);
			paintcolor = color.cpy();
			speed = 4f;
			baseheight = 2f;
			color.mul(0.5f);
			color.a = 1f;
			damage = 2;
		}

	}
	
	public static class FastBullet extends BounceBullet{

		public FastBullet(float x, float y, float angle, Entity owner) {
			super(x, y, angle, owner);
			paintcolor = Color.ORANGE;
			color = Color.ORANGE.cpy().mul(0.8f);
			speed = 4.5f;
			baseheight = 2f;
			color.a = 1f;
			damage = 1;
		}

	}
	
	public static class ShotBullet extends BounceBullet{

		public ShotBullet(float x, float y, float angle, Entity owner) {
			super(x, y, angle, owner);
			paintcolor = Color.YELLOW;
			color = Color.YELLOW.cpy().mul(0.8f);
			speed = 4f;
			baseheight = 2f;
			color.a = 1f;
			damage = 1;
		}

	}
	
	public static class ShotGrenade extends BounceBullet{

		public ShotGrenade(float x, float y, float angle, Entity owner) {
			super(x, y, angle, owner);
			paintcolor = Color.LIME;
			color = paintcolor.cpy().mul(0.8f);
			speed = 2.5f;
			baseheight = 4f;
			color.a = 1f;
			damage = 6;
		}
		
		@Override
		public void collision(SolidEntity other){
			super.collision(other);
			despawned();
		}
		
		void despawned(){
			Effects.sound("splat");
			for(int i = 0; i < 8; i ++){
				new ShotGrenadeBit(x, y, Mathf.random(360f), owner).add();
			}
		}
	}
	
	public static class ShotGrenadeBit extends BounceBullet{

		public ShotGrenadeBit(float x, float y, float angle, Entity owner) {
			super(x, y, angle, owner);
			paintcolor = Color.LIME;
			color = paintcolor.cpy().mul(0.8f);
			speed = 4f;
			baseheight = 1f;
			color.a = 1f;
			damage = 1;
			lifetime = 70f;
		}

	}
}
