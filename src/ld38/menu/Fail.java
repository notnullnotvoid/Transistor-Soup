package ld38.menu;

import static ld38.Game.game;

import ld38.Game;

public final class Fail extends Screen {
	public Fail() {
		super(400, 200);
	}

	@Override
	public void drawImpl() {
		game.textFont(game.montserrat);
		game.fill(0);
		game.textAlign(Game.CENTER, Game.CENTER);
		game.text("FAILED TESTS", game.width/2, game.height/2);

		//TODO: tell the user which tests they failed
	}
}
