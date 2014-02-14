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

public class TDP{

	//private TDPNode [] tdpatternvector;
	private Node [] tdpatternvector;

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

	public TDP(double drifting, int len)
	{
		this.D_drifting = drifting;
		this.tdpatternvector = new Node [len];
		//this.tdpatternvector = new Node [len];
		for (int i = 0; i < this.tdpatternvector.length; i++) {
			this.tdpatternvector[i] = null;
		}
	}

	public void setTDPvector(BTS btseg, FileWriter w)
	{


		boolean foundinterr = false;
		int j = 0;

		try{

			//this.tdpatternvector[j] = (TDPNode)btseg.getElement(0);
			this.tdpatternvector[j] = btseg.getElement(0);


			Node s = btseg.getElement(0);
			if ( (Math.abs(s.getK())) < btseg.getDrifting() ){
				//System.out.println("DOMINATED");
				this.tdpatternvector[0].setType('D');
			}
			else if ( (Math.abs(s.getK())) >= btseg.getDrifting() ){
				//System.out.println("DRIFTING");
				this.tdpatternvector[0].setType('R');
			}
			int i = 1;
			//System.out.println(btseg.getbtsvectorlen());

			while (i<btseg.getbtsvectorlen())
			{
				//System.out.println(i);
				Node seg = btseg.getElement(i);
				Point2D.Double start = seg.getStartPoint();
				Point2D.Double end = seg.getEndPoint();
				//Controllo se ci sono le condizioni per legare questo nodo all-interrupt precedente
				if ( (btseg.getElement(i+1) != null)&&(this.tdpatternvector[j] != null)&&(btseg.getElement(i+2) != null) ) {
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
						//System.out.print(cond1+"--"+cond2+"--"+cond3+"--"+cond4);
						//System.out.println("INTERRUPTED");
						j++;
						//this.tdpatternvector[j] = (TDPNode)btseg.getElement(i);
						this.tdpatternvector[j] = btseg.getElement(i);
						this.tdpatternvector[j].setEndPoint(btseg.getElement(i+1).getEndPoint());
						this.tdpatternvector[j].setK();
						this.tdpatternvector[j].setSigma();
						this.tdpatternvector[j].setType('I');
						i= i + 2;
					}
					//Se non ci sono le condizioni non è interrupted
					else {
						j++;
						//this.tdpatternvector[j] = (TDPNode)btseg.getElement(i);
						this.tdpatternvector[j] = btseg.getElement(i);
						if ( (Math.abs(seg.getK())) < btseg.getDrifting() ){
							//System.out.println("DOMINATED");
							this.tdpatternvector[j].setType('D');
						}
						else if ( (Math.abs(seg.getK())) >= btseg.getDrifting() ){
							//System.out.println("DRIFTING");
							this.tdpatternvector[j].setType('R');
						}
						i++;
					}
				}
				else {
					j++;
					//this.tdpatternvector[j] = (TDPNode)btseg.getElement(i);
					this.tdpatternvector[j] = btseg.getElement(i);
					if ( (Math.abs(seg.getK())) < btseg.getDrifting() ){
						//System.out.println("DOMINATED");
						this.tdpatternvector[j].setType('D');
					}
					else if ( (Math.abs(seg.getK())) >= btseg.getDrifting() ){
						//System.out.println("DRIFTING");
						this.tdpatternvector[j].setType('R');
					}
					i++;
				}


			}
		}
		catch(Exception e){System.err.println(e);}


	}




	public double getTdpDrifting() {
		return this.D_drifting;
	}

	public Node getTdpElement(int index) {
		try {
			return this.tdpatternvector[index];
		}
		catch (Exception e){ return null;}
	}

	public void printTopicDevelopPattern(EntrySignature [] ES, FileWriter w)
	{
		int i = 0;
		try {
			w.write("\n");
			w.write("[RISULTATI TOPIC DEVELOPMENT PATTERN]\n");
			w.write("\n");
			//w.write("Num. Base Topic Seg. = "+this.gettdpatternvectorlen()+"\n");
			//w.write("Delta_drifting = "+this.D_drifting+"\n");
			//w.write("\n");
			w.write("Seg.#      Entrys       Time       Tipo Pattern\n");
			w.write("\n");
			while (this.tdpatternvector[i] != null) {
				//System.out.println("ecco");
				String patterntype = "";
				if (this.tdpatternvector[i].getType() == 'D')
					patterntype = "DOMINATED";
				else if (this.tdpatternvector[i].getType() == 'R')
					patterntype = "DRIFTING";
				else patterntype = "INTERRUPTED";
				int start = findEntryByTime(this.tdpatternvector[i].getStartPoint().getX(), ES);
				int end = findEntryByTime(this.tdpatternvector[i].getEndPoint().getX(), ES);
			    w.write((i+1)+"           "+start+"-"+end+"             "+(int)this.tdpatternvector[i].getStartPoint().getX()+"-->"+(int)this.tdpatternvector[i].getEndPoint().getX()+"          "+patterntype+"\n");
				//w.write((int)this.tdpatternvector[i].getStartPoint().getX()+"-->"+(int)this.tdpatternvector[i].getEndPoint().getX()+"\n");
				//w.write("k = "+this.tdpatternvector[i].getK()+"\n");
				//w.write("sigma = "+this.tdpatternvector[i].getSigma()+"\n");
				//w.write(this.tdpatternvector[i].getType()+"\n");
				//w.write("----------------------------------------\n");
				i++;
			}
		}
		catch (Exception e) {}
	}

	public int gettdpatternvectorlen() {
		int i = 0;
		try{
			while (this.tdpatternvector[i] != null)
				i++;
		} catch (Exception e){}
		return i;

	}

	public int findEntryByTime(double time, EntrySignature [] ES){
		int i = 0;
		while (ES[i].getTime() != time) i++;
		return i;
	}

}