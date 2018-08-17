package ld38.parts;

import static ld38.Game.game;

import ld38.Contact;

public final class NotGate extends Part {
	private final Contact input = new Contact(0, 0, false, false);
	private final Contact output = new Contact(1, 0, true, true);

	public NotGate(float x, float y) {
		super(x, y);

		contacts.add(input);
		contacts.add(output);
	}

	@Override
	public int w() {
		return 2;
	}

	@Override
	protected void drawSymbol() {
		game.stroke(0);
		game.strokeWeight(4);

		game.triangle(x + sw()*3/8, y + sh()/4,
					  x + sw()*3/8, y + sh()*3/4,
					  x + sw()*5/8, y + sh()/2);
	}

	@Override
	public Part duplicate() {
		return new NotGate(x, y);
	}

	@Override
	public void advance() {
		boolean in = input.getSignal(gx, gy) > 0;
		output.propagate(gx, gy, !in);
	}
}
