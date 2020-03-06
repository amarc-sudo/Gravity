package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
		this.createPlayer(600,1000);
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
		this.drawFont(sb);
		debug.render(world, box2dCam.combined);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	/*@Override
	public void createTiles(TiledMap tiledMap) {
		// TODO Auto-generated method stub
		super.createTiles(tiledMap);
	}*/
	@Override
	public void createPlayer(int x, int y) {
		// TODO Auto-generated method stub
		super.createPlayer(x, y);
	}
	/*
	@Override
	public void createTiledBodies(TiledMapTileLayer layer, short BITS) {
		// TODO Auto-generated method stub
		super.createTiledBodies(layer, BITS);
	}*/

	@Override
	public void gameOver() {
		scoreArray.add(this.score);
		scoreArray.save("level1");
		super.gameOver();
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		movePlayer();
	}
	@Override
	public void movePlayer() {
		// TODO Auto-generated method stub
		super.movePlayer();
	}

}
