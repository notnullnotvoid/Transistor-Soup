package ld38.parts;

import static ld38.Game.game;

import ld38.Contact;

public final class Output extends Part {
	private final Contact input = new Contact(0, 0, false, false);

	public boolean active;

	public Output(float x, float y) {
		super(x, y);

		contacts.add(input);
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
	public Part duplicate() {
		return new Output(x, y);
	}

	@Override
	public void advance() {
		active = input.getSignal(gx, gy) > 0;
	}
}
