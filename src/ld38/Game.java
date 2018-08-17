package ld38;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import ld38.menu.Button;
import ld38.menu.Fail;
import ld38.menu.InfoScreen;
import ld38.menu.ManualScreenFactory;
import ld38.menu.Pass;
import ld38.menu.Screen;
import ld38.parts.Bridge;
import ld38.parts.Input;
import ld38.parts.Output;
import ld38.parts.Part;
import processing.core.PApplet;
import processing.core.PFont;

public final class Game extends PApplication {
	public static final Game game = new Game();

	public static final int CELL_SIZE = 32;
	public static final int SHELF_WIDTH = 100;
	public static final int SHELF_HEIGHT = 80;
	public static final int DEFAULT_WIDTH = 30*CELL_SIZE + SHELF_WIDTH;
	public static final int DEFAULT_HEIGHT = 24*CELL_SIZE + 1;

	public PFont montserrat, droid, small;

	public int w, h;

	private List<Button> buttons = new ArrayList<>();

	public Screen infoScreen, listScreen, specScreen, currentScreen;

	public int levelNumber = 0;
	private Level level;

	@Override
	public void settings() {
		size(DEFAULT_WIDTH, DEFAULT_HEIGHT, P2D);
		pixelDensity(displayDensity());

		println("WIDTH: " + DEFAULT_WIDTH + ", HEIGHT: " + DEFAULT_HEIGHT);
	}

	@Override
	public void setup() {
		strokeCap(PROJECT);

		frameRate(60);
		surface.setTitle("Transistor Soup");

		//make the window spawn on main monitor for my own sanity
		surface.setLocation(200, 100);

		//initialize fonts
		montserrat = createFont("Montserrat-Bold.ttf", 32);
		droid = createFont("DroidSansMono.ttf", 24);
		small = createFont("DroidSansMono.ttf", 16);

		//initialize board
		w = (width - SHELF_WIDTH)/CELL_SIZE;
		h = height/CELL_SIZE;

		wires = new Wire[w * h];

		//initialize buttons
		buttons.add(new Button("Clear", width - SHELF_WIDTH/2, height - 200, () -> {
			Level.clear();
		}));
		buttons.add(new Button("Test", width - SHELF_WIDTH/2, height - 160, () -> {
			currentScreen = level.test()? new Pass() : new Fail();
		}));
		buttons.add(new Button("Info", width - SHELF_WIDTH/2, height - 120, () -> {
			currentScreen = infoScreen;
		}));
		buttons.add(new Button("Spec", width - SHELF_WIDTH/2, height - 80, () -> {
			currentScreen = specScreen;
		}));
		buttons.add(new Button("Manual", width - SHELF_WIDTH/2, height - 40, () -> {
			currentScreen = listScreen;
		}));

		//initialize screens
		infoScreen = new InfoScreen();
		listScreen = ManualScreenFactory.get(0);

		//load relevant level
		levelNumber = diskStore.getInt("levelNumber", 0);
		println("levelNumber: " + levelNumber);

		//load saved game
		Level.loadAll();

		//load first level
		level = new Level(levelNumber);

		if (levelNumber == 0)
			currentScreen = infoScreen;
	}

	public void layoutShelf() {
		//TODO: factor part height into layout
		for (int i = 0; i < shelf.size(); ++i) {
			Part part = shelf.get(i);
			part.x = part.y = 0;
			float mx = SHELF_WIDTH/2 - part.sw()/2;
			float my = SHELF_HEIGHT/2 - part.sh()/2 + CELL_SIZE;
			part.move(width - SHELF_WIDTH + mx, SHELF_HEIGHT*i + my);
		}
	}

	public void nextLevel() {
		//load the next level
		levelNumber += 1;
		level = new Level(levelNumber);
	}

	public List<Part> shelf = new ArrayList<>();
	public List<Part> parts = new ArrayList<>();

	public Wire[] wires;

	@Override
	public void draw() {
		background(255);

		//draw grid
		stroke(128);
		strokeWeight(1);
		for (int y = 0; y < height; y += CELL_SIZE) {
			line(0, y, width, y);
		}
		for (int x = 0; x < width; x += CELL_SIZE) {
			line(x, 0, x, height);
		}

		//draw "tool shelf"
		fill(255, 255, 255);
		rect(width - SHELF_WIDTH, 0, SHELF_WIDTH - 1, height - 1);

		//draw wires
		for (Wire wire : wires)
			if (wire != null)
				wire.draw();

		//draw buttons
		for (Button button: buttons)
			button.draw();

		//draw parts on shelf
		for (Part part : shelf)
			part.draw();
		//draw parts on board
		for (Part part : parts)
			part.draw();

		//advance the simulation by one tick
		advance();

		//draw screen
		if (currentScreen != null)
			currentScreen.draw();
	}

	private void simulate() {
		reset();
		test();
	}

	public void reset() {
		for (Wire wire : wires)
		if (wire != null)
			wire.signal = 0;

		for (Part part : parts)
			part.reset();
	}

	public void test() {
		for (int i = 0; i < 100; ++i) {
			advance();
		}
	}

	private void advance() {
		//XXX: inefficient? (only needs to be called when wires change)
		for (Part part : parts) {
			part.disconnectInternalWires();

			if (part instanceof Bridge) {
				Bridge bridge = (Bridge)part;
				bridge.connect();
			}
		}

		for (Part part : parts)
			if (part != pick)
				part.advance();
	}

	Part pick;
	boolean dragging;
	boolean fromShelf;

	@Override
	public void mousePressed() {
		dragging = true;

		//click menu buttons
		if (mouseButton == LEFT) {
			for (Button button : buttons) {
				if (button.click()) {
					dragging = false;
					return;
				}
			}
		}

		//dismiss a screen if one exists
		if (currentScreen != null) {
			if (!currentScreen.click())
				currentScreen = null;
			dragging = false;
			return;
		}

		//drag a part from the shelf
		if (mouseButton == LEFT) {
			for (Part part : shelf) {
				if (part.contains(mouseX, mouseY)) {
					pick = part.duplicate();
					pick.pick();
					parts.add(pick);
					fromShelf = true;
					return;
				}
			}
		}

		//if you clicked the shelf, you probably didn't want to draw wire
		if (mouseX >= width - SHELF_WIDTH) {
			dragging = false;
			return;
		}

		//interact with inputs
		for (Part part : parts) {
			if (part instanceof Input && part.contains(mouseX, mouseY)) {
				Input input = (Input)part;
				input.active = !input.active;
				test();

				dragging = false;
				return;
			}
		}

		//delete parts
		if (mouseButton == RIGHT) {
			//because concurrent modification says NO
			Part remove = null;

			for (Part part : parts) {
				if (!(part instanceof Input || part instanceof Output) &&
						part.contains(mouseX, mouseY)) {
					remove = part;
				}
			}

			if (remove != null) {
				parts.remove(remove);
				dragging = false;
				return;
			}
		}

		//move parts
		if (mouseButton == LEFT) {
			for (Part part : parts) {
				if (!(part instanceof Input || part instanceof Output) &&
						part.contains(mouseX, mouseY)) {
					pick = part;
					pick.pick();
					simulate();
					fromShelf = false;
					return;
				}
			}
		}

		//delete wires
		if (mouseButton == RIGHT) {
			int x = cellX(mouseX);
			int y = cellY(mouseY);

			removeWire(x, y);
			simulate();
		}
	}

	@Override
	public void mouseDragged() {
		if (!dragging)
			return;

		//move held part
		if (pick != null) {
			pick.move(mouseX - pmouseX, mouseY - pmouseY);
			return;
		}

		//screen coordinates -> tile coordinates
		int  gx = cellX( mouseX);
		int  gy = cellY( mouseY);
		int pgx = cellX(pmouseX);
		int pgy = cellY(pmouseY);

		//delete wires
		if (mouseButton == RIGHT) {
			removeWire(gx, gy);
			simulate();
			return;
		}

		//draw new wires
		//TODO: disallow adding wire into spaces occupied by parts
		//TODO: continuous drag with line draw
		if (gx != pgx && gy == pgy) {
			int minx = min(gx, pgx);
			int maxx = max(gx, pgx);
			for (int x = minx; x <= maxx; ++x) {
				int i = gy*w + x;
				if (wires[i] == null)
					wires[i] = new Wire(x, gy);
				wires[i].west = wires[i].west || x != minx;
				wires[i].east = wires[i].east || x != maxx;
			}
			simulate();
		} else if (gx == pgx && gy != pgy) {
			int miny = min(gy, pgy);
			int maxy = max(gy, pgy);
			for (int y = miny; y <= maxy; ++y) {
				int i = y*w + gx;
				if (wires[i] == null)
					wires[i] = new Wire(gx, y);
				wires[i].north = wires[i].north || y != miny;
				wires[i].south = wires[i].south || y != maxy;
			}
			simulate();
		}
	}

	public void removeWire(int x, int y) {
		int i = y*w + x;

		wires[i] = null;

		//disconnect neighbors
		if (y > 0 && wires[i - w] != null)
			wires[i - w].south = false;
		if (x < w - 1 && wires[i + 1] != null)
			wires[i + 1].west = false;
		if (y < h - 1 && wires[i + w] != null)
			wires[i + w].north = false;
		if (x > 0 && wires[i - 1] != null)
			wires[i - 1].east = false;
	}

	public Wire getWire(int x, int y) {
		if (x >= 0 && y >= 0 && x < w && y < h)
			return wires[y*w + x];
		return null;
	}

	private int cellX(int x) {
		return constrain(x  / CELL_SIZE, 0, w - 1);
	}

	private int cellY(int y) {
		return constrain(y  / CELL_SIZE, 0, h - 1);
	}

	@Override
	public void mouseReleased() {
		if (pick == null)
			return;

		int x = pick.snapX();
		int y = pick.snapY();

		//remove picked part if it's dragged outside the grid
		if (x < 0 || y < 0 || x >= w || y >= h) {
			parts.remove(pick);
			if (pick instanceof Bridge) {
				Bridge bridge = (Bridge)pick;
				bridge.disconnect();
			}
		} else {
			boolean intersects = false;
			for (Part part : parts) {
				if (part != pick && part.intersects(x, y, x + pick.w(), y + pick.h())) {
					intersects = true;
					break;
				}
			}

			//reset position of part if dragged onto an occupied space
			if (intersects) {
				if (fromShelf) {
					parts.remove(pick);
				} else {
					pick.putBack();
				}
			} else {
				pick.drop();
			}
		}

		simulate();

		pick = null;
	}

	private boolean switching = false;
	private String input = "";

	@Override
	public void keyPressed() {
		if (switching) {
			if (key == ENTER) {
				try {
					int number = Integer.parseInt(input);
					level = new Level(number);
				} catch (NumberFormatException e) {
					println("Invalid level number");
				}

				input = "";
				switching = false;
			} else {
				input = input + (char)key;
				println(input);
			}

			return;
		}

		if (key == 'd')
			debug = !debug;
		else if (key == 'c')
			Level.clear();
		else if (key == '1')
			noLoop();
		else if (key == '2')
			loop();
		else if (key == 'w')
			println(Level.saveWires());
		else if (key == 'p')
			println(Level.saveParts());
		else if (key == 's')
			Level.saveAll();
		else if (key == 'l')
			Level.loadAll();
		else if (key == ' ')
			switching = true;
	}

	public boolean debug;

	private static final String PREFERENCES_NODE = "stuntddude.ld38";
	public final Preferences diskStore =
			Preferences.userNodeForPackage(getClass()).node(PREFERENCES_NODE);

	public static void main(String[] args) {
		PApplet.runSketch(new String[] { Game.class.getName() }, game);
	}
}
