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

public class Node_2D {

	  private Point3D startPoint;
	  private Point3D endPoint;
	  private Point3D breakPoint;
	  private int start;
	  private int end;
	  private int bpIndex;
	  private MDSPoint_2D [] mdsvector;
	  private double significativity;
	  private double k;
	  private double ky;
	  private double kz;
	  private double angle;
	  private double sigma;
	  private boolean iscs;
	  private boolean undertake;
	  private boolean visited;
	  public Node_2D left;
  	  public Node_2D right;
	  public Node_2D parent;

	  private char p_type;

	  public Node_2D (){}

	  public Node_2D (int s, int e, MDSPoint_2D [] Y, Node_2D p)
	  {

		this.start = s;
		this.end = e;
		int sx = Y[s].getX();
		double sy = Y[s].getY();
		double sz = Y[s].getZ();
		int ex = Y[e].getX();
		double ey = Y[e].getY();
		double ez = Y[e].getZ();
		this.startPoint = new Point3D(sx, sy, sz);
		this.endPoint = new Point3D(ex, ey, ez);
		this.mdsvector = Y;
		this.setBreakPoint();
		this.setSignif();
		this.setK();
		this.setKy();
		this.setKz();
		this.angle = 0;
		this.setSigma();
		this.setIscs(false);
		this.setUnderTake(false);
		this.setVisited(false);
		this.left = null;
		this.right = null;
		this.parent = p;
      }



      public void setBreakPoint()
      {
	  	// Calcolo la retta che passa per i punti s ed e
	  	//System.out.println(startPoint+"-"+endPoint);
	  	//Line2D.Double line = new Line2D.Double(this.startPoint,this.endPoint);

	  	// Calcolo la distanza tra tutti i punti compresi tra s ed e
	  	double maxdist = 0;
	  	int maxindex = 1;

	  	Point3D p;

		//for (int i=(int)startPoint.getX(); i<=(int)endPoint.getX(); i++)
		for (int i=this.start; i<=this.end; i++)
		{
			p = new Point3D(mdsvector[i].getX(), mdsvector[i].getY(), mdsvector[i].getZ());
			//System.out.println("punto confrontato con la linea: "+p);
			//System.out.println("distanza: "+line.ptLineDist(p) );

			//Calcolo la distanza tra il punto p e la retta nello spazio
			/*double base = Math.sqrt( Math.pow((endPoint.getX()-startPoint.getX()),2)+Math.pow((endPoint.getY()-startPoint.getY()),2)+Math.pow((endPoint.getZ()-startPoint.getZ()),2) );
			double lato = Math.sqrt( Math.pow((p.getX()-startPoint.getX()),2)+Math.pow((p.getY()-startPoint.getY()),2)+Math.pow((p.getZ()-startPoint.getZ()),2) );
			double ipotenusa = Math.sqrt( Math.pow((p.getX()-endPoint.getX()),2)+Math.pow((p.getY()-endPoint.getY()),2)+Math.pow((p.getZ()-endPoint.getZ()),2) );
			double semiP = (base + lato + ipotenusa)/2;
			double AreaT = Math.sqrt(semiP*(semiP-base)*(semiP-lato)*(semiP-ipotenusa));
			double distPuntoRetta = (2*AreaT)/base;*/

			double distPuntoRetta = dist_Punto_Retta(p, startPoint, endPoint);

			if (distPuntoRetta >= maxdist )
			{
				maxdist = distPuntoRetta;
				this.breakPoint = p;
				this.bpIndex = i;
			}
		}
			 //System.out.println("distanza massima: "+maxdist );
		     //System.out.println("BREAKPOINT: "+breakPoint.getX() );
			//System.out.println("DISTANZA MASSIMA: "+maxdist);
		//System.out.println("Breakpoint= "+p.getX()+","+p.getY());
		//System.out.println("BREAKPOINT: "+breakPoint);
	  }


	  public int getBreakPoint()
	  {
		return this.bpIndex;
	  }

	  public boolean isLeaf()
	  {
		return ( (this.left == null)&&(this.right == null) );
	  }

	  public void setIscs(boolean flag)
	  {
		this.iscs = flag;
	  }

	  public boolean getIscs()
	  {
		return this.iscs;
  	  }

	  public void setVisited(boolean flag)
	  {
		this.visited = flag;
	  }

	  public boolean getVisited()
	  {
		return this.visited;
	  }

  	  public void setUnderTake(boolean flag)
	  {
		this.undertake = flag;
	  }

	  public boolean getUnderTake()
	  {
		return this.undertake;
	  }

	  public void setSignif()
	  {
			//Calcolo la lunghezza della retta linelen
			//Calcolo la distanza di tutti i punti dalla retta distpointline
			//Calcolo la media delle distanze di tutti i punti dalla retta media
			//Calcolo il rapporto linelength/media
			//Line2D.Double line = new Line2D.Double(this.startPoint,this.endPoint);
			//double linelen = Math.sqrt( Math.pow((endPoint.getX()-startPoint.getX()),2)+Math.pow((endPoint.getY()-startPoint.getY()),2)+Math.pow((endPoint.getZ()-startPoint.getZ()),2) );

			double linelen =  lunghezza_Retta(startPoint, endPoint);
			//int s = (int)this.startPoint.getX();
			//int e = (int)this.endPoint.getX();
			//double sum = 0;
			double sum_distPuntoRetta = 0;
			int i;
			//for (i=s; i<=e; i++){
			for (i=this.start; i<=this.end; i++){
				Point3D p = new Point3D(mdsvector[i].getX(), mdsvector[i].getY(), mdsvector[i].getZ());
				sum_distPuntoRetta = sum_distPuntoRetta + dist_Punto_Retta(p, startPoint, endPoint);
				//sum = sum + line.ptLineDist(p);
			}

			this.significativity = sum_distPuntoRetta/i;

	  }

	  public double getSignif()
	  {
		 return this.significativity;
	  }


	  public double findA()
	  {
	  		return this.endPoint.getX()-this.startPoint.getX();
	  }
	  public double findB()
	  {
	  	  	return this.endPoint.getY()-this.startPoint.getY();
	  }
	  public double findC()
	  {
	  	  	return this.endPoint.getZ()-this.startPoint.getZ();
	  }

	  public void setAngle(Node_2D segprec)
	  {
			double a0 = segprec.findA();
			double b0 = segprec.findB();
			double c0 = segprec.findC();
			double a1 = this.findA();
			double b1 = this.findB();
			double c1 = this.findC();

			double numeratore = ((a0*a1)+(b0*b1)+(c0*c1));
			double denominatore = ( Math.sqrt(Math.pow(a0,2)+Math.pow(b0,2)+Math.pow(c0,2)) * Math.sqrt(Math.pow(a1,2)+Math.pow(b1,2)+Math.pow(c1,2)) );
			this.angle = Math.acos(numeratore/denominatore);

	  }

	  public double getAngle()
	  {
	  	    return this.angle;
	  }

	  public void setK()
	  {
			//this.k = ((this.endPoint.getY())-(this.startPoint.getY()))/((this.endPoint.getX())-(this.startPoint.getX()));
			this.k = ((this.endPoint.getZ())-(this.startPoint.getZ()))/((this.endPoint.getY())-(this.startPoint.getY()));

	  }

      public void setKy()
	  {
		//this.ky = ((this.endPoint.getY())-(this.startPoint.getY()))/((this.endPoint.getX())-(this.startPoint.getX()));
		//this.k = ((this.endPoint.getZ())-(this.startPoint.getZ()))/((this.endPoint.getY())-(this.startPoint.getY()));

		double start = this.getStartPoint().getY();
		double end = this.getEndPoint().getY();
		this.ky = (end - start)/(this.getEndPoint().getX() - this.getStartPoint().getX());;


	  }
	  public void setKz()
	  {
		//this.kz = ((this.endPoint.getZ())-(this.startPoint.getZ()))/((this.endPoint.getX())-(this.startPoint.getX()));
		//this.k = ((this.endPoint.getZ())-(this.startPoint.getZ()))/((this.endPoint.getY())-(this.startPoint.getY()));
		double start = this.getStartPoint().getZ();
		double end = this.getEndPoint().getZ();
		this.kz = (end - start)/(this.getEndPoint().getX() - this.getStartPoint().getX());
	  }


	  public double getK()
	  {
		 return k;
	  }

	  public double getKy()
	  {
		 return ky;
	  }

	  public double getKz()
	  {
		 return kz;
	  }



	  public void setSigma()
	  {
			//Line2D.Double line = new Line2D.Double(this.startPoint,this.endPoint);
			int s =  (int)this.startPoint.getX();
			int e =  (int)this.endPoint.getX();
			int i;
			double sum_distPuntoRetta = 0;
			//for (i=s; i<=e; i++) {
			for (i=this.start; i<=this.end; i++) {
				Point3D p = new Point3D(mdsvector[i].getX(), mdsvector[i].getY(), mdsvector[i].getZ());
				sum_distPuntoRetta = sum_distPuntoRetta + dist_Punto_Retta(p, startPoint, endPoint);
			}
			int div = e - s + 1;
			this.sigma = sum_distPuntoRetta/div;

	  }

	  public double getSigma()
	  {
			return this.sigma;
	  }

	  public Point3D getStartPoint()
	  {
	  	return this.startPoint;
	  }

	  public void setStartPoint(Point3D start)
	  {
	  	  this.startPoint = start;
	  }

	  public Point3D getEndPoint()
	  {
		return this.endPoint;
	  }

	  public void setEndPoint(Point3D end)
	  {
		  this.endPoint = end;
	  }

	  public void setType(char t){
		  this.p_type = t;
	  }

      public char getType(){
		  return this.p_type;
	  }

	  static double lunghezza_Retta(Point3D start, Point3D end)
	  {
		  double l_retta = Math.sqrt( Math.pow((end.getX()-start.getX()),2)+Math.pow((end.getY()-start.getY()),2)+Math.pow((end.getZ()-start.getZ()),2) );
		  return l_retta;
	  }

	  static double dist_Punto_Retta(Point3D p, Point3D start, Point3D end)
	  {
		  //double base = Math.sqrt( Math.pow((end.getX()-start.getX()),2)+Math.pow((end.getY()-start.getY()),2)+Math.pow((end.getZ()-start.getZ()),2) );
		  //double lato = Math.sqrt( Math.pow((p.getX()-start.getX()),2)+Math.pow((p.getY()-start.getY()),2)+Math.pow((p.getZ()-start.getZ()),2) );
		  //double ipotenusa = Math.sqrt( Math.pow((p.getX()-end.getX()),2)+Math.pow((p.getY()-end.getY()),2)+Math.pow((p.getZ()-end.getZ()),2) );
		  double base = lunghezza_Retta(start, end);
		  double lato = lunghezza_Retta(start, p);
		  double ipotenusa = lunghezza_Retta(end, p);
		  double semiP = (base + lato + ipotenusa)/2;
		  double AreaT = Math.sqrt(semiP*(semiP-base)*(semiP-lato)*(semiP-ipotenusa));
		  double distPR = (2*AreaT)/base;

		  return distPR;
	  }





};

