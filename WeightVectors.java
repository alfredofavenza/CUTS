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

public class WeightVectors {
	  public static String PATH = "C:/CUTS/";
	  private EntrySignature [] entryS;
	  public double [][] TFIDFmatrix;
	  private int rownum;
	  private int colnum;
	  private double [][] freqNorm;

	  public WeightVectors(EntrySignature [] es)
	  {
		  entryS = es;
		  rownum = entryS.length;
		  colnum = entryS[1].vec.length;
		  TFIDFmatrix = new double[rownum][colnum];

		  for(int i=0; i<rownum; i++)
		  {
		  	  for(int j=0; j<colnum; j++)
		  		TFIDFmatrix[i][j] = 0;
		  }


      }

      public void entrysTotfidfs(Vocabolario voc)
      {
		  //Calcolo, per ogni vettore in entrys la Frequenza normalizzata dei termini
		  double [][] freqNorm = new double[this.rownum][this.colnum];

		  for (int i=0; i<this.rownum; i++)
		  {
			  int max =0;
			  for (int j=0; j<this.colnum; j++)
			  {
				 if( this.entryS[i].getElement(j) > max)
				 	max = this.entryS[i].getElement(j);
			  }
			  for (int f=0; f<this.colnum; f++)
			  {
			  	 if( this.entryS[i].getElement(f) == 0)
			  	 	freqNorm[i][f] = 0;
			  	 else
			  		freqNorm[i][f] = (double)entryS[i].getElement(f)/(max);

			     //System.out.println("La frequenza normalizzata per il vettore :"+freqNorm[i][f]);
			  }

		  }

		  //Calcolo per ogni chiave il fattore idf,
		  double [] idf = new double[this.colnum];
		  for (int i=0; i<this.colnum; i++)
		  {
			  int numdocwithkey =0;
			  for (int j=0; j<this.rownum; j++)
			  {
				 if( this.entryS[j].getElement(i) > 0)
				 	numdocwithkey = numdocwithkey + 1 ;
			  }
			  if (numdocwithkey==0)
			  	idf[i]=0;
			  else
			  	idf[i] = Math.log((double)this.rownum/(double)numdocwithkey);

			    //System.out.println("Il fattore IDF per la chiave :"+entryS[1].getKey(i,voc)+" "+idf[i]);
		  }

		  for (int i=0; i<this.rownum; i++)
		  {
			  for (int j=0; j<this.colnum; j++)
			  {
				 double weight = freqNorm[i][j]*idf[j];

				 this.TFIDFmatrix[i][j]=weight;
			  }

		  }

      }

      public int getRownum()
      {
	  	  return this.rownum;
	  }

	  public int getColnum()
	  {
	  	  return this.colnum;
	  }

      public double getWeight(int i, int j)
      {
	  		return this.TFIDFmatrix[i][j];
	  }

	  public void printOnFile()
	  {
		  try{
			  FileWriter writer = new FileWriter(PATH+"data/vettoriTFIDF.dat");

			  for(int i=0; i<rownum; i++)
			  {
				  //writer.write("<");
				  for(int j=0; j<colnum; j++)
					writer.write(TFIDFmatrix[i][j]+" ");
				  //writer.write(">");
				  writer.write("\n");
			  }
			  writer.close();
		  }
		  catch(Exception e){};
	  }


};

