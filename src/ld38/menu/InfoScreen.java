package ld38.menu;

import static ld38.Game.game;

import ld38.Game;
import processing.core.PImage;

public class InfoScreen  extends Screen {
	private final String name;
	private final String[] text;
	private final PImage[] images;

	public InfoScreen() {
		this("How To Play", new String[] {
			"Welcome to Transistor Soup! As part of the company's",
			"standard training for new hires, you'll be getting",
			"acquainted with our proprietary circuit prototyping",
			"software through a series of challenging exercises.",
			"",
			"To create circuits with the software, click and drag",
			"parts from the shelf on the right side of the screen",
			"onto the grid. Drag a part outside of the grid to",
			"remove it. Create wires by clicking and dragging",
			"anywhere on the grid. Remove wires with right-click.",
			"",
			"The Parts Manual has information on every part",
			"available to you. You can open it by clicking the",
			"\"Manual\" button in the corner. If you're unsure what",
			"something does, consult the Manual.",
			"",
			"For information on the current puzzle, consult the",
			"Problem Specification. You can open it at any time by",
			"clicking the \"Spec\" button.",
			"",
			"When you think you've solved a puzzle, click the",
			"\"Test\" button to continue on to the next problem.",
			"",
			"Good luck, and have fun!"
		}, new String[] { /* TODO: info screen images */ });
	}

	public InfoScreen(String name, String[] text, String[] imageFiles) {
		super(800, 700);
		this.name = name;
		this.text = text;
		images = new PImage[imageFiles.length];
		for (int i = 0; i < images.length; ++i) {
			images[i] = game.loadImage(imageFiles[i]);
		}
	}

	@Override
	protected void drawImpl() {
		//top left corner
		float x = game.width/2 - w/2;
		float y = game.height/2 - h/2;
		float rx = x + GUTTER_WIDTH;

		//draw dividing line
		game.stroke(128);
		game.strokeWeight(1);
		game.line(x + GUTTER_WIDTH, y, x + GUTTER_WIDTH, y + h);

		//draw titles
		game.textFont(game.montserrat);
		game.fill(0);

		game.textAlign(Game.CENTER, Game.TOP);
		game.text("Illustrations", x + GUTTER_WIDTH/2, y + MARGIN - 6);

		game.textAlign(Game.LEFT, Game.TOP);
		game.text(name, rx + MARGIN, y + MARGIN - 6);

		//draw problem description
		game.textFont(game.small);
		for (int i = 0; i < text.length; ++i) {
			game.text(text[i], rx + MARGIN, y + MARGIN*2 + 32 + 24*i);
		}

		//draw images
		float ty = y + MARGIN*2 + 32; // + Game.CELL_SIZE;
		game.imageMode(Game.CENTER);
		for (PImage image : images) {
			game.image(image, x + GUTTER_WIDTH/2, ty + image.height/4, image.width/2, image.height/2);
			ty += image.height/2 + Game.CELL_SIZE*1;
		}
	}
}
