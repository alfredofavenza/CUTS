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

public class Node {
	  private Point2D.Double startPoint;
	  private Point2D.Double endPoint;
	  private int start;
	  private int end;
	  private Point2D.Double breakPoint;
	  private int bpIndex;
	  private MDSPoint [] mdsvector;
	  private double significativity;
	  private double k;
	  private double sigma;
	  private boolean iscs;
	  private boolean undertake;
	  private boolean visited;
	  public Node left;
  	  public Node right;
	  public Node parent;

	  private char p_type;

	  public Node (){}

	  public Node (int s, int e, MDSPoint [] Y, Node p)
	  {
		this.start = s;
		this.end = e;
		int sx = Y[s].getX();
		double sy = Y[s].getY();
		int ex = Y[e].getX();
		double ey = Y[e].getY();
		this.startPoint = new Point2D.Double(sx,sy);
		this.endPoint = new Point2D.Double(ex,ey);
		this.mdsvector = Y;
		this.setBreakPoint();
		this.setSignif();
		this.setK();
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
	  	//System.out.println(start+"-"+end);
	  	Line2D.Double line = new Line2D.Double(this.startPoint,this.endPoint);

	  	// Calcolo la distanza tra tutti i punti compresi tra s ed e
	  	double maxdist = 0;
	  	int maxindex = 1;

	  	Point2D.Double p = new Point2D.Double();
	  	//Per tutti i punti compresi tra s ed e
	  		//for (int i=(int)startPoint.getX(); i<=(int)endPoint.getX(); i++)
	  		//for (int i=start; i<=end; i++)
	  		//for (int i=1; i<4; i++)
	  		for (int i=this.start; i<=this.end; i++)
	  		{
				//Point2D.Double p = new Point2D.Double(i,mds.getValue(i));
				//p = new Point2D.Double(i,mdsvector[i]);
				p = new Point2D.Double(mdsvector[i].getX(),mdsvector[i].getY());
				//System.out.println("punto confrontato con la linea: "+p);
				//System.out.println("distanza: "+line.ptLineDist(p) );
				if (line.ptLineDist(p) >= maxdist )
				{
					maxdist = line.ptLineDist(p);
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

	  //public void setSignif(int start, int end)
	  public void setSignif()
	  {
			//Calcolo la lunghezza della retta linelen
			//Calcolo la distanza di tutti i punti dalla retta distpointline
			//Calcolo la media delle distanze di tutti i punti dalla retta media
			//Calcolo il rapporto linelength/media
			Line2D.Double line = new Line2D.Double(this.startPoint,this.endPoint);
			double linelen = this.startPoint.distance(this.endPoint);
			//int s = (int)this.startPoint.getX();
			//int e = (int)this.endPoint.getX();
			double sum = 0;
			int i;
			//for (i=s; i<=e; i++){
			for (i=this.start; i<=this.end; i++){
				Point2D.Double p = new Point2D.Double(mdsvector[i].getX(),mdsvector[i].getY());
				sum = sum + line.ptLineDist(p);
			}

			this.significativity = sum/i;

	  }

	  public double getSignif()
	  {
		 return this.significativity;
	  }

	  public void setK()
	  {
			this.k = ((this.endPoint.getY())-(this.startPoint.getY()))/((this.endPoint.getX())-(this.startPoint.getX()));
	  }

	  public double getK()
	  {
		 return k;
	  }

	  //public void setSigma(int start, int end)
	  public void setSigma()
	  {
			Line2D.Double line = new Line2D.Double(this.startPoint,this.endPoint);
			//int s =  (int)this.startPoint.getX();
			//int e =  (int)this.endPoint.getX();
			int i;
			double sum = 0;
			//for (i=start; i<=e; i++) {
			for (i=this.start; i<=this.end; i++) {
					Point2D.Double p = new Point2D.Double(mdsvector[i].getX(),mdsvector[i].getY());
					sum = sum + line.ptLineDist(p);
			}
			//int div = e - s + 1;
			int div = end - start + 1;
			this.sigma = sum/div;

	  }

	  public double getSigma()
	  {
			return this.sigma;
	  }

	  public Point2D.Double getStartPoint()
	  {
	  	return this.startPoint;
	  }

	  public void setStartPoint(Point2D.Double start)
	  {
	  	  this.startPoint = start;
	  }

	  public Point2D.Double getEndPoint()
	  {
		return this.endPoint;
	  }

	  public void setEndPoint(Point2D.Double end)
	  {
		  this.endPoint = end;
	  }

	  public void setType(char t){
		  this.p_type = t;
	  }

      public char getType(){
		  return this.p_type;
	  }

	  public double getDurata(){
		  return this.startPoint.getX()-this.endPoint.getX();
	  }



};

