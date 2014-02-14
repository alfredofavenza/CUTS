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

public class MDSPoint_2D {

	  private int X;
	  private double Y;
	  private double Z;



	  public MDSPoint_2D(int x, double y, double z) {
		  this.X = x;
		  this.Y = y;
		  this.Z = z;

  	  }

	  public void setX(int x)
	  {
		this.X = x;
	  }

  	  public void setY(double y)
  	  {
  	  	this.Y = y;
  	  }

	  public void setZ(double z)
	  {
		this.Z = z;
  	  }

	  public int getX()
	  {
		return this.X;
	  }

	  public double getY()
	  {
	  	return this.Y;
	  }

	  public double getZ()
	  {
	  	return this.Z;
	  }


};

