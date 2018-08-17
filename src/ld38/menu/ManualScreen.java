package ld38.menu;

import static ld38.Game.game;

public final class ManualScreen extends InfoScreen {
	private final Button previous, next;
	private final int index;

	public ManualScreen(String name, String[] text, String[] imageFiles, int index) {
		super(name, text, imageFiles);
		this.index = index;

		//top left corner
		float x = game.width/2 - w/2;
		float y = game.height/2 - h/2;
		float rx = x + GUTTER_WIDTH;

		previous = new Button("< Previous", rx + 100, y + h - 40, 120, () -> {
			game.listScreen = ManualScreenFactory.get(index - 1);
			game.currentScreen = game.listScreen;
		});
		next = new Button("Next >", x + w - 100, y + h - 40, 120, () -> {
			game.listScreen = ManualScreenFactory.get(index + 1);
			game.currentScreen = game.listScreen;
		});
	}

	@Override
	protected void drawImpl() {
		super.drawImpl();

		//draw buttons
		if (index > 0)
			previous.draw();
		if (index <= game.shelf.size())
			next.draw();
	}

	@Override
	public boolean click() {
		if (index > 0 && previous.click())
			return true;
		if (index <= game.shelf.size() && next.click())
			return true;
		return super.click();
	}
}
