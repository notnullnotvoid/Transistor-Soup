package ld38;

import static ld38.Game.CELL_SIZE;
import static ld38.Game.game;

public final class Contact {
	public static final float radius = 4;

	public final int dx, dy; //relative grid space
	public final boolean output, inverted; //these only affect appearance, not behavior
	private boolean active;

	public Contact(int dx, int dy, boolean output, boolean inverted) {
		this.dx = dx;
		this.dy = dy;
		this.output = output;
		this.inverted = inverted;
	}

	public void reset() {
		active = false;
	}

	public void propagate(int gx, int gy, boolean state) {
		//don't propagate if the signal hasn't changed
		if (state == active)
			return;

		int x = gx + dx;
		int y = gy + dy;

		Wire wire = game.getWire(x, y);
		if (wire != null) {
			if (output && wire.east) {
				wire = game.getWire(x + 1, y);
				wire.propagate(wire.signal + (state? 1 : -1));
			}
		}

		active = state;
	}

	public int getSignal(int gx, int gy) {
		int x = gx + dx;
		int y = gy + dy;

		Wire wire = game.getWire(x, y);
		active = wire != null && wire.signal > 0;
		if (wire == null)
			return 0;
		return wire.signal;
	}

	public void draw(float x0, float y0) {
		float r = radius;
		float x = x0 + dx*CELL_SIZE;
		float y = y0 + dy*CELL_SIZE;
		game.stroke(active? 0xFF00FF00 : 0xFF002288);
		game.strokeWeight(4);
		game.fill(inverted? 255 : active? 0xFF00FF00 : 0xFF002288);
		if (output)
			game.ellipse(x + CELL_SIZE - r, y + CELL_SIZE/2, r*2, r*2);
		else
			game.ellipse(x + r, y + CELL_SIZE/2, r*2, r*2);
	}

	public boolean contains(float x0, float y0, float mx, float my) {
		float x = x0 + dx*CELL_SIZE + (output? CELL_SIZE : 0);
		float y = y0 + dy*CELL_SIZE + CELL_SIZE/2;

		return Game.dist(x, y, mx, my) < radius*3;
	}
}
