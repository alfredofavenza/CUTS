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

public class BTS{

	private Node [] btsvector;

	private double D_drifting;

	private boolean curveDevelopment(Node n1,Node n2, FileWriter w)
	{

		double k1 = n1.getK();
		double k2 = n2.getK();
		double sigma1 = n2.getSigma();
		double sigma2 = n1.getSigma();
		double ksub = k1 - k2;
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
		if (  ( Math.abs(ksub) < this.D_drifting ) &&
			  ( Math.abs(sigmasub) < (sigmasum/2) )  )
			return true;
		else
			return false;
	}

	public BTS(double drifting,int len)
	{
		this.D_drifting = drifting;
		this.btsvector = new Node [len];
		for (int i = 0; i < this.btsvector.length; i++) {
			this.btsvector[i] = null;
		}
	}

	public void setBTSvector(Node [] csvector, FileWriter w) throws Throwable
	{
		//System.out.println("COMBINO");
		boolean combined = false;
		int i=0;
		//this.btsvector[i].setStartPoint(csvector[0].getStartPoint());
		//this.btsvector[i].setEndPoint(csvector[0].getEndPoint());
		this.btsvector[i] = csvector[0];
		//w.write("Considero i segmenti "+csvector[0].getStartPoint().getX()+"-"+csvector[0].getEndPoint().getX()+" e "+csvector[1].getStartPoint().getX()+"-"+csvector[1].getEndPoint().getX()+"\n");
		for (int j=1;j<csvector.length;j++)
		{
			if (combined == true){
				//System.out.println("trovo una combinazione");
				w.write("Considero i segmenti "+(int)btsvector[i].getStartPoint().getX()+"-"+(int)btsvector[i].getEndPoint().getX()+" e "+(int)csvector[j].getStartPoint().getX()+"-"+(int)csvector[j].getEndPoint().getX()+"\n");
				//System.out.println(btsvector[i].getStartPoint().getX()+","+csvector[j].getEndPoint());
				if (curveDevelopment(btsvector[i],csvector[j],w) == true){
					//System.out.println("Aggiungo ad una combinazione esistente");
					w.write("	Aggiungo il segmento alla combinazione precedente\n");
					//w.write("--> Curve Development= "+curveDevelopment(csvector[i],csvector[j],w)+"\n");
					this.btsvector[i].setEndPoint(csvector[j].getEndPoint());
					this.btsvector[i].setK();
					this.btsvector[i].setSigma();
					combined = true;
				}
				else {
					//System.out.println("Chiudo una combinazione");
					w.write("	Chiudo la combinazione precedente e tengo da parte il segmento\n");
					//w.write("--> Curve Development= "+curveDevelopment(csvector[i],csvector[j],w)+"\n");
					i = i+1;
					//this.btsvector[i].setStartPoint(csvector[j].getStartPoint());
					//this.btsvector[i].setEndPoint(csvector[j].getEndPoint());
					this.btsvector[i] = csvector[j];
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
				this.btsvector[i] = csvector[j-1];
				//System.out.println(this.btsvector[i].getStartPoint()+","+this.btsvector[i].getEndPoint());
				//this.btsvector[i].setStartPoint(csvector[j-1].getStartPoint());
				this.btsvector[i].setEndPoint(csvector[j].getEndPoint());
				//System.out.println(this.btsvector[i].getStartPoint()+","+this.btsvector[i].getEndPoint());
				this.btsvector[i].setK();
				this.btsvector[i].setSigma();
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
				this.btsvector[i] = csvector[j];
				combined = false;
			}
		}

	}

	public double getDrifting() {
		return this.D_drifting;
	}

	public Node getElement(int index) {
		try {
			return this.btsvector[index];
		}
		catch (Exception e){ return null;}
	}

	public void printBaseTopicSegments(FileWriter w, FileWriter plotx, FileWriter ploty )
	{
		int i = 0;
		try {
			w.write("\n");
			w.write("[RISULTATI BASE TOPIC SEGMENTS]\n");
			w.write("\n");
			w.write("Num. Base Topic Seg. = "+this.getbtsvectorlen()+"\n");
			w.write("Delta_drifting = "+this.D_drifting+"\n");
			w.write("\n");
			while (this.btsvector[i] != null) {
				w.write((int)this.btsvector[i].getStartPoint().getX()+"-->"+(int)this.btsvector[i].getEndPoint().getX()+"\n");
				w.write("k = "+this.btsvector[i].getK()+"\n");
				w.write("sigma = "+this.btsvector[i].getSigma()+"\n");
				w.write("-----------------------------\n");
				plotx.write(this.btsvector[i].getStartPoint().getX()+"\n");
				plotx.write(this.btsvector[i].getEndPoint().getX()+"\n");
				ploty.write(this.btsvector[i].getStartPoint().getY()+"\n");
				ploty.write(this.btsvector[i].getEndPoint().getY()+"\n");
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

}