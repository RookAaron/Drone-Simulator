import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class SaveLoadFiles {
	/*
	 * this will allow the code to save and load a string to a text file
	 */
	
	//set constants for class
	private String dir_path = "C://Users/aaron/eclipse-workspace/"; // declares path to send file
	private String f_type = ".txt"; // type of file being saved
	Scanner input; // declares scanner
	File f; // declares file
	
	private boolean doesFileExist(String name) {
		/*
		 * checks to see if file already exists or not so user does not overwrite any files
		 */
		f = new File(name);
		if (f.exists()) return true;
		return false;
	}
	
	private String getUserInput() {
		/*
		 * asks for users input and returns it back as a string
		 */
		input = new Scanner(System.in);
		System.out.print(">> ");
		String user_input = input.nextLine(); // gets users input line
		return user_input;
	}
	
	private String getFileName() {
		/*
		 * gets user to input file name
		 * keeps looping until valid file name is added
		 */
		String user_input;
		do {
			System.out.println("Enter the file name");
			user_input = getUserInput();
		} while (doesFileExist(user_input));
		return user_input;
	}
	
	public void writeFile(String info) {
		/*
		 * writes to info to text file
		 */
		String filename = getFileName();
		filename = dir_path + filename + f_type;
		
		try {
			// takes filenames and opens it into a veiw to accepts strings
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			//writes string to file
			writer.write(info);
			// closes file
			writer.close();
		} catch (IOException e) {
			System.out.println("Error: Could not save arena");
		}
	}

	public String readFile() {
		/*
		 * loads the text file in a returns it as a string
		 */
		String filename = getFileName();
		filename = dir_path + filename + f_type;
		String data = "";
		
		// allows code to read in text file format
		BufferedReader br = null;
		try {
			// reads in correct text file
			br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			// takes text file into string
		    String l = br.readLine();

		    while (l != null) {
		    	// goes through each line and adds to string
		        sb.append(l);
		        sb.append(System.lineSeparator());
		        l = br.readLine();
		    }
		    data = sb.toString();
		    br.close();
		} catch (IOException e) {
			System.out.println("Error: Could not load arena");
		}
		return data;
	}

	
	public static void main(String[] args) {
		//tests
		SaveLoadFiles test = new SaveLoadFiles();
		DroneArena arena = new DroneArena(20,6);
		for (int i = 0; i < 5; i++) arena.addDrone();
		System.out.println("Try writing ---------------------");
		test.writeFile(arena.stripObject());
		System.out.println("Try reading ---------------------");
		test.readFile();
	}
}
