
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



public class Cuts2{

    public static String PATH = "C:/CutsDEV_ori/";
    private int VOC_LEN;
	private double MINSPAN;
    private double LAMBDA_DRIFTING;


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


	//private int calcTime(){
	//}

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

    private static Vector2dElem [] buildMDSVector_2D(int len)
	{
		Vector2dElem [] MDS2dPoints = new Vector2dElem[len];
		for (int i = 0; i<len ; i++)
			MDS2dPoints[i] = null;
		System.out.println("Sono in buildMDSVector_2D");
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
				MDS2dPoints[i] = new Vector2dElem(yp,zp);
				ypoint = "";
				zpoint = "";
				i = i+1;
			}

		}
		catch(Exception e){};
		System.out.println("Esco da buildMDSVector_2D");
		return MDS2dPoints;
    }

	private static double [] buildMDSVector(int len)
	{
		double [] MDSPoints = new double[len];
		try {
			FileReader reader = new FileReader(PATH+"data/mdsres.dat");
			String point = "";
			int car;
			int i = 0;
			while ((car = reader.read()) != -1) {
				if ( (char)car != ' '){
					point = point+((char)car);
				} else {
					MDSPoints[i] = Double.parseDouble(point);
					point = "";
					i = i+1;
				}
			}
			MDSPoints[i] = Double.parseDouble(point);
			point = "";
		}
		catch(Exception e){};
		return MDSPoints;
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


	public Cuts2(String f, int voclen, double minspan, double drifting, int method) throws Throwable {

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

		//Files su cui verranno salvati i log visualizzari nell'interfaccia
		FileWriter log = new FileWriter(PATH+"data/log.txt");
		FileWriter entrylog = new FileWriter(PATH+"data/entrylog.txt");
		FileWriter cslog = new FileWriter(PATH+"data/cslog.txt");
		FileWriter btslog = new FileWriter(PATH+"data/btslog.txt");
		FileWriter tdplog = new FileWriter(PATH+"data/tdplog.txt");

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
		MyCutsStem ta = new MyCutsStem(in, f);
		//MyCutsStem2 ta = new MyCutsStem2(in, f);


		//Nella directory PATH/data/EntryFiles/ ci saranno tutti i file che contengono i testi
		//delle entry su cui è stato effettuato lo stemming
		File dir = new File(PATH+"data/EntryFiles/");
		String[] children = dir.list();

		//Creo il vettore di vettori (signature)
		EntrySignature [] ES = new EntrySignature[children.length];
		if (children == null) {
		        System.out.println("La directory di input non contiene file");
		} else {
			///log.write("\n");
			///log.write("=============================================\n");
			///log.write("========= ELENCO ENTRY DEL BLOG =============\n");
			///log.write("=============================================\n");
			///log.write("\n");
			entrylog.write("\n");
			entrylog.write("=============================================\n");
			entrylog.write("========= ELENCO ENTRY DEL BLOG =============\n");
			entrylog.write("=============================================\n");
			entrylog.write("\n");

			System.out.println("CREAZIONE VOCABOLARIO: Vocabolario.txt\n");
			System.out.println("CREAZIONE VETTORI DELLE ENTRY: Vettori.txt\n");
			//Per ogni entry creo un vettore(signature) che la rappresenta

			for (int i=0; i<children.length; i++) {
				// Get filename of file or directory

				String filename = children[i];

				///log.write(filename);
				///log.write("\n");

				entrylog.write(filename);
				entrylog.write("\n");

				Reader reader;
				reader = new InputStreamReader(new FileInputStream(dir+"/"+filename));
				reader = new BufferedReader(reader);

				//Creo il vettore di signature per l'entry corrente
				ES[i] = new EntrySignature(VOC_LEN);

				String characters = "";	//stringa che contiene ogni singola parola
				int car;
				boolean emptyline = false;

				while ((car = reader.read()) != -1) {

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
    	///log.write("\n");
		///log.write("##############################################################################################\n");
		entrylog.write("\n");
		entrylog.write("##############################################################################################\n");
		entrylog.close();

		//Scrivo il file VETTORI.TXT che contiene tutte le entry signature
		FileWriter writer = new FileWriter(PATH+"data/vettori.txt");
		for (int p=0; p<ES.length; p++)
			ES[p].printSignature(writer);
		writer.close();

		if (set_minspan_by_user == false) {
			double numentry = ES.length;
			System.out.println(numentry);
			double numentry1 = numentry/100;
			System.out.println(numentry1);
			double numentry2 = numentry1*5;
			System.out.println(numentry2);
			this.MINSPAN = (int)numentry2;
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


		double [] MDSpoints = null;
		Vector2dElem [] MDS2dpoints = null;
		CurveSegmentsTree CSTree = null;
		CurveSegmentsTree2D CSTree2d= null;
		Node tree = null;
		Node_2D tree2d = null;
		int numseg = 0;
		Node [] csv = null;
		Node_2D [] csv2d = null;

   		if ( (method == 0)||(method == 1) ){

   			//COSTRUZIONE DEL VETTORE CONTENENTE I PUNTI DEL MDS
   				MDSpoints = buildMDSVector(ES.length);
   				cslog.write("\n");
				cslog.write("=============================================\n");
				cslog.write("============== CURVE SEGMENTS ===============\n");
				cslog.write("=============================================\n");
				System.out.println("SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI\n");

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
				CSTree.printSegmentsLog(tree,cslog, segplotx, segploty);
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
					double percrange1 = range/100;
					double percrange2 = percrange1*5;
					this.LAMBDA_DRIFTING = percrange2;
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
				tdevpat.printTopicDevelopPattern(tdp);
				tdp.close();
		}

   		else if (method == 2){


			//COSTRUZIONE DEL VETTORE CONTENENTE I PUNTI DEL MDS
				MDS2dpoints = buildMDSVector_2D(ES.length);
				//System.out.println(MDS2dpoints[0].getY());
				//System.out.println(MDS2dpoints[0].getZ());
				cslog.write("\n");
				cslog.write("=============================================\n");
				cslog.write("============== CURVE SEGMENTS ===============\n");
				cslog.write("=============================================\n");
				System.out.println("SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI\n");

			//COSTRUZIONE ALBERO DEI CURVE SEGMENTS
				System.out.println("APPLICAZIONE CURVE SEGMENTATION\n");
				Node_2D nodo2d = new Node_2D();
				CSTree2d = new CurveSegmentsTree2D();
				tree2d = CSTree2d.buildTree(nodo2d, MDS2dpoints, 0, MDS2dpoints.length-1, null, MINSPAN);
				cslog.write("\n");
				cslog.write("=============================================\n");
				cslog.write("============== CURVE SEGMENTS ===============\n");
				cslog.write("=============================================\n");

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
				CSTree2d.printSegmentsLog(tree2d,cslog, segplotx, segploty, segplotz);
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
					double max = CSTree2d.getMaxValue();
					System.out.println("MAX = "+max);
					double min = CSTree2d.getMinValue();
					System.out.println("MIN = "+min);
					double range = max - min;
					double percrange1 = range/100;
					double percrange2 = percrange1*5;
					this.LAMBDA_DRIFTING = percrange2;
				}
				System.out.println("LAMBDA_DRIFTING ="+LAMBDA_DRIFTING );

     		//DATI I CURVE SEGMENTS SELEZIONO I BASE TOPIC SEGMENTS
				System.out.println("CURVE SEGMENTS --> BASE TOPIC SEGMENTS\n");
				csv2d = CSTree2d.getCurveSegmentsVector();
				btslog.write("\n");
				btslog.write("=============================================\n");
				btslog.write("============== BASE TOPIC SEGMENTS===========\n");
				btslog.write("=============================================\n");
				BTS_2D btseg2d = new BTS_2D(LAMBDA_DRIFTING,csv2d.length);
				//Calcolo i Base Topic Sgegments
				btseg2d.setBTSvector(csv2d, btslog);
				int numbts = btseg2d.getbtsvectorlen();
				FileWriter btsplotx = new FileWriter(PATH+"data/plotbtsX.dat");
				FileWriter btsploty = new FileWriter(PATH+"data/plotbtsY.dat");
				FileWriter btsplotz = new FileWriter(PATH+"data/plotbtsZ.dat");
				btseg2d.printBaseTopicSegments(btslog, btsplotx, btsploty, btsplotz);
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
				printAnnotation2D(MDS2dpoints.length, Voc.getNumVocaboli(), btseg2d, tdplog, numseg, numbts);

			//CALCOLO DEI TOPIC DEVELOPMENT PATTERN
				TDP_2D tdevpat2d = new TDP_2D(LAMBDA_DRIFTING, btseg2d.getbtsvectorlen());
				tdevpat2d.setTDPvector(btseg2d, log);
				FileWriter tdp = new FileWriter(PATH+"data/tdplog.txt");
				tdevpat2d.printTopicDevelopPattern(tdp);
				tdp.close();
		}

	}

}
