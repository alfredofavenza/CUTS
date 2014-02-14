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

public class Vector2dElem{

	private double y;
	private double z;

	public Vector2dElem(double ypoint, double zpoint)
	{
		this.y = ypoint;
		this.z = zpoint;
	}

	public void setY(double point)
	{
		this.y = point;
	}

	public double getY()
	{
		return this.y;
	}

	public void setZ(double point)
	{
		this.z = point;
	}

	public double getZ()
	{
		return this.z;
	}

}