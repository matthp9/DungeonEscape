package model;

public class Runner {
	public static void main(String... args) {
		final Dungeon d = new Dungeon(100, 100, 2);
		System.out.println(d.toString());
		System.out.println(d.solutionToString());
		System.out.println(d.solutionStackToString());
	}
}
