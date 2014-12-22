
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Used to read a .dic (dictionary) file
 * @author Codypeterson
 */
public class VocabReader {
	private Scanner fileReader;
	
	/**
	 * Reads the file specified and turns it into a map to be queried later
	 * @param file the file to read
	 * @param filter if true, it will filter out nsfw content
	 * @return a map of the parameters and their values
	 * @throws FileNotFoundException
	 */
	public Map<String, String[]> readFile(File file, boolean filter) throws FileNotFoundException{
		fileReader = new Scanner(file);
		
		Map<String, String[]> values = new HashMap<String, String[]>();
		String line = "";
		ArrayList<String> words = new ArrayList<String>();
		
		boolean filterOn = false;
		
		while (fileReader.hasNextLine()){
			line = fileReader.nextLine().trim();
			
			if (line.indexOf("#version ") == 0){
				values.put("version", line.substring("#version ".length()).split(" "));
			}
			if (line.indexOf("#name ") == 0){
				values.put("name", line.substring("#name ".length()).split(" "));
			}
			if (line.indexOf("#subs ") == 0){
				values.put("subs", line.substring("#subs ".length()).split(" "));
			}
			if (line.indexOf("#nsfw") != -1){
				filterOn = true && filter;
			}
			if (line.indexOf("#sfw") != -1){
				filterOn = false;
			}
			if (line.indexOf("> ") == 0 && !filterOn){
				words.add(line.substring("> ".length()));		
			}
		}
		
		String[] vocab = new String[words.size()];
		for (int i = 0; i < vocab.length && i < words.size(); i++){
			vocab[i] = words.get(i);
		}
		
		values.put("words", vocab);
		return values;
	}
}
