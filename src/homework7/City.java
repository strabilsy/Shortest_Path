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

import java.io.Serializable;

import latlng.LatLng;
/**
 * The City class represents a city with a name and GPS coordinates
 */
public class City implements Serializable{
	private String name;
	private LatLng location;
	private int indexPos;
	private static int cityCount;
	
	/**
	 * Creates a new City object with null values
	 */
	public City() {
		indexPos = cityCount;
		cityCount++;
	}
	
	/**
	 * Creates a new City object with a specified name and location
	 * @param city
	 * @param location
	 */
	public City(String city, LatLng location) {
		name = city;
		this.location = location;
		indexPos = cityCount;
		cityCount++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public int getIndexPos() {
		return indexPos;
	}

	public void setIndexPos(int indexPos) {
		this.indexPos = indexPos;
	}

	public static int getCityCount() {
		return cityCount;
	}

	public static void setCityCount(int cityCount) {
		City.cityCount = cityCount;
	}
	
	
}
