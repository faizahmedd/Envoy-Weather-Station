package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stationType5.Driver;
import weatherStation.WeatherStats;


public class WeatherStation5Test {

	private Driver driver;
	private WeatherStats myWS;
	private long newEntryTime;

	@BeforeEach
	public void setup() {
		driver = new Driver();
		myWS = new WeatherStats(3);
		newEntryTime = System.currentTimeMillis() / 10000;

	}
	@Test
	public void generateStringTest() {

		driver.insertData(1);
		String str = driver.generateString();
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
		sb.append(sensor1 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor2 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor3 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor4 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor5 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor6 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor7 + ":" + 0 + ":" + 0 + "|");
		sb.append(sensor8 + ":" + 0 + ":" + 1.0 + "|");
		sb.append(sensor9 + ":" + 0 + ":" + 1 + "|");
		sb.append(sensor10 + ":" + 0 + ":" + 0 + "|");
		sb.append(sensor11 + ":" + 0 + ":" + 0 + "|");
		assertEquals(sb.toString(), str);
	}
/*
	@Test
	public void runTest() throws InterruptedException {
		driver.runOriginal();
		for (int i = 0; i < 5; i++) {
			Thread.sleep(100);
			assertTrue(driver.generateString() != null);
		}
	}
*/
	@Test
	public void getStationIDTest() {
		driver.setStationID(4);
		assertEquals(4, driver.getStationID());
	}

	@Test
	public void getWeatherStatsTest() {
		driver.setMyWeatherStats(myWS);
		assertEquals(myWS, driver.getMyWeatherStats());
	}

	@Test
	public void getLastEntryTimeTest() {
		driver.setLastEntryTime(newEntryTime);
		assertEquals(newEntryTime, driver.getLastEntryTime());
	}

}