package io.anuke.wobble.ui;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.scene.Element;
import io.anuke.wobble.Vars;
import io.anuke.wobble.powerups.StatusEffect;

public class EffectBar extends Element{
	
	public void draw(){
		if(Draw.getSurface("pixel") != Draw.currentSurface()) return;
		
		Iterable<StatusEffect> effects = Vars.control.getPlayer().getEffects();
		
		float spacing = 40;
		int i = 0;
		for(StatusEffect e : effects){
			e.type.drawUI(x+(i++)*spacing+spacing/2, y+height/2);
		}
	}
}
