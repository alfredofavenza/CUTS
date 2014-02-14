import java.lang.Math;
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
import java.io.*;

public class DMatrix {
	  private double [][] D;
	  private int rownum;
	  private int colnum;

	  public DMatrix(int num)
	  {
		  this.rownum = num;
		  this.colnum = num;
		  D = new double[rownum][colnum];
		  //System.out.println("----->"+rownum);
		  //System.out.println("----->"+colnum);

		  for(int i=0; i<rownum; i++)
		  {
		  	  for(int j=0; j<colnum; j++)
		  		D[i][j] = 0;
		  }
      }

      public void calculate(WeightVectors ws)
      {

		  //Calcolo per ogni coppia di vettori pesati in ws, la loro similarità e di conseguenza
		  //la loro matrice di Dissimilarità
          System.out.println("CALCULATE");
		  for (int i=0; i<this.rownum; i++)
		  {
			  int max =0;
			  for (int j=0; j<this.colnum; j++)
			  {
				 //if( i != j )
				 //{
				 	double simil = 0;
				 	for (int k = 0; k<this.colnum ;k++)
				 		simil = simil + ((ws.getWeight(i,k))*(ws.getWeight(j,k)));
			        this.D[i][j] = 1 - simil;
			        //System.out.println("######"+ this.D[i][j]);
			  	 //}
			  }
	      }
	   }

	   public double getValue(int i,int j)
	   {
		   return D[i][j];
	   }

	   public int getRownum()
	   {
	      return this.rownum;
	   }

	   public int getColnum()
	   {
	      return this.colnum;
	   }

	   public void printOnFile()
	   {
		  try{
			  FileWriter writer = new FileWriter("c:/dmatrix.dat");

			  for(int i=0; i<rownum; i++)
			  {
				  //writer.write("<");
				  for(int j=0; j<colnum; j++)
					writer.write(this.D[i][j]+" ");
				  //writer.write(">");
				  writer.write("\n");
			  }
			  writer.close();
		  }
		  catch(Exception e){};
	   }

	   public static boolean simmetrica (double[][] m)
	   {
	   		//Assertion.check(quadrata(m));

	   		for (int i=0;i<righe(m);i++)
	   			for (int j=i+1; j<colonne(m); j++)
	   				if (m[i][j]!=m[j][i])
	   					 return false;
	   		return true;
	   }


};

