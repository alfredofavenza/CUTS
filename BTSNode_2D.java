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

public class BTSNode_2D {

	private Node_2D nodo;
	private double maxAngle;

	public BTSNode_2D (){}

	public void setMaxAngles(Node_2D [] csvector){

		int start = (int)(this.getNodo().getStartPoint().getX());
		int end = (int)(this.getNodo().getEndPoint().getX());

		double max = 0;
		for (int j = start; j < end ; j++){
			if (csvector[j].getAngle() > max)
				max = csvector[j].getAngle();
		}
	}

	public double getMaxAngle(){

		return this.maxAngle;
	}

	public void setNodo(Node_2D node){

		this.nodo = node;
	}

	public Node_2D getNodo(){

		return this.nodo;
	}

};

