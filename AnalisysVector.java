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


public class AnalisysVector {

	  public AnalisysElement [] vec;
	  private int len;
	  private int insertpoint;
	  private double avgprecision;
	  private double avgrecall;
	  private double f1;

	  public AnalisysVector (int dim)
	  {
		  this.len = dim;
		  vec = new AnalisysElement[len];
		  this.insertpoint = 0;
		  this.avgprecision = 0;
		  this.avgrecall = 0;
		  this.f1 = 0;
		  for (int i=0; i < len; i++)
			vec[i] = null;
      }

      public int getLen()
	  {
          return len;
      }

      public void refreshAssignment(){
		  for (int i=0; i< this.len; i++)
		  	this.vec[i].setTaken(false);
	  }

      public void addElement(int start, int end) {
		  vec[this.insertpoint] = new AnalisysElement(start, end);
		  this.insertpoint++;
	  }

	  public AnalisysElement getElement(int i)
	  {
		  return vec[i];
	  }

	  public double setTotalQuality(){

		  double sumquality = 0;
		  for (int i = 0;i<len;i++){
			  sumquality = this.vec[i].getMaxQuality() + sumquality;
			  //System.out.println(this.vec[i].getMaxQuality());
			  //System.out.println(sumquality);
		  }
		  //System.out.println("len= "+len);
		  return sumquality/len;
	  }

	  public void setAvgPrecision(double sumprec){

		  this.avgprecision = sumprec/this.len;
	  }

	  public double getAvgPrecision(){

	  	  return this.avgprecision;
	  }

	  public void setAvgRecall(double sumrec){

		  this.avgrecall = sumrec/this.len;
	  }

	  public double getAvgRecall(){

	   	  return this.avgrecall;
	  }

	  public void setF1(){

		  this.f1 = (2 * this.avgprecision * this.avgrecall)/(this.avgprecision + this.avgrecall);
	  }


	  public void printAnalisysVector(AnalisysVector av, FileWriter w, double stat, char ass_type) {

		  try {
			  w.write("\n");
			  w.write("\n");
			  w.write("-----------------------------------\n");
			  w.write("-----------------------------------\n");
			  w.write("       ANALISI STATISTICHE\n");
			  w.write("-----------------------------------\n");
			  w.write("-----------------------------------\n");
			  //w.write("AVG Precision: "+this.getAvgPrecision());
			  //w.write("\n");
			  //w.write("AVG Recall: "+this.getAvgRecall());
			  //w.write("\n");
			  //w.write("f-1: "+this.f1);
			  if (ass_type == 'P')
				w.write("Precision: "+stat);
			  else if (ass_type == 'R')
				w.write("Recall: "+stat);
			  else
				w.write("Overall: "+stat);

			  w.write("\n");
			  w.write("\n");
			  for (int i=0; i<this.len; i++){
				  w.write("Pattern: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
				  w.write("\n");
				  if (vec[i].getQualityElem() != -1)
					w.write("Best Quality: "+av.getElement(vec[i].getQualityElem()).getStart()+"-"+av.getElement(vec[i].getQualityElem()).getEnd());
				  else
					w.write("Best Quality: none");
				  /*w.write("\n");
				  w.write("Precision: "+vec[i].getPrecision());
				  w.write("\n");
				  w.write("Recall: "+vec[i].getRecall());*/
				  w.write("\n");
				  w.write("-----------------------------------\n");
			  }


		  }
		  catch(Exception e){};

	  }



	  public void printvec(){
		  for (int i = 0; i< len ; i++)
		  	System.out.println(vec[i].getStart()+"-"+vec[i].getEnd());
	  }


	  public void assignQuality(AnalisysVector av){

		  double qualitytot = 0;
		  double qualitymedia = 0;

		  int i = 0;
		  double maxq = 0;
		  while (i < len-1){
			  //Cerco elemento di qualità massima per i
			  maxq = this.vec[i].calculateQualityElem(av);
			  int j = i+1;
			  int qindex = this.vec[i].getQualityElem();
			  double maxq2 = 0;

			  if (qindex != -1){
				  while ((j<len)&&(this.vec[j].compreso(this.vec[j],av.getElement(qindex)) == true)){
					//interamente compreso
					if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						  ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) )  ) {

						maxq2 = (this.vec[j].getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
					}
					//non compreso a dx
					else if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getEnd() >= av.getElement(qindex).getEnd()) )  ) {

						maxq2 = (av.getElement(qindex).getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
					}
					//non compreso a sx
					else if ( ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getStart() <= av.getElement(qindex).getEnd()) )  ) {

						maxq2 = (this.vec[j].getEnd()-av.getElement(qindex).getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
					}

					if ( (maxq2 > maxq) ) {
						//Cerco un elemento di quality tra gli elementi precedenti e se lo
						//trovo lo setto come preso

						if (qindex > 0){
							double newmaxq = 0;
							for (int k = 0; k < qindex; k++){
								double q = 0;
								if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
									( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) )  ) {

										q = (this.vec[i].getEnd()-this.vec[i].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
								}
								//non compreso a dx
								else if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getEnd() >= av.getElement(k).getEnd()) )  ) {

										q = (av.getElement(k).getEnd()-this.vec[j].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
								}
								//non compreso a sx
								else if ( ( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getStart() <= av.getElement(k).getEnd()) )  ) {

										q = (this.vec[i].getEnd()-av.getElement(k).getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
								}
								else q = 0;

								if (q > newmaxq) {
									 if (av.getElement(k).getTaken() == false){
										newmaxq = q;
										this.vec[i].setMaxQualityElem(k);
									}
								}
							}

							av.getElement(this.vec[i].getQualityElem()).setTaken(true);
							this.vec[i].setMaxQuality(newmaxq);
							//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(this.vec[i].getQualityElem()).getStart()+"-"+av.getElement(this.vec[i].getQualityElem()).getEnd());
						}
						else {
							this.vec[i].setMaxQuality(0);
							this.vec[i].setMaxQualityElem(-1);
						}

					}
					else if (maxq2 <= maxq){
						//Setto l'elemento di quality come preso
						this.vec[i].setMaxQualityElem(qindex);
						av.getElement(qindex).setTaken(true);
						this.vec[i].setMaxQuality(maxq);
						//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
					}


					j++;
				  }

			  }
			  else {
				//System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
				this.vec[i].setMaxQualityElem(-1);
				this.vec[i].setMaxQuality(0);
			  }
				i++;

		  }//WHILE
		  maxq = this.vec[i].calculateQualityElem(av);
		  int qindex = this.vec[i].getQualityElem();
		  //System.out.println("qindex="+qindex);
		  if (qindex != -1){
			  this.vec[i].setMaxQualityElem(qindex);
			  av.getElement(qindex).setTaken(true);
			  this.vec[i].setMaxQuality(maxq);
			  //System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
		  }
		  else {
			  this.vec[i].setMaxQualityElem(-1);
			  this.vec[i].setMaxQuality(0);
			  //System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
		  }
  	  }


	  public void assignQualityBackward(AnalisysVector av){

		  double qualitytot = 0;
		  double qualitymedia = 0;

		  int i = 0;
		  double maxq = 0;
		  while (i < len-1){
			  //Cerco elemento di qualità massima per i
			  maxq = this.vec[i].calculateQualityElemByPrecision(av);
			  int j = i+1;
			  int qindex = this.vec[i].getQualityElem();
			  double maxq2 = 0;

			  if (qindex != -1){
				  while ((j<len)&&(this.vec[j].compreso(this.vec[j],av.getElement(qindex)) == true)){
					//interamente compreso
					if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						  ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) )  ) {

						//maxq2 = (this.vec[j].getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
						maxq2 = (this.vec[j].getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart());
					}
					//non compreso a dx
					else if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getEnd() >= av.getElement(qindex).getEnd()) )  ) {

						//maxq2 = (av.getElement(qindex).getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
						maxq2 = (av.getElement(qindex).getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart());
					}
					//non compreso a sx
					else if ( ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getStart() <= av.getElement(qindex).getEnd()) )  ) {

						//maxq2 = (this.vec[j].getEnd()-av.getElement(qindex).getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
						maxq2 = (this.vec[j].getEnd()-av.getElement(qindex).getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart());
					}

					if ( (maxq2 > maxq) ) {
						//Cerco un elemento di quality tra gli elementi precedenti e se lo
						//trovo lo setto come preso

						if (qindex > 0){
							double newmaxq = 0;
							for (int k = 0; k < qindex; k++){
								double q = 0;
								if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
									( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) )  ) {

										//q = (this.vec[i].getEnd()-this.vec[i].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
										q = (this.vec[i].getEnd()-this.vec[i].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart());
								}
								//non compreso a dx
								else if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getEnd() >= av.getElement(k).getEnd()) )  ) {

										//q = (av.getElement(k).getEnd()-this.vec[j].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
										q = (av.getElement(k).getEnd()-this.vec[j].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart());
								}
								//non compreso a sx
								else if ( ( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getStart() <= av.getElement(k).getEnd()) )  ) {

										//q = (this.vec[i].getEnd()-av.getElement(k).getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
										q = (this.vec[i].getEnd()-av.getElement(k).getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart());
								}
								else q = 0;

								if (q > newmaxq) {
									 if (av.getElement(k).getTaken() == false){
										newmaxq = q;
										this.vec[i].setMaxQualityElem(k);
									}
								}
							}

							av.getElement(this.vec[i].getQualityElem()).setTaken(true);
							this.vec[i].setMaxQuality(newmaxq);
							//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(this.vec[i].getQualityElem()).getStart()+"-"+av.getElement(this.vec[i].getQualityElem()).getEnd());
						}
						else {
							this.vec[i].setMaxQuality(0);
							this.vec[i].setMaxQualityElem(-1);
						}

					}
					else if (maxq2 <= maxq){
						//Setto l'elemento di quality come preso
						this.vec[i].setMaxQualityElem(qindex);
						av.getElement(qindex).setTaken(true);
						this.vec[i].setMaxQuality(maxq);
						//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
					}


					j++;
				  }

			  }
			  else {
				//System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
			  	this.vec[i].setMaxQualityElem(-1);
			  	this.vec[i].setMaxQuality(0);
			  }
				i++;

		  }//WHILE
		  maxq = this.vec[i].calculateQualityElemByPrecision(av);
		  int qindex = this.vec[i].getQualityElem();
		  //System.out.println("qindex="+qindex);
		  if (qindex != -1){
			  this.vec[i].setMaxQualityElem(qindex);
			  av.getElement(qindex).setTaken(true);
			  this.vec[i].setMaxQuality(maxq);
			  //System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
		  }
		  else {
			  this.vec[i].setMaxQualityElem(-1);
			  this.vec[i].setMaxQuality(0);
			  //System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
		  }
  	  }


	  public void assignQualityForeward(AnalisysVector av){

		  double qualitytot = 0;
		  double qualitymedia = 0;

		  int i = 0;
		  double maxq = 0;
		  while (i < len-1){
			  //Cerco elemento di qualità massima per i
			  maxq = this.vec[i].calculateQualityElemByRecall(av);
			  int j = i+1;
			  int qindex = this.vec[i].getQualityElem();
			  double maxq2 = 0;

			  if (qindex != -1){
				  while ((j<len)&&(this.vec[j].compreso(this.vec[j],av.getElement(qindex)) == true)){
					//interamente compreso
					if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						  ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) )  ) {

						//maxq2 = (this.vec[j].getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
						maxq2 = (this.vec[j].getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart());
					}
					//non compreso a dx
					else if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getEnd() >= av.getElement(qindex).getEnd()) )  ) {

						//maxq2 = (av.getElement(qindex).getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
						maxq2 = (av.getElement(qindex).getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart());
					}
					//non compreso a sx
					else if ( ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getStart() <= av.getElement(qindex).getEnd()) )  ) {

						//maxq2 = (this.vec[j].getEnd()-av.getElement(qindex).getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
						maxq2 = (this.vec[j].getEnd()-av.getElement(qindex).getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart());
					}

					if ( (maxq2 > maxq) ) {
						//Cerco un elemento di quality tra gli elementi precedenti e se lo
						//trovo lo setto come preso

						if (qindex > 0){
							double newmaxq = 0;
							for (int k = 0; k < qindex; k++){
								double q = 0;
								if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
									( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) )  ) {

										//q = (this.vec[i].getEnd()-this.vec[i].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
										q = (this.vec[i].getEnd()-this.vec[i].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart());
								}
								//non compreso a dx
								else if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getEnd() >= av.getElement(k).getEnd()) )  ) {

										//q = (av.getElement(k).getEnd()-this.vec[j].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
										q = (av.getElement(k).getEnd()-this.vec[j].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart());
								}
								//non compreso a sx
								else if ( ( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getStart() <= av.getElement(k).getEnd()) )  ) {

										//q = (this.vec[i].getEnd()-av.getElement(k).getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
										q = (this.vec[i].getEnd()-av.getElement(k).getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart());
								}
								else q = 0;

								if (q > newmaxq) {
									 if (av.getElement(k).getTaken() == false){
										newmaxq = q;
										this.vec[i].setMaxQualityElem(k);
									}
								}
							}

							av.getElement(this.vec[i].getQualityElem()).setTaken(true);
							this.vec[i].setMaxQuality(newmaxq);
							//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(this.vec[i].getQualityElem()).getStart()+"-"+av.getElement(this.vec[i].getQualityElem()).getEnd());
						}
						else {
							this.vec[i].setMaxQuality(0);
							this.vec[i].setMaxQualityElem(-1);
						}

					}
					else if (maxq2 <= maxq){
						//Setto l'elemento di quality come preso
						this.vec[i].setMaxQualityElem(qindex);
						av.getElement(qindex).setTaken(true);
						this.vec[i].setMaxQuality(maxq);
						//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
					}


					j++;
				  }

			  }
			  else {
				//System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
			  	this.vec[i].setMaxQualityElem(-1);
			  	this.vec[i].setMaxQuality(0);
			  }
				i++;

		  }//WHILE
		  maxq = this.vec[i].calculateQualityElemByRecall(av);
		  int qindex = this.vec[i].getQualityElem();
		  //System.out.println("qindex="+qindex);
		  if (qindex != -1){
			  this.vec[i].setMaxQualityElem(qindex);
			  av.getElement(qindex).setTaken(true);
			  this.vec[i].setMaxQuality(maxq);
			  //System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
		  }
		  else {
			  this.vec[i].setMaxQualityElem(-1);
			  this.vec[i].setMaxQuality(0);
			  //System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
		  }
  	  }


	  public void assignQualityOverall(AnalisysVector av){

		  double qualitytot = 0;
		  double qualitymedia = 0;

		  int i = 0;
		  double maxq = 0;
		  while (i < len-1){
			  //Cerco elemento di qualità massima per i
			  maxq = this.vec[i].calculateQualityElemByOverall(av);
			  int j = i+1;
			  int qindex = this.vec[i].getQualityElem();
			  double maxq2 = 0;

			  if (qindex != -1){
				  while ((j<len)&&(this.vec[j].compreso(this.vec[j],av.getElement(qindex)) == true)){
					//interamente compreso
					if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						  ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) )  ) {

						maxq2 = (this.vec[j].getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
					}
					//non compreso a dx
					else if ( ( (this.vec[j].getStart() >= av.getElement(qindex).getStart())&&(this.vec[j].getStart() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getEnd() >= av.getElement(qindex).getEnd()) )  ) {

						maxq2 = (av.getElement(qindex).getEnd()-this.vec[j].getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
					}
					//non compreso a sx
					else if ( ( (this.vec[j].getEnd() >= av.getElement(qindex).getStart())&&(this.vec[j].getEnd() <= av.getElement(qindex).getEnd()) ) &&
						   ( (this.vec[j].getStart() <= av.getElement(qindex).getEnd()) )  ) {

						maxq2 = (this.vec[j].getEnd()-av.getElement(qindex).getStart()+1)/(this.vec[j].getEnd()-this.vec[j].getStart()+1);
					}

					if ( (maxq2 > maxq) ) {
						//Cerco un elemento di quality tra gli elementi precedenti e se lo
						//trovo lo setto come preso

						if (qindex > 0){
							double newmaxq = 0;
							for (int k = 0; k < qindex; k++){
								double q = 0;
								if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
									( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) )  ) {

										q = (this.vec[i].getEnd()-this.vec[i].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
								}
								//non compreso a dx
								else if ( ( (this.vec[i].getStart() >= av.getElement(k).getStart())&&(this.vec[i].getStart() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getEnd() >= av.getElement(k).getEnd()) )  ) {

										q = (av.getElement(k).getEnd()-this.vec[j].getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
								}
								//non compreso a sx
								else if ( ( (this.vec[i].getEnd() >= av.getElement(k).getStart())&&(this.vec[i].getEnd() <= av.getElement(k).getEnd()) ) &&
								   ( (this.vec[i].getStart() <= av.getElement(k).getEnd()) )  ) {

										q = (this.vec[i].getEnd()-av.getElement(k).getStart()+1)/(this.vec[i].getEnd()-this.vec[i].getStart()+1);
								}
								else q = 0;

								if (q > newmaxq) {
									 if (av.getElement(k).getTaken() == false){
										newmaxq = q;
										this.vec[i].setMaxQualityElem(k);
									}
								}
							}

							av.getElement(this.vec[i].getQualityElem()).setTaken(true);
							this.vec[i].setMaxQuality(newmaxq);
							//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(this.vec[i].getQualityElem()).getStart()+"-"+av.getElement(this.vec[i].getQualityElem()).getEnd());
						}
						else {
							this.vec[i].setMaxQuality(0);
							this.vec[i].setMaxQualityElem(-1);
						}

					}
					else if (maxq2 <= maxq){
						//Setto l'elemento di quality come preso
						this.vec[i].setMaxQualityElem(qindex);
						av.getElement(qindex).setTaken(true);
						this.vec[i].setMaxQuality(maxq);
						//System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
					}


					j++;
				  }

			  }
			  else {
				//System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
			  	this.vec[i].setMaxQualityElem(-1);
			  	this.vec[i].setMaxQuality(0);
			  }
				i++;

		  }//WHILE
		  maxq = this.vec[i].calculateQualityElemByOverall(av);
		  int qindex = this.vec[i].getQualityElem();
		  //System.out.println("qindex="+qindex);
		  if (qindex != -1){
			  this.vec[i].setMaxQualityElem(qindex);
			  av.getElement(qindex).setTaken(true);
			  this.vec[i].setMaxQuality(maxq);
			  //System.out.println("Assegnamento: "+this.vec[i].getStart()+"-"+this.vec[i].getEnd()+" <-> "+av.getElement(qindex).getStart()+"-"+av.getElement(qindex).getEnd());
		  }
		  else {
			  this.vec[i].setMaxQualityElem(-1);
			  this.vec[i].setMaxQuality(0);
			  //System.out.println("Nessun elemento possibile trovato per "+this.vec[i].getStart()+"-"+this.vec[i].getEnd());
		  }
  	  }

};

