/*
 * dineshr93@gmail.com
 * Apache license 2.0
 * 
 */
package com.sh.hopcom.concrete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sh.hopcom.Ihopcoms;

public class Hopcoms implements Ihopcoms{

	@Override
	public HashMap<String, Float> readData(String url) throws IOException {


		Document document = Jsoup.connect(url).get();

		//2nd & 3rdrow
		Elements names1stlist = document.select("#ctl00_LC_grid1 td:eq(1)");
		Elements prices1stlist = document.select("#ctl00_LC_grid1 td:eq(2)");
		//5th & 6throw
		Elements names2ndlist = document.select("#ctl00_LC_grid1 td:eq(4)");
		Elements prices2ndlist = document.select("#ctl00_LC_grid1 td:eq(5)");

		//Last updated Date
		Elements dateElement = document.select("#ctl00_LC_DateText");

		//read Last updated date
		if(dateElement.hasText())printOnlyTitle(dateElement.get(0).text());

		Collection<String> items1 = new ArrayList<String>();
		Collection<String> items2 = new ArrayList<String>();
		Collection<Float> prices1 = new ArrayList<Float>();
		Collection<Float> prices2 = new ArrayList<Float>();

		Collection<String> items = new ArrayList<String>();
		Collection<Float> prices = new ArrayList<Float>();

		for (Element item : names1stlist) {
			if(item.hasText()==true)
				items1.add(item.text());
		}
		for (Element item : names2ndlist) {
			if(item.hasText()==true)
				items2.add(item.text());
		}
		items.addAll(items1);
		items.addAll(items2);


		for (Element item : prices1stlist) {
			if(item.hasText()==true)
				prices1.add(Float.valueOf(item.text()));
		}
		for (Element item : prices2ndlist) {
			if(item.hasText()==true)
				prices2.add(Float.valueOf(item.text()));
		}
		prices.addAll(prices1);
		prices.addAll(prices2);

		String[] itemsArray = items.toArray(new String[0]);
		Float[] pricesArray = prices.toArray(new Float[0]);

		int size = items.size();
		System.out.println("Total Items Listed "+size);
		HashMap<String, Float> hopcomsmap = new HashMap<>();
		for (int i = 0; i < size ; i++) {
			hopcomsmap.put(itemsArray[i],pricesArray[i]);
		}

		return hopcomsmap;
	}

	@Override
	public HashMap<String, Float> sortData(HashMap<String, Float> hopcomsitemMap) {

		// Create a list from elements of HashMap 
		List<Map.Entry<String, Float> > list = 
				new LinkedList<Map.Entry<String, Float> >(hopcomsitemMap.entrySet()); 

		// Sort the list 
		Collections.sort(list, new Comparator<Map.Entry<String, Float> >() { 
			public int compare(Map.Entry<String, Float> o1,  
					Map.Entry<String, Float> o2) 
			{ 
				return (o1.getValue()).compareTo(o2.getValue()); 
			} 
		}); 

		// put data from sorted list to hashmap  
		HashMap<String, Float> temp = new LinkedHashMap<String, Float>(); 
		for (Map.Entry<String, Float> aa : list) { 
			temp.put(aa.getKey(), aa.getValue()); 
		} 
		return temp; 
	}

	int standardLength = 40;
	String filler = "_";
	String finalword="/-";
	int j=0;
	@Override
	public void display(HashMap<String, Float> hopcomsItems) {

		List<String> fruits = new ArrayList<String>(Arrays.asList("Anjura/Fig","Apple Chilli","Apple Delicious",
				"Apple Fuji chaina","Apple Green smith","Apple Washington","Banana chandra","Banana Nendra","Banana pachabale",
				"Banana Yellaki","Chicco(Sapota)","Grapes Blore blue","Grapes Flame","Grapes Krishna sharad","Grapes Red globe",
				"Grapes Sonika","Grapes T.S.","Guava","Guava Allahabad(Red)","Indian Black globe Grapes","Mango Alphans","Mango Alphans box",
				"Mango Amarpalli","Mango Badami","Mango Bygan palli","Mango Dasheri","Mango kalapadu","Mango Kesar",
				"Mango malagova","Mango mallika","Mango Neelam","Mango Raspuri","Mango sakkaregutti","Mango Sendura",
				"Mango thotapuri","Mosambi","Orange","Orange Australia","Orange Ooty","Papaya nati","Papaya red indian",
				"Papaya sola","Papaya Taiwan","Pineapple","Pomegranate Bhagav","S.mellon Local (Luck)","Straw Berry",
				"Watermellon","Watermellon kiran","Lime Local"));
		List<String> LeafyVegetables = new ArrayList<String>(Arrays.asList("Basale Greens","Chakota greens","Dhantu greens","Greens Sabbakki"));
		List<String> NonVeg = new ArrayList<String>(Arrays.asList("Eggs"));


		//Print Vegetale
		printTitle(ENUM.VEGETABLES.toString());

		for (String key: hopcomsItems.keySet()){
			if(!fruits.contains(key)&&!LeafyVegetables.contains(key)&&!NonVeg.contains(key)) {

				printFormatedString((++j)+" ",key,hopcomsItems.get(key).toString(),standardLength,filler,finalword);
			}
		}
		printSpace();

		//print Leafy_Greens
		printRate(hopcomsItems, LeafyVegetables,ENUM.LEAFY_GREENS.toString());

		//print Fruits
		printRate(hopcomsItems, fruits,ENUM.FRUITS.toString());

		//print Non_Veg
		printRate(hopcomsItems, NonVeg,ENUM.NON_VEG.toString());

		j=0;

	}

	private void printFormatedString(String index,String key, String value, int standardLength,String filler,String finalword) {

		int length = index.length()+key.length()+value.length();

		int fillerSize = standardLength - length;
		String finalWord = "",temp = "";

		for (int i = 0; i < fillerSize; i++) {
			temp = temp + filler;
		}

		finalWord=index+key+temp+value+finalword;

		System.out.println(finalWord);

	}

	private void printRate(HashMap<String, Float> hopcomsItems, List<String> types, String title) {
		//int j=0;
		printTitle(title);
		for (String key: hopcomsItems.keySet()){
			if(types.contains(key)) {

				printFormatedString((++j)+" ",key,hopcomsItems.get(key).toString(),standardLength,filler,finalword);

			}
		}
		printSpace();
	}

	private void printSpace() {
		System.out.println();
		//System.out.println();
	}

	private void printTitle(String title) {
		System.out.println("=========================================");
		printFormatedString("",title,"(price low to high)",standardLength," ","|");
		System.out.println("=========================================");
	}
	private void printOnlyTitle(String title) {
		System.out.println("=========================================");
		printFormatedString("",title,"",standardLength," ","|");
		System.out.println("=========================================");
	}
	enum ENUM{
		FRUITS,LEAFY_GREENS,NON_VEG,VEGETABLES

	}

}
