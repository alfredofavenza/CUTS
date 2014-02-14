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
import java.util.Vector;

public class Vocabolario1 {
	  Vector vec;
	  int insertPoint;

	  public Vocabolario1 ()
	  {
          vec = new Vector(100,100);
          insertPoint = 0;
      }

      public int getLen()
	  {
          return vec.size();
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
		  vec.add(indice, vocabolo);
		  //vec[indice] = "";
		  //vec[indice] = vec[indice]+vocabolo;
	  }

	  public Object getKey(int indice)
	  {
	  		  return vec.get(indice);
	  }

	  public int findKey(String key)
	  {
		  return vec.indexOf(key);

	  }


};

