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


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.google.code.geocoder.*;
import com.google.code.geocoder.model.*;

import latlng.LatLng;

/**
 * The SigmaAir class contains an adjacency matrix for all airports. 
 */
public class SigmaAir implements Serializable {
	private ArrayList<City> cities; //contains all cities with an airport.
	
	public static final int MAX_CITIES = 100; // - maximum number of cities we are accommodating for in our application
	/**
	 * Adjacency matrix used in our application
	 * <br>connection[i][j] represents a link from city at i, to a city at j. 
	 * <br>The value is the distance between the 2 cities; 0 represents a link from i to i, and INFINITY indicates that no link exists.
	 */
	private double[][] connections;


	/**
	 * Creates a SigmaAir object
	 */
	public SigmaAir() { //- Constructor for SigmaAir object.Initializes all declared variables.
		cities = new ArrayList<City>();
		connections = new double[MAX_CITIES][MAX_CITIES];
		for(int u = 0; u < MAX_CITIES; u++){
            for(int v = 0; v < MAX_CITIES; v++){
            	connections[u][v] = Double.POSITIVE_INFINITY;
            }
		}
	}
	
	/**
	 * Helper method that checks if the String argument is the name of a City object in the list
	 * @param city
	 * @return Index of the City object, if it exists; -1 otherwise
	 */
	private int cityExists(String city) {
		for (City a : cities) {
			if (a.getName().equals(city))
				return a.getIndexPos();
		}
		return -1;
	}
	
	/**
	 * Returns the number of cities in the list
	 * @return number of cities
	 */
	public int size() {
		return cities.size();
	}
	
	/**
	 * Adds a new City object to the list if it does not already exist
	 * @param city
	 */
	public void addCity(String city) { 
		if (cityExists(city) == -1) {
			try {
				Geocoder geocoder = new Geocoder();
				GeocoderRequest geocoderRequest;
				GeocodeResponse geocodeResponse;
				//String addr;
				double lat;
				double lng;

				geocoderRequest = new GeocoderRequestBuilder().setAddress(city).getGeocoderRequest();
				geocodeResponse = geocoder.geocode(geocoderRequest);
				//addr = geocodeResponse.getResults().get(0).getFormattedAddress();
				lat = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
				lng = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
				LatLng location = new LatLng(lat, lng);
				City newCity = new City(city, location);

				cities.add(newCity);
				connections[newCity.getIndexPos()][newCity.getIndexPos()] = 0;
				System.out.println(city + " has been added: (" + lat + ", " + lng + ")");
			} catch (Exception e) {
				System.out.println("Geocoder error.");
			}
		}
		else 
			System.out.println(city +" already exists in the list.");
	}
	
	/**
	 * If appropriate cities are given, calculates the distance between the cities, and includes this entry in the connections matrix
	 * @param cityFrom
	 * @param cityTo
	 */
	public void addConnection(String cityFrom, String cityTo) { 
		int cfIndex = cityExists(cityFrom);
		int ctIndex = cityExists(cityTo);
		
		if (cfIndex != -1 && ctIndex != -1) {
			double distance = LatLng.calculateDistance(cities.get(cfIndex).getLocation(), cities.get(ctIndex).getLocation());
			connections[cfIndex][ctIndex] = distance;
			System.out.println(cityFrom + " --> " + cityTo + " added: " + distance);
		}
		else 
			System.out.println("Error adding connection: " + cityFrom + " --> " + cityTo);
	}
	
	/**
	 * If appropriate cities are given, removes the entry from the connections table
	 * @param cityFrom
	 * @param cityTo
	 */
	public void removeConnection(String cityFrom, String cityTo) {
		int cfIndex = cityExists(cityFrom);
		int ctIndex = cityExists(cityTo);
		
		if (cfIndex != -1 && ctIndex != -1) {
			connections[cfIndex][ctIndex] = Double.POSITIVE_INFINITY;
			System.out.println("Connection from " + cityFrom + " to " + cityTo + " has been removed!");
		}
		else 
			System.out.println("Error removing connection: " + cityFrom + " --> " + cityTo);
	}
	
	/**
	 * If appropriate cities are given, finds the shortest path between the cities, and returns a String representation of the path, if it exists
	 * Implements the Floyd Warshall algorithm to deteremine the distance of the shortest path
	 * @param cityFrom
	 * @param cityTo
	 * @return The shortest path between the two cities, if it exists
	 */
	public String shortestPath(String cityFrom, String cityTo) { 
		String s1 = "Shortest path from " + cityFrom + " to " + cityTo;
		int cfIndex = cityExists(cityFrom);
		int ctIndex = cityExists(cityTo);
		
		if (cfIndex != -1 && ctIndex != -1) {
			double[][] dist = new double[MAX_CITIES][MAX_CITIES];
			City[][] next = new City[MAX_CITIES][MAX_CITIES];
			
			//procedure FloydWarshallWithPathReconstruction ()
			for(int u = 0; u < size(); u++){
	            for(int v = 0; v < size(); v++){
	                dist[u][v] = connections[u][v];
	                next[u][v] = cities.get(v);
	            }
	        }
			
			for(int k = 0; k < size(); k++){
	            for(int i = 0; i < size(); i++){
	                for(int j = 0; j < size(); j++){
	                    if(dist[i][k] + dist[k][j] < dist[i][j])
	                    {
	                        dist[i][j] = dist[i][k] + dist[k][j];
	                        next[i][j] = next[i][k];
	                    }
	                }
	            }
	        }
			String path = path(cfIndex, ctIndex, next);
			if (dist[cfIndex][ctIndex] != Double.POSITIVE_INFINITY)
				return s1 + ":\n"+ path + " : " + dist[cfIndex][ctIndex]; 
		}
		return s1 + " does not exist!";
	}
	
	/**
	 * Helper method that returns the shortest path between the two cities
	 * @param u index of the source city
	 * @param v index of the destination city
	 * @param next 2D array of vertices
	 * @return String representation of shortest path, if it exists
	 */
	private String path(int u, int v, City[][] next) {
		if (next[u][v] == null)
			return "";//no path exists
		//":\n" + cities.get(u).getName() + " --> " + cities.get(v).getName();
		String path = cities.get(u).getName();
		while (!cities.get(u).equals(cities.get(v))) {
			u = next[u][v].getIndexPos();
			path += " --> " + cities.get(u).getName();
		}
		return path;		
	}
	
	/**
	 * Prints all cities in the order based on the given Comparator
	 * @param comp the comparator
	 */
	public void printAllCities(Comparator<City> comp) { 
		@SuppressWarnings("unchecked")
		ArrayList<City> sorted = (ArrayList<City>)cities.clone();
		Collections.sort(sorted, comp);
		System.out.println("\nCities:");
		System.out.print(String.format("%-30s%-15s%-15s", "City Name", "Latitude", "Longitude")
				+ "\n-------------------------------------------------------------\n");
		for (City c: sorted){
			System.out.printf("%-30s%-15f%-15f\n", c.getName(), c.getLocation().getLat(), c.getLocation().getLng());
		} 
	}
	
	/**
	 * Prints all connections available from all recorded cities
	 */
	public void printAllConnections() {
		System.out.print(String.format("%-35s%-20s", "Route", "Distance")
				+ "\n----------------------------------------------------------\n");
		for(int u = 0; u < MAX_CITIES; u++){
            for(int v = 0; v < MAX_CITIES; v++){
            	if (connections[u][v] > 0 && connections[u][v] != Double.POSITIVE_INFINITY)
            		System.out.printf("%-35s%-20f\n", cities.get(u).getName() + " --> " + cities.get(v).getName(), connections[u][v]);
            }
		}
	}
	
	/**
	 * Parses a file and adds all cities to the city list.
	 * @param filename
	 */
	public void loadAllCities(String filename) { 
		Scanner reader;
		try {
			reader = new Scanner(new File(filename));
			String cityName;

			while (reader.hasNext()) {
				cityName = reader.nextLine();
				addCity(cityName);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Parses a file and adds all connections to the connections matrix.
	 * @param filename
	 */
	public void loadAllConnections(String filename)  { 
		Scanner reader;
		try {
			reader = new Scanner(new File(filename));
			String[] connection;

			while (reader.hasNext()) {
				connection = reader.nextLine().split(",");
				addConnection(connection[0], connection[1]);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
