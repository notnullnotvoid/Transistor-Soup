package ld38.menu;

import static ld38.Game.game;

import ld38.Game;

public final class SpecScreen extends InfoScreen {
	private final TestCase[] testCases;

	public SpecScreen(String name, String[] text, TestCase[] testCases) {
		super(name, text, new String[] { /* array intentionally left blank */ });
		this.testCases = testCases;
	}

	@Override
	protected void drawImpl() {
		super.drawImpl();

		//top left corner
		float x = game.width/2 - w/2;
		float y = game.height/2 - h/2;

		//draw test cases
		float ty = y + MARGIN*2 + 32;
		for (TestCase test : testCases) {
			test.draw(x + MARGIN*2, ty);
			ty += test.height + Game.CELL_SIZE*1;
		}
	}
}
