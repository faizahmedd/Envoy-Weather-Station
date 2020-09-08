package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import stationType1.ISS;


/**
 * A testing class used to test the added functionality to the ISS for project #2
 * 
 * @author Jordan Holland
 */
class TestISSg2 {
	ISS test = new ISS();
	
	@Test
	void testSetPowerSwitch() {
		assertFalse(test.getPowerSwitch(), "Power switch initialized incorrectly");
		test.setPowerSwitch(true);
		assertTrue(test.getPowerSwitch(), "Power switch setting incorrectly");
	}
	
	@Test
	void testSetStationID() {
		test.setStationID(1);
		assertEquals(1, test.getStationID(), "Station ID setting incorrectly");
	}
	
	@Test
	void testSetLastEntryTime() {
		test.setLastEntryTime(1000);
		assertEquals(1000, test.getLastEntryTime(), "Last entry time setting incorrectly");
	}
	
	@Test
	void testGenerateString() {
		StringBuilder gen1 = new StringBuilder();
		test.setLastEntryTime(100);
		gen1.append("Wed May 27 11:50:44 PDT 2020\n");
		gen1.append("{innerHumidty: 11%, outerHumidity: 11%}\n");
		gen1.append("{rainfall: 0\"}\n");
		gen1.append("{outsideTemperature: 42.2°F, insideTemperature: 75.3°F}\n");
		gen1.append("{windSpeed: 10mph, windDirection: 0°}\n");
		assertEquals("OutterTemp:100:42.2|Barometric:100:0.0|OutterHum:100:11|RainDaily:100:0|InnerTemp:100:75.3|InnerHum:100:11|Chill:100:0.0|RainMO:100:0.0|WindS:100:10|WindDir:100:0|",
					test.debugGenerateString(gen1.toString()), "String not generated incorrectly");
	}
	
	@Test
	void testRun() {
		assertEquals(1, test.debugRun(), "Run not working correctly");
	}
}
