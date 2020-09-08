package stationType4;

/** TCSS 360
 *
 * */
/**
 * Program Description: Sensor class generates data for the V2SensorSuite.
 * @author
 *
 *
 * @version 4.10.20
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import weatherStation.ISSAbstract;
import weatherStation.WeatherStats;

public class ISSgroup5 extends ISSAbstract implements Runnable, Serializable{
//public class ISS  implements Runnable, Serializable
  private long entryTime;// = System.currentTimeMillis();
  private int stationID;
  private WeatherStats myWeatherStats;
  private String help;
  private Date date;

  private Timer myTimer = new Timer();

  ////////////////////////////////////



  private static double theTemperature;
    private static double theHumidity;
    private static double theWind;
    private static double theRain;
    protected static double[] myArr = new double[4];
    private static double minArray[] = new double[4];
    private static double maxArray[] = new double[4];
    private static boolean powerSwitch;
    private static final int TEMP_TYPE = 0;
    private static final int HUM_TYPE = 1;
    private static final int WIND_TYPE = 2;
    private static final int RAIN_TYPE = 3;

    //// CHANGE THIS TO A FILE PATH ON YOUR LOCAL REPO
     private static File dataPack = new File("/Users/End User/Desktop/weatherData.txt");
    /**Constructor for the ISS class for testing purposes only.
     *
     * */
    public ISSgroup5() {
      date = new Date();
      minArray[TEMP_TYPE] = 60;
      minArray[HUM_TYPE] = 44;
      minArray[WIND_TYPE] = 10;
      minArray[RAIN_TYPE] = 0;
      maxArray[TEMP_TYPE] = 60;
      maxArray[HUM_TYPE] = 44;
      maxArray[WIND_TYPE] = 10;
      maxArray[RAIN_TYPE] = 0;
      /////////////////////////////////

      theTemperature = minArray[TEMP_TYPE] = 60;
      theTemperature = maxArray[TEMP_TYPE] = 60;
      theHumidity = minArray[HUM_TYPE] = 44;
      theHumidity = maxArray[HUM_TYPE] = 44;
      theWind = minArray[WIND_TYPE] = 10;
      theWind = maxArray[WIND_TYPE] = 10;
      theRain = minArray[RAIN_TYPE] = 0;
      theRain = maxArray[RAIN_TYPE] = 0;




      powerSwitch = false;
    myWeatherStats = new WeatherStats(stationID);
    }


    /**This method returns a boolean of whether or not
     * the ISS class is turned on.
     * @return boolean, 0 if off, 1 if on.
     * */

    @Override
	public  boolean getPowerSwitch() {
      return powerSwitch;
    }
    /**This method turns the ISS off and on.
     * @return double[] the array containing maximum values.
     * */


    /**This method defines the sensor threads.
     * It creates a new thread, and passes the
     * sensors to it. It then starts the data generation.
     * */

  public static void sensorThread(Sensor sensors) {
         ReentrantLock lock = new ReentrantLock();
      lock.lock();
      try {
           Thread t = new Thread(sensors);
             t.start();
            try {
                  Thread.sleep(2500);
              } catch (InterruptedException e) {
              }
        }
      finally {
        lock.unlock();
      }
     }
   /**This method defines the sensor threads.
     * It creates a new thread, and passes the
     * sensors to it. It then starts the data generation.
     * */

  public static  void issThread(ISSgroup5 iss) {
         ReentrantLock lock = new ReentrantLock();
      lock.lock();
      try {
        Thread t = new Thread(iss);
          t.start();
        }
      finally {
        lock.unlock();
      }
     }
    /**This run method of the iss thread.
     * The methods that will be performed during the
     * iss thread.
     * */
  @Override
  public void run() {
	TimerTask task = new TimerTask() {

	@Override
	public void run() {
		 //FileWriter writer = new FileWriter(dataPack);
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(dataPack));
	        //Scanner console = new Scanner(System.in);
	          //Turns on the power
	        boolean power = true;
	        ISSgroup5 myISS = new ISSgroup5();
	        //int test controls the number of times the while loop will run.
	        int test = 4;
	        writer.write("Date: " + java.time.LocalDate.now());
            Sensor sensorSuite = new Sensor(myArr);
            //myArr = sensorSuite.getTheArray();
            sensorThread(sensorSuite);
            //issThread(myISS);
            writer.write("Time: " + java.time.LocalTime.now() + "data: ");
            writer.write(myArr[0] + " "+ myArr[1] +" "+ myArr[2]+" "+ myArr[3]);
            writer.write("Daily Maximum and Minimums: ");
            writer.write("Temp min: " + minArray[0]);
            writer.write("Temp max: " + maxArray[0]);
            writer.write("Hum min: " + minArray[1]);
            writer.write("Hum max: " + maxArray[1]);
            writer.write("Wind min: " + minArray[2]);
            writer.write("Wind max: " + maxArray[2]);
            writer.write("Rain min: " + minArray[3]);
            writer.write("Rain max: " + maxArray[3]);
	        writer.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    updateData();

	    date = new Date();
	    String outPut;
	    outPut = " ";


	    outPut += date.toString() + "\n";
	    outPut += theHumidity + "\n";
	    outPut += theRain + "\n";
	    outPut += theTemperature + "\n";
	    outPut += theWind + "\n";

	    //System.out.println(outPut);


	    /////////////////////////
	    entryTime = System.currentTimeMillis()/1000;
	    help = outPut;
	    //System.out.println(generateString());
	    myWeatherStats.processString(generateString());
	    //System.out.println(outPut);
	    //System.out.println();

	}};
	myTimer.schedule(task,  0, 2000);
    //myWeatherStats.getBySensor("InnerTemp");
     }

  //The main method.  If you add code make sure to add it in Runnable mainRun to run with the thread.
  //If you add it outside it will run like a normal main method
     public static void main(String[] args) throws InterruptedException, IOException
     {
        //FileWriter writer = new FileWriter(dataPack);
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataPack));
        //Scanner console = new Scanner(System.in);
          //Turns on the power
        boolean power = true;
        ISSgroup5 myISS = new ISSgroup5();
        //int test controls the number of times the while loop will run.
        int test = 4;
        writer.write("Date: " + java.time.LocalDate.now());
        while(powerSwitch) {
          Sensor sensorSuite = new Sensor(myArr);
          sensorThread(sensorSuite);
          issThread(myISS);
          writer.write("Time: " + java.time.LocalTime.now() + "data: ");
          writer.write(myArr[0] + " "+ myArr[1] +" "+ myArr[2]+" "+ myArr[3]);


          //java.util.concurrent.TimeUnit.MILLISECONDS.sleep(2500);

        }

        writer.write("Daily Maximum and Minimums: ");
        writer.write("Temp min: " + minArray[0]);
        writer.write("Temp max: " + maxArray[0]);
        writer.write("Hum min: " + minArray[1]);
        writer.write("Hum max: " + maxArray[1]);
        writer.write("Wind min: " + minArray[2]);
        writer.write("Wind max: " + maxArray[2]);
        writer.write("Rain min: " + minArray[3]);
        writer.write("Rain max: " + maxArray[3]);
        writer.close();
       //below sets up the socket to talk to the client
        try {


    Socket s = new Socket("localhost", 1999); //localhost captures the ip address of the host
          //reads the file

    BufferedReader br = new BufferedReader(new FileReader(dataPack));

    byte []b = new byte[30];
          String k = br.readLine();
          //Transfers the file to the client
          DataOutputStream dos = new DataOutputStream(s.getOutputStream());
          dos.writeUTF(k);
          System.out.println("Transfer Complete");
         } catch (IOException ie) {
            ie.printStackTrace();
        }

     }
     /**This method captures the new data from the data array
      * and sets the field values and checks for min and max temperatures.
      * Temperature is at index 0, humidity at index 1, wind at index 2,
      * and amount of rainfall at index 3.
      * */

     public void updateData() {
        theTemperature = myArr[0];
      minOrMax(theTemperature, TEMP_TYPE);
      theHumidity = myArr[1];
      minOrMax(theHumidity, HUM_TYPE);
      theWind = myArr[2];
      minOrMax(theWind, WIND_TYPE);
      theRain = myArr[3];
      minOrMax(theRain, RAIN_TYPE);
     }

  /**
   * This method will find the min or max value of the type of data specified for future display
   *
   *
   * @param data  data is the current data being passed to compare
   * @param type  type is a numbering system to tell the type of weather you want to find the Min or Max of:
   *  TEMP_TYPE = 0 HUM_TYPE = 1 WIND_TYPE = 2  RAIN_TYPE = 3
   */
     public static void minOrMax(double data, int type) {
      if(data < minArray[type]) {
        minArray[type] = data;
      } else if(data > maxArray[type]) {
        maxArray[type] = data;
      }
     }



  @Override
public String generateString() {
    StringBuilder sb = new StringBuilder();

    help = help.replace("{", "");
    help = help.replace("}", "");
    help = help.replaceAll("([a-zA-Z])", "");
    help = help.replace(" ", "");
    help = help.replace(":", "");
    help = help.replace("°", "");
    help = help.replace("%", "");
    help = help.replace("\"", "");

    String[] values = help.split("\n");
    String[] hums = values[1].split(",");
    String[] temps = values[3].split(",");
    String[] wind = values[4].split(",");

    entryTime = System.currentTimeMillis() / 1000;


    sb.append ("outterTemp:" + entryTime + ":" + theTemperature + "|");
    sb.append("Barometric:" + entryTime + ":" + 0.0 + "|");
    sb.append("OutterHum:" + entryTime   + ":" + theHumidity + "|");
    sb.append("RainDaily:" + entryTime + ":" + theRain + "|");
    sb.append("InnerTemp:" + entryTime + ":" + 0.0 + "|");
    sb.append("InnerHum:" + entryTime + ":" + 0.0 + "|");
    sb.append("Chill:" + entryTime + ":" + 0.0 + "|");
    sb.append("RainMO:" + entryTime + ":" + 0.0 + "|");
    sb.append("WindS:" + entryTime + ":" + theWind + "|");
    sb.append("WindDir:" + entryTime + ":" + 0.0 + "|");

   // System.out.println(sb);
    return sb.toString();
  }

  public void setStationID(int ID) {
    stationID = ID;
  }

  @Override
public long getLastEntryTime() {
    return entryTime;
  }



  @Override
public WeatherStats getMyWeatherStats() {
    return myWeatherStats;
  }


}


