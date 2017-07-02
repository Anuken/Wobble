package io.anuke.wobble;

import static io.anuke.wobble.Vars.*;

import com.badlogic.gdx.math.Interpolation;

import io.anuke.ucore.core.Draw;
import io.anuke.ucore.core.DrawContext;
import io.anuke.ucore.function.VisibilityProvider;
import io.anuke.ucore.modules.SceneModule;
import io.anuke.ucore.scene.actions.Actions;
import io.anuke.ucore.scene.builders.*;
import io.anuke.ucore.scene.ui.*;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.wobble.GameState.State;
import io.anuke.wobble.ui.*;

public class UI extends SceneModule{
	VisibilityProvider play = ()->{return GameState.is(State.playing);};
	VisibilityProvider inmenu = ()->{return GameState.is(State.menu);};
	
	KeybindDialog keybind;
	SettingsDialog settings;
	TextDialog about;
	TextDialog tutorial;
	Dialog paused, death;
	boolean donetutorial = false;
	
	@Override
	public void init(){
		DrawContext.font.setUseIntegerPositions(true);
		Dialog.closePadT = 2;
		
		death = new Dialog("You're dead!", "dialog");
		death.shown((()->{
			((Label)death.content().getChildren().first()).setText("[yellow]"+control.getKills() + "[white] enemies killed.");
		}));
		death.content().pad(10);
		death.content().add("insert text here");
		death.content().row();
		Label hiscore = new Label("[green]New high-score!");
		hiscore.setVisible(()->{return control.isHighScore();});
		death.content().add(hiscore);
		death.getButtonTable().pad(6);
		death.getButtonTable().addButton("Back to menu", ()->{
			death.hide();
			GameState.set(State.menu);
		}).size(200, 50);
		
		death.getButtonTable().addButton("Restart", ()->{
			death.hide();
			control.reset();
			GameState.set(State.playing);
		}).size(200, 50);
		
		paused = new Dialog("Paused", "dialog");
		
		about = new TextDialog("About", aboutText).padText(10);
		about.left();
		
		tutorial = new TextDialog("Tutorial", tutorialText).padText(10);
		tutorial.getButtonTable().addButton("OK", ()->{
			tutorial.hide();
			GameState.set(State.playing);
		}).size(140, 50);
		
		keybind = new KeybindDialog();
		settings = new SettingsDialog();
		
		settings.volumePrefs();
		settings.screenshakePref();
		
		build.begin(paused.content());
		
		paused.content().defaults().width(200);
		
		new button("Resume", ()->{
			GameState.set(State.playing);
			paused.hide();
		});
		
		paused.content().row();
		
		new button("Settings", ()->{
			settings.show();
		});
		
		paused.content().row();
		
		new button("Controls", ()->{
			keybind.show();
		});
		
		paused.content().row();
		
		new button("Back to Menu", ()->{
			paused.hide();
			//TODO back to menu code
			GameState.set(State.menu);
		});
		
		build.end();
		
		build.begin(scene);
		
		new table(){{
			
		}}.end();
		
		new table(){{
			String name = "Wobble";
			new label("[DARK_GRAY]"+name).scale(3).padBottom(-120);
			row();
			new label("[SKY]"+name).scale(3);
			row();
			add(new BlobBox(380)).size(470);
			
			visible(inmenu);
		}}.end();
		
		new table(){{
			defaults().width(200);
			
			add(new BlobButton("play", 50, ()->{
				control.reset();
				
				if(!donetutorial){
					tutorial.show();
					donetutorial = true;
				}else{
					GameState.set(State.playing);
				}
			})).size(120).colspan(2);
			
			row();
			
			add(new BlobButton("settings", 60, ()->{
				settings.show();
			})).size(120).pad(20);
			
			add(new BlobButton("controls", 60, ()->{
				keybind.show();
			})).size(120).pad(20);
			
			row();
			
			add(new BlobButton("about", 50, ()->{
				about.show();
			})).size(120).colspan(2);
			
			visible(inmenu);
		}}.end();
		
		new table(){{
			HealthBar bar = new HealthBar();
			atop().aleft();
			
			add(bar).padTop(8).padLeft(8).size(216, 40).left();
			
			row();
			
			add(new AmmoBar()).size(216, 40).padLeft(8).padTop(4).left();
			
			row();
			
			add(new EffectBar()).size(216, 40).padLeft(8).padTop(4).left();
			
			visible(play);
		}}.end();
		
		/*
		new table(){{
			atop().aright();
			new label(()->{
				return Gdx.graphics.getFramesPerSecond() + " FPS";
			});
			visible(play);
		}}.end();
		*/
		build.end();
	}
	
	@Override
	public void update(){
		scene.act();
		if(GameState.is(State.menu)){
			Draw.surface("pixel");
			Draw.end();
			scene.draw();
			Draw.begin();
			Draw.color();
			Draw.flushSurface();
			Draw.end();
			scene.draw();
		}else{
			Draw.surface("pixel");
			Draw.end();
			scene.draw();
			Draw.begin();
			Draw.color();
			Draw.flushSurface();
			Draw.end();
			scene.draw();
		}
	}
	
	public void showMessage(String text){
		Table table = fill();
		table.top();
		table.add(text);
		table.addAction(Actions.sequence(Actions.fadeOut(3f, Interpolation.fade), Actions.removeActor()));
	}
	
	public void showDeath(){
		death.show();
	}
	
	public void hidePaused(){
		paused.hide();
	}
	
	public void showPaused(){
		paused.show();
	}
	
}
