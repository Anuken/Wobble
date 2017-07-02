package io.anuke.wobble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.scene.Element;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Spring1D;
import io.anuke.ucore.util.Timers;

public class BlobBox extends Element{
	Spring1D spring = new Spring1D(0.1f, 4f);
	Color over = Color.PURPLE.cpy().mul(0.9f);
	float clicktime;
	float radius = 0f;

	public BlobBox(float radius) {
		this.radius = radius;
		spring.target = radius;
	}

	@Override
	public void draw(Batch batch, float alpha){
		if(Draw.getSurface("pixel") != Draw.currentSurface())
			return;
		
		float stime = 20;
		
		if(Timers.time() % stime < stime/2){
			spring.target = radius;
		}else{
			spring.target = radius*0.99f;
		}

		float scl = 10;

		clicktime = Mathf.clamp(clicktime, 0, scl);

		Draw.color(over, Color.PURPLE, Mathf.clamp(clicktime / scl));
		Draw.rect("circle", x + width / 2, y + height / 2, spring.value * 2.007f, spring.value * 2.007f);

		Draw.thick(7f);
		Draw.color(over, Color.PURPLE, 1f - Mathf.clamp(clicktime / scl));
		Draw.polygon(90, x + width / 2, y + height / 2, spring.value);

		Draw.reset();

	}

	public void act(float delta){
		spring.update(Gdx.graphics.getDeltaTime());
	}
}
