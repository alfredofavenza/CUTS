/*
 * SwingApplication.java is a 1.4 example that requires
 * no other files.
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingApplication implements ActionListener {
	public static String PATH = "C:/CutsDEV_ori/";
    private static String labelPrefix = "Number of button clicks: ";
    private int numClicks = 0;
    private JTextField voctext;
   	private JTextField drifttext;
    private JTextField mspantext;
    private JButton button;
    private JFileChooser filec;
    //private JTextArea logtextarea;
    private JTextArea entrylogtextarea;
    private JTextArea cslogtextarea;
    private JTextArea btslogtextarea;
    private JTextArea tdplogtextarea;
    private JCheckBox checkGraph;
    final JLabel label = new JLabel(labelPrefix + "0    ");

    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = null;

    public Component createComponents() {

        filec = new JFileChooser();
        filec.setControlButtonsAreShown(false);
        File dir = new File("C:/test/");
        filec.setCurrentDirectory(dir);
        JLabel voclab = new JLabel("Dictionary size:");
        JLabel driftlab = new JLabel("Drifting:");
        JLabel mspanlab = new JLabel("Min Span:");
        JLabel graph = new JLabel("Show graphics:");
        voctext = new JTextField("2000");
        drifttext = new JTextField("10");
        mspantext = new JTextField("6");

        entrylogtextarea = new JTextArea(20,50);
        entrylogtextarea.setRows(20);
        entrylogtextarea.setColumns(20);
        entrylogtextarea.setEditable(false);
        entrylogtextarea.setCaretPosition(entrylogtextarea.getDocument().getLength());

        cslogtextarea = new JTextArea(20,50);
        cslogtextarea.setRows(20);
        cslogtextarea.setColumns(20);
        cslogtextarea.setEditable(false);
        cslogtextarea.setCaretPosition(cslogtextarea.getDocument().getLength());

        btslogtextarea = new JTextArea(20,50);
		btslogtextarea.setRows(20);
		btslogtextarea.setColumns(20);
		btslogtextarea.setEditable(false);
        btslogtextarea.setCaretPosition(btslogtextarea.getDocument().getLength());

        tdplogtextarea = new JTextArea(20,50);
		tdplogtextarea.setRows(20);
		tdplogtextarea.setColumns(20);
		tdplogtextarea.setEditable(false);
        tdplogtextarea.setCaretPosition(tdplogtextarea.getDocument().getLength());

   		checkGraph = new JCheckBox();
   		button = new JButton("Start");



        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);


        JTabbedPane tabby = new JTabbedPane();



        JPanel input = new JPanel();

        JPanel output = new JPanel();

        JTabbedPane tabby1 = new JTabbedPane();

        output.add(tabby1);

        //JPanel title = new JPanel();

        //JLabel tit = new JLabel("CUTS");
        //title.add(tit);

        JPanel left = new JPanel();
        left.setBounds(0,0,5,5);
        left.add(filec);

        JPanel right = new JPanel(new GridLayout(10, 1));
       	right.add(voclab);
        right.add(voctext);
        right.add(mspanlab);
        right.add(mspantext);
        right.add(driftlab);
        right.add(drifttext);
        right.add(graph);
        right.add(checkGraph);
		JLabel lab = new JLabel();
		right.add(lab);
        right.add(button);

        //input.add(title,BorderLayout.NORTH);
        input.add(left,BorderLayout.WEST);
        input.add(right,BorderLayout.EAST);
        input.setBorder(BorderFactory.createEmptyBorder(
                                        30, //top
                                        30, //left
                                        10, //bottom
                                        30) //right
                                          );

        JPanel entrylog = new JPanel();
		JScrollPane scroller1 = new JScrollPane() {
					public Dimension getPreferredSize() {
						return new Dimension(600,400);
					}
					public float getAlignmentX() {
						return LEFT_ALIGNMENT;
					}
		};
		scroller1.getViewport().add(entrylogtextarea);
        entrylog.add("Center", scroller1);



		JPanel cslog = new JPanel();
		JScrollPane scroller2 = new JScrollPane() {
					public Dimension getPreferredSize() {
						return new Dimension(600,400);
					}
					public float getAlignmentX() {
						return LEFT_ALIGNMENT;
					}
		};
		scroller2.getViewport().add(cslogtextarea);
        cslog.add("Center", scroller2);



        JPanel btslog = new JPanel();
		JScrollPane scroller3 = new JScrollPane() {
					public Dimension getPreferredSize() {
						return new Dimension(600,400);
					}
					public float getAlignmentX() {
						return LEFT_ALIGNMENT;
					}
		};
		scroller3.getViewport().add(btslogtextarea);
        btslog.add("Center", scroller3);



        JPanel tdplog = new JPanel();
		JScrollPane scroller4 = new JScrollPane() {
					public Dimension getPreferredSize() {
						return new Dimension(600,400);
					}
					public float getAlignmentX() {
						return LEFT_ALIGNMENT;
					}
		};
		scroller4.getViewport().add(tdplogtextarea);
        tdplog.add("Center", scroller4);

		tabby1.addTab("Entry Log", entrylog);
        tabby1.addTab("Curve Segments Log", cslog);
        tabby1.addTab("Base Topic Segments Log", btslog);
        tabby1.addTab("Topic Development Patterns Log", tdplog);



        tabby.addTab("Inputs", input);
        tabby.addTab("Outputs", output);
        //tabby.addTab("Entry Log", entrylog);
        //tabby.addTab("Curve Segments Log", cslog);
        //tabby.addTab("Base Topic Segments Log", btslog);
        //tabby.addTab("Topic Development Patterns Log", tdplog);

        return tabby;
    }

    public void actionPerformed(ActionEvent e) {

		try {


	        int voc = Integer.parseInt(voctext.getText());
	        double mspan = Double.parseDouble(mspantext.getText());
	        double drift = Double.parseDouble(drifttext.getText());
	        File f = filec.getSelectedFile();
	        String file = f.getName();
	        File f_dir = filec.getCurrentDirectory();
	        String dir = f_dir.getName();
	        String path = "c:/"+dir+"/"+file;
	        //CutsMainAdv cmadv = new CutsMainAdv(path,voc, mspan, drift);
	        Cuts cuts = new Cuts(path,voc, mspan, drift);

	        //FileReader log = new FileReader("C:/CutsDEV/data/log.txt");
	        FileReader entryl = new FileReader(PATH+"data/testo.txt");

	        FileReader csl = new FileReader(PATH+"data/cslog.txt");
	        FileReader btsl = new FileReader(PATH+"data/btslog.txt");
	        FileReader tdpl = new FileReader(PATH+"data/tdplog.txt");
	        String characters = "";


	        //int car;
	        //while ((car = log.read()) != -1) {
				//System.out.println("scorro");
			//	characters = characters+((char)car);
			//}
			//logtextarea.append(characters);
			//logtextarea.append("\n");
			//characters = "";

			entrylogtextarea.setText("");;
			cslogtextarea.setText("");;
			btslogtextarea.setText("");;
    		tdplogtextarea.setText("");;
			char[] data =null;
			File fi = new File (PATH+"data/testo.txt");

			try {
				  FileReader fin = new FileReader (fi);
				  int filesize = (int) fi.length();
				  data = new char[filesize];
				  fin.read(data, 0, filesize);
				}
				catch (FileNotFoundException ex) {
				   System.err.println(ex);
				}
				catch (IOException ex) {
				  System.err.println(ex);
				}

			entrylogtextarea.setText(new String (data));

			int car2;
			while ((car2 = csl.read()) != -1) {
				//System.out.println("curve seg");
				characters = characters+((char)car2);
			}
			cslogtextarea.append(characters);
			cslogtextarea.append("\n");
			characters = "";

			int car3;
			while ((car3 = btsl.read()) != -1) {
				//System.out.println("base topic");
				characters = characters+((char)car3);
			}
			btslogtextarea.append(characters);
			btslogtextarea.append("\n");
			characters = "";

			int car4;
			while ((car4 = tdpl.read()) != -1) {
				//System.out.println("topic dev");
				characters = characters+((char)car4);
			}
			tdplogtextarea.append(characters);
			tdplogtextarea.append("\n");
			characters = "";

	   	}
	   	catch (Throwable t){
			System.err.println(t);
		}


    }

    private static void initLookAndFeel() {
        String lookAndFeel = null;

        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                   + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + lookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + lookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        initLookAndFeel();

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("SwingApplication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingApplication app = new SwingApplication();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        frame.getContentPane().add(contents, BorderLayout.CENTER);


        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
