import java.util.Random;

enum Direction{
	/*
	 * this holds all the possible directions of the drone
	 * used enum so i can set the drone to have a position item rather than data type of string
	 */
	NORTH, EAST, SOUTH, WEST;
	
	public static Direction randomDirection() {
		/*
		 * picks a random direction and returns it
		 */
		Random random = new Random();
		return values()[random.nextInt(values().length)];
	}
	
	public Direction nextDirection() {
		/*
		 * rotates the drone 90 degrees clockwise by adding one to the ordinal
		 */
		int i = this.ordinal() + 1;
		while (i >= values().length) i -= values().length;
		return values()[i];
	}
	
	public static Direction setDirection(int index) {
		/*
		 * set direction to anything by ordinal
		 */
		while (index >= values().length) index -= values().length;
		return values()[index];
	}
	
	public int getDirection() {return this.ordinal();} // returns the index number
}