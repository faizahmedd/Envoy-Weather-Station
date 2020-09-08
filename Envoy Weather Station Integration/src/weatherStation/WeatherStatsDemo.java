package weatherStation;

import java.util.List;

import weatherStation.WeatherStats.SensorDataN;
import weatherStation.WeatherStats.SensorDataT;

public class WeatherStatsDemo {
	private static final int NO_RUNS = 10 /*50*/;
	
	public static void main(String args[]) {
		String sensor1 = "Thermometer";
		String sensor2 = "Water Pressure Gauge";
		String sensor3 = "Yeet";
		WeatherStats ws = new WeatherStats(0);
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < NO_RUNS; i++) { 
			sb.append(sensor1);
			sb.append(":");
			sb.append(i);
			sb.append(":");
			sb.append((float) Math.random() * 100);
			sb.append("|");
			
			sb.append(sensor2);
			sb.append(":");
			sb.append(i);
			sb.append(":");
			sb.append((float) Math.random() * 100);
			sb.append("|");
			
			sb.append(sensor3);
			sb.append(":");
			sb.append(i);
			sb.append(":");
			sb.append((float) Math.random() * 69);
			sb.append("|");
			
			ws.processString(sb.toString());
			// System.out.println("Correctly organized datastring: " + sb.toString());
			sb.setLength(0);
		}
		
		List<SensorDataN> list1 = ws.getByTime(0);
		for (SensorDataN sd : list1) { 
			System.out.printf("Sensor: %s\nData: %f\n", sd.getSensor(), sd.getData());
		}
		
		List<SensorDataT> list2 = ws.getBySensor(sensor1);
		for (SensorDataT sd : list2) { 
			System.out.printf("Time: %dms\nData: %f\n", sd.getTimestamp(), sd.getData());
		}
	}
}