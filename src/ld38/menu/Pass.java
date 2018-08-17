package ld38.menu;

import static ld38.Game.game;

import ld38.Game;

public final class Pass extends Screen {
	private final Button button = new Button("Next Level", game.width/2, game.height/2 + 60, 120, () -> {
		game.nextLevel();
	});

	public Pass() {
		super(400, 200);
	}

	@Override
	public void drawImpl() {
		button.draw();

		game.textFont(game.montserrat);
		game.fill(0);
		game.textAlign(Game.CENTER, Game.CENTER);
		game.text("PASSED TESTS", game.width/2, game.height/2);
	}

	@Override
	public boolean click() {
		if (button.click())
			return true; //false;
		return super.click();
	}
}
