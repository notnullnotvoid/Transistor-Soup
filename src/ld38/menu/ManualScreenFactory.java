package ld38.menu;

//again, like, I'm really sorry
public final class ManualScreenFactory {
	private static final ManualScreen[] screens = new ManualScreen[] {
//0 - Input
		new ManualScreen("Input", new String[] {
			"The Input is used to power the circuit.",
			"It can either be ON or OFF.",
			"When it's turned ON, it transmits power to whatever",
			"wire is attached to it.",
			"",
			"Inputs can be toggled by clicking on them."
		}, new String[] { "0-0.png", "0-1.png" }, 0),
//1 - Output
		new ManualScreen("Output", new String[] {
			"The Output is used to measure the circuit.",
			"It will turn ON when the connected wire is powered.",
		}, new String[] { "1-0.png", "1-1.png" }, 1),
//2 - Bridge
		new ManualScreen("Bridge", new String[] {
			"The Bridge is used to cross wires over each other.",
			"It connects the wires attached to each end,",
			"while a third independent wire can run underneath."
		}, new String[] { "2-0.png", "2-1.png" }, 2),
//3 - Inverter
		new ManualScreen("Inverter", new String[] {
			"The Inverter is used to flip a wire's signal from",
			"ON to ON, or vice versa.",
			"",
			"The wire connected to its left side serves input,",
			"while the wire on the right is used as its output.",
			"",
			"In Transistor Soup, all parts transmit signal from",
			"left to right."
		}, new String[] { "3-0.png", "3-1.png" }, 3),
//4 - AND Gate
		new ManualScreen("AND Gate", new String[] {
			"Congratulations on your ingenuity! We've adapted your",
			"designs into a single gate for your convenience.",
			"",
			"The AND Gate is used to multiply two signals together.",
			"If both inputs are ON, the top output will also be ON.",
			"Otherwise, the top output will be OFF.",
			"",
			"The bottom output is the opposite of the top one."
		}, new String[] { "4-0.png", "4-1.png" }, 4),
//5 - XOR Gate
		new ManualScreen("XOR Gate", new String[] {
			"Congratulations on your ingenuity! We've adapted your",
			"design into a single gate for your convenience.",
			"",
			"The XOR Gate (short for \"Exclusive Or Gate\") is used",
			"to determine if two signals are identical.",
			"",
			"If the inputs are different from each other, then the",
			"top output will be ON. If both inputs are identical,",
			"then the top output will be OFF.",
			"",
			"The bottom output is the opposite of the top one."
		}, new String[] { "5-0.png", "5-1.png" }, 5),
//6 - Half Adder
		new ManualScreen("Half Adder", new String[] {
			"Congratulations on your ingenuity! We've adapted your",
			"design into a single part for your convenience.",
			"",
			"The Half Adder is used to sum two signals together.",
			"",
			"If both inputs are off, then both outputs will be off.",
			"If only one input is on, the top output will be on.",
			"If both inputs are on, the bottom output will be on.",
			"",
			"The output can be considered a two-digit binary number",
			"where the top output is the least significant digit."
		}, new String[] { "6-0.png", "6-1.png" }, 6),
//7 - Full Adder
		new ManualScreen("Full Adder", new String[] {
			"Congratulations on your ingenuity! We've adapted your",
			"design into a single part for your convenience.",
			"",
			"The Full Adder is used to sum three signals together.",
			"",
			"If none of the inputs are on, both outputs will be off.",
			"If only one input is on, the top output will be on.",
			"If two inputs are on, the bottom output will be on.",
			"If all three inputs are on, both outputs will be on.",
			"",
			"The output can be considered a two-digit binary number",
			"where the top output is the least significant digit."
		}, new String[] { "7-0.png", "7-1.png" }, 7),
	};

	public static ManualScreen get(int i) {
		return screens[i];
	}
}
