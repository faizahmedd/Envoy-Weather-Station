package stationType2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import weatherStation.ISSAbstract;
import weatherStation.WeatherStats;

public class Station1ISS extends ISSAbstract{
	public static final int RAINFALL_UPDATE_INTERVAL =  1; // 20 to 24 seconds per specification.
	public static final int RAINRATE_UPDATE_INTERVAL = 1; // 20 to 24 seconds per specification.
	public static final int TEMP_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specification.
	public static final int WINDCHILL_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specification.
	public static final int WINDDIRECTION_UPDATE_INTERVAL = 1; // 2.5 to 3 seconds per specification.
	public static final int WINDSPEED_UPDATE_INTERVAL = 1; // 2.5 1to 3 seconds per specification.
	public static final int HEATINDEX_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specifi-cation.
	public static final int HUMIDITY_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specification.
	public static final int DEWPOINT_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specification.

	/*
	 * Delay to before sensors start.
	 */
	public static final int SENSOR_INITIAL_DELAY = 0;

	/*
	 * Delay to before rate calculations start.
	 * Allows sensor data to populate before any rates are calculated on data.
	 */
	public static final int RATE_INITIAL_DELAY = 0;

	/*
	 * WindSensor's Cable Length, which affects the max wind speed allowed.
	 */
	public static final int WINDSENSOR_LENGTH = 30;

	/*
	 * Files used to transmit serialized data.
	 */
	public static final File RAINFALL_FILE = new File("rainfallSerializedOutput.txt");
	public static final File RAINRATE_FILE = new File("rainfallRateSerializedOutput.txt");
	public static final File TEMPERATURE_FILE = new File("temperatureSerializedOutput.txt");
	public static final File HUMIDITY_FILE = new File("humiditySerializedOutput.txt");
	public static final File WINDCHILL_FILE = new File("windChillSerializedOutput.txt");
	public static final File WINDDIRECTION_FILE = new File("windDirectionSerializedOutput.txt");
	public static final File WINDSPEED_FILE = new File("windSpeedSerializedOutput.txt");
	public static final File DEWPOINT_FILE = new File("dewpointSerializedOutput.txt");
	public static final File HEATINDEX_FILE = new File("heatIndexSerializedOutput.txt");

	TemperatureSensor temp;
	WindSensor windSpeed;
	WindDirection windDirection;
	RainSensor rain;
	HumiditySensor humidity;
	WindChill windChill;
	RainfallRate rainfallRate;
	DewPoint dewPoint;
	HeatIndex heatIndex;

	private static List<String> dataStrings;
	private WeatherStats myStats;
	private int id;
	private static long entryTime;
	private Timer myTimer;

	public Station1ISS() {
		//System.out.println("Hello");
		dataStrings = new ArrayList<>();
		myStats = new WeatherStats(1);
	}

	public void setStationId(int id) {
		this.id = id;
	}

/*
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello!");
		Station1ISS st = new Station1ISS();
		st.run();
		while (true) {
			Thread.sleep(500);
			System.out.println("Im generating");
			st.getMyWeatherStats().processString(st.generateString());
		}
	}
*/


	public void insertData(String s) {
		dataStrings.add(s);
	}

	@Override
    public String generateString() {
        StringBuilder sb = new StringBuilder();

        /*
        for (String s : dataStrings) {
            sb.append(s);
            sb.append("|");
        }
        */

        entryTime = (int)(System.currentTimeMillis() / 1000);
		sb.append("OutterTemp:" + entryTime + ":" + temp.getTemp() + "|");
		sb.append("Barometric:" + entryTime + ":" + 0.0 + "|");
		sb.append("OutterHum:" + entryTime + ":" + 0.0 + "|");
		sb.append("RainDaily:" + entryTime + ":" + rain.getRain() + "|");
		sb.append("InnerTemp:" + entryTime + ":" + 0.0 + "|");
		sb.append("InnerHum:" + entryTime + ":" + 0.0 + "|");
		sb.append("Chill:" + entryTime + ":" + 0.0 + "|");
		sb.append("RainMO:" + entryTime + ":" + 0.0 + "|");
		sb.append("WindS:" + entryTime + ":" + windSpeed.getWindSpeed() + "|");
		sb.append("WindDir:" + entryTime + ":" + windDirection.calcWindDirection() + "|");
        //System.out.println(sb);
        return sb.toString();
    }

	@Override
	public WeatherStats getMyWeatherStats() {
		return myStats;
	}

	@Override
	public long getLastEntryTime() {
		// TODO Auto-generated method stub
		return entryTime;
	}

	@Override
	public void run() {
		//ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		temp = new TemperatureSensor(TEMPERATURE_FILE);
		//scheduledExecutorService.scheduleAtFixedRate(temp, SENSOR_INITIAL_DELAY, TEMP_UPDATE_INTERVAL, TimeUnit.SECONDS);

		windSpeed = new WindSensor(WINDSPEED_FILE, WINDSENSOR_LENGTH);
		//scheduledExecutorService.scheduleAtFixedRate(windSpeed, SENSOR_INITIAL_DELAY, WINDSPEED_UPDATE_INTERVAL, TimeUnit.SECONDS);

		windDirection = new WindDirection(WINDDIRECTION_FILE);
		//scheduledExecutorService.scheduleAtFixedRate(windDirection, SENSOR_INITIAL_DELAY, WINDDIRECTION_UPDATE_INTERVAL, TimeUnit.SECONDS);

		rain = new RainSensor(RAINFALL_FILE);
		//scheduledExecutorService.scheduleAtFixedRate(rain, SENSOR_INITIAL_DELAY, RAINFALL_UPDATE_INTERVAL, TimeUnit.SECONDS);

		humidity = new HumiditySensor(HUMIDITY_FILE);
		//scheduledExecutorService.scheduleAtFixedRate(humidity, SENSOR_INITIAL_DELAY, HUMIDITY_UPDATE_INTERVAL, TimeUnit.SECONDS);

		windChill = new WindChill(WINDCHILL_FILE, temp.getSet(), windSpeed.getSet());
		//scheduledExecutorService.scheduleAtFixedRate(windChill, RATE_INITIAL_DELAY, WINDDIRECTION_UPDATE_INTERVAL, TimeUnit.SECONDS);

		rainfallRate = new RainfallRate(RAINRATE_FILE, rain.getSet());
		//scheduledExecutorService.scheduleAtFixedRate(rainfallRate, RATE_INITIAL_DELAY, RAINRATE_UPDATE_INTERVAL, TimeUnit.SECONDS);

		dewPoint = new DewPoint(DEWPOINT_FILE, temp.getSet(), humidity.getSet());
		//scheduledExecutorService.scheduleAtFixedRate(dewPoint, RATE_INITIAL_DELAY, DEWPOINT_UPDATE_INTERVAL, TimeUnit.SECONDS);

		HeatIndex heatIndex = new HeatIndex(HEATINDEX_FILE, temp.getSet(), humidity.getSet());
		//scheduledExecutorService.scheduleAtFixedRate(heatIndex, RATE_INITIAL_DELAY, HEATINDEX_UPDATE_INTERVAL, TimeUnit.SECONDS);

		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				//System.out.println(generateString());
				getMyWeatherStats().processString(generateString());
			}

		};

		myTimer = new Timer();
		myTimer.schedule(task, 0, 2000);

	}


	public static <T> void insertData(DataPacket<T> data) {
		String sensor = data.getSensor();
		entryTime = System.currentTimeMillis() / 1000;
		T value = data.getValue();
		dataStrings.add(sensor + ":" + entryTime + ":" + value);
	}

	public int getStationId() {
		return this.id;
	}


}
