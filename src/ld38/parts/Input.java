package ld38.parts;

import static ld38.Game.game;

import ld38.Contact;

public final class Input extends Part {
	private final Contact output = new Contact(0, 0, true, false);

	public boolean active;

	public Input(float x, float y) {
		super(x, y);

		contacts.add(output);
	}

	@Override
	protected void drawSymbol() {
		game.stroke(active? 0xFF00FF00 : 0xFF002288);
		game.strokeWeight(4);
		game.line(x + sw()/4, y + sh()/2, x + sw()*3/4, y + sh()/2); //horizontal
		game.line(x + sw()/2, y + sh()/4, x + sw()*3/4, y + sh()/2); //arrow top
		game.line(x + sw()/2, y + sh()*3/4, x + sw()*3/4, y + sh()/2); //arrow bottom
	}

	@Override
	public Input duplicate() {
		return new Input(x, y);
	}

	@Override
	public void advance() {
		output.propagate(gx, gy, active);
	}
}
