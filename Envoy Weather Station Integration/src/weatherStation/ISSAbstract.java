package weatherStation;
import java.util.ArrayDeque;

/**
 * @author Group 4.
 *
 */
public abstract class ISSAbstract {

	//Fields

	/** An Array that stores history of calculations  */
	public ArrayDeque<String> WeatherHistory;


	/** a custom class that stores every weather statistic and can display it on our GUI */
	public WeatherStats myWeatherStats;


	//private int clockTick;

	private long creationTime;

	private int stationID;

	private boolean powerSwitch;


	//Methods

	/** Generate string data
   * @returns String
  **/
	public abstract String generateString();


	public abstract WeatherStats getMyWeatherStats();

	public abstract long getLastEntryTime();



    /**This method returns a boolean of whether or not
     * the ISS class is turned on.
     * @return boolean, 0 if off, 1 if on.
     * */
    public boolean getPowerSwitch() {
        return powerSwitch;
    }


    /**This method turns the ISS off and on.
     * @return double[] the array containing maximum values.
     * */
    public void setPowerSwitch(boolean power) {
        powerSwitch = power;
    }


    /**This run method of the iss thread.
     * The methods that will be performed during the
     * iss thread.
     * */
    public abstract void run();


}