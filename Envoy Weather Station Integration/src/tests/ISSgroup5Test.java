package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stationType3.ISSg3;
/**
 * TCSS 360
 * 
 * Spring 2020
 * 
 * 
 */
import stationType4.ISSgroup5;
import stationType4.Sensor;

/**
 * ISSTest class
 * 
 * @author Kennedy Ganda
 *
 */

class ISSgroup5Test {
	
	@BeforeEach
	public void setUp() throws Exception{
		
		
	}

	ISSgroup5 iss = new ISSgroup5();

	Sensor sn = new Sensor();

	
	@SuppressWarnings("static-access")
	@Test
	void testGetPowerSwitch() {
		ISSgroup5 iss = new ISSgroup5();
		iss.setPowerSwitch(false);
		assertEquals(false, iss.getPowerSwitch());
		
		
	}
	
	
	//@Test
	//void testRun() throws InterruptedException {
		//ISSgroup5 iss = new ISSgroup5();
		//iss.run();
		//assertTrue(iss.generateString()==null);
		
	//}
	
	
	
	
	@Test
	void  TestgetLastEntryTime() {
		
		assertEquals(0, iss.getLastEntryTime());
		
	}
	
	
	@Test
	void  TestgetMyWeatherStats() {
		
		assertTrue(iss.getMyWeatherStats() != null);
		
		
	}
	
	 
	 @Test
		void testGetTheWind() {
	
			assertEquals(10, sn.getTheWind());
		}

		@Test
		void testGetTheTemperature() {
			
			assertEquals(60, sn.getTheTemperature());
		}

		@Test
		void testGetHumidity() {
			
			assertEquals(44, sn.getTheHumidity());
		}

		
		@Test
		void testGetDailyRain() {
			
			assertEquals(0, sn.getTheRain());
		}
		
		@Test
		void testSensorThread() throws InterruptedException {
			ISSgroup5.sensorThread(sn);
		}
		
		@Test
		void testIssThread() {
			
			ISSgroup5.issThread(iss);
		}
		

}
