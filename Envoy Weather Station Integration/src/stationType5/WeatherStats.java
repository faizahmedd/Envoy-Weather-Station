package stationType5;

import java.util.*;

public class WeatherStats {
	// id no. of the corresponding station
	private int weatherStationId; 
	// time stamped data dictionary that stores sensor data
	private Map<Integer, List<SensorDataN>> stationData;
	
	/**
	 * Default constructor for WeatherStats
	 * @param weatherStationId the id of the weather station
	 */
	public WeatherStats (int weatherStationId) {
		this.weatherStationId = weatherStationId;
		reset();
	}
	
	/**
	 * Yields this WeatherStats' station id
	 * @return the id
	 */
	public int getWeatherStationId() { 
		return weatherStationId;
	}
	
	/**
	 * Resets the stored data (erases everything)
	 */
	public void reset() {
		stationData = new HashMap<Integer, List<SensorDataN>>();
	}
	
	/**
	 * Gets all the sensor data corresponding to the given time
	 * @param time the specified timestamp
	 * @return list of SensorDataN for that time
	 */
	public List<SensorDataN> getByTime(Integer time) { 
		if (stationData.isEmpty()) { 
			throw new IllegalArgumentException("No data stored for station.");
		}
		return stationData.get(time);
	}
	
	/**
	 * Returns all data for a particular sensor (for all times)
	 * @param sensor The specified sensor
	 * @return list of SensorDataT objects
	 */
	public List<SensorDataT> getBySensor(String sensor) { 
		if (stationData.isEmpty()) { 
			throw new IllegalArgumentException("No data stored for station.");
		}
		List<SensorDataT> out = new ArrayList<>();
		for (int timestamp : stationData.keySet()) { 
			for (SensorDataN data : stationData.get(timestamp)) { 
				if (data.getSensor().contentEquals(sensor)) { 
					out.add(new SensorDataT(timestamp, data.getData()));
				}
			}
		}
		return out;
	}
	
	/**
	 * Takes in a string of the format:  “sensorType:timestamp:value|sensorType:timestamp:value”
	 * Extrapolates data and stores it
	 * @param data the formatted string
	 */
	public void processString(String data) { 
		if (data.isEmpty()) { 
			throw new IllegalArgumentException("No data received.");
		} 
		String tuples[] = data.split("\\|");
		for (String t : tuples) { 
			try {
				String info[] = t.split(":");
				if (info.length != 3) {
					throw new IllegalArgumentException("Invalid tuple.");
				}
				String sensor = info[0];
				int timestamp = Integer.valueOf(info[1]);
				if (timestamp < 0) { 
					throw new IllegalArgumentException("Negative timestamp.");
				}
				float value = Float.valueOf(info[2]);
				if (!stationData.containsKey(timestamp)) { 
					stationData.put(timestamp, new ArrayList<>());
				}
				stationData.get(timestamp).add(new SensorDataN(sensor, value));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid data/timestamp.");
			}
		}
	}
	
	/**
	 * Helper class, used to store data given a sensor and its data. This is used for storing by time.
	 * @author Egor Maksimenka
	 */
	public static class SensorDataN {
		/** The sensor. */
		private String sensorName;
		/** The data. */
		private float data;
		
		/**
		 * Constructor for SensorDataN
		 * @param sensor the specified sensor
		 * @param data the corresponding data
		 */
		public SensorDataN(String sensor, float data) { 
			this.sensorName = sensor;
			this.data = data;
		}
		
		/**
		 * Yields the sensor name for this data packet
		 * @return the sensor name
		 */
		public String getSensor() { 
			return sensorName;
		}
		
		/**
		 * Yields the data for this data packet
		 * @return the data
		 */
		public float getData() { 
			return data;
		}
	}
	
	/**
	 * Helper class, used to store data given a timestamp and data. This is used for storing by sensor.
	 * @author Egor Maksimenka
	 */
	public static class SensorDataT { 
		/** The time. */
		private int timestamp;
		/** The data. */
		private float data;
		
		/**
		 * Constructor for SensorDataT
		 * @param timestamp the time
		 * @param data the data
		 * @throws IllegalArgumentException() when time is < 0
		 */
		public SensorDataT(int timestamp, float data) { 
			if (timestamp < 0) { 
				throw new IllegalArgumentException("Invalid timestamp.");
			}
			this.timestamp = timestamp;
			this.data = data;
		}
		
		/**
		 * Yields the time for this data packet
		 * @return the time
		 */
		public int getTimestamp() { 
			return timestamp;
		}
		
		/**
		 * Yields the data for this data packet
		 * @return the data
		 */
		public float getData() { 
			return data;
		}
	}
	
}