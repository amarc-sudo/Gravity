package com.runespace.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.scoreboard.ScoreBoard;
import com.runespace.game.utils.Constants;

public class Level5 extends Level {

	ScoreBoard scoreArray;
	Vector2 center = new Vector2(0, 0);
	public Level5(GameStateManager gsm, Vector2 gravity) {
		super(gsm, new Vector2(0,0));
		scoreArray = new ScoreBoard();
		scoreArray.load("level3");
		tiledMap = LaunchGame.assetManager.get("maps/MetroidTest.tmx");
		createGround();
		createDead();
		//createTiles(LaunchGame.assetManager.get("maps/Main.tmx", TiledMap.class));
		//this.createTileCondition(Constants.SPHERE_BIT, "dead", true);
		// TODO Auto-generated constructor stub
		this.createPlayer(2500,19200-310);

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
		float G = 15; //modifier of gravity value - you can make it bigger to have stronger gravity

		float distance = player.getBody().getPosition().dst( center );
		float forceValue = G / ((distance) * (distance));
		//System.out.println(distance);
		System.out.println( "body pos y" + player.getBody().getPosition().y);
		System.out.println( "body pos x" + player.getBody().getPosition().x);
		System.out.println( "center pos y" + center.y);
		System.out.println( "center pos x" + center.x);
		Vector2 direction = center.sub( player.getBody().getPosition() );

		player.getBody().applyForce( direction.scl( forceValue ), center, true );

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
