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

public class Vocabolario {
	  String [] vec;
	  int insertPoint;
	  int len;
	  public static String PATH = "C:/CUTS/";


	  public Vocabolario (int dim)
	  {
          len = dim;
          vec = new String[len];
           for (int i=0; i < len; i++)
			vec[i]="";
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

      public void addKey(String vocabolo, int indice){
		  vec[indice] = "";
		  vec[indice] = vec[indice]+vocabolo;
	  }

	  public String getKey(int indice)
	  {
	  		  return vec[indice];
	  }

	  public int findKey(String key)
	  {
		  int i=0;
		  boolean ret = false;
		  //System.out.println("Parola cercata: "+key);
		  while ( (ret == false) && (i <= this.getLen()-1) ) {
			  	//System.out.println("cerco "+key);
			  	//System.out.println(this.getKey(i));

			  	//System.out.println("Confronto con: "+this.getKey(i));

				if (this.getKey(i).equals(key)) {
					//System.out.println(key+" trovata!");
					ret = true;
				}
				i++;

		  }
		  //System.out.println("L'indice di ricerca che ritorno è "+i);
		  return i-1;

	  }

	  public void display()
	  {
			  System.out.println("####### VOCABOLARIO #######");
			  System.out.println("\n");

			  for (int i=0; i<this.getLen(); i++) {
				System.out.println(this.vec[i]);
				System.out.println("\n");
			  }
			  System.out.println("###########################");
	  }

	  public void printOnFile()
	  {
		  try{
			  FileWriter writer = new FileWriter(PATH+"data/vocabolario.txt");

			  writer.write("####### VOCABOLARIO: "+this.getLen()+" elementi #######");
			  writer.write("\n");

			  for (int i=0; i<this.getLen(); i++) {
				writer.write(this.vec[i]);
				writer.write("\n");

			  }
			  writer.write("###########################");
			  writer.close();
		  }
		  catch(Exception e){};
	  }

	  public int getNumVocaboli()
	  {
		  int i=0;
		  try {
			  while (this.vec[i] != "")
				i = i+1;
			  return i;
		  } catch (Exception e) {return i;}

  	  }


};

