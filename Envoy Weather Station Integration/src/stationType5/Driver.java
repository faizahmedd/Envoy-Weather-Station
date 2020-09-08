package stationType5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Timer;
import java.util.TimerTask;

import weatherStation.ISSAbstract;
import weatherStation.WeatherStats;

/**
 * Driver
 */
public class Driver extends ISSAbstract {
    /** Location of wind sensor in array. */
    private static final int WIND = 4;

    /** Location of outside temperature sensor in array. */
    private static final int TEMPERATURE = 3;

    /** Location of outside humidity sensor in array. */
    private static final int HUMIDITY = 1;

    /** Location of barometer in array. */
    private static final int BAROMETER = 0;

    /** Location of rain sensor in array. */
    private static final int RAIN = 2;

    /** Location of inside humidity sensor in array. */
    private static final int HUMID_IN = 5;

    /** Location of inside temperature sensor in array. */
    private static final int TEMP_IN = 6;

    /** Debug flag. */
    private static boolean debug = false;

    /** GUI of program. */
    private static GUI theGUI;

    /** File object to the outside data file. */
   private static File Outside = new File("OutSide.txt");

    /** File object to the inside data file. */
    private static File Inside = new File("InSide.txt");

    /** Array to hold all sensor data. Sensor order is currently determined by GUI.updateDisplay()*/
    private Sensor[] myData = new Sensor[7];

    private long entryTime;

    /** WeatherStats class */
    private WeatherStats myWeatherStats;

	/**
	 * ID belongs to a station
	 */
	private int stationID;

	private Timer myTimer;

    public Driver() {
    	generateData();
    	myWeatherStats = new WeatherStats(stationID);

    }
    /*
    /**
     * Main program that runs everything.
     *
     * @param args
     */

//    public static void main(String[] args) {
//        generateData();
//
//        try {
//            theGUI = new GUI();
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//
//        try {
//            new Driver().run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Use the random generator to generate data set.
     */
    private static void generateData() {
        RandomSensorDataGenerator generate = new RandomSensorDataGenerator();
        generate.createISSData();
        generate.createEnvoyData();
    }

    /**
     * Read a line from a txt file, data is split by "," Create a new object of the
     * sensor class file with data.
     *
     * @param theFile
     * @throws Exception
     */
    public void runOriginal() {
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
		        try {
		            final BufferedReader rdrISS = new BufferedReader(new FileReader(Outside)); // reader for outside data
		            final BufferedReader rdrEnv = new BufferedReader(new FileReader(Inside)); // reader for inside data
		            String dataISS; // data lines
		            String dataEnv;
		            while ((dataISS = rdrISS.readLine()) != null && (dataEnv = rdrEnv.readLine()) != null) {
		                final String[] arrDataISS = dataISS.split(",", 7); // splitting data from line
		                final String[] arrDataEnv = dataEnv.split(",", 3);
		                for (int i = 1; i < dataISS.length(); i++) { // assign outside data to array
		                    switch (i) {
		                        case 1:
		                            myData[WIND] = new Wind(Integer.valueOf(arrDataISS[i]), Integer.valueOf(arrDataISS[i + 1]));
		                            break;
		                        case 3:
		                            myData[TEMPERATURE] = new Temperature(Double.valueOf(arrDataISS[i])/10);
		                            break;
		                        case 4:
		                            myData[HUMIDITY] = new Humidity(Double.valueOf(arrDataISS[i])/10);
		                            break;
		                        case 5:
		                            myData[BAROMETER] = new Barometer(Double.valueOf(arrDataISS[i])/10);
		                            break;
		                        case 6:
		                            myData[RAIN] = new Rain(Double.valueOf(arrDataISS[i])/100);
		                            break;
		                    }
		                }
		                for (int i = 1; i < dataEnv.length(); i++) { // assign inside data to array
		                    switch (i) {
		                        case 1:
		                            myData[TEMP_IN] = new TempIn(Double.valueOf(arrDataEnv[i])/10);
		                            break;
		                        case 2:
		                            myData[HUMID_IN] = new HumidIn(Double.valueOf(arrDataEnv[i])/10);
		                    }
		                }
		                myWeatherStats.processString(generateString());

		                synchronized (this) {
		                    this.wait(2000);
		                }

		                /*
		                theGUI.updateDisplay(myData);
		                //theGUI.updateDisplay(baroLabel, humiLabel, rainLabel, tempLabel, winLabel, humidIn, tempIn);
		                */
		                if (debug) { // if the program is in debug mode, stop after the first read file iteration
		                    break;
		                }
		            }
		            rdrISS.close();
		            rdrEnv.close();
		        } catch (final Exception e) {
		            e.printStackTrace();
		        }

			}
    	};

    	myTimer = new Timer();
    	myTimer.schedule(task, 0, 2000);

    }


    /**
     * Intended to use for JUnit testing. Generate data, initialize the GUI and pass 1 line of data
     * to the GUI for it to update, then STOP the program.
     */
    public int debug() {
    	int result = 1;
        generateData();
        debug = true;
        try {
            theGUI = new GUI();
            run();
        } catch (Exception e) {
        	result = 0;
            e.printStackTrace();
        }
        debug = false;
        return result;
    }

	@Override
	public String generateString() {
    	StringBuilder sb = new StringBuilder();
		String sensor1 = "Temp Out";
		String sensor2 = "Temp In";
		String sensor3 = "Hum Out";
		String sensor4 = "Hum In";
		String sensor5 = "Barometer";
		String sensor6 = "Rain Rate";
		String sensor7 = "Chill";
		String sensor8 = "WindSpeed";
		String sensor9 = "WindDirection";
		String sensor10 = "Rain Mo";
		String sensor11 = "UV";
		sb.append(sensor1 + ":" + entryTime + ":" + myData[3].getData() + "|");
		sb.append(sensor2 + ":" + entryTime + ":" + myData[6].getData() + "|");
		sb.append(sensor3 + ":" + entryTime + ":" + myData[1].getData() + "|");
		sb.append(sensor4 + ":" + entryTime + ":" + myData[5].getData() + "|");
		sb.append(sensor5 + ":" + entryTime + ":" + myData[0].getData() + "|");
		sb.append(sensor6 + ":" + entryTime + ":" + myData[2].getData() + "|");
		sb.append(sensor7 + ":" + entryTime + ":" + 0 + "|");
		sb.append(sensor8 + ":" + entryTime + ":" + myData[4].getData() + "|");
		sb.append(sensor9 + ":" + entryTime + ":" + ((Wind)myData[4]).getMyDirection() + "|");
		sb.append(sensor10 + ":" + entryTime + ":" + 0 + "|");
		sb.append(sensor11 + ":" + entryTime + ":" + 0 + "|");
    	return sb.toString();
	}

	public void insertData(int val) {
        myData[WIND] = new Wind(val, val);
        myData[TEMPERATURE] = new Temperature(Double.valueOf(val));
        myData[HUMIDITY] = new Humidity(Double.valueOf(val));
        myData[BAROMETER] = new Barometer(Double.valueOf(val));
        myData[RAIN] = new Rain(Double.valueOf(val));
        myData[TEMP_IN] = new TempIn(Double.valueOf(val));
        myData[HUMID_IN] = new Humidity(Double.valueOf(val));
	}

	public void setStationID(int ID) {
		stationID = ID;
	}

	public int getStationID() {
		return stationID;
	}
	@Override
	public WeatherStats getMyWeatherStats() {
		return myWeatherStats;
	}

	public void setMyWeatherStats(WeatherStats ws) {
		myWeatherStats = ws;
	}
	@Override
	public long getLastEntryTime() {
		return entryTime;
	}

	public void setLastEntryTime(long newEntryTime) {
		entryTime = newEntryTime;
	}

	@Override
	public void run() {
        try {
            runOriginal();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}