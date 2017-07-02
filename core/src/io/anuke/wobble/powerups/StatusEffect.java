package io.anuke.wobble.powerups;

public class StatusEffect{
	public float duration;
	public Powerup type;
	
	public StatusEffect(Powerup type, float duration){
		this.duration = duration;
		this.type = type;
	}
}
