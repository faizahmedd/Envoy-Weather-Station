package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import stationType1.ISS;
import stationType2.Station1ISS;
import stationType3.ISSg3;
import stationType4.ISSgroup5;
import stationType5.Driver;
import weatherStation.ISSAbstract;
import weatherStation.WeatherStats;
import weatherStation.WeatherStats.SensorDataN;

public class Console extends JFrame {

	/** Serial Version UID. */
	private static final long serialVersionUID = -2537044402683960271L;
	
	/** The width of this the western panel. */
	private static final int MY_PANEL_WIDTH = 300;

	/** The height of the western panel. */
	private static final int MY_PANEL_HEIGHT = 500;
	
	/** The max number of weather stations this panel can configure. */
	private static final int CONFIG_MAX = 8;

	/** The leftmost panel which holds the graph and wind display. */
	private static JPanel myWestPanel;
	
	/** The configuration panel for the console application. */
	private static JPanel myConfigPanel;
	
	/** The main console panel for the console application. */
	private static JPanel myConsolePanel;
	
	/** The center panel of the gui containing the current data of the sensors .*/
	private static JPanel myDataPanel;
	
	/** The top left panel containing the compass. */
	private static JPanel myCompass;

	/** The labels of the currently displayed stats. */
	private static String[] myLabels;

	/** The currently displayed values. */
	private static String[] myValues;

	/** The buttons for this GUI. */
	private static JButton[] myButtons;

	/** The eastern panel which contains buttons. */
	private static JPanel myEastPanel;
	
	/** The organizer for the main display panels of the application. */
	private static CardLayout myPanelOrg;
	
	/** The container holding all of the display panels of the application. */
	private static Container myContainer;
	
	/** Multi-line area that displays plain text. */
	private static JTextArea myDate, myTime, myTemp, myBaro, myDailyRain, myHumid, myTempIn, myHumidIn, myChill, myRainMO;
	
	/** Format the time with hour and minute. */
	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

	/** Format the date with month and day. */
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd");
	
	/** Immutable data-time object. */
	private static LocalDateTime time = LocalDateTime.now();

	private static LocalDateTime date = LocalDateTime.now();
	
	/** The number of weather stations chosen to be created upon configuration. */
	private static int myConfigSize = 0;
	
	/** Traces the current values of each JComboBox that the user has made on the configuration screen. */
	private static int[] myConfigSelections = new int[5];
	
	/** A list containing all of the instantiated weather stations. (Corresponds with myWeatherStats) */
	private static ArrayList<ISSAbstract> myWeatherStations = new ArrayList<>();
	
	/** A list containing the weather stats of each instantiated weather station. (Corresponds with myWeatherStations) */
	private static ArrayList<WeatherStats> myWeatherStats = new ArrayList<>();
	
	/** A list containing the names of the instantiated weather stations. (Corresponds with my myWeatherStations. */
	private static ArrayList<String> myStationNames = new ArrayList<>();
	
	/** The ArrayList<ISSAbstract> position of the weather station currently being displayed. */
	private static int stationDisplayed = 0;
	
	/** A timer used to monitor the events of this gui. */
	private static Timer myTimer = new Timer();
	
	/** The task used to trigger the gui display to update. */
	private static TimerTask updateTask;
	
	/** Tracks if the 2nd button on the console has been pushed. */
	private static boolean is2nd;
	
	/** This is the graph located in the bottom left corner of the gui. */
	private static Graph myGraph;
	
	/** The array containing the points to be plotted on the graph. */
	private double[] graphArray = new double[24];
	
	/** Indicates which set of data is being displayed on the graph. (Corresponds with myLabels) */
	private static int graphDisplay = 0;
	
	/** The name of the data that is currently being displayed on the graph. */
	private static String graphString = "Temp Out";
	
	// data field initialization
	static {
		myLabels = new String[10];
		myValues = new String[8];
		myGraph = new Graph();
		is2nd = false;
	}

	// label and myValues initialization.
	static {
		myTemp = new JTextArea();
		myBaro = new JTextArea();
		myHumid = new JTextArea();
		myDailyRain = new JTextArea();
		myTempIn = new JTextArea();
		myHumidIn = new JTextArea();
		myChill = new JTextArea();
		myRainMO = new JTextArea();

		myLabels[0] = new String("Temp Out");
		myLabels[1] = new String("Hum Out");
		myLabels[2] = new String("Barometer"); 
		myLabels[3] = new String("Temp In");
		myLabels[4] = new String("Hum In");
		myLabels[5] = new String("Chill");
		myLabels[6] = new String("Daily Rain");
		myLabels[7] = new String("Rain MO");
		myLabels[8] = new String("Wind Speed");
		myLabels[9] = new String("Wind Direction");
		for (int i = 0; i < 8; i++) {
			myValues[i] = "" + i;
		}
	}
	
	private JPanel dataPanel() {
		myDataPanel.add(myTemp);
		myDataPanel.add(myBaro);
		myDataPanel.add(myHumid);
		myDataPanel.add(myTempIn);
		myDataPanel.add(myHumidIn);
		myDataPanel.add(myChill);
		myDataPanel.add(myDailyRain);
		myDataPanel.add(myRainMO);
		
		myTemp.setText(myLabels[0] + "\u00B0" + "F");
		myBaro.setText(myLabels[2] + " in");
		myHumid.setText(myLabels[1] +  "%");
		myDailyRain.setText(myLabels[6] + " in/hr");
		myTempIn.setText(myLabels[3] + "\u00B0" + "F");
		myHumidIn.setText(myLabels[4] + "%");
		myChill.setText(myLabels[5] + "\u00B0" + "F");
		myRainMO.setText(myLabels[7] + " in/hr");
		return myDataPanel;
	}

	// Panel initialization.
	static {
		myWestPanel = new JPanel();
		myEastPanel = new JPanel();
		myDataPanel = new JPanel(new GridLayout(3,3));
		myConsolePanel =  new JPanel(new BorderLayout());
		myConfigPanel = new JPanel(new BorderLayout());
		myCompass = new makeCompass(0,0);
	}

	// Button initialization.
	static {
		myButtons = new JButton[12];
		myButtons[0] = new JButton("TEMP / HEAT");
		myButtons[0].addActionListener(event -> {
			graphDisplay = 0;
			graphString = "Temp Out";
		});
		myButtons[1] = new JButton("2ND / LAMPS");
		myButtons[1].addActionListener(event -> {
			if (is2nd) {
				if (stationDisplayed < myConfigSize - 1) {
					stationDisplayed++;
				} else {
					stationDisplayed = 0;
				}
				is2nd = false;
			} else {
				is2nd = true;
			}
		});
		myButtons[2] = new JButton("HUM / DEW");
		myButtons[2].addActionListener(event -> {
			graphDisplay = 2;
			graphString = "Hum Out";
		});
		myButtons[3] = new JButton("FORECAST / TIME");
		myButtons[4] = new JButton("WIND / CHILL");
		myButtons[4].addActionListener(event -> {
			graphDisplay = 8;
			graphString = "Wind Speed";
		});
		myButtons[5] = new JButton("GRAPH / UNITS");
		myButtons[6] = new JButton("RAINday / SOLAR");
		myButtons[6].addActionListener(event -> {
			graphDisplay = 3;
			graphString = "RainRate";
		});
		myButtons[7] = new JButton("HI/LOW / CLEAR");
		myButtons[8] = new JButton("RAINyr / UV");
		myButtons[9] = new JButton("ALARM / SET");
		myButtons[10] = new JButton("BAR / ET");
		myButtons[10].addActionListener(event -> {
			graphDisplay = 1;
			graphString = "Barometer";
		});
		myButtons[11] = new JButton("DONE");
		myButtons[11].addActionListener(event -> {
			System.exit(0);
		});
	}

	/**
	 * Constructor method for this gui.
	 */
	public Console() {
		myPanelOrg = new CardLayout();
		myContainer = getContentPane();
		myContainer.setLayout(myPanelOrg);
		myContainer.add(configurationPanel());
		myContainer.add(consolePanel());
		
		setPreferredSize(new Dimension(400, 400));
		pack();
		setTitle("Console");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Collects the weather stat data of a weather station to display on
	 * the graph on the console.
	 */
	private void generateGraphData() {
		int lastEntry =  (int) myWeatherStations.get(stationDisplayed).getLastEntryTime();
		int intervalEnd = lastEntry - 48;
		ArrayList<SensorDataN> data = (ArrayList<SensorDataN>) myWeatherStats.get(stationDisplayed).getByTime(lastEntry);
		for (int time = lastEntry, x = 0; time > intervalEnd; time -= 2) {
			data = (ArrayList<SensorDataN>) myWeatherStats.get(stationDisplayed).getByTime(time);
			if (data != null) {
				graphArray[x++] = data.get(graphDisplay).getData();
			}
		}
	}
	
	/**
	 * Creates the console panel used in this gui.
	 * @return The created console panel.
	 */
	private JPanel consolePanel() {
		myWestPanel.setPreferredSize(new Dimension(MY_PANEL_WIDTH, MY_PANEL_HEIGHT));
		myWestPanel.setLayout(new BorderLayout());
		myWestPanel.add(myCompass, BorderLayout.NORTH);
		myWestPanel.add(myGraph, BorderLayout.SOUTH);
		myEastPanel.setPreferredSize(new Dimension(300, 500));
		myEastPanel.setLayout(new GridLayout(6, 2));

		for (int i = 0; i < 12; i++) {
			myEastPanel.add(myButtons[i]);
		}
		
		myConsolePanel.add(myWestPanel, BorderLayout.WEST);
		myConsolePanel.add(dataPanel());
		myConsolePanel.add(myEastPanel, BorderLayout.EAST);
		
		return myConsolePanel;
	}
	
	/**
	 * Creates the configuration panel used in this gui.
	 * @return The created configuration panel.
	 */
	private JPanel configurationPanel() {
		JComboBox<Integer> qtyWS1, qtyWS2, qtyWS3, qtyWS4, qtyWS5;
		JLabel welcome = new JLabel("Configuration Setup");
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setFont(new Font(welcome.getFont().getName(), Font.BOLD, 30));
		
		// Quantity selections
		qtyWS1 = new JComboBox<>();
		qtyWS1.addActionListener(event -> {
			int value = qtyWS1.getItemAt((int) qtyWS1.getSelectedItem());
			myConfigSelections[0] = value;
		});
		qtyWS2 = new JComboBox<>();
		qtyWS2.addActionListener(event -> {
			int value = qtyWS2.getItemAt((int) qtyWS2.getSelectedItem());
			myConfigSelections[1] = value;
		});
		qtyWS3 = new JComboBox<>();
		qtyWS3.addActionListener(event -> {
			int value = qtyWS3.getItemAt((int) qtyWS3.getSelectedItem());
			myConfigSelections[2] = value;
		});
		qtyWS4 = new JComboBox<>();
		qtyWS4.addActionListener(event -> {
			int value = qtyWS4.getItemAt((int) qtyWS4.getSelectedItem());
			myConfigSelections[3] = value;
		});
		qtyWS5 = new JComboBox<>();
		qtyWS5.addActionListener(event -> {
			int value = qtyWS5.getItemAt((int) qtyWS5.getSelectedItem());
			myConfigSelections[4] = value;
		});
		
		for(int x = 0; x <= 8; x++) {
			Integer val = new Integer(x);
			qtyWS1.addItem(val);
			qtyWS2.addItem(val);
			qtyWS3.addItem(val);
			qtyWS4.addItem(val);
			qtyWS5.addItem(val);
		}
		
		// Weather station labels
		JLabel ws1, ws2, ws3, ws4, ws5;
		ws1 = new JLabel("Weather Station Type 1");
		ws1.setHorizontalAlignment(SwingConstants.CENTER);
		ws2 = new JLabel("Weather Station Type 2");
		ws2.setHorizontalAlignment(SwingConstants.CENTER);
		ws3 = new JLabel("Weather Station Type 3");
		ws3.setHorizontalAlignment(SwingConstants.CENTER);
		ws4 = new JLabel("Weather Station Type 4");
		ws4.setHorizontalAlignment(SwingConstants.CENTER);
		ws5 = new JLabel("Weather Station Type 5");
		ws5.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Selection panel creation
		JPanel selectionPanel = new JPanel(new GridLayout(5, 2));
		selectionPanel.add(ws1);
		selectionPanel.add(qtyWS1);
		selectionPanel.add(ws2);
		selectionPanel.add(qtyWS2);
		selectionPanel.add(ws3);
		selectionPanel.add(qtyWS3);
		selectionPanel.add(ws4);
		selectionPanel.add(qtyWS4);
		selectionPanel.add(ws5);
		selectionPanel.add(qtyWS5);
		
		// Just for the create button
		JPanel southPanel = new JPanel();
		JButton createButton = new JButton("Create");

		southPanel.add(createButton);
		
		myConfigPanel.add(selectionPanel);
		myConfigPanel.add(southPanel, BorderLayout.SOUTH);
		myConfigPanel.add(welcome, BorderLayout.NORTH);
		
		createButton.addActionListener(event -> {
			if (isValidCreation()) {
				configureWeatherStations();
				myPanelOrg.next(getContentPane());
				setPreferredSize(new Dimension(900,500));
				myTimer.schedule(updateTask, 3000, 2000);
				pack();
				setTitle(myStationNames.get(stationDisplayed));
				revalidate();
			} else {
				JOptionPane.showMessageDialog(new JFrame(),"You must select at least 1 weather station. You can have at most 8 weather stations total.",
						"Invalid Setup", JOptionPane.ERROR_MESSAGE);
				myConfigSize = 0;
			}

		});
		
		return myConfigPanel;
	}
	
	/**
	 * Calculates the number of weather stations that need created, then configures them
	 * and assigns their respective station id's
	 */
	private void configureWeatherStations() {
		int stationID = 0;
		for (int type = 0; type < myConfigSelections.length; type++) {
			if (myConfigSelections[type] > 0) {
				switch (type) {
					case 0:	for (int x = 0; x < myConfigSelections[type]; x++) {
								ISS station = new ISS();
								station.setStationID(stationID);
								stationID++;
								myWeatherStats.add(station.getMyWeatherStats());
								station.run();
								myWeatherStations.add(station);
								myStationNames.add("Weather Station Type 1 ID: " + stationID);
							}
							break;
					case 1: for (int x = 0; x < myConfigSelections[type]; x++) {
								Station1ISS station = new Station1ISS();
								station.setStationId(stationID);
								stationID++;
								myWeatherStats.add(station.getMyWeatherStats());
								station.run();
								myWeatherStations.add(station);
								myStationNames.add("Weather Station Type 2 ID: " + stationID);
							}
							break;
					case 2: for (int x = 0; x < myConfigSelections[type]; x++) {
								ISSg3 station = new ISSg3();
								station.setStationID(stationID);
								stationID++;
								myWeatherStats.add(station.getMyWeatherStats());
								station.run();
								myWeatherStations.add(station);
								myStationNames.add("Weather Station Type 3 ID: " + stationID);
							}
							break;
					case 3: for (int x = 0; x < myConfigSelections[type]; x++) {
								ISSgroup5 station = new ISSgroup5();
								station.setStationID(stationID);
								stationID++;
								myWeatherStats.add(station.getMyWeatherStats());
								station.run();
								myWeatherStations.add(station);
								myStationNames.add("Weather Station Type 4 ID: " + stationID);
					}
						break;
					case 4:	for (int x = 0; x < myConfigSelections[type]; x++) {
								Driver station = new Driver();
								station.setStationID(stationID);
								stationID++;
								myWeatherStats.add(station.getMyWeatherStats());
								station.run();
								myWeatherStations.add(station);
								myStationNames.add("Weather Station Type 5 ID: " + stationID);
							}
							break;
				}
			}
		}
	}
	
	/**
	 * Finds the max value in a given array.
	 * 
	 * @param array The array to be searched.
	 * @return The max value
	 */
	private double max(double[] array) {
		      double max = 0;
		     
		      for(int i=0; i<array.length; i++ ) {
		         if(array[i]>max) {
		            max = array[i];
		         }
		      }
		      return max;
		   }
	
	/**
	 * Updates the display on the console based on the weather station that is currently
	 * being displayed.
	 */
	private void updateDisplay() {
		WeatherStats currentStats = myWeatherStats.get(stationDisplayed);
		ISSAbstract currentStation = myWeatherStations.get(stationDisplayed);
		List<SensorDataN> data = currentStats.getByTime((int) currentStation.getLastEntryTime());
		//System.out.println(data);
		myTemp.setText(myLabels[0] + "\n" + data.get(0).getData() +  "\u00B0" + "F");
		myBaro.setText(myLabels[2] + "\n" + data.get(1).getData() + " in");
		myHumid.setText(myLabels[1] + "\n" + data.get(2).getData() +"%");
		myDailyRain.setText(myLabels[6] + "\n" +  data.get(3).getData() + " in/hr");
		myTempIn.setText(myLabels[3] + "\n" + data.get(4).getData() + "\u00B0" + "F");
		myHumidIn.setText(myLabels[4] + "\n" + data.get(5).getData() +"%");
		myChill.setText(myLabels[5] + "\n" + data.get(6).getData() +"\u00B0" + "F");
		myRainMO.setText(myLabels[7] + "\n" + data.get(7).getData() +" in/hr");
		myCompass = new makeCompass((int)data.get(8).getData(),(int) data.get(9).getData());
		generateGraphData();
		myGraph.setStats(graphArray, graphString, max(graphArray));
		
		myWestPanel.removeAll();
		myWestPanel.add(myCompass, BorderLayout.NORTH);
		myWestPanel.add(myGraph, BorderLayout.SOUTH);
		pack();
		setTitle(myStationNames.get(stationDisplayed));
		revalidate();
	}
	
	private boolean isValidCreation() {
		boolean answer = false;
		for (int x = 0; x < myConfigSelections.length; x++) {
			myConfigSize += myConfigSelections[x];
		}
		if (myConfigSize > 0 && myConfigSize <= CONFIG_MAX) {
			answer = true;
		}
		return answer;
	}
	
	
	public static void main(final String[] theArgs) {
		Console test = new Console();
		updateTask = new TimerTask() {

			@Override
			public void run() {
				test.updateDisplay();
			}
		};
	}
}
