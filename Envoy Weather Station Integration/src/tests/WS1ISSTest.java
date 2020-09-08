package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stationType2.Station1ISS;

class WS1ISSTest {

	private Station1ISS s1;

	@BeforeEach
	void setUp() throws Exception {
		s1 = new Station1ISS();
	}

	@Test
	void testGetWeatherStats() {
		assertTrue(s1.getMyWeatherStats() != null);
	}

	@Test
	void testGenerateString() {
		s1.insertData("sensor1:0:0.0");
		s1.insertData("sensor2:0:1.0");
		assertEquals("sensor1:0:0.0|sensor2:0:1.0|", s1.generateString());
	}

	@Test
	void testRun() throws InterruptedException {
		s1.run();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(500);
			assertTrue(s1.generateString() != null);
		}
	}
/*
	@Test
	void testSetId() {
		s1.setStationId(1);
		assertEquals(1, s1.getStationId());
	}
*/
	@Test
	void testGetLastEntryTime() {
		assertEquals(0, s1.getLastEntryTime());
	}


}
