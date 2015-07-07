/**
 * Samier Trabilsy
 * Student ID: 109839226
 * Homework #7
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Samier
 */
package homework7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * The SigmaAirDriver class contains a main method that interacts with the user
 */
public class SigmaAirDriver {
	/**
	 * The main method presents a menu for the user to interact with. The menu has the following options:
	 * <br><br>
	 * (A) Add City <br> Adds a City to the list of cities. 
	 * <br><br>
	 * (B) Add Connection <br> Adds a connection to the adjacency matrix.
	 * <br><br>
	 * (C) Load all cities <br> Loads all cities from a file.
	 * <br><br>
	 * (D) Load all connections <br> Loads all connections from a file.
	 * <br><br>
	 * (E) Print all Cities <br> Displays another menu that allows the user to sort the list based on a specific field.
	 * <br>
	 * <li>(EA) Sort by City Name <br> Displays a table that includes all cities sorted by name.
	 * <li>(EB) Sort by Latitude <br> Displays a table that includes all cities sorted by latitude.
	 * <li>(EC) Sort by Longitude <br> Displays a table that includes all cities sorted by longitude.
	 * <li>(Q) Quit <br> Returns to main menu.
	 * <br><br>
	 * (F) Print all Connections <br> Prints all cities, and their available connections.
	 * <br><br>
	 * (G) Remove Connection <br> Removes a connection from the adjacency matrix.
	 * <br><br>
	 * (H) Find Shortest Path <br> Displays the path from the starting city, all intermediate cities, and destination city. 
	 * Displays the total distance as well.
	 * <br><br>
	 * (Q) Quit <br> Terminates the program and saves the data to sigma_air.obj.
	 * <br>
	 */
	public static void main(String[] args) {
		String menu = "\n(A) Add City\n(B) Add Connection\n(C) Load all Cities\n(D) Load all Connections\n"
				+ "(E) Print all Cities\n(F) Print all Connections\n(G) Remove Connection\n(H) Find Shortest Path\n(Q) Quit\n"
				+ "\nEnter a selection: ";
		
		SigmaAir sigmaAir;
		try {
		     FileInputStream file = new FileInputStream("sigma_air.obj");
		     ObjectInputStream fin  = new ObjectInputStream(file);
		     sigmaAir = (SigmaAir) fin.readObject(); 
		     fin.close();
		     System.out.println("Loaded SigmaAir from sigma_air.obj.\n");
		} catch(IOException | ClassNotFoundException e){
			System.out.println("sigma_air.obj is not found. New SigmaAir object will be created.\n");
			sigmaAir = new SigmaAir();
		}
		
		Scanner input = new Scanner(System.in);
		String choice, subChoice;
		String cityName, cityTo, cityFrom, fileName;
		String submenu = "\n(EA) Sort Cities by Name\n(EB) Sort Cities by Latitude\n(EC) Sort Cities by Longitude\n(Q) Quit\n"
				+ "\nEnter a selection: ";
		char letter, subLetter, sortType;
		do {
			System.out.print(menu);
			//input = new Scanner(System.in);
		    choice = input.next();
		    System.out.println();
		    letter = choice.charAt(0);
		    switch(Character.toUpperCase(letter)) {
		    case('A'):
		    	System.out.print("Enter the name of the city: ");
		    	input.nextLine();
		    	cityName = input.nextLine();
		    	System.out.println();
		    	sigmaAir.addCity(cityName);
		    	break;
		    	
		    case('B'): 
		    	System.out.print("Enter source city: ");
		    	input.nextLine();
		    	cityFrom = input.nextLine();
		    	System.out.print("Enter destination city: ");
		    	cityTo = input.nextLine();
		    	System.out.println();
		    	sigmaAir.addConnection(cityFrom, cityTo);
		    	break;
		    	
		    case('C'):
		    	System.out.print("Enter the file name: ");
		    	fileName = input.next();
		    	System.out.println();
		    	sigmaAir.loadAllCities(fileName);
		    	break;
		    	
		    case('D'):
		    	System.out.print("Enter the file name: ");
			    fileName = input.next();
			    System.out.println();
			    sigmaAir.loadAllConnections(fileName);
			    break;
			    
		    case('E'):
		    	if (sigmaAir.size() > 0) {
		    		do{
		    			System.out.print(submenu);
		    			subChoice = input.next();
		    			subLetter = Character.toUpperCase(subChoice.charAt(0));
		    			if (subLetter == 'E' && subChoice.length() > 1){
		    				sortType = Character.toUpperCase(subChoice.charAt(1));
		    				switch(sortType) {
		    				case('A'):
		    					sigmaAir.printAllCities(new NameComparator());
		    				break;
		    				case('B'):
		    					sigmaAir.printAllCities(new LatComparator());
		    				break;
		    				case('C'):
		    					sigmaAir.printAllCities(new LngComparator());
		    				break;
		    				}
		    			}
		    		}while(subLetter!='Q');
		    	}
		    	else
		    		System.out.println("The list is empty.");
		    	break;
		    	
		    case('F'):
		    	if (sigmaAir.size() > 0) {
		    		sigmaAir.printAllConnections();
		    	}
		    	else
		    		System.out.println("The list is empty.");
		    	break;
		    	
		    case('G'):
		    	System.out.print("Enter source city: ");
		    	input.nextLine();
		    	cityFrom = input.nextLine();
		    	System.out.print("Enter destination city: ");
		    	cityTo = input.nextLine();
		    	System.out.println();
		    	sigmaAir.removeConnection(cityFrom, cityTo);
		    	break;
		    case('H'):
		    	System.out.print("Enter source city: ");
		    	input.nextLine();
		    	cityFrom = input.nextLine();
		    	System.out.print("Enter destination city: ");
		    	cityTo = input.nextLine();
		    	System.out.println();
		    	System.out.println(sigmaAir.shortestPath(cityFrom, cityTo));
		    	break;
		    case('Q'):
		    	try {
		    	      FileOutputStream file = new FileOutputStream("sigma_air.obj");
		    	      ObjectOutputStream fout = new ObjectOutputStream(file);
		    	      fout.writeObject(sigmaAir); //Writes sigmaAir to sigma_air.obj
		    	      fout.close();
		    	      System.out.println("\nSigmaAir object saved into file sigma_air.obj.");
		    	} catch (IOException e){
		    		System.out.println("Could not save the data to sigma_air.obj");
		    	}
		    	System.out.print("Program terminating normally...");
		    	input.close();
		    	System.exit(0);
		    }
		}while(letter!='Q');
		
	}

}
