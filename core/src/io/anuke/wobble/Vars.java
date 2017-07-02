package io.anuke.wobble;

public class Vars{
	public static Control control;
	public static UI ui;
	
	public static final int worldsize = 36;
	public static final int tilesize = 8;
	
	public static final float gravity = -0.18f;
	public static final String[] maps = {"spawn"};
	
	public static String[] tutorialText = {
		"[yellow]Controls:",
		"Use [yellow]WASD[] to move, hold \n[yellow]left-mouse[] to shoot.",
		"Powerups have a [green]green circle[]. \nSome give weapons,",
		"some give unique [royal]status effects[], \nwhich are displayed",
		"in the top left corner.",
		"[sky]Blue[] squares give \nyou double-jump",
		"[green]Green[] triangles give you speed.",
		"Ammo for weapons is displayed \nin the [orange]orange bar.[]",
		"[orange]Enemy spawn rate \nincreases with time."
	};
	
	public static String[] aboutText = {
		"Made by [crimson]Anuke[] for the GDL July Jam.",
		"",
		"Assets used:",
		"- [lime]freesound.org [] for sound effects",
		"- [yellow]freemusicarchive.org [] for music"
	};
}
