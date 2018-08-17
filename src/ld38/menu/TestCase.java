package ld38.menu;

import static ld38.Game.game;

import java.util.ArrayList;
import java.util.List;

import ld38.Game;
import ld38.parts.Input;
import ld38.parts.Output;
import ld38.parts.Part;

public final class TestCase {
	private static final float WIDTH = SpecScreen.GUTTER_WIDTH - SpecScreen.MARGIN*4;
	private static final float MARGIN = 0;
	private static final float ROW_HEIGHT = Game.CELL_SIZE + MARGIN;

	private final List<Part> parts = new ArrayList<>();

	public final float height;

	public TestCase(boolean[] inputs, boolean[] outputs) {
		float inputHeight = inputs.length * ROW_HEIGHT - MARGIN;
		float outputHeight = outputs.length * ROW_HEIGHT - MARGIN;
		height = Game.max(inputHeight, outputHeight);

		float iy = (height - inputHeight)/2;
		float oy = (height - outputHeight)/2;

		for (int i = 0; i < inputs.length; ++i) {
			Input current = new Input(0, iy + i*ROW_HEIGHT);
			current.active = inputs[i];
			parts.add(current);
		}

		//vertically center inputs and outputs
		for (int i = 0; i < outputs.length; ++i) {
			Output current = new Output(0, oy + i*ROW_HEIGHT);
			current.move(WIDTH - current.sw(), 0);
			current.active = outputs[i];
			parts.add(current);
		}
	}

	public void draw(float dx, float dy) {
		for (Part part : parts)
			part.move(dx, dy);

		for (Part part : parts)
			part.draw();

		float cx = dx + WIDTH/2;
		float cy = dy + height/2;

		game.stroke(0);
		game.strokeWeight(4);
		game.line(cx - 16, cy, cx + 16, cy);
		game.line(cx, cy - 10, cx + 16, cy);
		game.line(cx, cy + 10, cx + 16, cy);

		for (Part part : parts)
			part.move(-dx, -dy);
	}
}
