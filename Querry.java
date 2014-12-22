package com.jrant;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Used for querrying files
 * @author Codypeterson
 *
 */
public class Querry {
	private File dir;
	private ArrayList<Map<String, String[]>> vocabLists = new ArrayList<Map<String, String[]>>();;

	private static VocabReader vr = new VocabReader();
	private static Random rand = new Random();

	private boolean nsfwFilter;

	public Querry(String directory, boolean filter){
		try {
			dir = new File(directory);
			if (!dir.isDirectory()){
				throw new FileNotFoundException("File path is not a directory");
			}
			nsfwFilter = filter;
			loadVocab();
		}
		catch (FileNotFoundException e){
			System.out.println("File not found");
		}
	}

	public Querry(File directory, boolean filter){
		if (!directory.isDirectory()){

		}
		dir = directory;

		nsfwFilter = filter;
		loadVocab();
	}

	private void loadVocab(){
		for (String fileName: dir.list()){
			try {
				vocabLists.add(vr.readFile(new File(dir.getPath() + "/" + fileName), nsfwFilter));
			}
			catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
		}
	}

	public void setFilter(boolean filter) { 
		nsfwFilter = filter;
		vocabLists.clear();
		loadVocab();
	}
	public boolean getFilter() { return nsfwFilter; }

	public String find(String name, String cl, String sub){
		for (int i = 0; i < vocabLists.size(); i++){
			for (int j = 0; j < vocabLists.get(i).get("name").length; j++){
				if (vocabLists.get(i).get("name")[j].equalsIgnoreCase(name)){
					String[] subs = vocabLists.get(i).get("subs");
					int subN = 0;
					for (int k = 0; k < subs.length; k++){
						if (subs[k].equalsIgnoreCase(sub)){
							subN = k;
							break;
						}
					}

					String[] words = vocabLists.get(i).get("words");
					int r = rand.nextInt(words.length);

					return words[r].split("/")[subN];
				}
			}
		}

		return "[Undefined]";
	}
}
