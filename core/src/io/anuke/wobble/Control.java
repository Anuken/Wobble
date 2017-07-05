package io.anuke.wobble;

import static io.anuke.wobble.Vars.*;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import io.anuke.ucore.core.*;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.graphics.Atlas;
import io.anuke.ucore.modules.RendererModule;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Timers;
import io.anuke.wobble.GameState.State;
import io.anuke.wobble.entities.*;
import io.anuke.wobble.weapons.Weapon;
import io.anuke.wobble.world.*;

public class Control extends RendererModule{
	/**room tiles*/
	private Tile[][] tiles;
	private Room room;
	
	private Player player;
	private int kills;
	private int map;
	private boolean highscore;
	private int lastype = 0;
	private float playtime;
	
	//private GifRecorder recorder = new GifRecorder(batch);

	public Control() {
		atlas = new Atlas("wobble.atlas");
		//clearColor = Color.WHITE;
		
		

		cameraScale = 3;
		pixelate();
		//Draw.addSurface("pixel2", 1);

		KeyBinds.defaults(
			"up", Keys.W, 
			"left", Keys.A, 
			"down", Keys.S, 
			"right", Keys.D, 
			"pause", Keys.ESCAPE
		);
		
		for(int i = 0; i < maps.length; i ++){
			Settings.defaults("highscore" + i, 0);
		}

		Settings.loadAll("io.anuke.wobble");

		Entities.initPhysics(0, 0, worldsize*tilesize, worldsize*tilesize);
		Entities.setCollider(tilesize, (x, y)->{
			return !Mathf.inBounds(x, y, tiles) || tiles[x][y].solid();
		});
		
		Sounds.load("jump.wav", "land.wav", "slimebounce.wav", "slimedie.wav", "splat.wav", "land2.wav");
		
		Musics.load("menu.mp3", "west.mp3");
		
		Musics.createTracks("menu", "menu");
		
		Musics.createTracks("world", "west");

		EffectLoader.load();
		
		player = new Player();
	}
	
	public Player getPlayer(){
		return player;
	}

	public void reset(){
		
		player.set(worldsize*tilesize/2, worldsize*tilesize/2);
		player.heal();
		player.weapon = Weapon.shooter;
		player.clearEffects();
		
		setRoom(RoomGenerator.generate(RoomType.spawn));
		kills = 0;
		lastype = 0;
		playtime = 0;
	}
	
	public int getKills(){
		return kills;
	}
	
	public void addKill(){
		int previous = Settings.getInt("highscore"+map);
		kills ++;
		if(kills > previous){
			if(!highscore)
				Vars.ui.showMessage("[yellow]New high-score!");
			highscore = true;
			Settings.putInt("highscore"+map, kills);
			Settings.save();
		}
	}
	
	public boolean isHighScore(){
		return highscore;
	}
	
	public void onDeath(){
		Vars.ui.showDeath();
		GameState.set(State.paused);
	}
	
	public void setRoom(Room room){
		//UCore.log("switching room");
		
		Timers.run(1, ()->{
			Entities.clear();
			for(Entity e : room.entities)
				e.add();
			
			player.add();
		});
		
		Renderer.clearCache();
		
		this.room = room;
		this.tiles = room.tiles;
		
	}

	public Tile tile(int x, int y){
		if(!Mathf.inBounds(x, y, tiles)) return null;
		return tiles[x][y];
	}
	
	private void spawn(){
		if(Timers.get("spawnround", spawntime()*timeScale())){
			getEnemy().set(worldsize*tilesize/2-4, worldsize*tilesize-12).add();
		}
	}
	
	private Enemy getEnemy(){
		if(Mathf.chance(0.3) && !(lastype == 1 && Mathf.chance(0.7)))
			lastype = Mathf.random(3);
		
		return lastype == 0 ? new Enemy() : lastype == 1 ? new SmallEnemy() : lastype == 2 ? new BigEnemy() : new HugeEnemy();
	}
	
	private float spawntime(){
		float scale = 80;
		float wavetime = 1000;
		if(playtime%(wavetime*2) < wavetime+playtime/300f){
			return Mathf.clamp(100-playtime/(scale*4), 60, 10000);
		}
		return Mathf.clamp(320-playtime/scale, 60, 10000);
	}
	
	public float timeScale(){
		if(lastype == 0){
			return 1f;
		}else if(lastype == 1){
			return 0.25f;
		}else if(lastype == 2){
			return 1.2f;
		}else{
			return 3f;
		}
	}
	
	@Override
	public void init(){
		reset();
	}

	@Override
	public void update(){

		if(GameState.is(State.playing)){
			playtime += Mathf.delta();
			
			if(Gdx.app.getType() == ApplicationType.WebGL){
				camera.viewportWidth = Vars.viewWidth/cameraScale;
				camera.viewportHeight = Vars.viewHeight/cameraScale;
			}
			
			setCamera(worldsize * tilesize / 2-tilesize/2, worldsize * tilesize / 2-4);
			if(cameraScale == 3)
				camera.position.add(0, -0.5f, 0);
			
			updateShake();

			if(Inputs.keyUp("pause")){
				GameState.set(State.paused);
				Vars.ui.showPaused();
			}
			
			spawn();
		}else if(GameState.is(State.paused)){
			if(Inputs.keyUp("pause")){
				GameState.set(State.playing);
				Vars.ui.hidePaused();
			}
		}
		
		if(!GameState.is(State.menu)){
			Musics.playTracks("world");
			Entities.update();
			drawDefault();
		}else{
			clearScreen(Color.PURPLE.cpy().mul(0.3f));
			Musics.playTracks("menu");
		}
		
		//recorder.update();
		
		Timers.update();
		Inputs.update();
	}

	@Override
	public void draw(){
		Renderer.renderBackground();
		Entities.draw();
		Renderer.renderForeground();
	}
}
