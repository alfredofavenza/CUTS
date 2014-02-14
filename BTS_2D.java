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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.Math;
import java.io.*;

public class BTS_2D{

	private BTSNode_2D [] btsvector;

	private double D_drifting;
	private double D_drifting_y;
	private double D_drifting_z;

	/*private double calcAngolo(Node_2D seg1,Node_2D seg2)
	  {
			double a0 = seg1.findA();
			double b0 = seg1.findB();
			double c0 = seg1.findC();
			double a1 = seg2.findA();
			double b1 = seg2.findB();
			double c1 = seg2.findC();

			double numeratore = ((a0*a1)+(b0*b1)+(c0*c1));
			double denominatore = ( Math.sqrt(Math.pow(a0,2)+Math.pow(b0,2)+Math.pow(c0,2)) * Math.sqrt(Math.pow(a1,2)+Math.pow(b1,2)+Math.pow(c1,2)) );
			seg2.setAngle(Math.acos(
				numeratore/denominatore))
			return Math.acos(numeratore/denominatore);

	  }*/


	private boolean curveDevelopment(Node_2D n1,Node_2D n2, FileWriter w)
	{;

		//differenza Y(tk+1)-Y(tk), cioè la differenza tra i valori assunti alla fine
		//e all'inizio del segmento k
		double slope1_y = n1.getKy();
		double slope2_y = n2.getKy();
		double slope1_z = n1.getKz();
		double slope2_z = n2.getKz();


		//CALCOLO LA FORMULA

		double diff_y = (slope1_y - slope2_y);
		double diff_z = (slope1_z - slope2_z);

		double sigma1 = n2.getSigma();
		double sigma2 = n1.getSigma();
		double sigmasub = sigma1 - sigma2;
		double sigmasum = sigma1 + sigma2;

		if (  ( (Math.pow(diff_y,2)+Math.pow(diff_z,2)) < (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) ) &&
			  ( Math.abs(sigmasub) < (sigmasum/2) )  )
		   return true;
		else
		   return false;

		/*n2.setAngle(n1);
		double angolo = n2.getAngle();
		double sigma1 = n2.getSigma();
		double sigma2 = n1.getSigma();
		//double ksub = k1 - k2;
		double sigmasub = sigma1 - sigma2;
		double sigmasum = sigma1 + sigma2;
		try{
			//w.write("    ##########################\n");
			//w.write("    |k1 - k2| ="+Math.abs(ksub)+"\n");
			//w.write("    drifting ="+this.D_drifting+"\n");
			//w.write("    |sigma1 - sigma2| ="+Math.abs(sigmasub)+"\n");
			//w.write("    (sigma1 + sigma2)/2 ="+(sigmasum/2)+"\n");
			//w.write("    ##########################\n");

		}
		catch (Exception e){}
		if (  ( Math.abs(angolo) < this.D_drifting ) &&
			  ( Math.abs(sigmasub) < (sigmasum/2) )  )
			return true;
		else
			return false;*/
	}

	//public BTS_2D(double drifting, int len)
	public BTS_2D(double driftingy, double driftingz, int len)
	{
		//this.D_drifting = drifting;
		this.D_drifting_y = driftingy;
		this.D_drifting_z = driftingz;
		this.btsvector = new BTSNode_2D [len];
		for (int i = 0; i < this.btsvector.length; i++) {
			this.btsvector[i] = null;
		}
	}

	public void setBTSvector(Node_2D [] csvector, FileWriter w) throws Throwable
	{
		//System.out.println("COMBINO");
		boolean combined = false;
		int i=0;
		//this.btsvector[i].setStartPoint(csvector[0].getStartPoint());
		//this.btsvector[i].setEndPoint(csvector[0].getEndPoint());
		//this.btsvector[i].setNodo(csvector[0]);
		this.btsvector[i] = new BTSNode_2D();
		this.btsvector[i].setNodo(csvector[0]);
		//w.write("Considero i segmenti "+csvector[0].getStartPoint().getX()+"-"+csvector[0].getEndPoint().getX()+" e "+csvector[1].getStartPoint().getX()+"-"+csvector[1].getEndPoint().getX()+"\n");
		for (int j=1;j<csvector.length;j++)
		{
			if (combined == true){
				//System.out.println("trovo una combinazione");
				w.write("Considero i segmenti "+(int)btsvector[i].getNodo().getStartPoint().getX()+"-"+(int)btsvector[i].getNodo().getEndPoint().getX()+" e "+(int)csvector[j].getStartPoint().getX()+"-"+(int)csvector[j].getEndPoint().getX()+"\n");
				//System.out.println(btsvector[i].getStartPoint().getX()+","+csvector[j].getEndPoint());
				if (curveDevelopment(btsvector[i].getNodo(),csvector[j],w) == true){
					//System.out.println("Aggiungo ad una combinazione esistente");
					w.write("	Aggiungo il segmento alla combinazione precedente\n");
					//w.write("--> Curve Development= "+curveDevelopment(csvector[i],csvector[j],w)+"\n");
					this.btsvector[i].getNodo().setEndPoint(csvector[j].getEndPoint());
					this.btsvector[i].getNodo().setAngle(this.btsvector[i-1].getNodo());
					//this.btsvector[i].getNodo().setK();
					this.btsvector[i].getNodo().setSigma();
					combined = true;
				}
				else {
					//System.out.println("Chiudo una combinazione");
					w.write("	Chiudo la combinazione precedente e tengo da parte il segmento\n");
					//w.write("--> Curve Development= "+curveDevelopment(csvector[i],csvector[j],w)+"\n");
					i = i+1;
					//this.btsvector[i].setStartPoint(csvector[j].getStartPoint());
					//this.btsvector[i].setEndPoint(csvector[j].getEndPoint());
					this.btsvector[i] = new BTSNode_2D();
					this.btsvector[i].setNodo(csvector[j]);
					combined = false;
				}

			}
			else if (curveDevelopment(csvector[j-1],csvector[j],w) == true){
				int h = j-1;
				w.write("Considero i segmenti "+(int)csvector[j-1].getStartPoint().getX()+"-"+(int)csvector[j-1].getEndPoint().getX()+" e "+(int)csvector[j].getStartPoint().getX()+"-"+(int)csvector[j].getEndPoint().getX()+"\n");
				w.write("	Posso combinare i due segmenti\n");
				//w.write("--> Curve Development= "+curveDevelopment(csvector[j-1],csvector[j],w)+"\n");
				//System.out.println("i nodi: "+j+" e "+j+1+" sono combinati");
				//System.out.println(csvector[j-1].getStartPoint()+","+csvector[j].getEndPoint());
				//System.out.println(i);
				//System.out.println(csvector[j-1].getStartPoint()+","+csvector[j-1].getEndPoint());
				//this.btsvector[i] = new Node(csvector[j-1].getStartPoint().getX(),csvector[j-1].getStartPoint().getY(),csvector[j].getEndPoint().getX(),csvector[j].getEndPoint().getY(), null,null);
				this.btsvector[i] = new BTSNode_2D();
				this.btsvector[i].setNodo(csvector[j-1]);
				//System.out.println(this.btsvector[i].getStartPoint()+","+this.btsvector[i].getEndPoint());
				//this.btsvector[i].setStartPoint(csvector[j-1].getStartPoint());
				this.btsvector[i].getNodo().setEndPoint(csvector[j].getEndPoint());
				//System.out.println(this.btsvector[i].getStartPoint()+","+this.btsvector[i].getEndPoint());
				//this.btsvector[i].getNodo().setK();
				this.btsvector[i].getNodo().setAngle(this.btsvector[i-1].getNodo());
				this.btsvector[i].getNodo().setSigma();
				combined = true;
			}
			else {
				int h = j-1;
				w.write("Considero i segmenti "+(int)csvector[j-1].getStartPoint().getX()+"-"+(int)csvector[j-1].getEndPoint().getX()+" e "+(int)csvector[j].getStartPoint().getX()+"-"+(int)csvector[j].getEndPoint().getX()+"\n");
				w.write("	I due segmenti non possono essere combinati\n");
				//w.write("--> Curve Development= "+curveDevelopment(csvector[j-1],csvector[j],w)+"\n");
				//System.out.println(curveDevelopment(csvector[j-1],csvector[j]));
				i = i+1;
				//this.btsvector[i].setStartPoint(csvector[j].getStartPoint());
				//this.btsvector[i].setEndPoint(csvector[j].getEndPoint());
				this.btsvector[i] = new BTSNode_2D();
				this.btsvector[i].setNodo(csvector[j]);
				combined = false;
			}
		}

	}

	public double getDrifting() {
		return this.D_drifting;
	}

	public double getDriftingY() {
		return this.D_drifting_y;
	}

	public double getDriftingZ() {
		return this.D_drifting_z;
	}

	public Node_2D getElement(int index) {
		try {
			return this.btsvector[index].getNodo();
		}
		catch (Exception e){ return null;}
	}

	public void printBaseTopicSegments(FileWriter w, FileWriter plotx, FileWriter ploty, FileWriter plotz, MDSPoint_2D [] Y)
	{
		int i = 0;
		try {
			w.write("\n");
			w.write("[RISULTATI BASE TOPIC SEGMENTS]\n");
			w.write("\n");
			w.write("Num. Base Topic Seg. = "+this.getbtsvectorlen()+"\n");
			w.write("Delta_drifting = "+this.D_drifting+"\n");
			//w.write("Delta_drifting_Y = "+this.D_drifting_y+"\n");
			//w.write("Delta_drifting_Z = "+this.D_drifting_z+"\n");
			w.write("\n");
			while (this.btsvector[i] != null) {
				w.write((i+1)+"       Interventi : "+getNodeStartIndex(this.btsvector[i].getNodo(), Y)+"-"+getNodeEndIndex(this.btsvector[i].getNodo(), Y)+"\n");
				w.write("          Tempo (dal sec. al sec.) : "+(int)this.btsvector[i].getNodo().getStartPoint().getX()+"-->"+(int)this.btsvector[i].getNodo().getEndPoint().getX()+"\n");
				//w.write("Pendenza()k : "+this.btsvector[i].getNodo().getK()+"\n");
				w.write("          Concentrazione(sigma) : "+this.btsvector[i].getNodo().getSigma()+"\n");
				w.write("---------------------------------------\n");
				plotx.write(this.btsvector[i].getNodo().getStartPoint().getX()+"\n");
				plotx.write(this.btsvector[i].getNodo().getEndPoint().getX()+"\n");
				ploty.write(this.btsvector[i].getNodo().getStartPoint().getY()+"\n");
				ploty.write(this.btsvector[i].getNodo().getEndPoint().getY()+"\n");
				plotz.write(this.btsvector[i].getNodo().getStartPoint().getZ()+"\n");
				plotz.write(this.btsvector[i].getNodo().getEndPoint().getZ()+"\n");
				i++;
			}
		}
		catch (Exception e) {}
	}

	public int getbtsvectorlen() {

			int i = 0;
			try{
				while (this.btsvector[i] != null){
					//System.out.println(i);
					i++;
				}
			}
			catch(Exception e){}
			return i;

	}

	public int getNodeStartIndex(Node_2D nodo, MDSPoint_2D [] Y)
	{
		int i=0;
		while (nodo.getStartPoint().getX() != Y[i].getX())
			i++;
		return i;
	}

	public int getNodeEndIndex(Node_2D nodo, MDSPoint_2D [] Y)
	{
		int i=0;
		while (nodo.getEndPoint().getX() != Y[i].getX())
			i++;
		return i;
	}


}