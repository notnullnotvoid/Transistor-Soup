package ld38.parts;

import static ld38.Game.game;

import ld38.Wire;

public final class Bridge extends Part {
	public Bridge(float x, float y) {
		super(x, y);
	}

	public void connect() {
		Wire topWire = game.getWire(gx, gy);
		Wire bottomWire = game.getWire(gx, gy + 2);

		if (topWire != null && bottomWire != null) {
			topWire.bridgeSouth = true;
			bottomWire.bridgeNorth = true;
		} else {
			disconnect();
		}
	}

	public void disconnect() {
		Wire topWire = game.getWire(gx, gy);
		Wire bottomWire = game.getWire(gx, gy + 2);

		if (topWire != null)
			topWire.bridgeSouth = false;
		if (bottomWire != null)
			bottomWire.bridgeNorth = false;
	}

	@Override
	public void drop() {
		disconnect();
		super.drop();
		connect();
	}

	@Override
	public int h() {
		return 3;
	}

	@Override
	public boolean contains(float x0, float y0) {
		return x0 > x + sw()/4 && x0 < x + sw()*3/4 &&
			   y0 > y + sh()/5 && y0 < y + sh()*4/5;
	}

	@Override
	public void draw() {
		game.stroke(0);
		game.strokeWeight(4);
		game.noFill();

		game.rect(x + sw()/4, y + sh()/12, sw()/2, sh()/6); //top
		game.rect(x + sw()/4, y + sh()*3/4, sw()/2, sh()/6); //bottom
		game.fill(255);
		game.rect(x + sw()/3, y + sh()/4, sw()/3, sh()/2); //middle
	}

	@Override
	protected void drawSymbol() {
		//nop
	}

	@Override
	public Part duplicate() {
		return new Bridge(x, y);
	}

	@Override
	public void advance() {
		//nop
	}
}
