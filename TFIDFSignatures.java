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

public class WeightVectors {
	  private EntrySignature [] entryS;
	  public double [][] TFIDFmatrix;
	  private int rownum;
	  private int colnum;
	  private int [] freqNorm;

	  public TFIDFSignatures(EntrySignature [] es)
	  {
		  entryS = es;
		  rownum = entryS.length;
		  colnum = entryS[0].length;
		  TFIDFmatrix = new int[rownum][colnum];
      }

      public entrysTotfidfs()
      {
		  //Calcolo, per ogni vettore in entrys la Frequenza normalizzata dei termini
		  int [] freqNorm = new int[this.getRownum()];

		  for (int i=0; i<=this.rownum(); i++)
		  {
			  int max =0;
			  for (int j=0; j<=this.colnum(); j++)
			  {
				 if( this.entryS(i,j) > max)
				 	max = this.entryS(i,j);
			  }
			  freqNorm[i] = max;
		  }

		  //Calcolo per ogni chiave il fattore idf,
		  double [] idf = new double[this.colnum];
		  for (int i=0; i<=this.colnum; i++)
		  {
			  int numdocwithkey =0;
			  for (int j=0; j<=this.rownum; j++)
			  {
				 if( this.entryS(i,j) > 0)
				 	numdocwithkey = numdocwithkey + 1 ;
			  }
			  idf[i] = log(this.rownum/numdocwithkey);
		  }

		  for (int i=0; i<=this.rownum; i++)
		  {
			  for (int j=0; j<=this.colnum; j++)
			  {
				 int w = freqNorm[i]*idf[j];
				 this.TFIDFmatrix[i,j]=w;
			  }
			  idf[i] = log(this.rownum/numdocwithkey);
		  }

      }

};

