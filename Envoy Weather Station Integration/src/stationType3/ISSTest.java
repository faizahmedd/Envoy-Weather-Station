package stationType3;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.jupiter.api.Test;

class ISSTest {
	private static final String TEST_DATA = "29 Southwest 64 44 51 1";

	private ISSg3 mySensor;

	@Before
	public void setUp() {
		mySensor = new ISSg3(TEST_DATA);
	}

	@Test
	void testGetWindSpeed() {
		mySensor = new ISSg3(TEST_DATA);
		assertEquals("29", mySensor.getWindSpeed());
	}

	@Test
	void testGetTemp() {
		mySensor = new ISSg3(TEST_DATA);
		assertEquals("64", mySensor.getTemp());
	}

	@Test
	void testGetHumidity() {
		mySensor = new ISSg3(TEST_DATA);
		assertEquals("44", mySensor.getHumidity());
	}

	@Test
	void testGetBar() {
		mySensor = new ISSg3(TEST_DATA);
		assertEquals("51", mySensor.getBar());
	}

	@Test
	void testGetDailyRain() {
		mySensor = new ISSg3(TEST_DATA);
		assertEquals("1", mySensor.getDailyRain());
	}
}

