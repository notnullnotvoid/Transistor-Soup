package ld38.parts;

import static ld38.Game.game;

import java.util.ArrayList;
import java.util.List;

import ld38.Contact;
import ld38.Game;

public abstract class Part {
	//TODO: refactor to share one static list across all instances
	//of each part, if possible
	protected final List<Contact> contacts = new ArrayList<>();

	public float x, y; //screen space
	public int gx, gy; //grid space

	public Part(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public int snapX() {
		return Game.round(x / Game.CELL_SIZE);
	}

	public int snapY() {
		return Game.round(y / Game.CELL_SIZE);
	}

	public void pick() {
		//no-op
	}

	public void drop() {
		gx = snapX();
		gy = snapY();

		x = gx * Game.CELL_SIZE;
		y = gy * Game.CELL_SIZE;
	}

	public void putBack() {
		x = gx * Game.CELL_SIZE;
		y = gy * Game.CELL_SIZE;
	}

	public void reset() {
		for (Contact contact : contacts)
			contact.reset();
	}

	public final float sw() {
		return w() * Game.CELL_SIZE;
	}

	public final float sh() {
		return h() * Game.CELL_SIZE;
	}

	public int w() {
		return 1;
	}

	public int h() {
		return 1;
	}

	public boolean contains(float x0, float y0) {
		for (Contact contact : contacts)
			if (contact.contains(x, y, x0, y0))
				return false;
		return x0 > x && y0 > y && x0 < x + sw() && y0 < y + sh();
	}

	public boolean intersects(int x1, int y1, int x2, int y2) {
		return gx < x2 &&
			   gx + w() > x1 &&
			   gy < y2 &&
			   gy + h() > y1;
	}

	protected void drawBorder() {
		float r = Contact.radius;
		game.stroke(0);
		game.strokeWeight(2);
		game.fill(255);
		game.rect(x + r, y + r, sw() - r*2, sh() - r*2);
	}

	public void draw() {
		drawBorder();

		drawSymbol();

		for (Contact contact : contacts)
			contact.draw(x, y);
	}

	public void disconnectInternalWires() {
		if (w() < 2)
			return;

		for (int i = 0; i < h(); ++i) {
			if (game.getWire(gx, gy + i) != null)
				game.getWire(gx, gy + i).east = false;
			if (game.getWire(gx + w() - 1, gy + i) != null)
				game.getWire(gx + w() - 1, gy + i).west = false;
		}
	}

	@Override
	public String toString() {
		return this.getClass().getName() +":" + gx + "," + gy;
	}

	public String saveString() {
		return initial() + ":" + gx + "," + gy;
	}

	private String initial() {
		return this.getClass().getSimpleName().substring(0, 1);
	}

	protected abstract void drawSymbol();

	public abstract Part duplicate();

	public abstract void advance();
}
