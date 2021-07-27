
public class ConsoleCanvas {
	/*
	 * this class is in charge of displaying the drones on the screen as
	 * 		an easily viewable text art
	 */

	//set constants for class
	//private char drone_character = 'D';
	private int width; // width of arena
	private int height; // height of arena
	private char[][] arena_str; // will store the arena as a 2D array
	
	ConsoleCanvas(int w, int h){
		/*
		 * constructor that sets the width and height of arena
		 * also sets the size of the arena array and sets all the values of the array
		 * 		to be the border or an empty space
		 */
		width = w;
		height = h;
		arena_str = new char[height+2][width+2]; // +2 to include the border as well
		
		// loops through all elements and sets it to the appropriate char
		for (int i = 0; i < arena_str.length; i++) {
			for (int j = 0; j < arena_str[i].length; j++) {
				if (i == 0 || j == 0 || i == arena_str.length -1 || j == arena_str[0].length -1) {
					arena_str[i][j] = '#'; // adds border
				}
				else {arena_str[i][j] = ' ';} // adds empty square
			}
		}
	}
	
	public void addDrone(int x, int y, char display) {
		/*
		 * changes one of the blank squares of the array to be a drone instead
		 */
		arena_str[y+1][x+1] = display; // +1 because of the border
	}
	
	public String toString() {
		/*
		 * displays the arena with border, drones and blank spaces
		 */
		String str = "";
		// loops through every element adding it to the string in the correct order
		for (int i = 0; i < arena_str.length; i++) {
			for (int j = 0; j < arena_str[i].length; j++) {
				str += arena_str[i][j];
			}
			if (i != arena_str.length -1) str += '\n';
		}
		return str;
	}

	public static void main(String[] args) {
		// tests
		ConsoleCanvas c = new ConsoleCanvas (10, 5);
		System.out.println(c.toString());
		c.addDrone(2, 3, 'D');
		System.out.println(c.toString());
	}
	
}
