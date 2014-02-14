
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

public class MyCutsStem2 {

	public static String PATH = "C:/CUTS/";
	private int index;
	private String testoPagina;

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

    public static String leggiFile (String nomeFile) throws IOException
    {
        InputStream is = null;
        InputStreamReader isr = null;

        StringBuffer sb = new StringBuffer ();
        char[] buf = new char[1024];
        int len;

        try
        {
            is = new FileInputStream (nomeFile);
            isr = new InputStreamReader (is);

            while ((len = isr.read (buf)) > 0)
                sb.append (buf, 0, len);

            return sb.toString ();
        }
        finally
        {
            if (isr != null)
                isr.close ();
        }
    }


	static String search (String testoPagina, String [] data) {
		//String testoPagina = leggiFile (args[0]);

		//int i = 0;

		//while (i <= 10) {
		String res = null;
		int i = 0;
		int index;
		try{
			index = testoPagina.indexOf("<EventDecompositionMember>");
			System.out.println("index="+index);
		}
		catch(Exception e){System.out.println(testoPagina.indexOf("<EventDecompositionMember>")); return "ciao";}
		//System.out.println("kkk");
		data[0] = "";
		data[1] = "";
		if (index != -1) {
			//System.out.println("Trovato <EventDecompositionMember>");
			index = index + 26;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<Event");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Event");
			index = index + 6;
			i = 0;
			while (testoPagina.charAt(index+i) != '>')
				i++;
			index = index + i;
			i = 1;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<EventIdentification>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <EventIdentification>");
			index = index + 21;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<TimeIdentificationMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <TimeIdentificationMember>");
			index = index + 26;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<Time>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Time>");
			index = index + 6;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			int q = index;
			int z = q+25;
			while (q <= z) {
				data[0] = data[0]+testoPagina.charAt(q);
				q++;
			}
			index = index + 26;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</Time>");
			//res = testoPagina.substring(index);
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </Time>");
			index = index + 7;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			//System.out.println(testoPagina);
			index = testoPagina.indexOf("<Role>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Role>");
			index = index + 6;
			i = 0;
			while (testoPagina.charAt(index+i) != '<')
				i++;
			index = index + i;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</Role>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </Role>");
			index = index + 7;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</TimeIdentificationMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </TimeIdentificationMember>");
			index = index + 27;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<TimeIdentificationMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <TimeIdentificationMember>");
			index = index + 26;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<Time>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Time>");
			index = index + 6;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			index = index + 26;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</Time>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </Time>");
			index = index + 7;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<Role>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Role>");
			index = index + 6;
			i = 0;
			while (testoPagina.charAt(index+i) != '<')
				i++;
			index = index + i;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</Role>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </Role>");
			index = index + 7;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</TimeIdentificationMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </TimeIdentificationMember>");
			index = index + 27;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<PlaceIdentificationMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <PlaceIdentificationMember>");
			index = index + 27;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<Place/>");
			//System.out.println(index);
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Place/>");
			index = index + 9;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</PlaceIdentificationMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </PlaceIdentificationMember>");
			index = index + 28;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("</EventIdentification>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato </EventIdentification>");
			index = index + 22;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<Description>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Description>");
			index = index + 13;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<DescriptionMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <DescriptionMember>");
			index = index + 19;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<madl:Sentence ");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <madl:Sentence ");
			index = index + 15;
			i = 0;
			while (testoPagina.charAt(index+i) != '>')
				i++;
			index = index + i;
			i = 1;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			//System.out.println(testoPagina.charAt(index));
			//System.out.println(testoPagina.charAt(index+1));
			//System.out.println(testoPagina.charAt(index+2));
			//System.out.println(testoPagina.charAt(index+3));
			index = testoPagina.indexOf("<Attribution>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <Attribution>");
			index = index + 13;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<AttributionMember>");
		if ((index != -1) && (index == 0)) {
			//System.out.println("Trovato <AttributionMember>");
			index = index + 19;
			i = 0;
			while (testoPagina.charAt(index+i) == ' ')
				i++;
			index = index + i;
			testoPagina = testoPagina.substring(index);
			index = testoPagina.indexOf("<madl:Transcription>");
		if ((index != -1) && (index == 0)) {
			System.out.println("Trovato <madl:Transcription>");
			index = index + 20;

			res = testoPagina.substring(index);
			int p = 0;
				while (res.charAt(p) != '<') {
					System.out.println("qua1");
					data[1] = data[1]+res.charAt(p);
					System.out.println("qua2");
					p++;
			}
			System.out.println(data[1]);
			//System.out.println(testoPagina.substring(index).charAt(0));
			//i = 0;
			//while (testoPagina.charAt(index+i) == ' ')
			//	i++;
			//index = index + i;
			//testoPagina = testoPagina.substring(index);
			//index = testoPagina.indexOf("<madl:Sentence bo:id="">");
		}
		else {
			//System.out.println("Non trovato <madl:Transcription>");
			if (index > 0 ) index = 0;
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <AttributionMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Attribution>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <madl:Sentence >");
			//res = search(testoPagina.substring(index));
			//System.out.println(testoPagina);
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <DescriptionMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Description>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </EventIdentification>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </PlaceIdentificationMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Place />");
			//System.out.println(index);
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <PlaceIdentificationMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </TimeIdentificationMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </Role>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Role>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </Time>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Time>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <TimeIdentificationMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </TimeIdentificationMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </Role>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Role>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato </Time>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Time>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <TimeIdentificationMember>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <EventIdentification>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <Event>");
			//res = search(testoPagina.substring(index));
			res = search(testoPagina, data);
			}
		}
		else {
			//System.out.println("Non trovato <EventDecompositionMember>");
			//System.out.println(index);
			//res = search(testoPagina.substring(index));
			//res = search(testoPagina);
			res = null;
			}
		//}
		//else {
			//System.out.println("Non trovo nulla");
		//	res = null;
		//	}
		return res;

	}








		//public String search2 (String testoPagina,String [] data) {
		public String search2 (String [] data) {
			//String testoPagina = leggiFile (args[0]);

			//int i = 0;

			//while (i <= 10) {
			String res = null;
			int i = 0;
			//int index;

			this.index = testoPagina.indexOf("<EventDecompositionMember>");

			//System.out.println("kkk");
			//System.out.println(this.index);
			data[0] = "";
			data[1] = "";
			if (index != -1) {
				//System.out.println("Trovato <EventDecompositionMember>");
				index = index + 26;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<Event");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Event");
				index = index + 6;
				i = 0;
				while (testoPagina.charAt(index+i) != '>')
					i++;
				index = index + i;
				i = 1;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<EventIdentification>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <EventIdentification>");
				index = index + 21;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<TimeIdentificationMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <TimeIdentificationMember>");
				index = index + 26;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<Time>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Time>");
				index = index + 6;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				int q = index;
				int z = q+25;
				while (q <= z) {
					data[0] = data[0]+testoPagina.charAt(q);
					q++;
				}
				index = index + 26;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</Time>");
				//res = testoPagina.substring(index);
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </Time>");
				index = index + 7;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				//System.out.println(testoPagina);
				index = testoPagina.indexOf("<Role>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Role>");
				index = index + 6;
				i = 0;
				while (testoPagina.charAt(index+i) != '<')
					i++;
				index = index + i;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</Role>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </Role>");
				index = index + 7;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</TimeIdentificationMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </TimeIdentificationMember>");
				index = index + 27;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<TimeIdentificationMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <TimeIdentificationMember>");
				index = index + 26;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<Time>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Time>");
				index = index + 6;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				index = index + 26;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</Time>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </Time>");
				index = index + 7;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<Role>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Role>");
				index = index + 6;
				i = 0;
				while (testoPagina.charAt(index+i) != '<')
					i++;
				index = index + i;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</Role>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </Role>");
				index = index + 7;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</TimeIdentificationMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </TimeIdentificationMember>");
				index = index + 27;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<PlaceIdentificationMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <PlaceIdentificationMember>");
				index = index + 27;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<Place/>");
				//System.out.println(index);
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Place/>");
				index = index + 9;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</PlaceIdentificationMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </PlaceIdentificationMember>");
				index = index + 28;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("</EventIdentification>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato </EventIdentification>");
				index = index + 22;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<Description>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Description>");
				index = index + 13;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<DescriptionMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <DescriptionMember>");
				index = index + 19;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<madl:Sentence ");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <madl:Sentence ");
				index = index + 15;
				i = 0;
				while (testoPagina.charAt(index+i) != '>')
					i++;
				index = index + i;
				i = 1;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				//System.out.println(testoPagina.charAt(index));
				//System.out.println(testoPagina.charAt(index+1));
				//System.out.println(testoPagina.charAt(index+2));
				//System.out.println(testoPagina.charAt(index+3));
				index = testoPagina.indexOf("<Attribution>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <Attribution>");
				index = index + 13;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<AttributionMember>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <AttributionMember>");
				index = index + 19;
				i = 0;
				while (testoPagina.charAt(index+i) == ' ')
					i++;
				index = index + i;
				testoPagina = testoPagina.substring(index);
				index = testoPagina.indexOf("<madl:Transcription>");
			if ((index != -1) && (index == 0)) {
				//System.out.println("Trovato <madl:Transcription>");
				index = index + 20;

				res = testoPagina.substring(index);
				int p = 0;
					while (res.charAt(p) != '<') {
						//System.out.println("qua1");
						data[1] = data[1]+res.charAt(p);
						//System.out.println("qua2");
						p++;
				}
				//System.out.println(data[1]);
				//System.out.println(testoPagina.substring(index).charAt(0));
				//i = 0;
				//while (testoPagina.charAt(index+i) == ' ')
				//	i++;
				//index = index + i;
				//testoPagina = testoPagina.substring(index);
				//index = testoPagina.indexOf("<madl:Sentence bo:id="">");
			}
			else {
				//System.out.println("Non trovato <madl:Transcription>");
				if (index > 0 ) index = 0;
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <AttributionMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Attribution>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <madl:Sentence >");
				//res = search(testoPagina.substring(index));
				//System.out.println(testoPagina);
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <DescriptionMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Description>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </EventIdentification>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </PlaceIdentificationMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Place />");
				//System.out.println(index);
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <PlaceIdentificationMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </TimeIdentificationMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </Role>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Role>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </Time>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Time>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <TimeIdentificationMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </TimeIdentificationMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </Role>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Role>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato </Time>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Time>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <TimeIdentificationMember>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <EventIdentification>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <Event>");
				//res = search(testoPagina.substring(index));
				res = null;
				//res = search(testoPagina, data);
				}
			}
			else {
				//System.out.println("Non trovato <EventDecompositionMember>");
				//System.out.println(index);
				//res = search(testoPagina.substring(index));
				//res = search(testoPagina);
				res = null;
				}
			//}
			//else {
				//System.out.println("Non trovo nulla");
			//	res = null;
			//	}
			return res;

	}






    public MyCutsStem2(FileReader reader, String nomefile) throws Throwable {
		System.out.println("\n");
		System.out.println("STEMMING ED ELIMINAZIONE STOPWORDS --> Output: EntryFiles \n");

		File deldir = new File(PATH+"data/EntryFiles/");

		File[] ls = deldir.listFiles();

		for(int i = 0; i < ls.length; i++) {
			//System.out.println("deleting");
			ls[i].delete();
        }

        Class stemClass = Class.forName("org.tartarus.snowball.ext.italianStemmer");
		SnowballProgram stemmer = (SnowballProgram) stemClass.newInstance();
		Method stemMethod = stemClass.getMethod("stem", new Class[0]);

		//PARTE MODIFICATA DA ALFREDO FAVENZA

		//Vettore delle stopwords
		String [] stopw = new String[281];

		//Riempio il vettore delle stop words con le parole all'interno del file stopwords.txt
		stopw = buildSWVector(281);

		//for (int a=0; a<280; a++)
		//	System.out.println(stopw[a]);

		String characters;
		characters= "";
		int car;
		boolean emptyline = false;
		boolean closed;
		boolean found = false;

		//String testoPagina = leggiFile ("c:/test/ANTS_1152862334.15.madl2.xml");
		//String testoPagina = leggiFile (nomefile);
		this.testoPagina = leggiFile (nomefile);
		FileWriter doc = new FileWriter("C:/documento.txt");
		doc.write(this.testoPagina);
		doc.close();
		//System.out.println("testoPagina = "+testoPagina);

		//String s;
		//while (s != null)
		//s = search(testoPagina);

		String nfile = nomefile.substring(11,nomefile.length()-4);
		FileWriter text1 = new FileWriter(PATH+"data/testi/"+nfile+".txt");
		FileWriter text = new FileWriter(PATH+"data/testo.txt");
		text1.write("FILE: "+nomefile+"\n");
		text1.write("------------------------------------------------");
		text1.write("\n");
		text1.write("\n");
		text.write("FILE: "+nomefile+"\n");
		text.write("------------------------------------------------");
		text.write("\n");
		text.write("\n");

		this.index = 0;

		String s = null;
		//int ind = 0;
		//boolean found = false;
		boolean endfile = false;
		String [] data = new String [2];
		int k = 0;
		int filecount = 0;

		//s = search2(testoPagina, data);
		while ((s == null) && (this.index != -1) ) {
			//System.out.println("pre");
			s = search2(data);
			//testoPagina = testoPagina.substring(this.index);
			//System.out.println("PRIMO");
			//System.out.println("ind:"+this.index);
			//System.out.println("lung testo cons:"+testoPagina.length());
			//System.out.println("post");
		}

		//while ((s != null) && (this.index != -1)) {
		while (this.index != -1) {
			//System.out.println("sono dentro il while");

			//char [] res = new char [100];

			/*while ((s == null) && (this.index != -1)) {
				//System.out.println("pre");
				//s = search2(testoPagina, data);
				s = search2(data);
				//System.out.println("PRIMO");
				//System.out.println("ind:"+this.index);
				//System.out.println("lung testo cons:"+testoPagina.length());
				//System.out.println("post");
			}*/
			//testoPagina = testoPagina.substring(this.index);

			char [] res = new char [100];
			//System.out.println("filecount:"+filecount);
			//System.out.println("Time:"+data[0]);
			//System.out.println(s);
			if (s != null){
				//s = null;
				//System.out.println(data[1]);
				//System.out.println(k);
				//System.out.println("-------------------------------------------------------");
				//testoPagina = s;
			}
				//else System.out.println("PIPPOS");






				//Scriviamo il contentuo della stringa su un file che sarà l'input per lo stemmer
				FileWriter writer = new FileWriter(PATH+"data/outstemin.txt");
				//System.out.println(data[0]);
				//System.out.println(data[1]);
				//int len0 = data[0].length();
				//int len1 = data[1].length();
				//String time = data[0];
				//String text = data[1];
				//System.out.println(len0);
				//System.out.println(len1);
				text1.write("## ENTRY "+filecount+" ##");
				text1.write("\n");
				text.write("## ENTRY "+filecount+" ##");
				text.write("\n");
				//for (int j = 0; j < characters.length(); j++) {
				//   writer.write(characters.charAt(j));
				//   text.write(characters.charAt(j));
				//
				//}

				text.write("Time: "+data[0]);
				text.write("\n");
				text.write(data[1]);
				writer.write(data[0]);
				writer.write(' ');
				writer.write(data[1]);
				writer.close();

				text1.write("\n");
				text1.write("\n");
				text.write("\n");
				text.write("\n");



				//ELIMINAZIONE STOP WORDS
				//StopWordEliminate(stopw);

				//FINE ELIMINAZIONE STOP WORDS
				//System.out.println("TIME= "+data[0]);


				FileReader reader1 = new FileReader(PATH+"data/outstemin.txt");
				//FileReader reader1 = new FileReader("c:/nostopwords.txt");
				StringBuffer input = new StringBuffer();

				//String input;

				OutputStream outstream;

				String ord  = "";
				if (filecount > 9)
					ord = "_";
				if (filecount > 99)
					ord = ord+"_";
				if (filecount > 1999)
					ord = ord+"_";

				//outstream = new FileOutputStream("c:/CutsDEV/data/EntryFiles/entryfile"+ord+filecount+".txt");
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
				filecount = filecount + 1;
				//s = search2(testoPagina, data);

			//else {
				s = null;
				while ((s == null) && (this.index != -1)) {
					//System.out.println("pre1");
					//s = search2(testoPagina, data);
					s = search2(data);
					//System.out.println(s);
					//testoPagina = testoPagina.substring(this.index);
					//System.out.println("SECONDO");
					//System.out.println("ind:"+this.index);
					//System.out.println("lung testo cons:"+testoPagina.length());
					//System.out.println("post1");
				}


		}//CHIUDO IL PRIMO WHILE
		text.close();
		text1.close();
		//output.close();
	}//CHIUDO IL MAIN
}//CHIUDO LA CLASSE












