
import org.tartarus.snowball.*;
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
import java.awt.Graphics;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
import java.sql.Date;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.Math;
import java.lang.Thread;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class Cuts3{

    public static String PATH = "C:/CUTS/";
    public static double PERC_DRIF = 0.25;
    public static double PERC_MS = 0.05;
    private int VOC_LEN;
	private double MINSPAN;
    private double LAMBDA_DRIFTING;
    private double LAMBDA_DRIFTING_Y;
	private double LAMBDA_DRIFTING_Z;


	private Calendar ExtractDate(Reader rd){
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int min = 0;
		int sec = 0;
		int car = 0;
		String character = "";
		try {
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			year = Integer.parseInt(character);
			character = "";
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			month = Integer.parseInt(character);
			character = "";
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			day = Integer.parseInt(character);
			character = "";
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			hour = Integer.parseInt(character);
			character = "";
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			min = Integer.parseInt(character);
			character = "";
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			character = character + ((char)car);
			car = rd.read();
			sec = Integer.parseInt(character);
			character = "";
		}
		catch (Exception e){};

		Calendar date = new GregorianCalendar(year, month, day, hour, min, sec);

		return date;

	}



	private static String [] buildSWVector(int len)
	{
		String [] stopwords = new String[len];
		try {
			FileReader reader = new FileReader(PATH+"stopwords.txt");
			String characters = "";
			int car;
			int i = 0;
			while ((car = reader.read()) != -1) {
				if (((char)car) != '\n')
					characters = characters+((char)car);
				else {
					stopwords[i] = characters;
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

	private static AnalisysVector buildAnalisysVector(int len)
	{

		AnalisysVector av = new AnalisysVector(len);

		try {
			FileReader reader = new FileReader(PATH+"statvec.txt");
			String start = "";
			String end = "";
			int car;

			while ((car = reader.read()) != -1) {
				if ( (char)car != ' ')
					start = start+((char)car);
				while( ((car = reader.read()) != -1)&&( (char)car != ' ') ) {
					start = start+((char)car);
				}
				while( ((car = reader.read()) != -1)&&( (char)car != ' ') ) {
					end = end+((char)car);
				}

				int s = Integer.parseInt(start);
				int e = Integer.parseInt(end);
				av.addElement(s,e);
				start = "";
				end = "";

			}

		}
		catch(Exception e){};
		return av;
	}

    private static MDSPoint_2D [] buildMDSVector_2D(int len, EntrySignature [] ES)
	{
		//Vector2dElem [] MDS2dPoints = new Vector2dElem[len];
		MDSPoint_2D [] mdsp2d = new MDSPoint_2D[len];

		try {
			FileReader reader = new FileReader(PATH+"data/mdsres.dat");
			String ypoint = "";
			String zpoint = "";
			int car;
			int i = 0;
			while ((car = reader.read()) != -1) {
				if ( (char)car != ' ')
					ypoint = ypoint+((char)car);
				while( ((car = reader.read()) != -1)&&( (char)car != ' ') ) {
					//System.out.println("Carattere di y: "+(char)car);
					ypoint = ypoint+((char)car);
				}
				while( ((car = reader.read()) != -1)&&( (char)car != ' ') ) {
					//System.out.println("Carattere di z: "+(char)car);
					zpoint = zpoint+((char)car);
				}

				double yp = Double.parseDouble(ypoint);
				double zp = Double.parseDouble(zpoint);
				//System.out.println("yp= "+yp);
				//System.out.println("zp= "+zp);
				//MDS2dPoints[i] = new Vector2dElem(yp,zp);
				mdsp2d[i] = new MDSPoint_2D(ES[i].getTime(),yp,zp);
				ypoint = "";
				zpoint = "";
				i = i+1;
			}

		}
		catch(Exception e){};
		//System.out.println("Esco da buildMDSVector_2D");
		return mdsp2d;
    }

	private static MDSPoint [] buildMDSVector(int len, EntrySignature [] ES)
	{

		MDSPoint [] mdsp = new MDSPoint[len];
		//System.out.println("CIAO");
		try {
			FileReader reader = new FileReader(PATH+"data/mdsres.dat");
			String point = "";
			int car;
			int i = 0;
			while ((car = reader.read()) != -1) {
				//System.out.println((char)car);
				if ( (char)car != ' '){
					point = point+((char)car);
				} else {
					//System.out.println("-->"+ES[i].getTime());
					mdsp[i] = new MDSPoint(ES[i].getTime(),Double.parseDouble(point));
					point = "";
					i = i+1;
				}
			}
			mdsp[i] = new MDSPoint(ES[i].getTime(),Double.parseDouble(point));
			point = "";
		}
		catch(Exception e){};
		return mdsp;
    }

    private static void buildCurveSegmentsVector(Node nodo,Node [] CurveSegmentsVector, int indice)
	{
		System.out.println(indice);
		if (nodo.left!=null)
			buildCurveSegmentsVector(nodo.left, CurveSegmentsVector, indice);
		if (nodo.getIscs()==true){
			CurveSegmentsVector[indice] = nodo;
			indice = indice+1;
		}
		if (nodo.right!=null)
			buildCurveSegmentsVector(nodo.right,CurveSegmentsVector, indice);

	}


	private static void printAnnotation(int numentry, int numvocaboli, BTS btseg, FileWriter w, int nseg, int nbts)
	{

		try {
			w.write("\n");
			w.write("\n");
			w.write("=============================================\n");
			w.write("========= RISULTATI DELL' ANALISI ===========\n");
			w.write("=============================================\n");
			w.write("N. entry= "+numentry+"       "+"N. vocaboli= "+numvocaboli+"\n");
			w.write("N. Curve Seg.= "+nseg+"       "+"N. Base Topic Seg.= "+nbts+"\n");
			w.write("lambda_drifting= "+btseg.getDrifting()+"\n");
			w.write("\n");
			int i = 0;
			boolean interr;

			while (btseg.getElement(i) != null)
			{
				//System.out.println("    Considero il segmento: "+btseg.getElement(i).getStartPoint().getX()+"-"+btseg.getElement(i).getEndPoint().getX());
				interr = false;
				Node seg = btseg.getElement(i);
				Point2D.Double start = seg.getStartPoint();
				Point2D.Double end = seg.getEndPoint();
				if ( (btseg.getElement(i+1) != null)&&(btseg.getElement(i-1) != null)&&(btseg.getElement(i+2) != null) ) {
					Node seg2 = btseg.getElement(i+1);
					Point2D.Double start2 = seg2.getStartPoint();
					Point2D.Double end2 = seg2.getEndPoint();
					double k_h = Math.abs( (seg.getStartPoint().getY())-(seg2.getEndPoint().getY()) )/Math.abs( (seg.getStartPoint().getX())-(seg2.getEndPoint().getX()));

					boolean cond1 = (Math.abs(seg.getK())) >= ((btseg.getDrifting())) ;
					boolean cond2 = Math.abs(seg2.getK()) >= (btseg.getDrifting());
					boolean cond3 = ( seg.getK()*seg2.getK() ) < 0;
					boolean cond4 = ( Math.abs(btseg.getElement(i-1).getK() - (k_h)) + Math.abs(btseg.getElement(i+2).getK() - (k_h)) ) < btseg.getDrifting();
					//System.out.print(cond1+"--"+cond2+"--"+cond3+"--"+cond4);
					if   ( cond1 && cond2 && cond3 && cond4)
					{
							System.out.print("Pattern "+start.getX()+"-"+end2.getX()+" :");
							System.out.print("INTERRUPTED");
							System.out.println("\n");
							w.write("Pattern "+start.getX()+"-"+end2.getX()+" : ");
							w.write("INTERRUPTED\n");
							w.write("\n");
							interr = true;
							i++;
							i++;
					}
				}
				if (interr == false) {
					//System.out.print("Pattern "+start.getX()+"-"+end.getX()+" :");
					w.write("Pattern "+start.getX()+"-"+end.getX()+" : ");
					if ( (Math.abs(seg.getK())) < btseg.getDrifting() )
						//System.out.print("DOMINATED");
						w.write("DOMINATED\n");
					else if ( (Math.abs(seg.getK())) >= btseg.getDrifting() )
						//System.out.print("DRIFTING");
						w.write("DRIFTING\n");
					//System.out.println("\n");
					w.write("\n");
					i++;
				}
			}
				w.write("============== FINE RISULTATI ===============\n");
		}
		catch (Exception e){};
	}

	private static void printAnnotation2D(int numentry, int numvocaboli, BTS_2D btseg, FileWriter w, int nseg, int nbts)
	{

		try {
			w.write("\n");
			w.write("\n");
			w.write("=============================================\n");
			w.write("========= RISULTATI DELL' ANALISI ===========\n");
			w.write("=============================================\n");
			w.write("N. entry= "+numentry+"       "+"N. vocaboli= "+numvocaboli+"\n");
			w.write("N. Curve Seg.= "+nseg+"       "+"N. Base Topic Seg.= "+nbts+"\n");
			w.write("lambda_drifting= "+btseg.getDrifting()+"\n");
			w.write("\n");
			int i = 0;
			boolean interr;

			while (btseg.getElement(i) != null)
			{
				//System.out.println("    Considero il segmento: "+btseg.getElement(i).getStartPoint().getX()+"-"+btseg.getElement(i).getEndPoint().getX());
				interr = false;
				Node_2D seg = btseg.getElement(i);
				Point3D start = seg.getStartPoint();
				Point3D end = seg.getEndPoint();
				if ( (btseg.getElement(i+1) != null)&&(btseg.getElement(i-1) != null)&&(btseg.getElement(i+2) != null) ) {
					Node_2D seg2 = btseg.getElement(i+1);
					Point3D start2 = seg2.getStartPoint();
					Point3D end2 = seg2.getEndPoint();
					double k_h = Math.abs( (seg.getStartPoint().getY())-(seg2.getEndPoint().getY()) )/Math.abs( (seg.getStartPoint().getX())-(seg2.getEndPoint().getX()));

					boolean cond1 = (Math.abs(seg.getK())) >= ((btseg.getDrifting())) ;
					boolean cond2 = Math.abs(seg2.getK()) >= (btseg.getDrifting());
					boolean cond3 = ( seg.getK()*seg2.getK() ) < 0;
					boolean cond4 = ( Math.abs(btseg.getElement(i-1).getK() - (k_h)) + Math.abs(btseg.getElement(i+2).getK() - (k_h)) ) < btseg.getDrifting();
					//System.out.print(cond1+"--"+cond2+"--"+cond3+"--"+cond4);
					if   ( cond1 && cond2 && cond3 && cond4)
					{
							System.out.print("Pattern "+start.getX()+"-"+end2.getX()+" :");
							System.out.print("INTERRUPTED");
							System.out.println("\n");
							w.write("Pattern "+start.getX()+"-"+end2.getX()+" : ");
							w.write("INTERRUPTED\n");
							w.write("\n");
							interr = true;
							i++;
							i++;
					}
				}
				if (interr == false) {
					//System.out.print("Pattern "+start.getX()+"-"+end.getX()+" :");
					w.write("Pattern "+start.getX()+"-"+end.getX()+" : ");
					if ( (Math.abs(seg.getK())) < btseg.getDrifting() )
						//System.out.print("DOMINATED");
						w.write("DOMINATED\n");
					else if ( (Math.abs(seg.getK())) >= btseg.getDrifting() )
						//System.out.print("DRIFTING");
						w.write("DRIFTING\n");
					//System.out.println("\n");
					w.write("\n");
					i++;
				}
			}
				w.write("============== FINE RISULTATI ===============\n");
		}
		catch (Exception e){};
	}


	public Cuts3(String f, int voclen, double minspan, double drifting, int method, boolean stat) throws Throwable {

		//Set dei parametri offerti dall'interfaccia utente
		boolean set_minspan_by_user = false;
		boolean set_drifting_by_user = false;

		this.VOC_LEN = voclen;

		if (minspan != 0.0) {
			this.MINSPAN = minspan;
			set_minspan_by_user = true;
		}

		if (drifting != 0.0) {
			this.LAMBDA_DRIFTING = drifting;
			set_drifting_by_user = true;
		}

		System.out.println(this.VOC_LEN);
		System.out.println(this.MINSPAN);
		System.out.println(this.LAMBDA_DRIFTING);
		System.out.println(f);
		System.out.println(method);

		AnalisysVector avinput = new AnalisysVector(0);
		AnalisysVector avoutput = new AnalisysVector(0);
		String numelementi;
		String start;
		String end;

		if (stat == true){
			System.out.println("ACCERTARSI DI AVER INSERITO NEL SISTEMA LA CLASSIFICAZIONE REALE...\n");
			System.out.println("Quanti elementi contiene la classificazione?\n ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//System.out.readln(numelementi);
			numelementi = br.readLine();
			avinput = new AnalisysVector(Integer.parseInt(numelementi));
			avinput = buildAnalisysVector(Integer.parseInt(numelementi));
		}

		/*System.out.println("INSERISCI LA TUA CLASSIFICAZIONE :\n");
		System.out.println("Quanti elementi?\n ");
		AnalisysVector avinput;
		AnalisysVector avoutput;
		int numelementi = 0;
		int start;
		int end;
		System.out.readln(numelementi);
		avinput = new AnalisysVector(numelementi);
		for (int i = 1; i <= numelementi; i++) {
			System.out.println("Elemento "+i+"\n");
			System.out.println("Start: ");
			System.out.readln(start);
			System.out.println("\nEnd: ");
			System.out.readln(end);
			avinput.addElement(start, end);
		}*/

		//Files su cui verranno salvati i log visualizzari nell'interfaccia
		FileWriter log = new FileWriter(PATH+"data/log.txt");
		FileWriter entrylog = new FileWriter(PATH+"data/entrylog.txt");
		FileWriter cslog = new FileWriter(PATH+"data/cslog.txt");
		FileWriter btslog = new FileWriter(PATH+"data/btslog.txt");
		FileWriter tdplog = new FileWriter(PATH+"data/tdplog.txt");
		FileWriter statlog = new FileWriter(PATH+"data/statlog.txt");

		//Creazione del vocabolario vuoto
		Vocabolario Voc = new Vocabolario(VOC_LEN);			//Vocabolario di tutti i termini

		//Cancellazione di tutti i files nella directory PATH/data/
		File deldir = new File(PATH+"data/");
		File[] ls = deldir.listFiles();
		for(int i = 0; i < ls.length; i++) {
			//System.out.println("deleting");
			if (ls[i].isFile()) ls[i].delete();
        }

		//FASE DI PARSING + STEMMING + SW ELIMINATION
		FileReader in = new FileReader(f);
		//MyCutsStem ta = new MyCutsStem(in, f);
		MyCutsStem2 ta = new MyCutsStem2(in, f);

		//Nella directory PATH/data/EntryFiles/ ci saranno tutti i file che contengono i testi
		//delle entry su cui è stato effettuato lo stemming
		File dir = new File(PATH+"data/EntryFiles/");
		String[] children = dir.list();

		//Creo il vettore di vettori (signature)
		EntrySignature [] ES = new EntrySignature[children.length];
		if (children == null) {
		        System.out.println("La directory di input non contiene file");
		} else {

			entrylog.write("\n");
			entrylog.write("=============================================\n");
			entrylog.write("========= ELENCO ENTRY DEL BLOG =============\n");
			entrylog.write("=============================================\n");
			entrylog.write("\n");

			System.out.println("CREAZIONE VOCABOLARIO: Vocabolario.txt\n");
			System.out.println("CREAZIONE VETTORI DELLE ENTRY: Vettori.txt\n");
			//Per ogni entry creo un vettore(signature) che la rappresenta

			/*String filename = children[0];
			Reader reader;
			reader = new InputStreamReader(new FileInputStream(dir+"/"+filename));
			reader = new BufferedReader(reader);

			Calendar startTime = ExtractDate(reader);

			filename = children[1];
			reader = new InputStreamReader(new FileInputStream(dir+"/"+filename));
			reader = new BufferedReader(reader);

			Calendar endTime = ExtractDate(reader);

			System.out.println("START SECONDI= "+startTime.get(Calendar.SECOND));
			System.out.println("END SECONDI= "+endTime.get(Calendar.SECOND));

			long diffMillis = endTime.getTimeInMillis()-startTime.getTimeInMillis();

			// Get difference in seconds
    		long diffSecs = diffMillis/(1000);
    		System.out.println("DIFFERENZA= "+diffSecs);*/

			Calendar startTime = null;

			for (int i=0; i<children.length; i++) {
				// Get filename of file or directory

				String filename = children[i];


				entrylog.write(filename);
				entrylog.write("\n");

				Reader reader;
				reader = new InputStreamReader(new FileInputStream(dir+"/"+filename));
				reader = new BufferedReader(reader);

				//Creo il vettore di signature per l'entry corrente
				ES[i] = new EntrySignature(VOC_LEN);

				if (i == 0) {
					startTime = ExtractDate(reader);
					ES[i].setTime(0);
				}
				else {
					Calendar endTime = ExtractDate(reader);
					long diffMillis = endTime.getTimeInMillis()-startTime.getTimeInMillis();
    				long diffSecs = diffMillis/(1000);
					ES[i].setTime((int)diffSecs);
					//System.out.println("DIFFERENZA= "+diffSecs);
				}

				String characters = "";	//stringa che contiene ogni singola parola
				int car;
				boolean emptyline = false;
				for (int r = 0; r<=6; r++)
					reader.read();

				while ((car = reader.read()) != -1) {

					//if (i == 3) System.out.println("car ="+(char)car);
					if ( ( ((char)car) != '\n') && (car != 13) ) {
						characters = characters+((char)car);
						emptyline = false;
					}
					else if ( car == 13) {
					}
					else if ( ( ((char)car) == '\n' ) && ( emptyline == false ) ) {
						int ins = Voc.getInsertPoint();
						int searchindex = Voc.findKey(characters);
						if (searchindex >= ins) {
							//System.out.println("characters ="+characters);
							Voc.addKey(characters, ins);
							ES[i].addKey(ins);
							Voc.setInsertPoint(ins+1);
							characters="";
						}
						else {
							ES[i].addKey(searchindex);
							characters="";
						}
						emptyline = true;
					}

				}

				//Mentre creo la signature aggiungo anche nuovi vocaboli al Dizionario
				int ins = Voc.getInsertPoint();
				int searchindex = Voc.findKey(characters);
				if (searchindex >=ins) {
					Voc.addKey(characters, ins);
					ES[i].addKey(ins);
					Voc.setInsertPoint(ins+1);
					characters="";
				}
				else {
					ES[i].addKey(searchindex);
				}
			}//Chiudo il for di lettura dei files
    	}//chiudo else

		entrylog.write("\n");
		entrylog.write("##############################################################################################\n");
		entrylog.close();

		//Scrivo il file VETTORI.TXT che contiene tutte le entry signature
		//Scrivo i tempi delle entry sul file timepoints.dat
		FileWriter writer = new FileWriter(PATH+"data/vettori.txt");
		FileWriter timewriter = new FileWriter(PATH+"data/timepoints.dat");
		for (int p=0; p<ES.length; p++) {
			ES[p].printSignature(writer);
			ES[p].printTimePointsOnFile(timewriter);
		}
		writer.close();
		timewriter.close();

		/*if (set_minspan_by_user == false) {
			double numentry = ES.length;
			System.out.println(numentry);
			double numentry1 = numentry/100;
			System.out.println(numentry1);
			//double numentry2 = numentry1*5;
			double numentry2 = numentry1*15;
			System.out.println(numentry2);
			this.MINSPAN = (int)numentry2;
		}
		System.out.println("MINSPAN="+MINSPAN);*/


		if (set_minspan_by_user == false) {
			double numentry = ES.length;
			System.out.println(numentry);
			double numentry1 = numentry*PERC_MS;
			this.MINSPAN = (int)numentry1;
		}
		System.out.println("MINSPAN="+MINSPAN);

		//Scrivo il file VOCABOLARIO.TXT che contiene tutti i termini trovati nelle entry
		Voc.printOnFile();


		System.out.println("APPLICAZIONE TFIDF AD OGNI VETTORE DI ENTRY --> Output: vettoriTFIDF.dat\n");

		//Applico la Normalizzazione TFIDF ai vettori di ogni entry
		WeightVectors WS = new WeightVectors(ES);
		WS.entrysTotfidfs(Voc);
		WS.printOnFile();


		//Dato il file TFIDFSignature.dat applico a questo input il MultiDimensonal scaling di Matlab
        System.out.println("APPLICAZIONE MDS CON MATLAB -->Output: mdsres.dat\n");
        Process p = null;
        if (method == 0) {
			System.out.println("Applico il metodo Euristico");
			p = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r mymdskdplus");
		}
		else if (method == 1) {
			System.out.println("Applico il metodo 1D");
			p = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r mymds1d");

		}
		else if (method == 2) {
			System.out.println("Applico il metodo 2D");
			p = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r mymds2d");
		}
		p.waitFor();
		//Thread.sleep(30000);
     	System.out.println("Done.");


		//MDSPoint [] MDSpoints = null;
		//Vector2dElem [] MDS2dpoints = null;
		CurveSegmentsTree CSTree = null;
		CurveSegmentsTree2D CSTree2d= null;
		Node tree = null;
		Node_2D tree2d = null;
		int numseg = 0;
		Node [] csv = null;
		Node_2D [] csv2d = null;

   		if ( (method == 0)||(method == 1) ){

   			//COSTRUZIONE DEL VETTORE CONTENENTE I PUNTI DEL MDS
   				MDSPoint [] MDSpoints = buildMDSVector(ES.length, ES);


			//COSTRUZIONE ALBERO DEI CURVE SEGMENTS
				System.out.println("APPLICAZIONE CURVE SEGMENTATION\n");
				Node nodo = new Node();
				CSTree = new CurveSegmentsTree();
				tree = CSTree.buildTree(nodo, MDSpoints,0,MDSpoints.length-1,null,MINSPAN);
				cslog.write("\n");
				cslog.write("=============================================\n");
				cslog.write("============== CURVE SEGMENTS ===============\n");
				cslog.write("=============================================\n");


			//SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI
				System.out.println("SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI\n");
				//Visito l'albero in modo bottom-up e
				//SETTO I NODI CHE RAPPRESENTANO CURVE SEGMENTS DA PRENDERE
				CSTree.setCurveSegmentsNodes(tree);
				//INSERISCO I CURVE SEGMENTS CHE HO SCELTO IN UN VETTORE
				CSTree.setCSVectorLen(tree);
				numseg = CSTree.getLen();
				CSTree.setCurveSegmentsVector(tree);
				FileWriter segplotx = new FileWriter(PATH+"data/plotsegmentsX.dat");
				FileWriter segploty = new FileWriter(PATH+"data/plotsegmentsY.dat");
				CSTree.printSegmentsLog(tree,cslog, segplotx, segploty, MDSpoints);
				//CSTree.printTree(tree);
				cslog.write("\n");
				cslog.write("##############################################################################################\n");
				cslog.close();
				segplotx.close();
				segploty.close();
				//Visualizzazione del grafico dei Curve Segments
				Process p1 = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r plotsegments");
				p1.waitFor();
     			System.out.println("Done.");

     		//CALCOLO DEL VALORE DI DRIFTING
				if (set_drifting_by_user == false) {
					double max = CSTree.getMaxValue();
					double min = CSTree.getMinValue();
					double range = max - min;
					//double totalTime = CSTree.get
					//double percrange1 = range/100;
					//double percrange2 = percrange1*5;
					double percrange1 = range*PERC_DRIF;
					//this.LAMBDA_DRIFTING = percrange2;
					this.LAMBDA_DRIFTING = percrange1;
					System.out.println("MIN ="+max);
					System.out.println("MAX ="+min);
				}

				System.out.println("LAMBDA_DRIFTING ="+LAMBDA_DRIFTING);

     		//DATI I CURVE SEGMENTS SELEZIONO I BASE TOPIC SEGMENTS
				System.out.println("CURVE SEGMENTS --> BASE TOPIC SEGMENTS\n");
				csv = CSTree.getCurveSegmentsVector();
				btslog.write("\n");
				btslog.write("=============================================\n");
				btslog.write("============== BASE TOPIC SEGMENTS===========\n");
				btslog.write("=============================================\n");
				BTS btseg = new BTS(LAMBDA_DRIFTING,csv.length);
				//Calcolo i Base Topic Sgegments
				btseg.setBTSvector(csv, btslog);
				int numbts = btseg.getbtsvectorlen();
				FileWriter btsplotx = new FileWriter(PATH+"data/plotbtsX.dat");
				FileWriter btsploty = new FileWriter(PATH+"data/plotbtsY.dat");
				btseg.printBaseTopicSegments(btslog, btsplotx, btsploty);
				btslog.write("\n");
				btslog.write("##############################################################################################\n");
				btslog.close();
				btsplotx.close();
				btsploty.close();
				//Visualizzazione del grafico dei Base Topic Segments
				Process p2 = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r plotbts");
				p2.waitFor();
				System.out.println("Done.");
				printAnnotation(MDSpoints.length, Voc.getNumVocaboli(), btseg, tdplog, numseg, numbts);

   			//CALCOLO DEI TOPIC DEVELOPMENT PATTERN
				TDP tdevpat = new TDP(LAMBDA_DRIFTING, btseg.getbtsvectorlen());
				tdevpat.setTDPvector(btseg, log);
				FileWriter tdp = new FileWriter(PATH+"data/tdplog.txt");
				tdevpat.printTopicDevelopPattern(ES, tdp);
				tdp.close();

			if (stat == true){
			//COSTRUZIONE VETTORE PER STATISTICHE
				avoutput = new AnalisysVector(tdevpat.gettdpatternvectorlen());
				for (int i = 0; i <tdevpat.gettdpatternvectorlen(); i++) {
					int s = tdevpat.findEntryByTime(tdevpat.getTdpElement(i).getStartPoint().getX(), ES);
					int e = tdevpat.findEntryByTime(tdevpat.getTdpElement(i).getEndPoint().getX(), ES);
					avoutput.addElement(s, e);
				}
			}
		}

   		else if (method == 2){


			//COSTRUZIONE DEL VETTORE CONTENENTE I PUNTI DEL MDS
				MDSPoint_2D [] MDS2dpoints = buildMDSVector_2D(ES.length, ES);
				System.out.println(MDS2dpoints[0].getY());
				System.out.println(MDS2dpoints[0].getZ());
				cslog.write("\n");
				cslog.write("=============================================\n");
				cslog.write("============== CURVE SEGMENTS ===============\n");
				cslog.write("=============================================\n");

			//COSTRUZIONE ALBERO DEI CURVE SEGMENTS
				System.out.println("APPLICAZIONE CURVE SEGMENTATION\n");
				Node_2D nodo2d = new Node_2D();
				CSTree2d = new CurveSegmentsTree2D();
				tree2d = CSTree2d.buildTree(nodo2d, MDS2dpoints, 0, MDS2dpoints.length-1, null, MINSPAN);

			//SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI
				System.out.println("SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI\n");
				//Visito l'albero in modo bottom-up e
				//SETTO I NODI CHE RAPPRESENTANO CURVE SEGMENTS DA PRENDERE
				CSTree2d.setCurveSegmentsNodes(tree2d);
				//INSERISCO I CURVE SEGMENTS CHE HO SCELTO IN UN VETTORE
				CSTree2d.setCSVectorLen(tree2d);
				numseg = CSTree2d.getLen();
				CSTree2d.setCurveSegmentsVector(tree2d);
				FileWriter segplotx = new FileWriter(PATH+"data/plotsegmentsX.dat");
				FileWriter segploty = new FileWriter(PATH+"data/plotsegmentsY.dat");
				FileWriter segplotz = new FileWriter(PATH+"data/plotsegmentsZ.dat");
				CSTree2d.printSegmentsLog(tree2d,MDS2dpoints, cslog, segplotx, segploty, segplotz);
				//CSTree.printTree(tree);
				cslog.write("\n");
				cslog.write("##############################################################################################\n");
				cslog.close();
				segplotx.close();
				segploty.close();
				segplotz.close();
				//Visualizzazione del grafico dei Curve Segments
				Process p1 = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r plotsegments2d");
				p1.waitFor();
     			System.out.println("Done.");

			//CALCOLO DEL VALORE DI DRIFTING
				if (set_drifting_by_user == false) {

					double rangeony = CSTree2d.getRangeOnY();
					System.out.println("RANGE_Y ="+rangeony);
					double rangeonz = CSTree2d.getRangeOnZ();
					System.out.println("RANGE_Z ="+rangeonz);
					double boundingy = CSTree2d.getBoundingBoxOnY();
					double boundingz = CSTree2d.getBoundingBoxOnZ();
					System.out.println("BOUNDING_Y ="+boundingy);
					System.out.println("BOUNDING_Z ="+boundingz);
					//LAMBDA_DRIFTING_Y = (double)(rangeony/boundingy) * 0.25;
					//LAMBDA_DRIFTING_Z = (double)(rangeonz/boundingz) * 0.25;
					//LAMBDA_DRIFTING_Y = (double)(rangeony/boundingy) * 0.15;
					//LAMBDA_DRIFTING_Z = (double)(rangeonz/boundingz) * 0.15;
					LAMBDA_DRIFTING_Y = (double)(rangeony/boundingy) * PERC_DRIF;
					LAMBDA_DRIFTING_Z = (double)(rangeonz/boundingz) * PERC_DRIF;
					//LAMBDA_DRIFTING_Y = (double)(rangeony/boundingy);
					//LAMBDA_DRIFTING_Z = (double)(rangeonz/boundingz);
				}
				System.out.println("LAMBDA_DRIFTING_Y ="+LAMBDA_DRIFTING_Y );
				System.out.println("LAMBDA_DRIFTING_Z ="+LAMBDA_DRIFTING_Z );

     		//DATI I CURVE SEGMENTS SELEZIONO I BASE TOPIC SEGMENTS
				System.out.println("CURVE SEGMENTS --> BASE TOPIC SEGMENTS\n");
				csv2d = CSTree2d.getCurveSegmentsVector();
				btslog.write("\n");
				btslog.write("=============================================\n");
				btslog.write("============== BASE TOPIC SEGMENTS===========\n");
				btslog.write("=============================================\n");
				BTS_2D btseg2d = new BTS_2D(LAMBDA_DRIFTING_Y, LAMBDA_DRIFTING_Z, csv2d.length);

				//Calcolo i Base Topic Sgegments
				btseg2d.setBTSvector(csv2d, btslog);
				int numbts = btseg2d.getbtsvectorlen();
				FileWriter btsplotx = new FileWriter(PATH+"data/plotbtsX.dat");
				FileWriter btsploty = new FileWriter(PATH+"data/plotbtsY.dat");
				FileWriter btsplotz = new FileWriter(PATH+"data/plotbtsZ.dat");
				btseg2d.printBaseTopicSegments(btslog, btsplotx, btsploty, btsplotz, MDS2dpoints);
				btslog.write("\n");
				btslog.write("##############################################################################################\n");
				btslog.close();
				btsplotx.close();
				btsploty.close();
				btsplotz.close();
				//Visualizzazione del grafico dei Base Topic Segments
				Process p2 = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r plotbts2d");
				p2.waitFor();
				System.out.println("Done.");
				//printAnnotation2D(MDS2dpoints.length, Voc.getNumVocaboli(), btseg2d, tdplog, numseg, numbts);

			//CALCOLO DEI TOPIC DEVELOPMENT PATTERN
				TDP_2D tdevpat2d = new TDP_2D(LAMBDA_DRIFTING_Y, LAMBDA_DRIFTING_Y, btseg2d.getbtsvectorlen());
				tdevpat2d.setTDPvector(btseg2d, log);
				FileWriter tdp = new FileWriter(PATH+"data/tdplog.txt");
				tdevpat2d.printTopicDevelopPattern(ES, tdp);
				tdp.close();

			if (stat == true){
				//COSTRUZIONE VETTORE PER STATISTICHE
				avoutput = new AnalisysVector(tdevpat2d.gettdpatternvectorlen());
				for (int i = 0; i <tdevpat2d.gettdpatternvectorlen(); i++) {
					int s = tdevpat2d.findEntryByTime(tdevpat2d.getTdpElement(i).getStartPoint().getX(), ES);
					int e = tdevpat2d.findEntryByTime(tdevpat2d.getTdpElement(i).getEndPoint().getX(), ES);
					avoutput.addElement(s, e);
				}
			}

		}

		if (stat == true){
			//CALCOLO DELLE STATISTICHE
			//double sumprecision;
			//double sumrecall;
			/*avoutput = new AnalisysVector(tdevpat.gettdpatternvectorlen());
			for (int i = 0; i <tdevpat.gettdpatternvectorlen(); i++) {
				int start = findEntryByTime(this.tdpatternvector[i].getStartPoint().getX(), ES);
				int end = findEntryByTime(this.tdpatternvector[i].getEndPoint().getX(), ES);
				avoutput.addElement(start, end);
			}*/

			/*avinput.printvec();
			avoutput.printvec();


			//FOREWARD PRECISION
			sumprecision = 0;
			sumrecall = 0;
			for (int j = 0; j < avinput.getLen(); j++){
				avinput.getElement(j).setQualityElem(avoutput);
				//avinput.getElement(j).setQualityElemv2(avoutput);
				int q = avinput.getElement(j).getQualityElem();
				if (q != -1 ){
					avinput.getElement(j).setPrecision(avoutput.getElement(q));
					avinput.getElement(j).setRecall(avoutput.getElement(q));
				}
				sumprecision = sumprecision + avinput.getElement(j).getPrecision();
				sumrecall = sumrecall + avinput.getElement(j).getRecall();
			}

			avinput.setAvgPrecision(sumprecision);
			avinput.setAvgRecall(sumrecall);
			avinput.setF1();

			avinput.printAnalisysVector(avoutput, statlog);


			//BACKWARD PRECISION
			sumprecision = 0;
			sumrecall = 0;
			for (int k = 0; k < avoutput.getLen(); k++){
				avoutput.getElement(k).setQualityElem(avinput);
				//avoutput.getElement(k).setQualityElemv2(avinput);
				int q = avoutput.getElement(k).getQualityElem();
				if (q != -1 ){
					avoutput.getElement(k).setPrecision(avinput.getElement(q));
					avoutput.getElement(k).setRecall(avinput.getElement(q));
				}
				sumprecision = sumprecision + avoutput.getElement(k).getPrecision();
				sumrecall = sumrecall + avoutput.getElement(k).getRecall();
			}
			avoutput.setAvgPrecision(sumprecision);
			avoutput.setAvgRecall(sumrecall);
			avoutput.setF1();

			avoutput.printAnalisysVector(avinput, statlog);

			statlog.close();*/

			/*avinput.assignQuality(avoutput);
			avoutput.assignQuality(avinput);
			double recall = avinput.setTotalQuality();
			double precision = avoutput.setTotalQuality();
			avoutput.printAnalisysVector(avinput, statlog, precision, recall);*/


			avoutput.assignQualityBackward(avinput);
			double precision = avoutput.setTotalQuality();
			avoutput.printAnalisysVector(avinput, statlog, precision, 'P');
			avinput.refreshAssignment();

			avoutput.assignQualityForeward(avinput);
			double recall = avoutput.setTotalQuality();
			avoutput.printAnalisysVector(avinput, statlog, recall, 'R');
			avinput.refreshAssignment();

			avoutput.assignQualityOverall(avinput);
			System.out.println("overall");
			double overall = avoutput.setTotalQuality();
			avoutput.printAnalisysVector(avinput, statlog, overall, 'O');


			statlog.close();
		}

	}

}
