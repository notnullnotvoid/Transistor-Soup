package ld38;

import static ld38.Game.game;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ld38.menu.SpecScreenFactory;
import ld38.parts.AndGate;
import ld38.parts.Bridge;
import ld38.parts.FullAdder;
import ld38.parts.HalfAdder;
import ld38.parts.Input;
import ld38.parts.NotGate;
import ld38.parts.Output;
import ld38.parts.Part;
import ld38.parts.XorGate;

public final class Level {
	private final String[] tests;

	public Level(int number) {
		String[] lines = game.loadStrings(number + ".txt");
		for (String line : lines)
			Game.println(line);

		loadParts(lines[0]);
		tests = Arrays.copyOfRange(lines, 1, lines.length);
		game.specScreen = SpecScreenFactory.get(number);
		game.currentScreen = game.specScreen;

		//update shelf to be current
		game.shelf = new ArrayList<>();
		for (int i = 0; i < number; ++i) {
			switch (i) {
				case 0: game.shelf.add(new Bridge(0, 0)); break;
				case 1: game.shelf.add(new NotGate(0, 0)); break;
				case 5: game.shelf.add(new AndGate(0, 0)); break;
				case 6: game.shelf.add(new XorGate(0, 0)); break;
				case 7: game.shelf.add(new HalfAdder(0, 0)); break;
				case 8: game.shelf.add(new FullAdder(0, 0)); break;
			}
		}

		game.layoutShelf();
	}

	public boolean test() {
		//save current configuration to disk
		saveAll();
		game.diskStore.putInt("levelNumber", game.levelNumber);

		List<Input> inputs = new ArrayList<>();
		List<Output> outputs = new ArrayList<>();

		for (Part part : game.parts) {
			if (part instanceof Input)
				inputs.add((Input)part);
			else if (part instanceof Output)
				outputs.add((Output)part);
		}

		inputs.sort((a, b) -> a.gy - b.gy);
		outputs.sort((a, b) -> a.gy - b.gy);

		for (Input input : inputs)
			Game.println(input);
		for (Output output : outputs)
			Game.println(output);

		game.reset();

		//now for the actual testing...
		for (String test : tests) {
			String[] halves = test.split(";");

			//set inputs
			for (int i = 0; i < inputs.size(); ++i) {
				inputs.get(i).active = Util.bool(halves[0].charAt(i));
			}

			game.test();

			//test outputs
			for (int i = 0; i < outputs.size(); ++i) {
				if (outputs.get(i).active != Util.bool(halves[1].charAt(i))) {
					Game.println("failed test: " + test);
					return false;
				}
			}

			Game.println("passed test: " + test);
		}

		return true;
	}

	public static void clear() {
		for (int i = 0; i < game.wires.length; ++i)
			game.wires[i] = null;

		List<Part> io = new ArrayList<>();
		for (Part part : game.parts)
			if (part instanceof Input || part instanceof Output)
				io.add(part);
		game.parts = io;
	}

	public static void loadWires(String source) {
		String[] defs = source.split(" ");
		for (String def : defs) {
			if (def.isEmpty())
				break;
			String[] aspects = def.split(":");
			String[] coords = aspects[0].split(",");

			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);

			Wire wire = new Wire(x, y);
			wire.north = Util.bool(aspects[1].charAt(0));
			wire.east  = Util.bool(aspects[1].charAt(1));
			wire.south = Util.bool(aspects[1].charAt(2));
			wire.west  = Util.bool(aspects[1].charAt(3));

			game.wires[y*game.w + x] = wire;
		}
	}

	private static final Map<String, String> partNames = new HashMap<>();
	static {
		partNames.put("A", "ld38.parts.AndGate");
		partNames.put("B", "ld38.parts.Bridge");
		partNames.put("F", "ld38.parts.FullAdder");
		partNames.put("H", "ld38.parts.HalfAdder");
		partNames.put("I", "ld38.parts.Input");
		partNames.put("N", "ld38.parts.NotGate");
		partNames.put("O", "ld38.parts.Output");
		partNames.put("X", "ld38.parts.XorGate");
	}

	public static void loadParts(String source) {
		String[] defs = source.split(" ");
		for (String def : defs) {
			if (def.isEmpty())
				break;

			String[] aspects = def.split(":");
			String[] coords = aspects[1].split(",");

			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);

			try {
				Class<?> clazz = Class.forName(partNames.get(aspects[0]));
				Constructor<?> constructor = clazz.getConstructor(float.class, float.class);
				Object object = constructor.newInstance(x*Game.CELL_SIZE, y*Game.CELL_SIZE);
				Part part = (Part)object;
				part.drop();
				game.parts.add(part);
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
	}

	public static String saveWires() {
		StringBuilder builder = new StringBuilder();

		for (Wire wire : game.wires)
			if (wire != null)
				builder.append(wire.saveString()).append(' ');

		return builder.substring(0, Game.max(0, builder.length() - 1));
	}

	public static String saveParts() {
		StringBuilder builder = new StringBuilder();

		for (Part part : game.parts)
			if (part != null && !(part instanceof Input ||part instanceof Output))
				builder.append(part.saveString()).append(' ');

		return builder.substring(0, Game.max(0, builder.length() - 1));
	}

	public static void saveAll() {
		game.diskStore.put("wires", saveWires());
		game.diskStore.put("parts", saveParts());
	}

	public static void loadAll() {
		clear();
		loadWires(game.diskStore.get("wires", ""));
		loadParts(game.diskStore.get("parts", ""));
	}
}
