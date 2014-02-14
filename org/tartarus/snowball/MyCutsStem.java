
package org.tartarus.snowball;

import java.lang.reflect.Method;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.*;

public class MyCutsStem {

	public static String PATH = "C:/CutsDEV_ori/";
    private static void usage()
    {
        //System.err.println("Usage: TestApp <algorithm> <input file> [-o <output file>]");
        System.err.println("Usage: TestApp <input file> ");
    }

	private static boolean checkStopWord(String [] sw, StringBuffer word)
	{
		int i = 0;
		boolean found = false;
		try {
			while ((i< sw.length) && (found == false)) {
				if (sw[i].equals(word.toString()))
				{
					//System.out.println("Trovato "+sw[i]+" con "+word);
					found = true;
				}
				else i = i+1;
			}

		} catch (Exception e){}
		return found;
	}

	private static String [] buildSWVector(int len)
	{
		String [] stopwords = new String[len];
		try {

			FileReader reader = new FileReader(PATH+"org/tartarus/snowball/stopwords.txt");
			String characters = "";
			int car;
			int i = 0;
			while ((car = reader.read()) != -1) {
				//if ((((char)car) != '\n') && (car != 13))
				if ((((char)car) != '\n'))
					characters = characters+((char)car);
				else {
					stopwords[i] = characters.substring(0,characters.length()-1);
					//stopwords[i] = characters;
					characters = "";
					i = i+1;
				}
			}
			stopwords[i] = characters;
			characters = "";
		}
		catch(Exception e){};
		return stopwords;
    }

    private static void StopWordEliminate(String[] sw)
	{

		try {
			FileReader reader = new FileReader("c:/outstemin.txt");
			FileWriter writer = new FileWriter("c:/nostopwords.txt");

			OutputStream os;
			os = new FileOutputStream("c:/prova.txt");
			Writer prova = new OutputStreamWriter(os);
			prova = new BufferedWriter(prova);
			//FileWriter prova = new FileWriter("c:/prova.txt");
			String characters = "";
			int car;
			boolean found;
			while ((car = reader.read()) != -1) {
				found = false;
				//System.out.println("->"+((char)13)+"<-");
				if ((char)car != ' '){
					characters = characters+((char)car);
					//System.out.println("Aggiungo il carattere"+(char)car);
				}
				else {
					int i = 0;

					prova.write(characters);
					prova.write('\n');
					while ((i< sw.length) && (found == false)) {
						if (sw[i].equals(characters.toLowerCase())){
							System.out.println("Trovato");
							found = true;
						}
						else i=i+1;
					}//while
					if (found == false) {
						writer.write(characters);
						//writer.write(13);

					}
					characters = "";
				}//else
			}//while
			writer.close();
			prova.close();
		}
		catch(Exception e){};
	}



    public MyCutsStem(FileReader reader, String nomefile) throws Throwable {
		System.out.println("\n");
		System.out.println("STEMMING ED ELIMINAZIONE STOPWORDS --> Output: EntryFiles \n");
    //public static void main(String [] args) throws Throwable {
		//if (args.length < 1) {
		//		usage();
		//		return;
		//	}

		//Class stemClass = Class.forName("org.tartarus.snowball.ext." +
		//				args[0] + "Stemmer");


		File deldir = new File(PATH+"data/EntryFiles/");

		File[] ls = deldir.listFiles();

		for(int i = 0; i < ls.length; i++) {
			//System.out.println("deleting");
	        ls[i].delete();
        }


		Class stemClass = Class.forName("org.tartarus.snowball.ext.italianStemmer");
		SnowballProgram stemmer = (SnowballProgram) stemClass.newInstance();
		Method stemMethod = stemClass.getMethod("stem", new Class[0]);



		//FileReader reader = new FileReader(args[0]);

		//PARTE MODIFICATA DA ALFREDO FAVENZA

		//Vettore delle stopwords
		String [] stopw = new String[281];

		//Riempio il vettore delle stop words con le parole all'interno del file stopwords.txt
		stopw = buildSWVector(281);

		String characters;
		characters= "";
		int car;
		boolean emptyline = false;
		boolean closed;
		boolean found;

		String nfile = nomefile.substring(11,nomefile.length()-4);

		FileWriter text1 = new FileWriter(PATH+"data/testi/"+nfile+".txt");
		FileWriter text = new FileWriter("C:/CutsDEV_ori/data/testo.txt");

		text1.write("FILE: "+nomefile+"\n");
		text1.write("------------------------------------------------");
		text1.write("\n");
		text1.write("\n");
		text.write("FILE: "+nomefile+"\n");
		text.write("------------------------------------------------");
		text.write("\n");
		text.write("\n");



		int filecount = 0;
		while((car = reader.read()) != -1) {
			closed = false;
			found = false;
			while ((closed == false) && ((car = reader.read()) != -1) ) {

				if (((char)car) == '<')
				{
					car = reader.read();
				if (((char)car) == 'm')
				{
					car = reader.read();
				if (((char)car) == 'a')
				{
					car = reader.read();
				if (((char)car) == 'd')
				{
					car = reader.read();
				if (((char)car) == 'l')
				{
					car = reader.read();
				if (((char)car) == ':')
				{
					car = reader.read();
				if (((char)car) == 'T')
				{
					car = reader.read();
				if (((char)car) == 'r')
				{
					car = reader.read();
				if (((char)car) == 'a')
				{
					car = reader.read();
				if (((char)car) == 'n')
				{
					car = reader.read();
				if (((char)car) == 's')
				{
					car = reader.read();
				if (((char)car) == 'c')
				{
					car = reader.read();
				if (((char)car) == 'r')
				{
					car = reader.read();
				if (((char)car) == 'i')
				{
					car = reader.read();
				if (((char)car) == 'p')
				{
					car = reader.read();
				if (((char)car) == 't')
				{
					car = reader.read();
				if (((char)car) == 'i')
				{
					car = reader.read();
				if (((char)car) == 'o')
				{
					car = reader.read();
				if (((char)car) == 'n')
				{
					car = reader.read();
				if (((char)car) == '>')
				{

					found = true;
					//ABBIAMO TROVATO UNA ENTRY
					//Salviamo il contentuto della entry in una stringa
					//System.out.println("Trovato <madl:Transcription>");
					characters= "";
					while ((car = reader.read()) != '<') {
						characters = characters+((char)car);

					}

					//Scriviamo il contentuo della stringa su un file che sarà l'input per lo stemmer
					FileWriter writer = new FileWriter(PATH+"data/outstemin.txt");
					text1.write("## ENTRY "+filecount+" ##");
					text1.write("\n");
					text.write("## ENTRY "+filecount+" ##");
					text.write("\n");
					for (int j = 0; j < characters.length(); j++) {
					   writer.write(characters.charAt(j));
					   text.write(characters.charAt(j));

					}
					text1.write("\n");
					text1.write("\n");
					text.write("\n");
					text.write("\n");
					writer.close();

					//FINE PARTE MODIFICATA DA ALFREDO FAVENZA

				}}}}}}}}}}}}}}}}}}}} //Chiusura di tutti gli if

				if (found == true)
				{
					if (((char)car) == '<')
					{
						car = reader.read();
					if (((char)car) == '/')
					{
						car = reader.read();
					if (((char)car) == 'm')
					{
						car = reader.read();
					if (((char)car) == 'a')
					{
						car = reader.read();
					if (((char)car) == 'd')
					{
						car = reader.read();
					if (((char)car) == 'l')
					{
						car = reader.read();
					if (((char)car) == ':')
					{
						car = reader.read();
					if (((char)car) == 'T')
					{
						car = reader.read();
					if (((char)car) == 'r')
					{
						car = reader.read();
					if (((char)car) == 'a')
					{
						car = reader.read();
					if (((char)car) == 'n')
					{
						car = reader.read();
					if (((char)car) == 's')
					{
						car = reader.read();
					if (((char)car) == 'c')
					{
						car = reader.read();
					if (((char)car) == 'r')
					{
						car = reader.read();
					if (((char)car) == 'i')
					{
						car = reader.read();
					if (((char)car) == 'p')
					{
						car = reader.read();
					if (((char)car) == 't')
					{
						car = reader.read();
					if (((char)car) == 'i')
					{
						car = reader.read();
					if (((char)car) == 'o')
					{
						car = reader.read();
					if (((char)car) == 'n')
					{
						car = reader.read();
					if (((char)car) == '>')
					{
						closed = true;

						//ELIMINAZIONE STOP WORDS
						//StopWordEliminate(stopw);

						//FINE ELIMINAZIONE STOP WORDS



						FileReader reader1 = new FileReader(PATH+"data/outstemin.txt");
						//FileReader reader1 = new FileReader("c:/nostopwords.txt");
						StringBuffer input = new StringBuffer();

						//String input;

						OutputStream outstream;

						//if (args.length > 2) {
						//		if (args.length == 4 && args[2].equals("-o")) {
						//			outstream = new FileOutputStream("c:/EntryFiles/entryfile"+filecount+".txt");
						//		} else {
						//			usage();
						//			return;
						//		}
						//} else {
						//	outstream = System.out;
						//}
						String ord  = "";
						if (filecount > 9)
							ord = "_";
						if (filecount > 99)
							ord = ord+"_";
						if (filecount > 1999)
							ord = ord+"_";

						outstream = new FileOutputStream(PATH+"data/EntryFiles/entryfile"+ord+filecount+".txt");

						Writer output = new OutputStreamWriter(outstream);
						output = new BufferedWriter(output);
						//output.write("<START>");
						//output.write('\n');
						int repeat = 1;
							//if (args.length > 4) {
							//	repeat = Integer.parseInt(args[4]);
							//}
						Object [] emptyArgs = new Object[0];
						int character;
						while ((character = reader1.read()) != -1) {
							char ch = (char) character;

							if (Character.isWhitespace((char) ch)) {
								if (input.length() > 0) {
									if (checkStopWord(stopw,input) == true)
									{
										//System.out.println("Elimino "+input.toString());
										input.delete(0, input.length());

									}
									else {

										stemmer.setCurrent(input.toString());
										for (int i = repeat; i != 0; i--) {
										stemMethod.invoke(stemmer, emptyArgs);
										}
										output.write(stemmer.getCurrent());
										output.write('\n');
										input.delete(0, input.length());
									}
								}
							} else {
								input.append(Character.toLowerCase(ch));
							}

						}//while
						if (checkStopWord(stopw,input) == true)
						{
							//System.out.println("Elimino "+input);
							input.delete(0, input.length());

						}
						else {
							stemmer.setCurrent(input.toString());
							for (int i = repeat; i != 0; i--) {
								stemMethod.invoke(stemmer, emptyArgs);
							}
							output.write(stemmer.getCurrent());
							//output.write('\n');
							//output.write("<END>");
							//output.write('\n');
							input.delete(0, input.length());
						}
						output.close();
					}//if (((char)car) == '>')

					}}}}}}}}}}}}}}}}}}}} //Chiusura di tutti gi if

				}//if (found == true)
			}//CHIUDI IL SECONDO WHILE
			filecount = filecount + 1;
		}//CHIUDO IL PRIMO WHILE
		text.close();
		text1.close();
		//output.close();
	}//CHIUDO IL MAIN
}//CHIUDO LA CLASSE
