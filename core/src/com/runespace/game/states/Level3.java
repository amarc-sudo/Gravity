package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.scoreboard.ScoreBoard;
import com.runespace.game.utils.Constants;

public class Level3 extends Level {

	ScoreBoard scoreArray;
	public Level3(GameStateManager gsm, Vector2 gravity) {
		super(gsm, gravity);
		scoreArray = new ScoreBoard();
		scoreArray.load("level1");

		createGround();
		createDead();
		//createTiles(LaunchGame.assetManager.get("maps/Main.tmx", TiledMap.class));
		//this.createTileCondition(Constants.SPHERE_BIT, "dead", true);
		// TODO Auto-generated constructor stub
		this.createPlayer(400, 1000);
		create();
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		super.update(dt);
		moveCam(5);
		handleInput();
		checkGameOver();
	
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		super.render(sb);
		
		sb.begin();
		player.render(sb, false);
		sb.end();
		//pointLight.setPosition(this.box2dCam.position.x+Constants.VIEWPORT_WIDTH/2/Constants.PIXEL_METER, this.box2dCam.position.y+Constants.VIEWPORT_HEIGHT/2/Constants.PIXEL_METER);
		debug.render(world, box2dCam.combined);
		hud.stage.draw();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	@Override
	public void gameOver() {
		scoreArray.add(this.score);
		scoreArray.save("level1");
		super.gameOver();
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		player.movePlayer(customContactListener,gravityBool);
	}

	public void create() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
