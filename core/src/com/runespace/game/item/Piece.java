package com.runespace.game.item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Piece extends Item {
    public Piece(World world, float y, float x) {
        super(world, y, x);
        //setRegion();
        velocity = new Vector2(0,0);
    }

    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);


    }

    @Override
    public void use() {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() /2 , body.getPosition().y - getHeight() /2);
        body.setLinearVelocity(velocity);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
