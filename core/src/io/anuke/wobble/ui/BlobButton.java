package io.anuke.wobble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.DrawContext;
import io.anuke.ucore.function.Listenable;
import io.anuke.ucore.graphics.Hue;
import io.anuke.ucore.scene.ui.TextButton;
import io.anuke.ucore.scene.utils.ClickListener;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Spring1D;

public class BlobButton extends TextButton{
	Spring1D spring = new Spring1D(0.1f, 4f);
	ClickListener click = clicked(null);
	Color over = Color.SKY;
	float clicktime;
	float radius = 0f;

	public BlobButton(String text, float radius, Listenable clicked) {
		super(text);
		this.radius = radius;
		this.clicked(clicked);
		spring.target = radius;
		setStyle(new TextButtonStyle(DrawContext.skin.get(TextButtonStyle.class)));
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		if(Draw.getSurface("pixel") == Draw.currentSurface()){
			if(click.isOver()){
				clicktime += Mathf.delta();
				spring.target = radius*1.5f;
			}else{
				spring.target = radius;
				clicktime -= Mathf.delta();
			}
			
			float scl = 10;
			
			clicktime = Mathf.clamp(clicktime, 0, scl);
			
			Draw.color(over, Color.PURPLE, Mathf.clamp(clicktime/scl));
			label.getStyle().fontColor = Hue.mix(over, Color.PURPLE, 1f-Mathf.clamp(clicktime/scl));
			Draw.rect("circle", x + width/2, y + height/2, spring.value*2f, spring.value*2f);
			
			Draw.thick(7f);
			Draw.color(over, Color.PURPLE, 1f-Mathf.clamp(clicktime/scl));
			Draw.polygon(90, x + width/2, y + height/2, spring.value);
			
			Draw.reset();
		}else{
			drawChildren(batch, alpha);
		}
	}
	
	public void act(float delta){
		spring.update(Gdx.graphics.getDeltaTime());
	}

}
