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

import java.util.Comparator;
/**
 * Compares the names of cities for order
 */
public class NameComparator implements Comparator<City>{
	
	@Override
	public int compare(City o1, City o2) {
		return (o1.getName().compareTo(o2.getName()));
	}
}
