package io.anuke.wobble;

import io.anuke.ucore.modules.Core;

public class Wobble extends Core {
	
	@Override
	public void init(){
		add(Vars.control = new Control());
		add(Vars.ui = new UI());
	}
	
}
