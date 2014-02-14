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


public class AnalisysElement {

	  private int start;
	  private int end;
	  private int qualityelem;
	  private double max_quality;
	  private boolean taken;
	  private double precision;
	  private double recall;

	  public AnalisysElement (int s, int e)
	  {
		  this.start = s;
		  this.end = e;
		  this.qualityelem = -1;
		  this.taken = false;
		  this.precision = 0;
		  this.recall = 0;
		  this.max_quality = 0;

      }

      public void setTaken(boolean t){
		  this.taken = t;
	  }

	  public boolean getTaken(){
		  return this.taken;
	  }

	  public void setMaxQualityElem(int q){
		  this.qualityelem = q;
	  }

	  public void setMaxQuality(double mq){
			  this.max_quality = mq;
	  }

	  public void setQualityElem(AnalisysVector av){

		 int maxqualityelem = -1;
		 int maxquality = 0;
		 for (int i = 0; i < av.getLen(); i++){
			 int q = 0;
			 if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
			 	  ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) )  ) {

				q = this.getEnd()-this.getStart()+1;
		 	 }
		 	 else if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
			 	       ( (this.getEnd() >= av.getElement(i).getEnd()) )  ) {

				q = av.getElement(i).getEnd()-this.getStart()+1;
		 	 }
		 	 else if ( ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) ) &&
			 	       ( (this.getStart() <= av.getElement(i).getEnd()) )  ) {

				q = this.getEnd()-av.getElement(i).getStart()+1;
		 	 }
		 	 else q = 0;

		 	 if (q > maxquality) {
				 if (av.getElement(i).getTaken() == false){
				 	maxquality = q;
				 	maxqualityelem = i;
				}
			 }
		 }

		 if (maxqualityelem != -1)
		 	av.getElement(maxqualityelem).setTaken(true);
		 this.qualityelem = maxqualityelem;

	  }


	  public void setQualityElemv2(AnalisysVector av){

		 int maxqualityelem = -1;
		 int maxquality = 0;
		 for (int i = 0; i < av.getLen(); i++){
			 int q = 0;
			 if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
				  ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) )  ) {

				q = this.getEnd()-this.getStart()+1;
			 }
			 else if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getEnd() >= av.getElement(i).getEnd()) )  ) {

				q = av.getElement(i).getEnd()-this.getStart()+1;
			 }
			 else if ( ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getStart() <= av.getElement(i).getEnd()) )  ) {

				q = this.getEnd()-av.getElement(i).getStart()+1;
			 }
			 else q = 0;

			 if (q > maxquality) {
					maxquality = q;
					maxqualityelem = i;
			 }
		 }

		 this.qualityelem = maxqualityelem;

	  }


	   public double calculateQualityElem(AnalisysVector av){

	  		 int maxqualityelem = -1;
	  		 double maxquality = 0;
	  		 for (int i = 0; i < av.getLen(); i++){
	  			 double q = 0;
	  			 //interamente compreso
	  			 if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
	  				  ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) )  ) {

	  				q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
	  				//q = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
	  				//q = (double)(this.getEnd()-this.getStart()+1);
	  				//System.out.println("i="+i);
	  				//System.out.println("Caso 1 q="+q);
	  			 }
	  			 //non compreso a dx
	  			 else if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
	  					   ( (this.getEnd() >= av.getElement(i).getEnd()) )  ) {

	  				q = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
	  				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
	  				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1);
	  				//System.out.println("i="+i);
	  				//System.out.println("Caso 2 q="+q);
	  			 }
	  			 //non compreso a sx
	  			 else if ( ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) ) &&
	  					   ( (this.getStart() <= av.getElement(i).getEnd()) )  ) {

	  				q = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
	  				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);
	  				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1);
	  				//System.out.println("i="+i);
	  				//System.out.println("Caso 3 q="+q);
	  			 }
	  			 else if ( ( (this.getStart() < av.getElement(i).getStart())&&(this.getStart() < av.getElement(i).getEnd()) ) &&
	  			 		   ( (this.getEnd() > av.getElement(i).getStart())&&(this.getEnd() > av.getElement(i).getEnd()) )  ) {

	  				q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
	  				q = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);;
	  				//q = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
	  				//q = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
	  				//System.out.println("i="+i);
	  				//System.out.println("Caso 4 q="+q);
	  			 }

	  			 else q = 0;

	  			 if (q > maxquality) {
	  				 if (av.getElement(i).getTaken() == false){
	  					maxquality = q;
	  					maxqualityelem = i;
	  				}
	  			 }


	  		 }

	  		 //if (maxqualityelem != -1)
	  		 //	av.getElement(maxqualityelem).setTaken(true);
	  		 this.qualityelem = maxqualityelem;
	  		 return maxquality;

	  }


	  public double calculateQualityElemByPrecision(AnalisysVector av){

		 int maxqualityelem = -1;
		 double maxquality = 0;
		 for (int i = 0; i < av.getLen(); i++){
			 double q = 0;
			 //interamente compreso
			 if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
				  ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) )  ) {

				//q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(this.getEnd()-this.getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 1 q="+q);
			 }
			 //non compreso a dx
			 else if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getEnd() >= av.getElement(i).getEnd()) )  ) {

				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 2 q="+q);
			 }
			 //non compreso a sx
			 else if ( ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getStart() <= av.getElement(i).getEnd()) )  ) {

				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 3 q="+q);
			 }
			 else if ( ( (this.getStart() < av.getElement(i).getStart())&&(this.getStart() < av.getElement(i).getEnd()) ) &&
			 		   ( (this.getEnd() > av.getElement(i).getStart())&&(this.getEnd() > av.getElement(i).getEnd()) )  ) {

				//q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//q = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				q = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);;

				//System.out.println("i="+i);
				//System.out.println("Caso 4 q="+q);
			 }

			 else q = 0;

			 if (q > maxquality) {
				 if (av.getElement(i).getTaken() == false){
					maxquality = q;
					maxqualityelem = i;
				}
			 }


		 }

		 //if (maxqualityelem != -1)
		 //	av.getElement(maxqualityelem).setTaken(true);
		 this.qualityelem = maxqualityelem;
		 return maxquality;

	  }


	  public double calculateQualityElemByRecall(AnalisysVector av){

		 int maxqualityelem = -1;
		 double maxquality = 0;
		 for (int i = 0; i < av.getLen(); i++){
			 double q = 0;
			 //interamente compreso
			 if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
				  ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) )  ) {

				q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//q = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(this.getEnd()-this.getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 1 q="+q);
			 }
			 //non compreso a dx
			 else if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getEnd() >= av.getElement(i).getEnd()) )  ) {

				q = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 2 q="+q);
			 }
			 //non compreso a sx
			 else if ( ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getStart() <= av.getElement(i).getEnd()) )  ) {

				q = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 3 q="+q);
			 }
			 else if ( ( (this.getStart() < av.getElement(i).getStart())&&(this.getStart() < av.getElement(i).getEnd()) ) &&
			 		   ( (this.getEnd() > av.getElement(i).getStart())&&(this.getEnd() > av.getElement(i).getEnd()) )  ) {

				//q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//q = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 4 q="+q);
				//System.out.println(this.getStart()+"-"+this.getEnd()+"        "+av.getElement(i).getStart()+"-"+av.getElement(i).getEnd());
			 }

			 else q = 0;

			 if (q > maxquality) {
				 if (av.getElement(i).getTaken() == false){
					maxquality = q;
					maxqualityelem = i;
				}
			 }


		 }

		 //if (maxqualityelem != -1)
		 //	av.getElement(maxqualityelem).setTaken(true);
		 this.qualityelem = maxqualityelem;
		 return maxquality;

	  }


	  public double calculateQualityElemByOverall(AnalisysVector av){

		 int maxqualityelem = -1;
		 double maxquality = 0;
		 for (int i = 0; i < av.getLen(); i++){
			 double q_over = 0;
			 double q_back = 0;
			 double q_fore = 0;
			 //interamente compreso
			 if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
				  ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) )  ) {

				q_back = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				q_fore = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q_over = (2*q_back*q_fore)/(q_back+q_fore);
				//System.out.println("i="+i);
				//System.out.println("Caso 1 q="+q);
			 }
			 //non compreso a dx
			 else if ( ( (this.getStart() >= av.getElement(i).getStart())&&(this.getStart() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getEnd() >= av.getElement(i).getEnd()) )  ) {

				q_back = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				q_fore = (double)(av.getElement(i).getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q_over = (2*q_back*q_fore)/(q_back+q_fore);
				//q = (double)(av.getElement(i).getEnd()-this.getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 2 q="+q);
			 }
			 //non compreso a sx
			 else if ( ( (this.getEnd() >= av.getElement(i).getStart())&&(this.getEnd() <= av.getElement(i).getEnd()) ) &&
					   ( (this.getStart() <= av.getElement(i).getEnd()) )  ) {

				q_back = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);
				q_fore = (double)(this.getEnd()-av.getElement(i).getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q_over = (2*q_back*q_fore)/(q_back+q_fore);
				//q = (double)(this.getEnd()-av.getElement(i).getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 3 q="+q);
			 }
			 else if ( ( (this.getStart() < av.getElement(i).getStart())&&(this.getStart() < av.getElement(i).getEnd()) ) &&
					   ( (this.getEnd() > av.getElement(i).getStart())&&(this.getEnd() > av.getElement(i).getEnd()) )  ) {

				//q = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//q_back = (double)(this.getEnd()-this.getStart()+1)/(this.getEnd()-this.getStart()+1);
				//q_fore = (double)(this.getEnd()-this.getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q_back = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1)/(this.getEnd()-this.getStart()+1);
				q_over = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1)/(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				q_over = (2*q_back*q_fore)/(q_back+q_fore);
				//q = (double)(av.getElement(i).getEnd()-av.getElement(i).getStart()+1);
				//System.out.println("i="+i);
				//System.out.println("Caso 4 q="+q);
			 }

			 else q_over = 0;

			 if (q_over > maxquality) {
				 if (av.getElement(i).getTaken() == false){
					maxquality = q_over;
					maxqualityelem = i;
				}
			 }


		 }

		 //if (maxqualityelem != -1)
		 //	av.getElement(maxqualityelem).setTaken(true);
		 this.qualityelem = maxqualityelem;
		 return maxquality;

	  }


	  public boolean compreso(AnalisysElement a1,AnalisysElement a2) {

		  boolean cond1 = ( ( (a1.getStart() >= a2.getStart())&&(a1.getStart() <= a2.getEnd()) ) &&
				 	   ( (a1.getEnd() >= a2.getStart())&&(a1.getEnd() <= a2.getEnd()) )  );

		  boolean cond2 = ( ( (a1.getStart() >= a2.getStart())&&(a1.getStart() <= a2.getEnd()) ) &&
					   ( (a1.getEnd() >= a2.getEnd()) )  );

		  boolean cond3 = ( ( (a1.getEnd() >= a2.getStart())&&(a1.getEnd() <= a2.getEnd()) ) &&
					   ( (a1.getStart() <= a2.getEnd()) )  );

		  if (cond1 || cond2 || cond3)
		  	return true;

		  else return false;

	  }


	  public int getQualityElem(){
		  return this.qualityelem;
	  }

	  public void setPrecision(AnalisysElement qe){

			 double num = 0;

			 if ( ( (this.getStart() >= qe.getStart())&&(this.getStart() <= qe.getEnd()) ) &&
				  ( (this.getEnd() >= qe.getStart())&&(this.getEnd() <= qe.getEnd()) )  ) {
				//System.out.println("Caso 1\n");
				num = this.getEnd()-this.getStart()+1;
			 }
			 else if ( ( (this.getStart() >= qe.getStart())&&(this.getStart() <= qe.getEnd()) ) &&
					   ( (this.getEnd() >= qe.getEnd()) )  ) {
				//System.out.println("Caso 2\n");
				num = qe.getEnd()-this.getStart()+1;
			 }
			 else if ( ( (this.getEnd() >= qe.getStart())&&(this.getEnd() <= qe.getEnd()) ) &&
					   ( (this.getStart() <= qe.getStart()) )  ) {
				//System.out.println("Caso 3\n");
				num = this.getEnd()-qe.getStart()+1;
			 }

			 this.precision = num/(this.end-this.start+1);


	  }

	  public double getPrecision(){

		  return this.precision;
	  }

	  public void setRecall(AnalisysElement qe){

		  double num = 0;

		  if ( ( (this.getStart() >= qe.getStart())&&(this.getStart() <= qe.getEnd()) ) &&
		 	  ( (this.getEnd() >= qe.getStart())&&(this.getEnd() <= qe.getEnd()) )  ) {

		 	num = this.getEnd()-this.getStart()+1;
		  }
		  else if ( ( (this.getStart() >= qe.getStart())&&(this.getStart() <= qe.getEnd()) ) &&
		 		   ( (this.getEnd() >= qe.getEnd()) )  ) {

		 	num = qe.getEnd()-this.getStart()+1;
		  }
		  else if ( ( (this.getEnd() >= qe.getStart())&&(this.getEnd() <= qe.getEnd()) ) &&
		 		    ( (this.getStart() <= qe.getStart()) )  ) {

		 	num = this.getEnd()-qe.getStart()+1;
		  }

		  this.recall = num/(qe.end-qe.start+1);

	  }

	  public double getRecall(){

		  return this.recall;
	  }

	  public int getStart(){

		  return this.start;
	  }

	  public int getEnd(){

	      return this.end;
	  }

	  public double getMaxQuality(){
		  return this.max_quality;
	  }

};

