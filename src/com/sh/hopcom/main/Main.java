/*
 * dineshr93@gmail.com
 * Apache license 2.0
 * 
 */
package com.sh.hopcom.main;



import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import com.sh.hopcom.concrete.Hopcoms;

public class Main {

	public static void main(String[] args) throws IOException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM_dd_yyyy");
		LocalDate localDate = LocalDate.now();

		String fileName = dtf.format(localDate)+"_Hopcoms"+".txt";
		String url = "https://hopcoms.kar.nic.in/(S(1uvvvq55ledgfuzbeavr2by5))/RateList.aspx";

		System.setOut(new PrintStream(new FileOutputStream(fileName)));
		Hopcoms h = new Hopcoms();
		HashMap<String, Float> data = h.readData(url);

		HashMap<String, Float> sortedData = h.sortData(data);

		h.display(sortedData);
		
		//Open the file
		Desktop.getDesktop().open(new File(fileName));

	}

}
/*
 * dineshr93@gmail.com
 * 
 */