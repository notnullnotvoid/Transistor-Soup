package ld38.parts;

import static ld38.Game.game;

import ld38.Contact;
import ld38.Game;

public final class AndGate extends Part {
	private final Contact inTop = new Contact(0, 0, false, false);
	private final Contact inBottom = new Contact(0, 1, false, false);
	private final Contact outTop = new Contact(1, 0, true, false);
	private final Contact outBottom = new Contact(1, 1, true, true);

	public AndGate(float x, float y) {
		super(x, y);

		contacts.add(inTop);
		contacts.add(inBottom);
		contacts.add(outTop);
		contacts.add(outBottom);
	}

	@Override
	public int w() {
		return 2;
	}

	@Override
	public int h() {
		return 2;
	}

	@Override
	protected void drawSymbol() {
		game.stroke(0);
		game.strokeWeight(4);
		game.line(x + sw()/4, y + sh()/4, x + sw()/4, y + sh()*3/4); //left
		game.line(x + sw()/4, y + sh()/4, x + sw()/2, y + sh()/4); //top
		game.line(x + sw()/4, y + sh()*3/4, x + sw()/2, y + sh()*3/4);
		game.arc(x + sw()/2, y + sh()/2, sw()/2, sh()/2, -Game.HALF_PI, Game.HALF_PI);
	}

	@Override
	public AndGate duplicate() {
		return new AndGate(x, y);
	}

	@Override
	public void advance() {
		boolean top = inTop.getSignal(gx, gy) > 0;
		boolean bottom = inBottom.getSignal(gx, gy) > 0;
		outTop.propagate(gx, gy, top && bottom);
		outBottom.propagate(gx, gy, !(top && bottom));
	}
}
