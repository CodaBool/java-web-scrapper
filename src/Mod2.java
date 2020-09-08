import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Mod2 {
    public static void main(String[] args) throws IOException {
		try {
		      int totalWords = 0;
		      TreeMap<String, Integer> freqMap = generateFrequencyList();
		      for (String key : freqMap.keySet()) { //loop to find how many words were in the file (used for the percentage calculation)
		    	  totalWords += freqMap.get(key);
		      }
		      System.out.println("The total number of words found: " + totalWords + "\n"); //prints the total number of words
		      System.out.println("Word\t\tCount\t\tPercentage\n"); //prints the heading for the table
		      
		      for (int i = 0; i < 20; i++) {  //loop to print the top 20 words in a file
		    	  int maxValueInMap = (Collections.max(freqMap.values())); //finds the max frequency in the Treemap
		    	  String keyToRemove = ""; //declare a String for key removal after the row has been displayed
		    	  for (Entry<String, Integer> entry : freqMap.entrySet()) {  // Iterates to display a row
		              if (entry.getValue()==maxValueInMap) { //if the current max value has been found 
		            	  System.out.printf("%s\t|\t%d\t|\t%.2f %%\n", entry.getKey(), maxValueInMap, ((double)freqMap.get(entry.getKey())*100.0/(double)totalWords));
		                  keyToRemove = entry.getKey(); //sets the String for removal outside for loop
		              }
		          }
		    	  freqMap.remove(keyToRemove); //removes the key so that Collections.max() can find the 2nd max key 
		      }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    public static TreeMap<String, Integer> generateFrequencyList() throws IOException {
	    TreeMap<String, Integer> freqMap = new TreeMap<String, Integer>(); //A Treemap for word storage: ("the_word , frequency")
	    
	    //Jsoup connect DOC: https://jsoup.org/apidocs/org/jsoup/Jsoup.html#connect-java.lang.String-
//    	Document doc = Jsoup.connect("http://shakespeare.mit.edu/macbeth/full.html").maxBodySize(0).timeout(0).get(); //Load from URL
	    
    	File input = new File("C:/Users/Dougie/Downloads/macbeth.html"); //Load from .HTML file 
    	//Jsoup parse DOC: https://jsoup.org/apidocs/org/jsoup/Jsoup.html#parse-java.io.InputStream-java.lang.String-java.lang.String-
    	Document doc = Jsoup.parse(input, "UTF-8", "http://shakespeare.mit.edu/macbeth/full.html"); //takes a baseUri to resolve relative links against
    	
		String [] tokens = doc.text().split("\\s+"); //split entire .HTML text into tokens in a String array based on spaces
		
		for (String token : tokens) { //iterates through the entire array of words
			token = token.replaceAll("[^a-zA-Z]", ""); //removes all punctuation
			token = token.toLowerCase(); //lowercase all tokens
			if (!freqMap.containsKey(token)) {
				freqMap.put(token, 1); //if a word is occurring for the first time it will be given a 1 frequency
			} else {
				int count = freqMap.get(token); //gets the current frequency
				freqMap.put(token, count + 1); //adds one to the current frequency
			}
		}
		
		//writes the document to a write.txt file in workspace for testing purposes
//		String fileContent = doc.text();
//		BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Dougie/eclipse-workspace/Mod 2/write.txt")); //use write directory
//		writer.write(fileContent);
//		writer.close();
		
		return freqMap;
	  }
}