import java.io.*;
import java.util.regex.*;

public class Prova
{
    static String search (String testoPagina, String [] data) {
		//String testoPagina = leggiFile (args[0]);

		//int i = 0;

		//while (i <= 10) {
		String res = null;
		int i = 0;
		int index = testoPagina.indexOf("<EventDecompositionMember>");
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
					data[1] = data[1]+res.charAt(p);
					p++;
			}
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

    public static void main (String[] args)
    {
        if (args.length == 1)
        {
            try
            {
				String characters;
                String testoPagina = leggiFile (args[0]);
				String s = "";
				String [] data = new String [2];
				int k = 0;

				while (s != null) {
					s = search(testoPagina, data);
					char [] res = new char [100];
					//s.getChars(0, 50, res, 0);
					//System.out.println(res);

					//characters= "";
					//int i = 0;
					//while (s.charAt(i) != '<') {
					//while ((car = reader.read()) != '<') {
						//System.out.println(characters);
					//	characters = characters+s.charAt(i);
					//	i++;
					//}


					//System.out.println(characters);
					if (s!=null){
						System.out.println(data[0]);
						System.out.println(data[1]);
						System.out.println(k);
						System.out.println("-------------------------------------------------------");
						testoPagina = s;
						k++;
					}

				}

				//Scriviamo il contentuo della stringa su un file che sarà l'input per lo stemmer
				//FileWriter writer = new FileWriter("c:/CutsDEV/data/outstemin.txt");
				//for (int j = 0; j < characters.length(); j++)
				//   writer.write(characters.charAt(j));

				//writer.close();

				if (s == null)
					System.out.println("vuota");


                Pattern pattern = Pattern.compile ("<EventDecompositionMember><Event>");
                Matcher matcher = pattern.matcher (testoPagina);

                //if (matcher.find ())
                int j = 0;
                while (matcher.find ())
                {
                    //String titolo = matcher.group (1);

                    System.out.println ("Trovato! "+j);
                    j++;


                }
            }
            catch (Exception e)
            {
                System.out.println (e);
            }
        }
    }


    public static String leggiFile (String nomeFile)
        throws IOException
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
}