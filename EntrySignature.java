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


public class EntrySignature {
	  public int [] vec;
	  public int time;
	  public int insertPoint;
	  public int len;

	  public EntrySignature (int dim)
	  {
		  len = dim;
		  vec = new int[len];
		  for (int i=0; i < len; i++)
			vec[i]=0;
		  time = 0;
		  insertPoint = 0;
      }

      public int getLen()
	  {
          return len;
      }


      public void setInsertPoint(int point)
	  {
		  insertPoint = point;
	  }

	  public int getInsertPoint()
	  {
		  return insertPoint;
	  }

	  public void setTime(int t)
	  {
		  time = t;
	  }

	  public int getTime()
	  {
		  return this.time;
	  }

      public void addKey(int indice){
		  vec[indice] = vec[indice] + 1;
	  }


	  public String getKey(int indice,Vocabolario v){
	      return v.getKey(indice);
	  }

	  public void printSignature(FileWriter w)
	  {
		  try{
			  w.write("<");

			  for (int i=0; i<this.getLen(); i++) {
				w.write(this.vec[i]+" ");
			  }
			  w.write(">");
			  w.write("\n");
		  }
		  catch(Exception e){};
	  }

	  public int getElement(int i)
	  {
		  return vec[i];
	  }

	  public void printTimePointsOnFile(FileWriter w) throws Throwable
	  {
		  	w.write(this.time+" ");

  	  }

};

