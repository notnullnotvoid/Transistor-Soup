package ld38;

import static ld38.Game.game;

public class Wire {
	public final int x, y; //grid space

	public boolean north, east, south, west;
	public boolean bridgeNorth, bridgeSouth;
	public int signal;

	public Wire(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw() {
		int w = Game.CELL_SIZE;
		int h = Game.CELL_SIZE;

		int sx = x * Game.CELL_SIZE;
		int sy = y * Game.CELL_SIZE;

		game.stroke(signal > 0? 0xFF00FF00 : signal < 0? 0xFFFF0000 : 0xFF002288);
		game.strokeWeight(8);

		if (!north && !east && !south && !west) {
			game.point(sx + w/2, sy + h/2);
		} else {
			if (north || south) {
				float y1 = north? sy : sy + w/2;
				float y2 = south? sy + w : sy + w/2;
				game.line(sx + w/2, y1, sx + w/2, y2);
			}
			if (east || west) {
				float x1 = west? sx : sx + w/2;
				float x2 = east? sx + w : sx + w/2;
				game.line(x1, sy + h/2, x2, sy + h/2);
			}
		}

		if (game.debug) {
			game.fill(0xFFFF00FF);
			game.textFont(game.droid);
			game.textAlign(Game.CENTER, Game.CENTER);
			game.text(signal, sx + w/2, sy + h/2 - 6);
		}
	}

	public void propagate(int state) {
		if (signal == state)
			return;

		signal = Game.max(0, state);

		if (north) game.getWire(x, y - 1).propagate(signal);
		if (east)  game.getWire(x + 1, y).propagate(signal);
		if (south) game.getWire(x, y + 1).propagate(signal);
		if (west)  game.getWire(x - 1, y).propagate(signal);

		if (bridgeNorth) game.getWire(x, y - 2).propagate(signal);
		if (bridgeSouth) game.getWire(x, y + 2).propagate(signal);
	}

	@Override
	public String toString() {
		return x + "," + y + ":" +
			   north + "," + east + "," + south + "," + west;
	}

	public String saveString() {
		return x + "," + y + ":" +
		       Util.bool(north) + Util.bool(east) + Util.bool(south) + Util.bool(west);
	}
}
