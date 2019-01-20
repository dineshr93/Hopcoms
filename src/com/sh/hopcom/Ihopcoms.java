/*
 * dineshr93@gmail.com
 * Apache license 2.0
 * 
 */
package com.sh.hopcom;

import java.io.IOException;
import java.util.HashMap;

public interface Ihopcoms {

	HashMap<String, Float> readData(String url) throws IOException;
	
	HashMap<String, Float> sortData(HashMap<String, Float> hopcomsitemMap);
	
	void display(HashMap<String, Float> hopcomsItems);

	
}
