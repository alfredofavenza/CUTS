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

public class TDP_2D{

	//private TDPNode [] tdpatternvector;
	private Node_2D [] tdpatternvector;

	private double D_drifting;
	private double D_drifting_y;
	private double D_drifting_z;


	public TDP_2D(double driftingy, double driftingz, int len)
	{
		//this.D_drifting = drifting;
		this.D_drifting_y = driftingy;
		this.D_drifting_z = driftingz;
		this.tdpatternvector = new Node_2D [len];
		//this.tdpatternvector = new Node [len];
		for (int i = 0; i < this.tdpatternvector.length; i++) {
			this.tdpatternvector[i] = null;
		}
	}


	public void setTDPvector(BTS_2D btseg, FileWriter w)
	{


		boolean foundinterr = false;
		int j = 0;

		try{

			//this.tdpatternvector[j] = (TDPNode)btseg.getElement(0);
			this.tdpatternvector[j] = btseg.getElement(0);


			Node_2D s = btseg.getElement(0);
			//if ( (Math.abs(s.getK())) < btseg.getDrifting() ){
			//System.out.println("KY: "+Math.pow(s.getKy(),2));
			//System.out.println("KZ: "+Math.pow(s.getKz(),2));
			if ( ( Math.pow(s.getKy(),2)+Math.pow(s.getKz(),2) ) < Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2) ){

				//System.out.println("DOMINATED");
				this.tdpatternvector[0].setType('D');
			}

			//else if ( (Math.abs(s.getK())) >= btseg.getDrifting() ){
			else if ( ( Math.pow(s.getKy(),2)+Math.pow(s.getKz(),2) ) >= Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2) ){
				//System.out.println("DRIFTING");
				this.tdpatternvector[0].setType('R');
			}
			int i = 1;
			//System.out.println(btseg.getbtsvectorlen());

			while (i<btseg.getbtsvectorlen())
			{
				//System.out.println(i);
				Node_2D seg = btseg.getElement(i);
				Point3D start = seg.getStartPoint();
				Point3D end = seg.getEndPoint();
				//Controllo se ci sono le condizioni per legare questo nodo all-interrupt precedente
				if ( (btseg.getElement(i+1) != null)&&(this.tdpatternvector[j] != null)&&(btseg.getElement(i+2) != null) ) {
					Node_2D seg2 = btseg.getElement(i+1);
					Point3D start2 = seg2.getStartPoint();
					Point3D end2 = seg2.getEndPoint();
					//double k_h = Math.abs( (seg.getStartPoint().getY())-(seg2.getEndPoint().getY()) )/Math.abs( (seg.getStartPoint().getX())-(seg2.getEndPoint().getX()));
					double slope_khy = (end2.getY() - start.getY())/(seg2.getEndPoint().getX() - seg.getStartPoint().getX());
					double slope_khz = (end2.getZ() - start.getZ())/(seg2.getEndPoint().getX() - seg.getStartPoint().getX());

					//double k_h = Math.abs( (seg.getStartPoint().getY())-(seg2.getEndPoint().getY()) )/Math.abs( (seg.getStartPoint().getX())-(seg2.getEndPoint().getX()));

					//boolean cond1 = ( Math.abs(seg.getK()) ) >= ((btseg.getDrifting())) ;
					//boolean cond1 = ( Math.abs(seg.getKy())+Math.abs(seg.getKz()) ) >= (this.D_drifting_y+this.D_drifting_z) ;
					boolean cond1 = ( ( Math.pow(seg.getKy(),2)+Math.pow(seg.getKz(),2) )  >= (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) );
					//boolean cond2 = Math.abs(seg2.getK()) >= (btseg.getDrifting());
					//boolean cond2 = ( Math.abs(seg2.getKy())+Math.abs(seg2.getKz()) ) >= (this.D_drifting_y+this.D_drifting_z);
					boolean cond2 = ( ( Math.pow(seg2.getKy(),2)+Math.pow(seg2.getKz(),2) )  >= (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) );
					//boolean cond3 = ( seg.getK()*seg2.getK() ) < 0;
					//boolean cond3 = ( seg.getK()*seg2.getK() ) < 0;
					//boolean cond3 = ( (seg.getKy()+seg.getKy()) * (seg2.getKy()+seg2.getKz()) ) < 0;

					boolean cond3 = ( (seg.getStartPoint().getY() * seg2.getStartPoint().getY()) + (seg.getStartPoint().getZ()*seg2.getStartPoint().getZ()) ) < 0;
					//boolean cond4 = ( Math.abs(btseg.getElement(i-1).getK() - (k_h)) + Math.abs(btseg.getElement(i+2).getK() - (k_h)) ) < btseg.getDrifting();
					boolean cond4 = ( ( Math.pow(btseg.getElement(i-1).getKy()-(slope_khy),2) +
										Math.pow(btseg.getElement(i-1).getKz()-(slope_khz),2) ) +
									  ( Math.pow(btseg.getElement(i+2).getKy()-(slope_khy),2) +
										Math.pow(btseg.getElement(i+2).getKz()-(slope_khz),2) ) ) < ( Math.pow(this.D_drifting_y,2) + Math.pow(this.D_drifting_z,2));

					//System.out.print(cond1+"--"+cond2+"--"+cond3+"--"+cond4);
					if   ( cond1 && cond2 && cond3 && cond4)
					{
						//System.out.print(cond1+"--"+cond2+"--"+cond3+"--"+cond4);
						//System.out.println("INTERRUPTED");
						j++;
						//this.tdpatternvector[j] = (TDPNode)btseg.getElement(i);
						this.tdpatternvector[j] = btseg.getElement(i);
						this.tdpatternvector[j].setEndPoint(btseg.getElement(i+1).getEndPoint());
						//this.tdpatternvector[j].setK();
						this.tdpatternvector[j].setKy();
						this.tdpatternvector[j].setKz();
						this.tdpatternvector[j].setSigma();
						this.tdpatternvector[j].setType('I');
						i= i + 2;
					}
					//Se non ci sono le condizioni non è interrupted
					else {
						j++;

						//System.out.println("SINISTRA= "+( Math.pow(seg.getKy(),2)+Math.pow(seg.getKz(),2) ));
						//System.out.println("DESTRA= "+(Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)));
						//this.tdpatternvector[j] = (TDPNode)btseg.getElement(i);
						this.tdpatternvector[j] = btseg.getElement(i);
						//if ( (Math.abs(seg.getK())) < btseg.getDrifting() ){
						if ( ( Math.pow(seg.getKy(),2)+Math.pow(seg.getKz(),2) )  < (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) ) {
							//System.out.println("DOMINATED");
							this.tdpatternvector[j].setType('D');
						}
						//else if ( (Math.abs(seg.getK())) >= btseg.getDrifting() ){
						else if ( ( Math.pow(seg.getKy(),2)+Math.pow(seg.getKz(),2) )  >= (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) ) {
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
					//if ( (Math.abs(seg.getK())) < btseg.getDrifting() ){
					if ( ( Math.pow(seg.getKy(),2)+Math.pow(seg.getKz(),2) )  < (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) ) {
						//System.out.println("DOMINATED");
						this.tdpatternvector[j].setType('D');
					}
					//else if ( (Math.abs(seg.getK())) >= btseg.getDrifting() ){
					else if ( ( Math.pow(seg.getKy(),2)+Math.pow(seg.getKz(),2) )  >= (Math.pow(this.D_drifting_y,2)+Math.pow(this.D_drifting_z,2)) ) {
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

	public Node_2D getTdpElement(int index) {
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

			w.write("Seg.#      Entries       Time(sec.)          Tipo Pattern\n");
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