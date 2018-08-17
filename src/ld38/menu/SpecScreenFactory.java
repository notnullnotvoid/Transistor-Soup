package ld38.menu;

//LEVELS:
//0 - Swapper
//1 - Either On (OR Gate)
//2 - Both On (AND Gate)
//3 - All On (Multi-AND Gate)
//4 - Pattern Matching
//5 - Splitter (Branch Inverter)
//6 - Only One (XOR Gate)
//7 - Add Two (Half Adder)
//8 - Add Three (Full Adder)

//I'm sorry. I'm so, so sorry.
public final class SpecScreenFactory {
	private static final SpecScreen[] screens = new SpecScreen[] {
//0
		new SpecScreen("Swapper", new String[] {
			"The top output should be the same as the bottom input.",
			"The bottom output should be the same as the top input.",
			"",
			"In other words, the lines are swapped.",
			"",
			"You may find the Bridge part useful for this exercise.",
			"You can read about it in the Parts Manual. To open the",
			"Manual, click the \"Parts\" button in the corner.",
			"",
			"If you're unsure how to play, click the \"Info\" button."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false }, new boolean[] { false, false }),
			new TestCase(new boolean[] { false, true  }, new boolean[] { true , false }),
			new TestCase(new boolean[] { true , false }, new boolean[] { false, true  }),
			new TestCase(new boolean[] { true , true  }, new boolean[] { true , true  })
		}),
//1
		new SpecScreen("Either On", new String[] {
			"The output should turn on if either input is on.",
			"",
			"If both inputs are off, the output should be off."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { false, true  }, new boolean[] { true  }),
			new TestCase(new boolean[] { true , false }, new boolean[] { true  }),
			new TestCase(new boolean[] { true , true  }, new boolean[] { true  })
		}),
//2
		new SpecScreen("Both On", new String[] {
			"The output should turn on only when both inputs are",
			"turned on.",
			"",
			"If either input is off, the output should be off.",
			"",
			"You'll want to use the new Inverter that just arrived.",
			"We've added an entry for it in the Parts Manual."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { false, true  }, new boolean[] { false }),
			new TestCase(new boolean[] { true , false }, new boolean[] { false }),
			new TestCase(new boolean[] { true , true  }, new boolean[] { true  })
		}),
//3
		new SpecScreen("All On", new String[] {
			"The output should turn on only when all inputs are",
			"turned on.",
			"",
			"If any input is off, the output should be off."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false, false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { false, true,  false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { true , false, true,  true  }, new boolean[] { false }),
			new TestCase(new boolean[] { true , true , true,  true  }, new boolean[] { true  })
		}),
//4
		new SpecScreen("Pattern Matching", new String[] {
			"The output should turn on only when the inputs match",
			"the pattern as shown in the examples:",
			"",
			"ON, OFF, ON, ON, OFF",
			"",
			"If any input doesn't match, the output should be off."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, true , false, false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { true , false, true , false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { true , false, true , true , false }, new boolean[] { true  })
		}),
//5
		new SpecScreen("Branch Inverter", new String[] {
			"The top output should be the same as the input.",
			"The bottom output should be the opposite of the input.",
			"",
			"There will always be one output turned on, and one",
			"output turned off."
		}, new TestCase[] {
			new TestCase(new boolean[] { false }, new boolean[] { false, true  }),
			new TestCase(new boolean[] { true  }, new boolean[] { true , false }),
		}),
//6
		new SpecScreen("Only One", new String[] {
			"The output should turn on only when exactly one of",
			"the inputs is turned on.",
			"",
			"If the inputs are both on, or both off, then the",
			"output should be off.",
			"",
			"You'll likely find the new part useful. You can read", //TODO: better phrasing?
			"about it in the Parts Manual."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false }, new boolean[] { false }),
			new TestCase(new boolean[] { false, true  }, new boolean[] { true  }),
			new TestCase(new boolean[] { true , false }, new boolean[] { true  }),
			new TestCase(new boolean[] { true , true  }, new boolean[] { false })
		}),
//7
		new SpecScreen("Add Two", new String[] {
			"If both inputs are off, both outputs should be off.",
			"If only one input is on, the top output should be on.",
			"If both inputs are on, the bottom output should be on.",
			"",
			"In addition to the AND Gate, I think you'll find the",
			"new XOR gate useful for this exercise.",
			"Feel free to read about it in the Parts Manual."
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false }, new boolean[] { false, false }),
			new TestCase(new boolean[] { false, true  }, new boolean[] { true , false }),
			new TestCase(new boolean[] { true , false }, new boolean[] { true , false }),
			new TestCase(new boolean[] { true , true  }, new boolean[] { false, true  })
		}),
//8
		new SpecScreen("Add Three", new String[] {
			"If no inputs are on, both outputs should be off.",
			"If only one input is on, the top output should be on.",
			"If two inputs are on, the bottom output should be on.",
			"If all three inputs are on, both outputs should be on.",
			"",
			"The Half Adder you made in the last exercise will",
			"probably come in handy. As always, you can read about",
			"it in the Parts Manual"
		}, new TestCase[] {
			new TestCase(new boolean[] { false, false, false }, new boolean[] { false, false }),
			new TestCase(new boolean[] { false, false, true  }, new boolean[] { true , false }),
			new TestCase(new boolean[] { true , true , false }, new boolean[] { false, true  }),
			new TestCase(new boolean[] { true , false, true  }, new boolean[] { false, true  }),
			new TestCase(new boolean[] { true , true , true  }, new boolean[] { true , true  })
		}),
//9
		new SpecScreen("Binary Addition", new String[] {
			"Treat the input as two three-digit binary numbers",
			"ranging from 0 to 7. The top three inputs represent",
			"the first number, with the least significant bit on",
			"top and the most significant bit on bottom. The bottom",
			"three inputs represent the second number, arranged in",
			"the same way as the first.",
			"",
			"The output should then represent the sum of the two",
			"numbers, also arranged from least significant bit on",
			"top to most significant bit on bottom.",
			"",
			"The first example represents 4 + 6 = 10.",
			"The second example represents 3 + 1 = 4.",
			"",
			"The Full Adder will probably come in handy here.",
			"Feel free to read about it in the Parts Manual."
		}, new TestCase[] {
//			new TestCase(new boolean[] { true , false, false, false, true , true  },
//						 new boolean[] { true , true , true , false }), //1 + 6 = 7
			new TestCase(new boolean[] { false, false, true , false, true , true  },
						 new boolean[] { false, false, true , true  }), //4 + 6 = 10
//			new TestCase(new boolean[] { true , true , true , true , false, false },
//						 new boolean[] { false, false, false, true  }), //7 + 1 = 8
			new TestCase(new boolean[] { true , true , false, true , false, false },
						 new boolean[] { false, false, true , false }), //3 + 1 = 4
//			new TestCase(new boolean[] { true , true , true  }, new boolean[] { true , true  })
		}),
//10
		new SpecScreen("Bit Counting", new String[] {
			"Count the total number of inputs that are on and",
			"activate the corresponding output.",
			"",
			"If no inputs are on, no outputs should be on.",
			"If only one input is on, activate the first output.",
			"If two inputs are on, activate the second output.",
			"If three inputs are on, activate the third output.",
			"If all four outputs are on, activate the fourth output.",
			"",
			"At most one output should be active for any input."
		}, new TestCase[] {
//			new TestCase(new boolean[] { false, false, false, false }, new boolean[] { false, false, false, false }),
			new TestCase(new boolean[] { false, false, true , false }, new boolean[] { true , false, false, false }),
			new TestCase(new boolean[] { true , true , false, false }, new boolean[] { false, true , false, false }),
			new TestCase(new boolean[] { true , false, true , true  }, new boolean[] { false, false, true , false }),
			new TestCase(new boolean[] { true , true , true , true  }, new boolean[] { false, false, false, true  })
		}),
//10
		new SpecScreen("Sandbox", new String[] {
			"Congratulation! Your orientation is complete.",
			"",
			"There are no more challenges for you, but you're free",
			"to stick around and try thing out as long as you like."
		}, new TestCase[] {
			//none
		}),
	};

	//TODO: capitalize all textual instances of "on" and "off"

	public static SpecScreen get(int i) {
		return screens[i];
	}
}
