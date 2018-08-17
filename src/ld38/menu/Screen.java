package ld38.menu;

import static ld38.Game.game;

import ld38.Game;

public class Screen {
	public static final float GUTTER_WIDTH = 222;
	public static final float MARGIN = 16;

	public float w, h;

	public Screen(float w, float h) {
		this.w = w;
		this.h = h;
	}

	public void draw() {
		game.rectMode(Game.CENTER);

		game.stroke(0);
		game.strokeWeight(2);
		game.fill(255);
		game.rect(game.width/2, game.height/2, w, h);

		game.rectMode(Game.CORNER);

		drawImpl();
	}

	public boolean click() {
		return false;
	}

	public boolean hovered(float mx, float my) {
		float x = game.width/2;
		float y = game.height/2;

		return mx > x - w/2 &&
			   mx < x + w/2 &&
			   my > y - h/2 &&
			   my < y + h/2;
	}

	protected void drawImpl() {}
}
