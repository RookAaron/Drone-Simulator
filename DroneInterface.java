import java.util.Scanner;

public class DroneInterface {
	/*
	 * this is the main class, needs to be run first and will call all other classes
	 * this deals with the user interface and allows the user to interact with all
	 * 		the drones in many different ways
	 */

	//set constants for class
	private Scanner input; // creates scanner which is used for user input
	private DroneArena arena; // creates an DroneArena class
	private SaveLoadFiles saveLoad = new SaveLoadFiles(); // calls class to save and load text files
	
	DroneInterface(int x, int y){
		/*
		 * constructor takes two values which relate to the size of the arena
		 */
		input = new Scanner(System.in);
		arena = new DroneArena(x,y);
	}

	private void pause() {
		/*
		 * this pauses the code for 200 milliseconds
		 */
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// if fails try again
			pause();
		}
    }
	
	private void doDisplay() {
		/*
		 * creates a new ConsoleCanvas and calls it to show arena
		 */
		int[] size = arena.getArenaSize();
		ConsoleCanvas c = new ConsoleCanvas(size[0], size[1]);
		arena.showDrones(c); // in here, it calls c.toString()
	}
	
	private int[] getSizeInput() {
		/*
		 * allows the user to input the size of the arena
		 * returns input as a array
		 */
		int[] size = new int[2];
		char xORy = 'x';
		for (int i = 0; i < 2; i++) {
			System.out.print("\nEnter the " +xORy+ " you want for the arena size: ");
			size[i] = input.nextInt();
			xORy = 'y';
		}
		return size;
	}
	
	private String menuMessage() {
		/*
		 * creates the menu string with all the options
		 */
		String msg = "";
		msg += "\nEnter 'c' to create a new arena";
		msg += "\nEnter 'a' to add a drone to arena";
		msg += "\nEnter 'i' to see infomation";
		msg += "\nEnter 'd' to show arena and drones";
		msg += "\nEnter 'm' to move all drones in arena";
		msg += "\nEnter 'n' to move all drones 10 times";
		msg += "\nEnter 's' to save this file";
		msg += "\nEnter 'l' to load a file";
		msg += "\nEnter 'x' to exit";
		msg += "\n\t>> ";
		return msg;
	}
	
	private void menuInput() {
		/*
		 * the user selects which option they want and the code
		 * 		then runs the correct bits
		 */
		char user_input;
		String menuMsg = menuMessage();
		do {
			System.out.print(menuMsg);
			user_input = input.next().charAt(0); // gets user input, only one character
			input.nextLine();
			
			switch (user_input) {
			case 'c':
			case 'C':
				// create new arena
				int[] size = getSizeInput();
				arena = new DroneArena(size[0],size[1]);
				break;
			case 'a':
			case 'A':
				// adds a drone to the arena
				arena.addDrone();
				break;
			case 'i':
			case 'I':
				// displays all the information about the arena
				System.out.println(arena.toString());
				break;
			case 'd':
			case 'D':
				// uses ConsoleCanvas to display arena
				doDisplay();
				break;
			case 'm':
			case 'M':
				// moves all the drones once
				arena.moveAllDrones();
				doDisplay();
				break;
			case 'n':
			case 'N':
				// moves all the drones 10 times
				for (int i = 0; i < 10; i++){
					if (i != 0) pause();
					arena.moveAllDrones();
					doDisplay();
					System.out.println(arena.toString());
				}
				break;
			case 's':
			case 'S':
				// saves the arena by first stripping arena and then sending it to text file
				String arena_info = arena.stripObject();
				saveLoad.writeFile(arena_info);
				break;
			case 'l':
			case 'L':
				// loads the arena by loading text file and then setting the data to be new arena
				String arena_info_2 = saveLoad.readFile();
				arena.unstripObject(arena_info_2);
				doDisplay();
				break;
			case 'x':
			case 'X':
				// in case user entered lower case, will break out of loop still
				user_input = 'X';
				break;
			default:
				// invalid option
				System.out.println("Please enter one of the options");
			}
		} while (user_input != 'X');
	}
	
	public static void main(String[] args) {
		// main class
		// needs to be run first for all other classes to be used
		DroneInterface gui = new DroneInterface(20,6);
		gui.menuInput();
	}
	
}
