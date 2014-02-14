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

public class MDSPoint {

	  private int X;
	  private double Y;



	  public MDSPoint(int x, double y) {
		  this.X = x;
		  this.Y = y;

  	  }

	  public void setX(int x)
	  {
		this.X = x;
	  }

  	  public void setY(double y)
  	  {
  	  	this.Y = y;
  	  }

	  public int getX()
	  {
		return this.X;
	  }

	  public double getY()
	  {
	  	return this.Y;
	  }


};

