package com.paulgeorge.imdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class BaconFinder {
	private HashMap<String,Integer> baconList = new HashMap<String, Integer>();
	private HashMap<String, HashSet<String>> movieList = new HashMap<String, HashSet<String>>();
	private HashMap<String, HashSet<String>> actorList = new HashMap<String, HashSet<String>>();
	
	public static void main(String[] args) {
		BaconFinder bf = new BaconFinder();
		try {
			//bf.buildLists("/actors.list");
			//bf.buildLists("/actresses.list");
			bf.buildLists("/actors2.list");
			bf.buildLists("/actresses2.list");
			//bf.buildLists("/actors3.list");
			//bf.buildLists("/actresses3.list");
			

			bf.findBacons();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BaconFinder() {
	}
	
	
	/************************************************************************************
	 * 
	 * @param previousEntries
	 * @param baconNumber
	 * @return
	 ************************************************************************************/
	private HashMap<String, String> findBaconEntries( HashMap<String, String> previousEntries, int baconNumber) {
		HashMap<String, String> baconEntries = new HashMap<String, String>();
		for ( String p : previousEntries.keySet()) {
			HashSet<String> movies = actorList.get(p);
			if ( movies == null ) {
				System.out.println("Actor: " + p + " has No Movies!");
				continue;
			}
			for ( String thisMovie : movies ) {
				HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
				for ( String anActor : actorsInThisMovie ) {
					if ( !baconList.containsKey(anActor)) {
						baconList.put(anActor,baconNumber);
						String origTrace = previousEntries.get(p);
						//System.out.println("Original Trace: " + origTrace);
						baconEntries.put(anActor,  anActor + " and " + p + " appeared in " + thisMovie + "\n" + origTrace );
					}
				}
			}
		}
		return baconEntries;
	}
	
	/********************************************************************************
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 ********************************************************************************/
	private void findBacons() {
		if ( actorList.containsKey("Bacon, Kevin (I)")) {
			System.out.println("We found BACON!");
			HashSet<String> baconMovies = actorList.get("Bacon, Kevin (I)");
			HashMap<String, String> baconOnes = new HashMap<String, String>();
			for ( String movie : baconMovies ) {
				if ( movieList.containsKey(movie)) {
					HashSet<String> baconOneNames = movieList.get(movie);
					//System.out.println("For movie : " + movie + " -- There are : " + baconOneNames.size() + " actors.");
					
					for ( String n : baconOneNames ) {
						if ( !baconList.containsKey(n)) {
							baconList.put(n, 1);
							String origTrace = n + " and Kevin Bacon appeared in " + movie;
							baconOnes.put(n, origTrace);
							System.out.println("Trace : " + origTrace);
						}
					}
				}
			}
			System.out.println("There are " + baconOnes.size() + " actors with BaconNumber = 1.");
		
			//if ( true ) System.exit(0);
			
			
			List<HashMap<String, String>> baconLists = new ArrayList<HashMap<String,String>>();
			baconLists.add(baconOnes);
			int baconNumber = 1;
			for ( int x = 1; x < 13 ; x++ ) {
				baconNumber = x+1;
				HashMap<String,String> previousBacons = baconLists.get(x-1);
				
				HashMap<String,String> currentBacons = findBaconEntries( previousBacons, baconNumber);
				
				baconLists.add( currentBacons );
				System.out.println("There are " + currentBacons.size() + " actors with BaconNumber = " + baconNumber);
			}
			
			HashMap<String,String> the8s = baconLists.get(6);
			for ( String p : the8s.keySet()) {
				System.out.println("Person: " + p);
				System.out.println( the8s.get(p) );
				System.out.println("\n\n\n");
			}
			
			/*
			for ( String p : baconOnes.keySet()) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,2);
							String origTrace = baconOnes.get(anActor);
							baconTwos.put(anActor, origTrace + "\n" + anActor + " and " + p + " appeared in " + thisMovie);
						}
					}
				}
			}
			System.out.println("There are " + baconTwos.size() + " actors with BaconNumber = 2.");
			
			
			
			
			
			
			
			HashMap<String, String> baconTwos = new HashMap<String, String>();
			for ( String p : baconOnes.keySet()) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,2);
							String origTrace = baconOnes.get(anActor);
							baconTwos.put(anActor, origTrace + "\n" + anActor + " and " + p + " appeared in " + thisMovie);
						}
					}
				}
			}
			System.out.println("There are " + baconTwos.size() + " actors with BaconNumber = 2.");
			
			HashSet<String> baconThrees = new HashSet<String>();
			for ( String p : baconTwos) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,3);
							baconThrees.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconThrees.size() + " actors with BaconNumber = 3.");
			
			HashSet<String> baconFours = new HashSet<String>();
			for ( String p : baconThrees) {
				HashSet<String> movies = actorList.get(p);
				if (movies == null ) {
					System.out.println("Actor: " + p + " HAS NO MOVIES!");
					continue;
				}
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,4);
							baconFours.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconFours.size() + " actors with BaconNumber = 4.");
			
			HashSet<String> baconFives = new HashSet<String>();
			for ( String p : baconFours) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,5);
							baconFives.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconFives.size() + " actors with BaconNumber = 5.");
			
			HashSet<String> baconSixes = new HashSet<String>();
			for ( String p : baconFives) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,6);
							baconSixes.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconSixes.size() + " actors with BaconNumber = 6.");
			
			HashSet<String> baconSevens = new HashSet<String>();
			for ( String p : baconSixes) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,7);
							baconSevens.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconSevens.size() + " actors with BaconNumber = 7.");
			for ( String b7 : baconSevens) {
				System.out.println(" - " + b7);
			}
			HashSet<String> baconEights = new HashSet<String>();
			for ( String p : baconSevens) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,8);
							baconEights.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconEights.size() + " actors with BaconNumber = 8.");
			for ( String b8 : baconEights) {
				System.out.println(" - " + b8);
			}
			
			HashSet<String> baconNines = new HashSet<String>();
			for ( String p : baconEights) {
				HashSet<String> movies = actorList.get(p);
				for ( String thisMovie : movies ) {
					HashSet<String> actorsInThisMovie = movieList.get(thisMovie);
					for ( String anActor : actorsInThisMovie ) {
						if ( !baconList.containsKey(anActor)) {
							baconList.put(anActor,9);
							baconNines.add(anActor);
						}
					}
				}
			}
			System.out.println("There are " + baconNines.size() + " actors with BaconNumber = 9.");
			*/
		}
		else {
			System.out.println("There is no Bacon.");
		}
	}
	
	
	/***********************************************************************************
	 * 
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws IOException
	 ***********************************************************************************/
	private void buildLists( String fileName ) throws URISyntaxException, IOException {
		int lineCounter = 0;
		//Open the actor file
		URI fileURI = Driver.class.getResource(fileName).toURI();
		File actorFile = new File(fileURI);
		System.out.println("Looking at file: " + actorFile.getAbsolutePath());
				
		//Loop through every line
		LineIterator it = FileUtils.lineIterator(actorFile);
		String actorName = "";
		String movieName = "";
		HashSet<String> movies = new HashSet<String>();
		try {
		    while (it.hasNext()) {
		    	String line = it.nextLine();
		    	lineCounter++;
		    	if ( lineCounter % 500000 == 0 ) {
		    		System.out.println("Processed " + lineCounter + " records. " + actorName);
		    	}
		    	//System.out.println("Line: " + line);
		    	
		    	if ( line.length() > 0 ) {
		    		StringBuffer movieSB = new StringBuffer();
		    		StringBuffer actorSB = new StringBuffer();
			    	int pos = 0;
			    	boolean endOfMovie = false;
			    	boolean startOfMovie = false;
			    	boolean skipMovie = false;
		    		char[] chars = line.toCharArray();

		    		//If the first character is not a tab \t then it's an actors name, get the lastName, firstName
	    			if ( chars[0] != '\t' ) {
	    				//System.out.println("Looking at Actor name line.");
	    				//This is a new actor, we must process the previous actor
	    				if ( actorName.length() > 0  && movies.size() > 0 ) {
	    					actorList.put(actorName, movies);
	    					actorName = "";
	    					movieName = "";
	    					movies = new HashSet<String>();
	    				}
	    				
	    				boolean endOfName = false;
	    				
	    				
	    				while ( !endOfName ) {
	    		    		//get characters until we hit a tab \t or an open paren (
	    					//System.out.println("Looking at char[" + chars[pos] + "]");
	    					if ( chars[pos] != '\t'  ) {    //&& chars[pos] != '(' 
	    						actorSB.append(chars[pos]);
	    		    			pos++;
	    		    		}
	    		    		else {
	    		    			endOfName = true;
	    		    		}
	    				}
	    				actorName = actorSB.toString().trim();
	    				if ( actorName.startsWith("Bacon, Kevin")) {
	    					System.out.println("Found actor: " + actorName + " line: " + lineCounter );
	    				}
	    			}
	    			//get the rest of this line. (First movie) Strip off leading tabs and open/close parens.
	    			endOfMovie = false;

	    			skipMovie = false;
	    			startOfMovie = true;
	    			movieSB = new StringBuffer();
	    			//System.out.println("Getting movie");
	    			if ( line.contains("(TV)") ) {
	    				skipMovie = true;
	    				endOfMovie = true;
	    			}
	    			while ( !endOfMovie ) {
	    				//If first chara is " - skip this line (TV Show).
	    				if ( chars[pos] == '\t' ) {
	    					pos++;
	    				}
	    				else if ( chars[pos] == '(') {
	    					endOfMovie = true;
	    				}
	    				else if ( chars[pos] == '"' && startOfMovie ) {
	    					skipMovie = true;
	    					endOfMovie = true;
	    				}
	    				else {
	    					startOfMovie = false;
	    					movieSB.append(chars[pos]);
	    					pos++;
	    				}
	    				if ( pos > line.length() ) {
	    					System.out.println("Error: end of line reached reading movie name.");
	    					endOfMovie = true;
	    				}
	    			}
	    			
	    			if ( !skipMovie ) {
	    				movieName = movieSB.toString();
	    				//System.out.println("Adding movie: " + movieName);
	    				//Add this movie to the actor's movie list
	    				movies.add(movieName);
	    				//Add this actor's appearance in this movie to the master movieList
	    				HashSet<String> theActors = null;
	    				if ( movieList.containsKey(movieName) ) {
	    					theActors = movieList.get(movieName);
	    				}
	    				else {
	    					theActors = new HashSet<String>();
	    				}
    					theActors.add(actorName);
    					movieList.put(movieName, theActors);
	    			}
		    		else {
		    			//System.out.println("Skipping TV Show....");
		    		}	
    				


		    	}
//		    	try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		    }
		} finally {
		    it.close();
		}
		
		System.out.println("Movie List has: " + movieList.size() + " entries");
		System.out.println("Actor List has: " + actorList.size() + " entries");
		
	}
}
