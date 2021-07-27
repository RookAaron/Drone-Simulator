import java.util.ArrayList;
import java.util.Random;

public class DroneArena {
	/*
	 * this class holds all the drones within the arena and the arena size
	 * 
	 * the stripObject and unstripObject will be used to store the class
	 * 		as a text file and then convert a text file back into the
	 * 		same class
	 * 
	 * move all drones will loop through every drone and move it once
	 * 		the order the drones move in will be in order to their index
	 * 		in array
	 */
	
	//set constants for class
	private ArrayList<Drone> Drones; // holds an array of all drones
	private int[] size = new int[2]; // size of the arena
	private Random randGen = new Random(); // allows me to call the built-in function random
	
	DroneArena(int width, int height){
		/*
		 * constructor which sets the size of the arena
		 * 		and creates a new array of Drones where
		 * 			each element is a drone class
		 */
		Drones = new ArrayList<Drone>();
		size[0] = width;
		size[1] = height;
	}
	
	public int[] getArenaSize() {return size;} // returns size as a array of length 2
	
	public void addDrone() {
		/*
		 * adds a drone to the Drones array
		 * sets drone position to be random
		 * the drone position will not be the same as an existing drone
		 * 		and exist within the arena
		 */
		int x;
		int y;
		do {
			x = randGen.nextInt(size[0]);
			y = randGen.nextInt(size[1]);
		} while (isDroneAt(x,y));
		Drone d = new Drone(x,y,Direction.randomDirection());
		Drones.add(d); // adds drone to array
	}
	
	public void removeDrones() {
		/*
		 * this removes all Drones in array
		 * this is used when the user loads a new arena
		 */
		Drones = new ArrayList<Drone>();
	}
	
	private boolean isDroneAt(int x, int y) {
		/*
		 * loops through all drones to see if a drone is at given x,y
		 * returns the answer as boolean
		 */
		for(Drone d:Drones) {
			if (d.getPos()[0] == x && d.getPos()[1] == y) return true;
		}
		return false;
	}
	
	public boolean canMoveHere(int x, int y) {
		/*
		 * sees if a drone can move to square
		 * tests if drone is already there or it is the arena border
		 */
		if (x < 0 || x >= size[0]) return false;
		if (y < 0 || y >= size[1]) return false;
		if (isDroneAt(x, y)) return false;
		return true;
	}
	
	public void moveAllDrones() {
		/*
		 * loops through all drones and runs the drone tryToMove method
		 */
		for (Drone d:Drones) {
			d.tryToMove(this);
		}
	}
	
	/*private Drone getDroneAt(int x, int y) {
		for(Drone d:Drones) {
			if (d.getPos()[0] == x && d.getPos()[1] == y) return d;
		}
	}*/
	
	public String stripObject() {
		/*
		 * this takes all the information of the arena and compresses it into
		 * 		a string that can be converted back to get the same class back
		 * 
		 * all bits look like Ci;x,y,d where C is type of object, i is ID, x,y is
		 * 		position and d is direction
		 */
		String arena_info = "";
		arena_info +=  "S;" + size[0] + "," + size[1];
		
		int[] pos = new int[2];
		for(Drone d:Drones) {
			pos = d.getPos();
			arena_info += "D" + d.getID() + ";" + pos[0] + "," + pos[1] + "," + d.getDir();
		}
		arena_info += "X"; // this tells the code it has reached the end
		return arena_info;
	}
	
	private void changeArena(String[] new_data) {
		/*
		 * this is passed an array telling the arena what to change
		 * array is in format {"C","i","x","y","d"} where each element
		 * 		means the same as above method
		 */
		if (new_data[0].equals("S")) { // this means change size of the arena
			size[0] = Integer.valueOf(new_data[2]);
			size[1] = Integer.valueOf(new_data[3]);
		}
		else if (new_data[0].equals("D")) { // add the drone with relevant info
			long id = Long.valueOf(new_data[1]);
			int x = Integer.valueOf(new_data[2]);
			int y = Integer.valueOf(new_data[3]);
			Direction dir = Direction.setDirection(Integer.valueOf(new_data[4]));
			Drone d = new Drone(x,y,dir);
			d.setID(id);
			Drones.add(d);
		}
	}
	
	public void unstripObject(String arena_info) {
		/*
		 * this takes a string and strips it so it can create an arena
		 * 		the string is containing
		 * array is in format {"C","i","x","y","d"} where each element
		 * 		means the same as above method
		 * 
		 * will erase any unsaved progress
		 */
		removeDrones(); // removes all drones
		
		String[] data = {"","","","",""};
		int index = 0;
		for (char ch : arena_info.toCharArray()) { // loops through each char of string
			if ((int)ch >= (int)'A' && (int)ch <= (int)'Z') {
				changeArena(data); // calls the changeArena where the arena data is set
				// resets for next bit of info
				index = 0;
				for (int i = 0; i < data.length; i++) data[i] = "";
				data[index] += ch;
				index++;
			}
			else if (ch == ',' || ch == ';') index++; //breaks in the string for new info
			else {data[index] += ch;} // add bit of data to array
		}
	}
	
	
	public void showDrones(ConsoleCanvas c) {
		/*
		 * loops through all drones and adds to ConsoleCanvas
		 * then uses ConsoleCancas toString to display all
		 * 		drones and their information
		 */
		for(Drone d:Drones) {
			d.displayDrone(c);
		}
		System.out.println(c.toString());
	}
	
	public String toString() {
		/*
		 * displays all information about this class and all the information
		 * 		it holds about the drones
		 */
		String s = "Arena is of size " + size[0] + ", " + size[1];
		for(Drone d:Drones) {
			s += '\n' + d.toString();
		}
		return s;
	}
	
	public static void main(String[] args) {
		// tests
		DroneArena arena = new DroneArena(20,10);
		for (int i = 0; i < 6; i ++) arena.addDrone();
		System.out.println(arena.toString());
		String s = arena.stripObject();
		System.out.println(s);
		arena.unstripObject("S;10,6D0;1,9,3D1;10,0,0D2;17,4,1D3;14,6,2D4;8,1,3D5;0,1,2X");
		System.out.println(arena.toString());
	}
	
}
