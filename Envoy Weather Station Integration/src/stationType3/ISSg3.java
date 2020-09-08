package stationType3;

import java.util.Timer;
import java.util.TimerTask;

import weatherStation.ISSAbstract;
import weatherStation.WeatherStats;

public class ISSg3 extends ISSAbstract {

	private static final int PARAMETERS = 6;

	/** Instance field to store a line of data. */
	private String myData;

	/** Wind Speed. */
	private String myWindSpeed;


	/** Temperature. */
	private String myTemperature;

	/** Humidity. */
	private String myHumidity;

	/** Barometric Pressure */
	private String myBar;

	/** Daily Rain fall */
	private String myRainFall;

	private long entryTime;// = System.currentTimeMillis();

	private WeatherStats myWeatherStats;

	private Timer myTimer;

	private int stationID;

	/**
	 * Constructor to initialize a line of Sensor Data. [0] = WindSpeed, [1] =
	 * myWindDirection, [2] = myTemp, [3] = Humidity [4] = Barometric Pressure. ADD
	 * MORE AND DOCUMENT HERE AS NEEDED.
	 *
	 * @param theData A String representing the line of data.
	 * @throws IllegalArugmentException if the file contains illegal number of
	 *                                  parameters.
	 */
	public ISSg3() {
		myWeatherStats = new WeatherStats(stationID);
		myTimer = new Timer();
	}

	/**
	 * Strictly for test purposes. Not used for integrating the weather station.
	 *
	 * @param theData
	 */
	public ISSg3(String theData) {
		myData = theData;
		parseData(myData);
	}

	/**
	 * Strictly for test purposes. Not used for integrating the weather station.
	 *
	 * @param myData2
	 */
	private void parseData(String myData2) {
		String[] data = myData2.split(" ");
		int length = data.length;
		if (PARAMETERS == length) {
			myWindSpeed = data[0];
			myTemperature = data[2];
			myHumidity = data[3];
			myBar = data[4];
			myRainFall = data[5];
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Sets the station id of this weather station.
	 *
	 * @param id The new ID.
	 */
	public void setStationID(int id) {
		stationID = id;
	}

	/** An access method wind speed */
	public String getWindSpeed() {
		return myWindSpeed;
	}

	/** An access method for temperature. */
	public String getTemp() {
		return myTemperature;
	}

	/** An access method for humidity */
	public String getHumidity() {
		return myHumidity;
	}

	/** An access method for Barometric Pressure */
	public String getBar() {
		return myBar;
	}

	/** An access method for daily rain fall */
	public String getDailyRain() {
		return myRainFall;
	}

	@Override
	public String generateString() {
		StringBuilder sb = new StringBuilder();

		entryTime = System.currentTimeMillis() / 1000;

		sb.append("OutterTemp:" + entryTime + ":" + 0.0 + "|");
		sb.append("Barometric:" + entryTime + ":" + Sensors.getBar() + "|");
		sb.append("OutterHum:" + entryTime + ":" + 0.0 + "|");
		sb.append("RainDaily:" + entryTime + ":" + Sensors.getRain() + "|");
		sb.append("InnerTemp:" + entryTime + ":" + Sensors.getTemperature() + "|");
		sb.append("InnerHum:" + entryTime + ":" + Sensors.getHumidity() + "|");
		sb.append("Chill:" + entryTime + ":" + 0.0 + "|");
		sb.append("RainMO:" + entryTime + ":" + 0.0 + "|");
		sb.append("WindS:" + entryTime + ":" + Sensors.getWindSpeed() + "|");
		sb.append("WindDir:" + entryTime + ":" + 0.0 + "|");


		return sb.toString();
	}

	@Override
	public WeatherStats getMyWeatherStats() {
		return myWeatherStats;
	}

	@Override
	public void run() {
		// Set up task for timer
		TimerTask task = new TimerTask() {

			private String output;

			@Override
			public void run() {
				entryTime = System.currentTimeMillis() / 1000;
				myWeatherStats.processString(generateString());
				//System.out.println(output);
				//System.out.println();
				//myWeatherStats.getBySensor("InnerTemp");
			}
		};

		myTimer = new Timer();

		myTimer.schedule(task, 0, 2000);
	}

	@Override
	public long getLastEntryTime() {
		return entryTime;
	}

}
