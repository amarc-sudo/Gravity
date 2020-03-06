package com.runespace.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.runespace.game.LaunchGame;

public class CustomContactListener implements ContactListener {
	
	private int footContacts = 0;
	private int sensorGContacts = 0;
	private int headContacts = 0;
	private int dead = 0;

	//contact commence
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		
		if(fA.getUserData() != null && fA.getUserData().equals("foot")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			footContacts++;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("foot")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			footContacts++;
		}
		if(fA.getUserData()!= null && fA.getUserData().equals("sensorG")) {
			sensorGContacts++;
			
		}
		
		if(fB.getUserData()!= null && fB.getUserData().equals("sensorG")) {
			sensorGContacts++;
			
		}
		if(fA.getUserData() != null && fA.getUserData().equals("head")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			headContacts++;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("head")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			headContacts++;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("deadInversed")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			dead++;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("deadInversed")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			dead++;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("dead")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			dead++;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("dead")) {
			System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
			dead++;
		}

		//System.out.println(fA.getUserData()+ " dis coucou a " +fB.getUserData());
	}
	
	//contact termine
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		
		if(fA.getUserData() != null && fA.getUserData().equals("foot")) {
			footContacts--;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("foot")) {
			footContacts--;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("sensorG")) {
			sensorGContacts--;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("sensorG")) {
			sensorGContacts--;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("head")) {
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
			headContacts--;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("head")) {
			headContacts--;
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
		}
		if(fA.getUserData() != null && fA.getUserData().equals("deadInversed")) {
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
			dead--;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("deadInversed")) {
			dead--;
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
		}
		if(fA.getUserData() != null && fA.getUserData().equals("dead")) {
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
			dead--;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("dead")) {
			dead--;
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
		}
		//System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
		
	}

	//avant le dbut du contact
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	//avant la fin du contact
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isOnGround() {
		return footContacts > 0;
	}
	public boolean isDead() {
		return dead > 0;
	}
	public boolean isOnHead() { return headContacts > 0; }
	public boolean isSensorG() {
		return sensorGContacts > 0;
	}

}
