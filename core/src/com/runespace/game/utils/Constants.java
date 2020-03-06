package com.runespace.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	public final static int VIEWPORT_WIDTH = 960+64*2;
	public final static int VIEWPORT_HEIGHT = 512+64*2;

	public final static int WINDOW_WIDTH = 1280;
	public final static int WINDOW_HEIGHT = 720;
	
	public final static String GAME_TITLE = "Gravity";
	
	public final static float PIXEL_METER = 100;
	
	public final static float GRAVITY = -9.8f;
	
	//bit filtering
	public final static short PLARTFORM_BIT = 2;
	public final static short BOX_BIT = 4;
	public final static short SPHERE_BIT = 8;
	
	public final static short SPEED = 20;
	public final static short JUMP_FORCE = 400;
	
	
	public final static int WIDTH_PLAYER = 72;
	public final static int HEIGHT_PLAYER = 97;
	
	public final static Vector2 GRAVITY_WORLD = new Vector2(0, GRAVITY);
}
