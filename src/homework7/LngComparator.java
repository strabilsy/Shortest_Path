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
 * Compares the longitudes of cities for order
 */
public class LngComparator implements Comparator<City>{

	@Override
	public int compare(City o1, City o2) {
		if (o1.getLocation().getLng() == o2.getLocation().getLng())
            return 0;
        else if (o1.getLocation().getLng() > o2.getLocation().getLng())
            return 1;
        else
            return -1;
	}

}
