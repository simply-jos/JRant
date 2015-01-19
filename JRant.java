
import java.io.File;
import java.sql.Time;
import java.time.Clock;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRant {
	private static Random rand = new Random();
	private Querry q;

	public JRant(String dir, boolean filter){
		q = new Querry(dir, filter);
	}

	public JRant(File dir, boolean filter){
		q = new Querry(dir, filter);
	}

	public void setFilter(boolean on){ q.setFilter(on); }
	public boolean getFilter(){ return q.getFilter(); }

	public String replacer(String querry){

		String word = "";
		querry = querry.replaceAll("[<>]", "");
		int i = 0, j = 0;

		if ((i = querry.indexOf(".")) != -1 && (j = querry.indexOf("-")) != -1){
			word = q.find(querry.substring(0, i), querry.substring(j + 1), querry.substring(i + 1, j));
		}
		else if ((j = querry.indexOf("-")) != -1){
			word = q.find(querry.substring(0, j), querry.substring(j + 1), "default");
		}
		else if ((i = querry.indexOf(".")) != -1){
			word = q.find(querry.substring(0, i), "", querry.substring(i + 1));
		}
		else {
			word = q.find(querry, "", "default");
		}

		return word;
	}

	public String parse(String markup){

		int i = 0, j = 0;

		String regex = Pattern.quote("<") + "(.*?)" + Pattern.quote(">");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(markup);

		while (m.find()){
			i = m.start();
			j = m.end();

			markup = markup.replaceFirst(markup.substring(i, j), replacer(markup.substring(i, j)));

			m = p.matcher(markup);
		}



		return markup;
	}
}
