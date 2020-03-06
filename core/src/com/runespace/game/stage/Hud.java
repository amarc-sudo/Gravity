/**
 * 
 */
package com.runespace.game.stage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author aurel
 *
 */
public class Hud {
	
	Stage stage;
	SpriteBatch sb;
	Viewport viewport;
	public Hud(Viewport viewport, SpriteBatch sb) {
		this.sb = sb;
		this.viewport = viewport;
		stage = new Stage(this.viewport, this.sb);
	}
	
}
