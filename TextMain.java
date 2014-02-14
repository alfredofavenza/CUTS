
package org.tartarus.snowball;

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
import java.awt.Graphics;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;



public class TextMain {

    private static void usage()
    {
        System.err.println("Usage: CutsMain <input file> ");
    }

    public static void main(String [] args) throws Throwable {
		if (args.length < 1)
		{
			usage();
			return;
		}

		//Leggo il file in input dallo Stemmer
		Reader reader;
		reader = new InputStreamReader(new FileInputStream(args[0]));
		reader = new BufferedReader(reader);
		int i=0;

		FileWriter fileout = new FileWriter("testo.txt");


		//PrintWriter out = new PrintWriter(new FileWriter("c:\outputfile.txt"));

		//out.println("world");
		//out.close();

		String characters;	//stringa che contiene ogni singola parola
		characters= "";


		int car;

		boolean emptyline = false;
		boolean closed = false;
		//Leggo ciascuna parola
		//Inserisco la parola nel vocabolario (se non esiste gia)
		//Incremento il valore nel vettore della entry ogni qual volta incontro una parola
		//while ( ((car = reader.read()) != -1) && (closed == false) ) {
		while ( ((car = reader.read()) != -1)) {
			//if ( ( ((char)car) != '\n') && (car != 13) ) {
				//System.out.println(car);
				boolean found = false;

				if (((char)car) == '<')
				{
					car = reader.read();
					if (((char)car) == 'm')
					{
						car = reader.read();
						if (((char)car) == 'a')
						{
							car = reader.read();
							if (((char)car) == 'd')
							{
								car = reader.read();
								if (((char)car) == 'l')
								{
									car = reader.read();
									if (((char)car) == ':')
									{
										car = reader.read();
										if (((char)car) == 'T')
										{
											car = reader.read();
											if (((char)car) == 'r')
											{
												car = reader.read();
												if (((char)car) == 'a')
												{
													car = reader.read();
													if (((char)car) == 'n')
													{
														car = reader.read();
														if (((char)car) == 's')
														{
															car = reader.read();
															if (((char)car) == 'c')
															{
																car = reader.read();
																if (((char)car) == 'r')
																{
																	car = reader.read();
																	if (((char)car) == 'i')
																	{
																		car = reader.read();
																		if (((char)car) == 'p')
																		{
																			car = reader.read();
																			if (((char)car) == 't')
																			{
																				car = reader.read();
																				if (((char)car) == 'i')
																				{
																					car = reader.read();
																					if (((char)car) == 'o')
																					{
																						car = reader.read();
																						if (((char)car) == 'n')
																						{
																							car = reader.read();
																							if (((char)car) == '>')
																					        {
																								System.out.println("Trovato <madl:Transcription>");
																								//car = reader.read();
																								//System.out.println((char)car);
																								//characters = characters+((char)car);
																								characters= "";
																								while ((car = reader.read()) != '<') {
																									//car = reader.read();
																									//System.out.println((char)car);
																									characters = characters+((char)car);


																								}
																										found = true;
																										//characters = characters+"\n";
																										//characters = characters+"\n";
																										//characters = characters+"##########################################################################################";
																										//characters = characters+"\n";
																										//characters = characters+"\n";

																										for (int j = 0; j < characters.length(); j++)
																								            fileout.write(characters.charAt(j));
																								            fileout.close();

																								        Reader stemreader;
																										stemreader = new InputStreamReader(new FileInputStream(fileout));
																										stemreader = new BufferedReader(stemreader);

																										Class stemClass = Class.forName("org.tartarus.snowball.ext.italianStemmer");
																										SnowballProgram stemmer = (SnowballProgram) stemClass.newInstance();
																										Method stemMethod = stemClass.getMethod("stem", new Class[0]);

																										OutputStream outstream;
																										outstream = new FileOutputStream("c:\final.txt");
																										Writer output = new OutputStreamWriter(outstream);
																										output = new BufferedWriter(output);


																											Object [] emptyArgs = new Object[0];
																											int character;
																											while ((character = reader.read()) != -1) {
																											    char ch = (char) character;
																											    if (Character.isWhitespace((char) ch)) {
																													if (input.length() > 0) {
																													    stemmer.setCurrent(input.toString());
																													    for (int i = repeat; i != 0; i--) {
																															stemMethod.invoke(stemmer, emptyArgs);
																													    }
																													    output.write(stemmer.getCurrent());
																													    output.write('\n');
																													    input.delete(0, input.length());
																													}
																											    }
																											    else {
																													input.append(Character.toLowerCase(ch));
																											    }
																											}

																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (found == true)
				{
					if (((char)car) == '<')
					{
						car = reader.read();
						if (((char)car) == '/')
						{
							car = reader.read();
							if (((char)car) == 'm')
							{
								car = reader.read();
								if (((char)car) == 'a')
								{
									car = reader.read();
									if (((char)car) == 'd')
									{
										car = reader.read();
										if (((char)car) == 'l')
										{
											car = reader.read();
											if (((char)car) == ':')
											{
												car = reader.read();
												if (((char)car) == 'T')
												{
													car = reader.read();
													if (((char)car) == 'r')
													{
														car = reader.read();
														if (((char)car) == 'a')
														{
															car = reader.read();
															if (((char)car) == 'n')
															{
																car = reader.read();
																if (((char)car) == 's')
																{
																	car = reader.read();
																	if (((char)car) == 'c')
																	{
																		car = reader.read();
																		if (((char)car) == 'r')
																		{
																			car = reader.read();
																			if (((char)car) == 'i')
																			{
																				car = reader.read();
																				if (((char)car) == 'p')
																				{
																					car = reader.read();
																					if (((char)car) == 't')
																					{
																						car = reader.read();
																						if (((char)car) == 'i')
																						{
																							car = reader.read();
																							if (((char)car) == 'o')
																							{
																								car = reader.read();
																								if (((char)car) == 'n')
																								{
																									car = reader.read();
																									if (((char)car) == '>')
																									{
																										closed = true;

																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}


		}
		System.out.println("ciao");
		//out.print(characters);
		//System.out.println(characters);


	}


}
