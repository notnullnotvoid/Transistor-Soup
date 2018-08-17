package ld38;

public final class Util {
	public static boolean bool(char ch) {
		return ch == 't' || ch == 'T';
	}

	public static char bool(boolean b) {
		return b? 't' : 'f';
	}
}
