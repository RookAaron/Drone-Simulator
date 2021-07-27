//import java.util.UUID;

public class Drone {
	/*
	 * this class will be a singular drone
	 * hold its position and direction as well as have a unique ID
	 * the main methods will be tryToMove and displayDrone where
	 * 		tryToMove will either move the drone 1 square forward or
	 * 		rotate the drone and displayDrone puts the drone in the
	 * 		ConsoleCanvas class
	 */
	
	//set constants for class
	private static long count = 0; // this counts how many times this class has been called
	private long ID; // the id for the drone, this is set in the constructor using count above
	private int x_pos; // the x position in the grid
	private int y_pos; // the y position in the grid
	private Direction dir; // direction the drone is facing, Direction is a enum for all four directions
	
	Drone(int x, int y, Direction d){
		/*
		 * constructor which sets x, y and direction
		 */
		x_pos = x;
		y_pos = y;
		dir = d;
		
		ID = count++; // sets the id and uses count static to always give a unique ID
	}
	
	public int[] getPos(){
		/*
		 * returns the position of the drone as an array of length 2
		 */
		int[] pos = new int[2];
		pos[0] = x_pos;
		pos[1] = y_pos;
		return pos;
	}
	
	public int getDir() {return dir.getDirection();} // returns the direction as an integer
	
	public long getID() {return ID;} // returns ID values a long number
	
	public void setID(long id) {ID = id;} // can set the ID by passing long to function
	
	public void displayDrone(ConsoleCanvas c) {
		/*
		 * adds a drone to ConsoleCanvas array passing x,y position and
		 * 		what to display in the grid
		 */
		c.addDrone(x_pos, y_pos, 'D');
	}
	
	public int[] nextSquare() {
		/*
		 * finds which square the drone will move to if it moves forwards once
		 * returns new position as array of length 2
		 */
		int[] pos = new int[2];
		pos[0] = x_pos; pos[1] = y_pos;
		if (dir == Direction.NORTH) pos[1]--;
		else if (dir == Direction.SOUTH) pos[1]++;
		else if (dir == Direction.EAST) pos[0]++;
		else if(dir == Direction.WEST) pos[0]--;
		return pos;
	}
	
	public void tryToMove(DroneArena a) {
		/*
		 * tries to move drone to next square
		 * 		if not, drone rotates 90 degrees clockwise
		 * pass arena to it so the function can test if 
		 * 		there are any other drones in its way
		 */
		int[] pos = nextSquare();
		if (a.canMoveHere(pos[0], pos[1])) {
			x_pos = pos[0];
			y_pos = pos[1];
		}
		else {
			// calls the Direction enum where nextDirection will change bots
			// 		direction to be 90 degrees clockwise from current direction
			dir = dir.nextDirection();
		}
	}
	
	public String toString() {
		/*
		 * creates and returns a string of all the drones information
		 */
		String s = "Drone " + ID + " is at " + x_pos + ", " + y_pos + " facing " + dir;
		//System.out.println(s);
		return s;
	}
		
	public static void main(String[] args) {
		// tests
		Drone d = new Drone(5, 1, Direction.NORTH);
		System.out.println(d.toString());
		d.dir = d.dir.nextDirection();
		System.out.println(d.toString());
		int[] pos = d.nextSquare();
		Drone d2 = new Drone(pos[0], pos[1], d.dir);
		System.out.println(d2.toString());
		
	}
}
