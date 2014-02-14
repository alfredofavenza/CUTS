
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



public class CutsMainAdv {

    public static String PATH = "C:/CutsDEV_ori/";
    private int VOC_LEN;
	private double MINSPAN;
    private double DELTA_DRIFTING;

    //private static void usage()
    //{
    //    System.err.println("Usage: CutsMain <input file> ");
    //}


    /*private static boolean checkStopWord(String[] sw, String word)
	{
		int i = 0;
		boolean found = false;
		//System.out.println(word);
		while ((i< sw.length) && (found == false)) {
			System.out.println("Confronto "+word+" con "+sw[i]);
			if (word.compareTo(sw[i])==0) {
				//System.out.println(word);
				//System.out.println(sw[i]);
					found = true;
			}
			else i = i+1;
		}
		return found;
	}*/

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
			w.write("delta_drifting= "+btseg.getDrifting()+"\n");
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
							//System.out.print("Pattern "+start.getX()+"-"+end2.getX()+" :");
							//System.out.print("INTERRUPTED");
							//System.out.println("\n");
							w.write("Pattern "+start.getX()+"-"+end2.getX()+" : ");
							w.write("INTERRUPTED\n");
							w.write("\n");
							interr = true;
							i++;
							i++;
					}
				}
				if (interr == false) {
					//Calcolo che tipo di pattern (Dominated, Drifting, Interrupted)
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

	public CutsMainAdv(String f, int voclen, double minspan, double drifting) throws Throwable {

		this.VOC_LEN = voclen;
		this.MINSPAN = minspan;
		this.DELTA_DRIFTING = drifting;

		System.out.println(this.VOC_LEN);
		System.out.println(this.MINSPAN);
		System.out.println(this.DELTA_DRIFTING);
		System.out.println(f);

		FileWriter log = new FileWriter(PATH+"data/log.txt");
		FileWriter entrylog = new FileWriter(PATH+"data/entrylog.txt");
		FileWriter cslog = new FileWriter(PATH+"data/cslog.txt");
		FileWriter btslog = new FileWriter(PATH+"data/btslog.txt");
		FileWriter tdplog = new FileWriter(PATH+"data/tdplog.txt");

		Vocabolario Voc = new Vocabolario(VOC_LEN);			//Vocabolario di tutti i termini

		File deldir = new File(PATH+"data/");

		File[] ls = deldir.listFiles();
		for(int i = 0; i < ls.length; i++) {
			//System.out.println("deleting");
			if (ls[i].isFile()) ls[i].delete();
        }


		//Leggo i file dalla directory di input (files Stemmati)
		FileReader in = new FileReader(f);
		MyCutsStem ta = new MyCutsStem(in, f);
		//MyCutsStem2 ta = new MyCutsStem2(in);




		File dir = new File(PATH+"data/EntryFiles/");

		String[] children = dir.list();
		EntrySignature [] ES = new EntrySignature[children.length];     //Vettore che conterrà tutti i vettori
		if (children == null) {
		        // Either dir does not exist or is not a directory
		        System.out.println("La directory di input non contiene file");
		} else {
			log.write("\n");
			log.write("=============================================\n");
			log.write("========= ELENCO ENTRY DEL BLOG =============\n");
			log.write("=============================================\n");
			log.write("\n");
			entrylog.write("\n");
			entrylog.write("=============================================\n");
			entrylog.write("========= ELENCO ENTRY DEL BLOG =============\n");
			entrylog.write("=============================================\n");
			entrylog.write("\n");

			System.out.println("CREAZIONE VOCABOLARIO: Vocabolario.txt\n");
			System.out.println("CREAZIONE VETTORI DELLE ENTRY: Vettori.txt\n");
			for (int i=0; i<children.length; i++) {
				// Get filename of file or directory


				String filename = children[i];

				log.write(filename);
				log.write("\n");

				entrylog.write(filename);
				entrylog.write("\n");

				Reader reader;
				reader = new InputStreamReader(new FileInputStream(dir+"/"+filename));
				reader = new BufferedReader(reader);

				//Creo il vettore di sognature per entry corrente
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
			}//Chido il for di lettura dei files
    	}//chiudo else
    	log.write("\n");
		log.write("##############################################################################################\n");
		entrylog.write("\n");
		entrylog.write("##############################################################################################\n");
		entrylog.close();

		//Scrivo il file VETTORI.TXT che contiene tutte le entry signature
		FileWriter writer = new FileWriter(PATH+"data/vettori.txt");
		for (int p=0; p<ES.length; p++)
			ES[p].printSignature(writer);
		writer.close();

		//Scrivo il file VOCABOLARIO.TXT che contiene tutti i termini trovati nelle entry
		Voc.printOnFile();


		System.out.println("APPLICAZIONE TFIDF AD OGNI VETTORE DI ENTRY --> Output: vettoriTFIDF.dat\n");
		//Applico TFIDF ai vettori di ogni entry
		WeightVectors WS = new WeightVectors(ES);
		WS.entrysTotfidfs(Voc);
		WS.printOnFile();



        System.out.println("APPLICAZIONE MDS CON MATLAB -->Output: mdsres.dat\n");
		Process p = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r mymds");
		p.waitFor();
		//Thread.sleep(30000);
     	System.out.println("Done.");

   		double [] MDSpoints = buildMDSVector(ES.length);
		//for (int q=0; q<MDSpoints.length; q++) {
		//	System.out.println(MDSpoints[q]);
		//	System.out.println('\n');
		//}


		//Costruisco l'albero dei CURVE SEGMENTS
		System.out.println("APPLICAZIONE CURVE SEGMENTATION\n");
		CurveSegmentsTree CSTree= new CurveSegmentsTree();
		Node nodo = new Node();
		Node tree = CSTree.buildTree(nodo, MDSpoints,0,MDSpoints.length-1,null,MINSPAN);

		log.write("\n");
		log.write("\n");
		log.write("=============================================\n");
		log.write("============== CURVE SEGMENTS ===============\n");
		log.write("=============================================\n");

		cslog.write("\n");
		cslog.write("=============================================\n");
		cslog.write("============== CURVE SEGMENTS ===============\n");
		cslog.write("=============================================\n");


		System.out.println("SELEZIONE DELL'INSIEME OTTIMALE DI SEGMENTI\n");

		//Visito l'albero in modo bottom-up e
		//SETTO I NODI CHE RAPPRESENTANO CURVE SEGMENTS DA PRENDERE
		CSTree.setCurveSegmentsNodes(tree);
		//INSERISCO I CURVE SEGMENTS CHE HO SCELTO IN UN VETTORE
		CSTree.setCSVectorLen(tree);
		int numseg = CSTree.getLen();
		log.write("Minspan = "+MINSPAN+"   Num. segmenti = "+numseg+"\n");
		log.write("\n");
		CSTree.setCurveSegmentsVector(tree);
		FileWriter segplotx = new FileWriter(PATH+"data/plotsegmentsX.dat");
		FileWriter segploty = new FileWriter(PATH+"data/plotsegmentsY.dat");
		CSTree.printSegmentsLog(tree,cslog, segplotx, segploty);
		//CSTree.printTree(tree);

		log.write("\n");
		log.write("##############################################################################################\n");
		cslog.write("\n");
		cslog.write("##############################################################################################\n");
		cslog.close();

		segplotx.close();
		segploty.close();

		Process p1 = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r plotsegments");
		p1.waitFor();
     	System.out.println("Done.");


		//DATI I CURVE SEGMENTS SELEZIONO I BASE TOPIC SEGMENTS
		System.out.println("CURVE SEGMENTS --> BASE TOPIC SEGMENTS\n");
		Node [] csv = CSTree.getCurveSegmentsVector();
		//Creo un oggetto vettore di BaseTopicSegment
		log.write("\n");
		log.write("=============================================\n");
		log.write("============== BASE TOPIC SEGMENTS===========\n");
		log.write("=============================================\n");

		btslog.write("\n");
		btslog.write("=============================================\n");
		btslog.write("============== BASE TOPIC SEGMENTS===========\n");
		btslog.write("=============================================\n");


		BTS btseg = new BTS(DELTA_DRIFTING,csv.length);
		//Calcolo i Base Topic Sgegments
		btseg.setBTSvector(csv, btslog);
		int numbts = btseg.getbtsvectorlen();

		FileWriter btsplotx = new FileWriter(PATH+"data/plotbtsX.dat");
		FileWriter btsploty = new FileWriter(PATH+"data/plotbtsY.dat");
		btseg.printBaseTopicSegments(btslog, btsplotx, btsploty);

		log.write("\n");
		log.write("##############################################################################################\n");
		btslog.write("\n");
		btslog.write("##############################################################################################\n");
		btslog.close();

		btsplotx.close();
		btsploty.close();
		Process p2 = Runtime.getRuntime().exec("matlab /nosplash /nodesktop /r plotbts");
		p2.waitFor();
     	System.out.println("Done.");

		printAnnotation(MDSpoints.length, Voc.getNumVocaboli(), btseg, tdplog, numseg, numbts);

		log.write("\n");
		log.write("##############################################################################################\n");
		log.write("\n");

		//Calcolo i Topic Development Pattern
		TDP tdevpat = new TDP(DELTA_DRIFTING, btseg.getbtsvectorlen());
		tdevpat.setTDPvector(btseg, log);
		//System.out.println(tdevpat.gettdpatternvectorlen());
		FileWriter tdp = new FileWriter(PATH+"data/tdplog.txt");
		tdevpat.printTopicDevelopPattern(tdp);
		tdp.close();
		//Elimino Oversegmentation

		log.close();

	}

}
