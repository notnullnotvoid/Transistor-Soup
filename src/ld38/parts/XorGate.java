package ld38.parts;

import static ld38.Game.game;

import ld38.Contact;

public final class XorGate extends Part {
	private final Contact inTop = new Contact(0, 0, false, false);
	private final Contact inBottom = new Contact(0, 1, false, false);
	private final Contact outTop = new Contact(1, 0, true, false);
	private final Contact outBottom = new Contact(1, 1, true, true);

	public XorGate(float x, float y) {
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

		//draw an X in lieu of proper symbol
		game.line(x + sw()/4, y + sh()/4, x + sw()*3/4, y + sh()*3/4);
		game.line(x + sw()/4, y + sh()*3/4, x + sw()*3/4, y + sh()/4);
	}

	@Override
	public Part duplicate() {
		return new XorGate(x, y);
	}

	@Override
	public void advance() {
		boolean top = inTop.getSignal(gx, gy) > 0;
		boolean bottom = inBottom.getSignal(gx, gy) > 0;
		outTop.propagate(gx, gy, top != bottom);
		outBottom.propagate(gx, gy, top == bottom);
	}
}
