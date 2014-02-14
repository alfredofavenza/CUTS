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

public class Point3D {

	  private double X;
	  private double Y;
	  private double Z;


	  public Point3D(double x, double y, double z) {
		  this.X = x;
		  this.Y = y;
		  this.Z = z;
  	  }

	  public void setX(double x)
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

	  public double getX()
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

