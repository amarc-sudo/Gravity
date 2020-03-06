package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.LaunchGame;
import com.runespace.game.entities.Player;
import com.runespace.game.handlers.CustomContactListener;
import com.runespace.game.handlers.CustomInputHandling;
import com.runespace.game.handlers.CustomInputProcessor;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.utils.Constants;

public abstract class Level extends GameState {

	//body
	protected Body body;
	//score
	private BitmapFont font;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private FreeTypeFontGenerator generator;
	protected int score;
	
	//box2d elements
	protected World world;
	protected BodyDef bdef;
	protected FixtureDef fdef;
	protected OrthographicCamera box2dCam;
	protected Box2DDebugRenderer debug;
	
	//customcontactlistener
	protected CustomContactListener customContactListener;
	
	

	//tiled elements
	protected TiledMap tiledMap;
	protected OrthogonalTiledMapRenderer tmr;
	protected float tileSize;
	protected TiledMapTileLayer layer;
	
	//player elements
	protected Player player;
	protected Body boxPlayer;
	
	//boolean for gravity and backwalk
	protected Boolean backward = false;
	protected Boolean gravityBool = false;
	
	protected Texture background;
	
	//timer for gravity change
	protected int time = 0;
	
	//Music
	Music music;
	public Level(GameStateManager gsm, Vector2 gravity) {
		super(gsm);

	
		//
		tiledMap = LaunchGame.assetManager.get("maps/Main.tmx", TiledMap.class);
		//setup background;
		background = LaunchGame.assetManager.get("background/aled.png", Texture.class);

		//setup Box2d elements
		world = new World(gravity, true);
		bdef = new BodyDef();
		fdef = new FixtureDef();
		box2dCam = new OrthographicCamera();
		box2dCam.setToOrtho(false, Constants.VIEWPORT_WIDTH/Constants.PIXEL_METER,Constants.VIEWPORT_HEIGHT/Constants.PIXEL_METER);
		debug = new Box2DDebugRenderer();

		//set inputprocessor


		//set contactlistener
		customContactListener = new CustomContactListener();
		world.setContactListener(customContactListener);
		
		//set font
		this.createFont();
		debug.setDrawBodies(false);
		//setup camera
		cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		
	}

	public void update(float dt) {
		//update World
		world.step(1/60f, 6, 2);
	}
	
	public void render(SpriteBatch sb) {
		
		

		tmr.setView(cam);
		tmr.render();

		sb.setProjectionMatrix(cam.combined);
	}
	
	@Override
	public void dispose() {


		tmr.dispose();
		tiledMap.dispose();

	}

	@Override
	public void resize(int width, int height) {

	}

	//Map set
	/*
	public void createTiles(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);

		TiledMapTileLayer layer = (TiledMapTileLayer)this.tiledMap.getLayers().get("ground");
		tileSize = layer.getTileHeight();
		createTiledBodies(layer, Constants.PLARTFORM_BIT);
	}

	public void createTiledBodies(TiledMapTileLayer layer, short BITS){

		for(int row = 0 ; row < layer.getHeight() ; row++) {
			for(int col = 0 ; col < layer.getWidth() ;col++) {
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);

				if(cell == null)continue;
				if(cell.getTile()==null)continue;

				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set((col+0.5f)*tileSize/Constants.PIXEL_METER,(row+0.5f)*tileSize/Constants.PIXEL_METER);
				ChainShape chainShape = new ChainShape();
				Vector2[] vectors= new Vector2[5];

				vectors[0] = new Vector2(-tileSize/2/Constants.PIXEL_METER, -tileSize/2/Constants.PIXEL_METER);
				vectors[1] = new Vector2(-tileSize/2/Constants.PIXEL_METER, tileSize/2/Constants.PIXEL_METER);
				vectors[2] = new Vector2(tileSize/2/Constants.PIXEL_METER, tileSize/2/Constants.PIXEL_METER);
				vectors[3] = new Vector2(tileSize/2/Constants.PIXEL_METER, -tileSize/2/Constants.PIXEL_METER);
				vectors[4] = new Vector2(-tileSize/2/Constants.PIXEL_METER, -tileSize/2/Constants.PIXEL_METER);

				chainShape.createChain(vectors);
				fdef.shape = chainShape;
				fdef.filter.categoryBits = Constants.PLARTFORM_BIT;
				fdef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;

				world.createBody(bdef).createFixture(fdef).setUserData("ground");
			}
		}
	}*/
	
	public void createTileCondition(short BIT, String type, Boolean fixture){
		
		layer = (TiledMapTileLayer)this.tiledMap.getLayers().get(type);
		
		tileSize = layer.getTileHeight();
		for(int row = 0 ; row < layer.getHeight() ; row++) {
			for(int col = 0 ; col < layer.getWidth() ;col++) {
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);
						
				if(cell == null)continue;
				if(cell.getTile()==null)continue;
				
				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set((col+0.5f)*tileSize/Constants.PIXEL_METER,(row+0.5f)*tileSize/Constants.PIXEL_METER);
				ChainShape chainShape = new ChainShape();
				Vector2[] vectors= new Vector2[5];
				if(type.equals("deadInversed")) {
					vectors[0] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 12 / Constants.PIXEL_METER);
					vectors[1] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[2] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[3] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, -tileSize / 12 / Constants.PIXEL_METER);
					vectors[4] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 12 / Constants.PIXEL_METER);
				}
				else if(type.equals("dead")) {
					vectors[0] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[1] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, tileSize / 12 / Constants.PIXEL_METER);
					vectors[2] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, tileSize / 12 / Constants.PIXEL_METER);
					vectors[3] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[4] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
				}
				else{
					vectors[0] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[1] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[2] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[3] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[4] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
				}
				chainShape.createChain(vectors);
				fdef.shape = chainShape;
				fdef.filter.categoryBits = Constants.PLARTFORM_BIT;
				fdef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
				if(fixture)
					fdef.isSensor = true;
				world.createBody(bdef).createFixture(fdef).setUserData(type);
				if(fixture)
					fdef.isSensor = false;
			}
		}
	}
	
	public void createPlayer(int x, int y) {
		//Body Def
		PolygonShape pshape = new PolygonShape();
		/////////////BOX/////////////
		
		//Body Def
		bdef.position.set(x/Constants.PIXEL_METER, y/Constants.PIXEL_METER);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		//Create Body
		boxPlayer = world.createBody(bdef);
		
		
		//Polygon shape
		pshape.setAsBox(Constants.WIDTH_PLAYER/4/Constants.PIXEL_METER, Constants.HEIGHT_PLAYER/3/Constants.PIXEL_METER);
		
		fdef.shape = pshape;
		fdef.filter.categoryBits = Constants.BOX_BIT;
		fdef.filter.maskBits = Constants.PLARTFORM_BIT | Constants.SPHERE_BIT;
		
		//create Fixture
		boxPlayer.createFixture(fdef).setUserData("box");

		player = new Player(boxPlayer);
		boxPlayer.setUserData(player);

		//////Create foot sensor///////
		pshape.setAsBox(Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER, 
				new Vector2(0, -Constants.WIDTH_PLAYER/3/Constants.PIXEL_METER-1/Constants.PIXEL_METER), 0);
		fdef.shape = pshape;
		fdef.isSensor = true;
		boxPlayer.createFixture(fdef).setUserData("foot");
		fdef.isSensor = false;
		//////Create head sensor///////
		pshape.setAsBox(Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER, 
				new Vector2(0, Constants.WIDTH_PLAYER/3/Constants.PIXEL_METER+1/Constants.PIXEL_METER), 0);
		fdef.shape = pshape;
		fdef.isSensor = true;
		boxPlayer.createFixture(fdef).setUserData("head");
		fdef.isSensor = false;
		///////END BOX/////////////////
	}
	
	public void movePlayer() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !gravityBool ) {
			 if(customContactListener.isOnGround())
				boxPlayer.applyForceToCenter(0, 400, true);
			 gsm.pause();
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			if(customContactListener.isOnHead())
				boxPlayer.applyForceToCenter(0, -400, true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && boxPlayer.getLinearVelocity().x > -3.00f) {
			boxPlayer.applyLinearImpulse( new Vector2(-0.15f,0), boxPlayer.getWorldCenter(), true);

			if(!backward) {
				player.reverseW();
				backward = true;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && boxPlayer.getLinearVelocity().x < 3.00f) {
			boxPlayer.applyLinearImpulse( new Vector2(0.15f,0), boxPlayer.getWorldCenter(), true);
			if(backward) {
				player.reverseW();
				backward = false;
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.I) ) {
			debug.setDrawBodies(false);
		}
	}
	
	public void moveCam(int speedCam) {
		/*
		this.box2dCam.position.x += speedCam/Constants.PIXEL_METER;
		this.box2dCam.position.y = player.getPosition().y;
		this.box2dCam.update();
		this.cam.position.x += speedCam;
		this.cam.position.y = player.getPosition().y*Constants.PIXEL_METER;
		this.cam.update();*/
		this.box2dCam.position.x = player.getPosition().x;
		this.box2dCam.position.y = player.getPosition().y;
		this.box2dCam.update();
		this.cam.position.x = player.getPosition().x*Constants.PIXEL_METER;
		this.cam.position.y = player.getPosition().y*Constants.PIXEL_METER;
		this.cam.update();
		
	}

	public void checkGameOver() {
		if(/*player.getPosition().x < ((box2dCam.position.x)-box2dCam.viewportWidth/2)-Constants.WIDTH_PLAYER/Constants.PIXEL_METER ||*/ customContactListener.isDead()) {
			gameOver();
			for(int i = 0 ; i < 10000 ; i++);
		}
	}
	
	public void gameOver() {

		gsm.set(new MainMenue2(gsm, true));
	}
	public void gravityChange() {
		if(customContactListener.isSensorG() && !gravityBool && time >= 50) {
			world.setGravity(new Vector2(0, -Constants.GRAVITY));
			gravityBool = true;
			time = 0;
		}
		time++;
		if(customContactListener.isSensorG() && gravityBool && time >= 50) {
			world.setGravity(new Vector2(0, Constants.GRAVITY));
			gravityBool = false;
			time = 0;
		}
	}
	
	public void createFont() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);
	}
	
	public void drawFont(SpriteBatch sb) {
		sb.begin();
		if((!gravityBool && !customContactListener.isOnGround()) || (gravityBool && !this.customContactListener.isOnHead()))
			score+=1;
		if((!gravityBool && customContactListener.isOnGround()) || (gravityBool && this.customContactListener.isOnHead()))
			score-=1;
		font.draw(sb, String.valueOf(score),  cam.position.x + Constants.VIEWPORT_WIDTH/2-100, 
				cam.position.y+ Constants.VIEWPORT_HEIGHT/2);
		sb.end();
	}

	public void createGround(){

		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		for(MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
			fixtureDef.shape = shape;
			fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
			fixtureDef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
			body.createFixture(fixtureDef).setUserData("ground");
		}
	}
	public void createDead(){

		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
			fixtureDef.shape = shape;
			fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
			fixtureDef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef).setUserData("dead");
			fixtureDef.isSensor = false;
		}
	}
	public void createSensorG(){

		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		for(MapObject object : tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
			fixtureDef.shape = shape;
			fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
			fixtureDef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef).setUserData("sensorG");
			fixtureDef.isSensor = false;
		}
	}
}
