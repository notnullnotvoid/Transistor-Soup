package ld38.menu;

import static ld38.Game.game;

import ld38.Game;

public final class Button {
	private final String text;
	private final float x, y; //screen space
	private final float w, h = 24;
	private final Runnable behavior;

	public Button(String text, float x, float y, Runnable behavior) {
		this(text, x, y, 80, behavior);
	}

	public Button(String text, float x, float y, float w, Runnable behavior) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.w = w;
		this.behavior = behavior;
	}

	public void draw() {
		game.textAlign(Game.CENTER, Game.CENTER);
		game.rectMode(Game.CENTER);

		game.stroke(0);
		game.strokeWeight(2);

		game.fill(hovered(game.mouseX, game.mouseY)? 200 : 240);
		game.rect(x, y, w, h);

		game.fill(0);
		game.textFont(game.small);
		game.text(text, x, y - 4);

		game.rectMode(Game.CORNER);
	}

	public boolean click() {
		if (hovered(game.mouseX, game.mouseY)) {
			behavior.run();
			return true;
		}
		return false;
	}

	public boolean hovered(float mx, float my) {
		return mx > x - w/2 &&
			   mx < x + w/2 &&
			   my > y - h/2 &&
			   my < y + h/2;
	}
}
