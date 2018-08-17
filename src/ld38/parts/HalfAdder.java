package ld38.parts;

import static ld38.Game.game;

import ld38.Contact;

public final class HalfAdder extends Part {
	private final Contact inTop = new Contact(0, 0, false, false);
	private final Contact inBottom = new Contact(0, 1, false, false);
	private final Contact outTop = new Contact(1, 0, true, false);
	private final Contact outBottom = new Contact(1, 1, true, false);

	public HalfAdder(float x, float y) {
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

		game.line(x + sw()/4, y + sh()/2, x + sw()*3/4, y + sh()/2);
		game.line(x + sw()/2, y + sh()/4, x + sw()/2, y + sh()*3/4);
	}

	@Override
	public HalfAdder duplicate() {
		return new HalfAdder(x, y);
	}

	@Override
	public void advance() {
		boolean top = inTop.getSignal(gx, gy) > 0;
		boolean bottom = inBottom.getSignal(gx, gy) > 0;

		int total = (top? 1 : 0) + (bottom? 1 : 0);

		outTop.propagate(gx, gy, total == 1);
		outBottom.propagate(gx, gy, total == 2);
	}
}
