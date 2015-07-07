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
 * Compares the latitudes of cities for order
 */
public class LatComparator implements Comparator<City>{

	@Override
	public int compare(City o1, City o2) {
		if (o1.getLocation().getLat() == o2.getLocation().getLat())
            return 0;
        else if (o1.getLocation().getLat() > o2.getLocation().getLat())
            return 1;
        else
            return -1;
	}

}
