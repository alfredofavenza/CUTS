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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class CurveSegmentsTree{

	private Node [] CurveSegmentsVector;
	private int len;
	private int insert_index;
	private int nseg;

	public CurveSegmentsTree(){
		this.len = 0;
		this.CurveSegmentsVector = null;
		this.insert_index = 0;
		this.nseg = 1;
	}

	public int getLen(){
		return this.len;
	}

	//public Node buildTree(Node nodo, double[] Y, int start, int end, Node par, double minspan)
	public Node buildTree(Node nodo, MDSPoint[] Y, int start, int end, Node par, double minspan)
	{

		// variabili che segnano l'inizio e la fine di ogni segmento all'interno del vettore
		int lineStart = start;
		int lineEnd = end;

		// Calcolo la distanza tra tutti i punti compresi tra s ed e
		//System.out.println(lineEnd-lineStart+1+" > "+minspan+"?");
		//nodo = null;
		//nodo = new Node(lineStart,Y[lineStart],lineEnd,Y[lineEnd],Y, par);
		try {
			nodo = new Node(lineStart,lineEnd, Y, par);
		}
		catch (Exception e){};
		if ( (lineEnd-lineStart+1) > minspan) {
			//nodo = new Node(lineStart,Y[lineStart],lineEnd,Y[lineEnd],Y, par);
			//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
			int bp = nodo.getBreakPoint();
			//System.out.println("Breakpoint: "+bp);
			//nodo.left = new Node(lineStart,Y[lineStart],bp,Y[bp],Y, nodo);

			//System.out.println("vado a SX");
			nodo.left = buildTree(nodo.left,Y, lineStart, bp, nodo, minspan);
			//System.out.println("vado a DX");
			nodo.right = buildTree(nodo.right,Y, bp, lineEnd, nodo, minspan);
		}
		return nodo;

	}

	/*public Node_2D buildTree2D(Node_2D nodo, Vector2dElem[] Y, int start, int end, Node_2D par, double minspan)
	{

		// variabili che segnano l'inizio e la fine di ogni segmento all'interno del vettore
		int lineStart = start;
		int lineEnd = end;

		// Calcolo la distanza tra tutti i punti compresi tra s ed e
		//System.out.println(lineEnd-lineStart+1+" > "+minspan+"?");
		//nodo = null;
		nodo = new Node_2D(lineStart,Y[lineStart].getY(), Y[lineStart].getZ(), lineEnd, Y[lineEnd].getY(), Y[lineEnd].getZ(), Y, par);
		if ( (lineEnd-lineStart+1) > minspan) {
			//nodo = new Node(lineStart,Y[lineStart],lineEnd,Y[lineEnd],Y, par);
			//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
			int bp = nodo.getBreakPoint();
			//System.out.println("Breakpoint: "+bp);
			//nodo.left = new Node(lineStart,Y[lineStart],bp,Y[bp],Y, nodo);

			//System.out.println("vado a SX");
			nodo.left = buildTree2D(nodo.left,Y, lineStart, bp, nodo, minspan);
			//System.out.println("vado a DX");
			nodo.right = buildTree2D(nodo.right,Y, bp, lineEnd, nodo, minspan);
		}
		return nodo;

	}*/


	static boolean hasChilds(Node nodo)
	{
		if ( (nodo.right == null) && (nodo.left == null) )
			return true;
		else return false;
	}

	static void setCurveSegmentsNodes(Node nodo)
	{

		if ( (nodo.isLeaf() == false) ) {
			//System.out.println();
			//System.out.println(nodo.getStartPoint().getX()+"-->"+nodo.getEndPoint().getX());
			//System.out.println("S-->"+nodo.getSignif());
			//System.out.println("Iscs-->"+nodo.getIscs());
			setCurveSegmentsNodes(nodo.right);
			//System.out.println("Torno da DX");

			setCurveSegmentsNodes(nodo.left);
			//System.out.println("Torno da SX");
			//System.out.println(nodo.right.isLeaf()+"-"+nodo.left.isLeaf()+nodo.right.getIscs()+"-"+nodo.left.getIscs());
			if ( (nodo.right.isLeaf()==true)&&(nodo.left.isLeaf()==true) ) {
				//System.out.println("CONFORNTO IL NODO CON 2 FOGLIE");
				if ((nodo.right.getIscs()==true)&&(nodo.left.getIscs()==true) ) {
					//System.out.println("Prendo i figli");
					nodo.setIscs(false);
					nodo.setUnderTake(true);
				}
				else if ( (nodo.right.getIscs()==true)&&(nodo.left.getIscs()==false) ) {
					//System.out.println("Prendo i figli");
					nodo.left.setIscs(true);
					nodo.setIscs(false);
					nodo.setUnderTake(true);
				}
				else if ( (nodo.right.getIscs()==false)&&(nodo.left.getIscs()==true) ) {
					//System.out.println("Prendo i figli");
					nodo.right.setIscs(true);
					nodo.setIscs(false);
					nodo.setUnderTake(true);
				}
				else if ( (nodo.right.getIscs()==false)&&(nodo.left.getIscs()==false) ) {
					//System.out.println("Prendo il padre");
					if( (nodo.getSignif()>nodo.parent.getSignif())&&(nodo.getUnderTake()==false) ) {
						nodo.setIscs(true);
						//nodo.parent.setUnderTake(true);
					}
					//nodo.setUnderTake(false);
				}
			}
			else if ( (nodo.right.isLeaf()==false)&&(nodo.left.isLeaf()==true) ) {
				//System.out.println("CONFRONTO NODO CON FOGLIA SX e ALBERO DX");
				if (nodo.left.getIscs()==true) {
					nodo.setIscs(false);
					if (nodo.right.getUnderTake() == true)
						nodo.setUnderTake(true);
				}

				else if (nodo.left.getIscs()==false) {
					if ((nodo.right.getIscs() == true)||(nodo.right.getUnderTake() == true)) {
						nodo.left.setIscs(true);
						nodo.setIscs(false);
						nodo.setUnderTake(true);
					}
					else if (nodo.getSignif()>nodo.parent.getSignif()){
						nodo.setIscs(true);
						nodo.parent.setIscs(false);
						nodo.parent.setUnderTake(true);

					}

				}
			}
			else if ( (nodo.right.isLeaf()==true)&&(nodo.left.isLeaf()==false) ) {
				//System.out.println("CONFRONTO NODO CON ALBERO SX e FOGLIA DX");
				if (nodo.right.getIscs()==true) {
					//System.out.println("BINGO1!");
					//	System.out.println(nodo.getStartPoint().getX()+"-->"+nodo.getEndPoint().getX());
					nodo.setIscs(false);
					if (nodo.left.getUnderTake() == true)
						nodo.setUnderTake(true);
				}

				else if (nodo.right.getIscs()==false) {
					if ((nodo.left.getIscs() == true)||(nodo.left.getUnderTake() == true)) {
					//	System.out.println("BINGO2!");
					//	System.out.println(nodo.getStartPoint().getX()+"-->"+nodo.getEndPoint().getX());
						nodo.right.setIscs(true);
						nodo.setIscs(false);
						nodo.setUnderTake(true);
					}
					else if (nodo.getSignif()>nodo.parent.getSignif()){
						nodo.setIscs(true);
						nodo.parent.setIscs(false);
						nodo.parent.setUnderTake(true);

					}

				}
			}
			else if ( (nodo.right.isLeaf()==false)&&(nodo.left.isLeaf()==false) ) {
				//System.out.println("CONFRONTO NODO CON DUE ALBERI");
				//System.out.println("BINGO2!");
				if ( (nodo.right.getUnderTake()==true)||(nodo.left.getUnderTake()==true)||
					 (nodo.right.getIscs()==true)||(nodo.left.getIscs()==true)  ) {
					if ( (nodo.right.getUnderTake()==true)&&(nodo.left.getUnderTake()==false) ) {
						nodo.setIscs(false);
						nodo.setUnderTake(true);
						nodo.left.setIscs(true);
					}
					else if ( (nodo.right.getUnderTake()==false)&&(nodo.left.getUnderTake()==true) ) {
						nodo.setIscs(false);
						nodo.setUnderTake(true);
						nodo.right.setIscs(true);
					}
					else if ( (nodo.right.getUnderTake()==true)&&(nodo.left.getUnderTake()==true) ) {
						nodo.setIscs(false);
						nodo.setUnderTake(true);
					}
					else if ( (nodo.right.getIscs()==true)&&(nodo.left.getIscs()==false)) {
						nodo.setIscs(false);
						nodo.setUnderTake(true);
						nodo.left.setIscs(true);
					}
					else if ( (nodo.left.getIscs()==true)&&(nodo.right.getIscs()==false)) {
						nodo.setIscs(false);
						nodo.setUnderTake(true);
						nodo.right.setIscs(true);
					}
				}
			}

		} else {
			//System.out.println("Sono nella foglia: "+nodo.getStartPoint().getX()+"-->"+nodo.getEndPoint().getX());

			if (nodo.getSignif()>nodo.parent.getSignif()) {
				nodo.setIscs(true);
			}
			//System.out.println("Iscs-->"+nodo.getIscs());
		}
	}

	public void setCSVectorLen(Node nodo)
	{
		countSegments(nodo);
		//System.out.println("LEN "+this.len);
		this.CurveSegmentsVector = new Node [this.len];
	}


	public void setCurveSegmentsVector(Node nodo)
	{
		//System.out.println(i);
		if ((nodo.left!=null)&&(nodo.right!=null)) {

			//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
			//System.out.println("Iscs-->"+nodo.getIscs());
			if (nodo.getIscs() == true) {
				//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
				//System.out.println(this.insert_index);
				this.CurveSegmentsVector[this.insert_index] = nodo;
				this.insert_index = this.insert_index + 1;
			}
			setCurveSegmentsVector(nodo.left);
			setCurveSegmentsVector(nodo.right);
		}
		else if (nodo.getIscs() == true){
			//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
			//System.out.println(this.insert_index);
			this.CurveSegmentsVector[this.insert_index] = nodo;
			this.insert_index = this.insert_index + 1;
			//System.out.println("Iscs-->"+nodo.getIscs());
		}
	}

	public Node [] getCurveSegmentsVector() {
		return this.CurveSegmentsVector;
	}

	public void countSegments(Node nodo)
	{
		if ((nodo.left!=null)&&(nodo.right!=null)) {

			//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
			//System.out.println("Iscs-->"+nodo.getIscs());
			if (nodo.getIscs() == true){
				//System.out.println("---->>"+nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
				this.len = this.len + 1;
			}
			countSegments(nodo.left);
			countSegments(nodo.right);
		}
		else if (nodo.getIscs() == true){
			//System.out.println("----->>"+nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
			this.len = this.len + 1;
			//System.out.println("Iscs-->"+nodo.getIscs());
		}
	}

	public void printSegmentsLog(Node nodo, FileWriter w,FileWriter plotx,FileWriter ploty, MDSPoint [] Y )
	{

		try{
			if ((nodo.left!=null)&&(nodo.right!=null)) {

				//System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
				//System.out.println("Iscs-->"+nodo.getIscs());
				if (nodo.getIscs() == true){
					/*w.write(nseg+"       Interventi : "+getNodeStartIndex(nodo,Y)+"-"+getNodeEndIndex(nodo,Y));
					w.write("\n");
					w.write("        Tempo (dal sec. al sec.) : "+(int)nodo.getStartPoint().getX()+"-"+(int)nodo.getEndPoint().getX());
					w.write("\n");
					w.write("        Pendenza(k) : "+nodo.getK()+"\n");
					w.write("        Concentrazione(sigma) : "+nodo.getSigma()+"\n");
					w.write("-------------------------------------\n");*/
					w.write(getNodeStartIndex(nodo,Y)+"-"+getNodeEndIndex(nodo,Y)+"\n");

					plotx.write(nodo.getStartPoint().getX()+"\n");
					plotx.write(nodo.getEndPoint().getX()+"\n");
					ploty.write(nodo.getStartPoint().getY()+"\n");
					ploty.write(nodo.getEndPoint().getY()+"\n");
					nseg++;
				}
				printSegmentsLog(nodo.left,w,plotx, ploty, Y);
				printSegmentsLog(nodo.right,w,plotx, ploty, Y);
			}
			else if (nodo.getIscs() == true){
				/*w.write(nseg+"       Interventi : "+getNodeStartIndex(nodo,Y)+"-"+getNodeEndIndex(nodo,Y));
				w.write("\n");
				w.write("        Tempo (dal sec. al sec.) : "+(int)nodo.getStartPoint().getX()+"-"+(int)nodo.getEndPoint().getX());
				w.write("\n");
				w.write("        Pendenza(k) : "+nodo.getK()+"\n");
				w.write("        Concentrazione(sigma) : "+nodo.getSigma()+"\n");
				w.write("-------------------------------------\n");*/
				w.write(getNodeStartIndex(nodo,Y)+"-"+getNodeEndIndex(nodo,Y)+"\n");

				plotx.write(nodo.getStartPoint().getX()+"\n");
				plotx.write(nodo.getEndPoint().getX()+"\n");
				ploty.write(nodo.getStartPoint().getY()+"\n");
				ploty.write(nodo.getEndPoint().getY()+"\n");
				nseg++;
			}
		} catch (Exception e){}

	}

	public double getMaxValue()
	{
		double max = CurveSegmentsVector[0].getStartPoint().getY();
		if (CurveSegmentsVector[0].getEndPoint().getY() > max)
			max = CurveSegmentsVector[0].getEndPoint().getY();
		for (int i = 1; i<CurveSegmentsVector.length; i++){
			if (CurveSegmentsVector[i].getStartPoint().getY() > max)
				max = CurveSegmentsVector[i].getStartPoint().getY();
			if (CurveSegmentsVector[i].getEndPoint().getY() > max)
				max = CurveSegmentsVector[i].getEndPoint().getY();
		};

		return max;
	}

	public double getMinValue()
	{
		double min = CurveSegmentsVector[0].getStartPoint().getY();
		if (CurveSegmentsVector[0].getEndPoint().getY() < min)
			min = CurveSegmentsVector[0].getEndPoint().getY();
		for (int i = 1; i<CurveSegmentsVector.length; i++){
			if (CurveSegmentsVector[i].getStartPoint().getY() < min)
				min = CurveSegmentsVector[i].getStartPoint().getY();
			if (CurveSegmentsVector[i].getEndPoint().getY() < min)
				min = CurveSegmentsVector[i].getEndPoint().getY();
		}

		return min;
	}

	public void printTree(Node nodo)
		{
			if ((nodo.left!=null)&&(nodo.right!=null)) {

				System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
				System.out.println(nodo.getSignif());
				printTree(nodo.left);
				printTree(nodo.right);
			}
			else {
				System.out.println(nodo.getStartPoint().getX()+"-"+nodo.getEndPoint().getX());
				System.out.println(nodo.getSignif());
			}

	}


	public int getNodeStartIndex(Node nodo, MDSPoint [] Y)
	{
		int i=0;
		while (nodo.getStartPoint().getX() != Y[i].getX())
			i++;
		return i;
	}

	public int getNodeEndIndex(Node nodo, MDSPoint [] Y)
	{
		int i=0;
		while (nodo.getEndPoint().getX() != Y[i].getX())
			i++;
		return i;
	}


}